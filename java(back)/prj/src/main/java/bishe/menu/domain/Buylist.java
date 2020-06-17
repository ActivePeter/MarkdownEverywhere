package bishe.menu.domain;

import java.io.Serializable;

public class Buylist implements Serializable {
    private Integer id;
    private Integer user_id;
    private Integer filelist_id;
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getFilelist_id() {
        return filelist_id;
    }

    public void setFilelist_id(Integer filelist_id) {
        this.filelist_id = filelist_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
