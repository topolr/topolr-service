package com.test.site.controller;

import com.axes.Result;
import com.axes.contenter.Contenter;
import com.axes.contenter.ContenterAdapter;
import com.axes.contenter.data.ContentPage;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.file.Jile;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller(basePath = "/contenter")
public class ContenterController extends BaseController {

    private String moduleType;
    private String moduleId;
    private String values;
    private String pageType;
    private String pageinfo;
    private String pagename;
    private String removemoduleids;
    private String pagetitle;
    private String pagedesc;
    private String pageclone;
    private String pagecontent;
    private String pagekeywords;
    private String pageId;
    private String pageurl;
    private String headerstring;

    private String cssname;
    private String csscontent;
    private String jsname;
    private String jscontent;
    private File image;

    private String desc;
    private boolean editable;
    private String fields;
    private String icon;
    private boolean single;
    private String sort;
    private String template;
    private String type;
    private String version;
    private String view;
    private String editSetting;
    private File moduleicon;
    private String content;
    private String keywords;
    private String title;
    private String logo;
    private String author;
    private String id;
    private String option;
    private boolean removeable;

    private File file;

    @Action(path = "/editor")
    public View test() throws Exception {
        ContenterAdapter.renderPage("editor", request, response);
        return null;
    }

    @Action(path = "/page")
    public View page() throws Exception {
        ContenterAdapter.renderPage("pageone", request, response);
        return null;
    }

    @Action(path = "/pagestring")
    public View pagestring() throws Exception {
        String a = Contenter.getContenter().getPageString(pagename, request);
        return Result.success(a);
    }

    @Action(path = "/render")
    public View render() throws Exception {
        ContenterAdapter.renderPage(pagename, request, response);
        return null;
    }

    @Action(path = "/module")
    public View module() throws Exception {
        ContenterAdapter.renderModule("moduleone", request, response);
        return null;
    }

    @Action(path = "/refresh")
    public View refresh() throws Exception {
        ContenterAdapter.refreshContenter();
        return Result.success();
    }

    @Action(path = "/getmoduleinfo")
    public View getModuleInf() throws Exception {
        return Result.success(ContenterAdapter.getClientModuleInfo(moduleId, request));
    }

    @Action(path = "/moduleinfo")
    public View getModuleInfo() throws Exception {
        return Result.success(ContenterAdapter.getClientMoudleInfo(moduleId, moduleType, values, request));
    }

    @Action(path = "/newmodule")
    public View newModule() throws Exception {
        return Result.success(ContenterAdapter.getNewClientModule(moduleType, request));
    }

    @Action(path = "/modulelist")
    public View modulelist() throws Exception {
        return Result.success(Contenter.getContenter().getContentModuleContainer().getTemplateModulesWithSort());
    }

    @Action(path = "/allmodulelist")
    public View allModuleList() throws Exception {
        Object t = Contenter.getContenter().getContentModuleContainer().getTemplateModuleInfosWithSort();
        return Result.success(t);
    }

    @Action(path = "/savepage")
    public View savepage() throws Exception {
        ContenterAdapter.save(pageinfo);
        return Result.success();
    }

    @Action(path = "/pagelist")
    public View pagelist() throws Exception {
        return Result.success(Contenter.getContenter().getContentPageContainer().getEditableContentPageWithSort());
    }

    @Action(path = "/allpagelist")
    public View allPageList() throws Exception {
        return Result.success(Contenter.getContenter().getContentPageContainer().getContentPageWithSort());
    }

    @Action(path = "/removeModules")
    public View removeModules() throws Exception {
        Contenter.getContenter().getContentModuleContainer().removeObjectModule(this.removemoduleids);
        return Result.success();
    }

    @Action(path = "/addpage")
    public View addPage() throws Exception {
        Contenter.getContenter().getContentPageContainer().cloneNewContentPage(pageclone, pagename, pagedesc, pagekeywords, pagecontent, pageurl);
        return Result.success();
    }

    @Action(path = "/getbasepage")
    public View getBasePage() throws Exception {
        return Result.success(Contenter.getContenter().getContentPageContainer().getBasePageString());
    }

