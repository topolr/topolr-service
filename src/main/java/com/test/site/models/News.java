package com.test.site.models;

import com.axes.mvc.annotation.Id;
import com.axes.mvc.annotation.Model;
import java.text.SimpleDateFormat;
import java.util.Date;

@Model(name = "news")
public class News {

    @Id
    private int id;
    private String title;
    private String content;
    private String time;
    private String image;
    private String author;
    private String ntimeis;

    public News() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.time = sdf.format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNtimeis() {
        return ntimeis;
    }

    public void setNtimeis(String ntimeis) {
        this.ntimeis = ntimeis;
    }

}
