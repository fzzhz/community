package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model,
                       HttpServletRequest request){
        Question question = questionMapper.getQuestionByQuestionId(id);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || question == null || !(user.getAccount_id().equals(question.getCreator()))){
            return "redirect:/";
        }
        String title = question.getTitle();
        String description = question.getDescription();
        String tag = question.getTag();
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model,
                            @RequestParam("id") Integer id){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title == ""){
            model.addAttribute("error","问题标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccount_id());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
