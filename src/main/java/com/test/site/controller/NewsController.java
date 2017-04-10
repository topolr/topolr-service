package com.test.site.controller;

import com.axes.Result;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.annotation.ControllerService;
import com.axes.mvc.annotation.Filters;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.file.Jile;
import com.test.site.models.News;
import com.test.site.service.NewsService;
import java.io.File;

@Controller(basePath = "/news")
@Filters(filters = "session")
public class NewsController extends BaseController {

    @ControllerService(name = "news")
    private NewsService service;
    private int id;
    private String title="";
    private String content;
    private String time;
    private String image;
    private String author;
    private File file;
    private int page;
    private int pageSize;
    private String ids;
    private String ntimeis;

    @Action(path = "/editnews")
    public View editBrand() throws Exception {
        News news = new News();
        if (null != image && !image.equals("")) {
            news.setImage(image);
        }
        news.setId(id);
        news.setAuthor(author);
        news.setContent(content);
        news.setTitle(title);
        news.setNtimeis(ntimeis);
        service.edit(news);
        return Result.success();
    }

    @Action(path = "/newslist")
    public View newsList() throws Exception {
        return Result.success(service.findNewsPage("select * from news where title like '%" + title + "%'", page-1, pageSize, null));
    }
    
    @Action(path = "/news")
    public View news() throws Exception {
        System.out.println("==="+service.findNewsPage("select * from news", page, pageSize, null));
        return Result.success(service.findNewsPage("select * from news", page, pageSize, null));
    }

    @Action(path = "/addnews")
    public View addNews() throws Exception {
        News news = new News();
        if (null != image && !image.equals("")) {
            news.setImage(image);
        }
        news.setId(id);
        news.setAuthor(author);
        news.setContent(content);
        news.setTitle(title);
        news.setNtimeis(ntimeis);
        service.add(news);
        return Result.success();
    }

    @Action(path = "/removenewss")
    public View name() throws Exception {
        service.removeNews(ids);
        return Result.success();
    }

    @Action(path = "/newsimage")
    public View newsImage() throws Exception {
        String picname = System.currentTimeMillis() + ".png";
        Jile.with(file).move(this.basePath() + File.separator + "uploads" + File.separator + "news" + File.separator + picname);
        return Result.success("uploads/news/" + picname);
    }

}
