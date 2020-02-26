package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null){
            String token = UUID.randomUUID().toString();
            User user = userMapper.findByAccountId(githubUser.getId());
            if (user == null){
                user = new User();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccount_id(githubUser.getId());
                user.setAvatar_url(githubUser.getAvatar_url());
                user.setGmt_create(System.currentTimeMillis());
                user.setGmt_modefied(user.getGmt_create());
                userMapper.insert(user);
            }else {
                user.setToken(token);
                user.setAvatar_url(githubUser.getAvatar_url());
                user.setGmt_modefied(System.currentTimeMillis());
                user.setName(githubUser.getName());
                userMapper.updateUser(user);
            }
            response.addCookie(new Cookie("token",token));
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
