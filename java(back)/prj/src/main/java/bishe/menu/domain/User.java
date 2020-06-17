package bishe.menu.domain;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private String tel;
    private String username;
    private String password;
    private Integer coin;
    private Integer isSuperUser;    //默认0
    private Integer isUser;         //默认1
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(Integer isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    public Integer getIsUser() {
        return isUser;
    }

    public void setIsUser(Integer isUser) {
        this.isUser = isUser;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
