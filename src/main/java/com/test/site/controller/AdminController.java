package com.test.site.controller;

import com.axes.Result;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.annotation.ControllerService;
import com.axes.mvc.annotation.Filters;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.file.Jile;
import com.test.site.service.MenuService;
import java.io.File;

@Controller(basePath = "/admin")
@Filters(filters = "session")
public class AdminController extends BaseController {

    private String username;
    private String password;
    @ControllerService(name = "menu")
    private MenuService menu;
    private File file;

    @Action(path = "/editor")
    public View editor() throws Exception {
        return this.getJspxView("editor");
    }

    @Action(path = "/logout")
    public View logout() throws Exception {
        this.request.getSession().removeAttribute("user");
        return Result.success();
    }

    @Action(path = "/menu")
    public View Menu() throws Exception {
        return Result.success(this.menu.getDefaultMenu());
    }
    
    @Action(path = "/userinfo")
    public View userInfo() throws Exception {
        return Result.success(this.request.getSession().getAttribute("user"));
    }
    
    @Action(path = "/background")
    public View background() throws Exception {
        String path=this.basePath()+File.separator+"assets"+File.separator+"packets"+File.separator+"admin"+File.separator+"style"+
                File.separator+"images";
        Jile.with(file).move(path+File.separator+"m.jpg");
        return Result.success(this.basePath());
    }
}
