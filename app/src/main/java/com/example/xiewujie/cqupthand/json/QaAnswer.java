package com.example.xiewujie.cqupthand.json;

import java.util.List;

public class QaAnswer {
    private String id;
    private String nicname;
    private String photo_thumbnail_src;
    private String gender;
    private String content;
    private String created_at;
    private String praise_num;
    private String comment_num;
    private String is_adopted;
    private String is_praised;
    private String photo_url;
    private List<String> photo_urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNicname() {
        return nicname;
    }

    public void setNicname(String nicname) {
        this.nicname = nicname;
    }

    public String getPhoto_thumbnail_src() {
        return photo_thumbnail_src;
    }

    public void setPhoto_thumbnail_src(String photo_thumbnail_src) {
        this.photo_thumbnail_src = photo_thumbnail_src;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getIs_adopted() {
        return is_adopted;
    }

    public void setIs_adopted(String is_adopted) {
        this.is_adopted = is_adopted;
    }

    public String getIs_praised() {
        return is_praised;
    }

    public void setIs_praised(String is_praised) {
        this.is_praised = is_praised;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