    @Action(path = "/removepage")
    public View removePage() throws Exception {
        ContentPage page = Contenter.getContenter().getContentPageContainer().getContentPage(pagename);
        Contenter.getContenter().getContentPageContainer().removeContentPage(page);
        return Result.success();
    }

    @Action(path = "/pageinfo")
    public View pageInfo() throws Exception {
        return Result.success(Contenter.getContenter().getContentPageContainer().getContentPage(pagename));
    }

    @Action(path = "/editpageinfo")
    public View editPageInfo() throws Exception {
        Contenter.getContenter().getContentPageContainer().editContentPageInfo(pageId, pagename, pagedesc, pagekeywords, pagecontent, pageurl);
        return Result.success();
    }

    @Action(path = "/getallpagemapping")
    public View getpageMapping() {
        return Result.success(Contenter.getContenter().getContentPageContainer().getAllPagesMaping());
    }

    @Action(path = "/exportss")
    public View exports() throws Exception {
        Contenter.getContenter().exportDevResource(response);
        return null;
    }

    @Action(path = "/exportspage")
    public View exportspage() throws Exception {
        Contenter.getContenter().exportsAllPages(response);
        return null;
    }

    @Action(path = "/exports")
    public View name() throws Exception {
        Contenter.getContenter().exports(response);
        return null;
    }

    @Action(path = "/upload")
    public View upload() throws Exception {
        String id = Contenter.getUUID();
        String path = Contenter.getWebInfoPath() + "uploads" + File.separator + "contenter" + File.separator + id + ".png";
        Jile.with(file).move(path);
        return Result.success("uploads/contenter/" + id + ".png");
    }

    @Action(path = "/csspath")
    public View cssPath() throws Exception {
        return Result.success(Contenter.getContenter().getClientCssFiles());
    }

    @Action(path = "/savecss")
    public View saveCss() throws Exception {
        Contenter.getContenter().saveClientCssFile(cssname, csscontent);
        return Result.success();
    }

    @Action(path = "/jspath")
    public View jsPath() throws Exception {
        return Result.success(Contenter.getContenter().getClientJsFiles());
    }

    @Action(path = "/savejs")
    public View saveJs() throws Exception {
        Contenter.getContenter().saveClientJsFile(jsname, jscontent);
        return Result.success();
    }

    @Action(path = "/imagepath")
    public View imagePath() throws Exception {
        List<HashMap<String, String>> list = Contenter.getContenter().getClientImageFiles();
        String basepath = this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + this.request.getContextPath() + "/";
        for (HashMap<String, String> map : list) {
            map.put("url", basepath + map.get("url"));
        }
        return Result.success(list);
    }

    @Action(path = "/saveimage")
    public View saveImage() throws Exception {
        Contenter.getContenter().saveClientImage(image);
        return Result.success();
    }

    @Action(path = "/savemoduleinfo")
    public View saveModuleInfo() throws Exception {
        Contenter.getContenter().saveTemplateModuleInfo(
                desc,
                editable,
                fields,
                icon,
                single,
                sort,
                template,
                type,
                version,
                view,
                editSetting,
                removeable);
        return Result.success();
    }

    @Action(path = "/moduleicon")
    public View uploadModuleIcon() throws Exception {
        String name = Contenter.getContenter().getContentModuleContainer().resetModuleIcon(moduleicon);
        return Result.success("assets/icons/" + name);
    }

    @Action(path = "/savepageinfo")
    public View savePageInfo() throws Exception {
        ContentPage page = new ContentPage();
        page.setAuthor(author);
        page.setContent(content);
        page.setEditable(editable);
        page.setId(id);
        page.setKeywords(keywords);
        page.setLogo(logo);
        page.setPageurl(pageurl);
        page.setSort(sort);
        page.setTemplate(template);
        page.setDesc(desc);
        page.setTitle(title);
        Contenter.getContenter().getContentPageContainer().savePageInfo(page);
        return Result.success();
    }

    @Action(path = "/downloadmodule")
    public View downLoadModule() throws Exception {
        Contenter.getContenter().downLoadModule(type, response);
        return null;
    }

    @Action(path = "/downloadpage")
    public View downloadPage() throws Exception {
        Contenter.getContenter().downloadPage(id, response);
        return null;
    }
}
