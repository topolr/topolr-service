package com.topolr.contenter.data;

import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.jsonx.Jsonx;

public class ContentPage extends BaseContentData {

    private String title;
    private String desc;
    private String content;
    private String keywords;
    private String author;
    private String logo;
    private boolean editable;
    private String id;
    private String pageurl;
    private String template;
    private String sort;

    @Override
    public BaseContentData cloneModule() {
        ContentPage page = new ContentPage();
        ObjectSnooper.snoop(page).sett(ObjectSnooper.snoop(this).toHashMap());
        return page;
    }

    public ContentPage(Jsonx json) {
        this.title = json.get("title").toString();
        this.desc = json.get("desc").toString();
        this.content = json.get("content").toString();
        this.keywords = json.get("keywords").toString();
        this.author = json.get("author").toString();
        this.logo = json.get("logo").toString();
        this.editable = json.get("editable").toBoolean();
        this.id = json.get("id").toString();
        this.pageurl = json.get("pageurl").toString();
        this.template = json.get("template").toString();
        this.sort = json.get("sort").toString();
    }

    public ContentPage() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
