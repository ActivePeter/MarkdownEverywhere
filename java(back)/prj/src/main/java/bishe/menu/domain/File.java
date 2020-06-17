package bishe.menu.domain;

import java.io.Serializable;

public class File implements Serializable {
    private Integer id;
    private Integer user_id;
    private String filname;
    private String filpath;
    private Integer useCoin;    //1表示收费,0表示免费
    private Integer fee;        //收取的费用,默认为0
    private Integer popcount;
    private String title;
    private Integer filetype;   //1表示文本,2表示视频,本项默认为文本

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

    public String getFilname() {
        return filname;
    }

    public void setFilname(String filname) {
        this.filname = filname;
    }

    public String getFilpath() {
        return filpath;
    }

    public void setFilpath(String filpath) {
        this.filpath = filpath;
    }

    public Integer getUseCoin() {
        return useCoin;
    }

    public void setUseCoin(Integer useCoin) {
        this.useCoin = useCoin;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getPopcount() {
        return popcount;
    }

    public void setPopcount(Integer popcount) {
        this.popcount = popcount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String addtime) {
        this.title = addtime;
    }

    public Integer getFiletype() {
        return filetype;
    }

    public void setFiletype(Integer filetype) {
        this.filetype = filetype;
    }
}
