package com.test.site.models;

import com.axes.mvc.annotation.Id;
import com.axes.mvc.annotation.Model;

@Model(name = "product")
public class Product {
    @Id
    private int id;
    private String descc;
    private String image;
    private String pagename;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescc() {
        return descc;
    }

    public void setDescc(String desc) {
        this.descc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
