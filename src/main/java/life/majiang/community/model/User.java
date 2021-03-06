package life.majiang.community.model;

public class User {
    private Integer id;
    private Long account_id;
    private String name;
    private String token;
    private Long gmt_create;
    private Long gmt_modefied;
    private String avatar_url;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Long getGmt_modefied() {
        return gmt_modefied;
    }

    public void setGmt_modefied(Long gmt_modefied) {
        this.gmt_modefied = gmt_modefied;
    }
}
