package com.test.site.controller;

import com.axes.Result;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.annotation.ControllerService;
import com.axes.mvc.annotation.Filters;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.test.site.models.User;
import com.test.site.service.UserService;
import java.awt.geom.AffineTransform;

@Controller(basePath = "/user")
@Filters(filters = "session")
public class UserController extends BaseController {

    @ControllerService(name = "user")
    private UserService service;
    private String password;
    private String reinput;
    private int page;
    private int pageSize;
    private String username = "";
    private String ids;

    @Action(path = "/editpassword")
    public View editPassword() throws Exception {
        if (this.password.equals(this.reinput)) {
            User user = (User) this.request.getSession().getAttribute("user");
            user.setPassword(password);
            service.editUser(user);
            return Result.success();
        } else {
            return Result.error("the twice password is not the same");
        }
    }

    @Action(path = "/userlist")
    public View userlist() throws Exception {
        return Result.success(service.getUserPage("select * from user where username like '%" + username + "%'", page-1, pageSize, null));
    }

    @Action(path = "/adduser")
    public View addUser() throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword("111111");
        service.addUser(user);
        return Result.success();
    }

    @Action(path = "/removeusers")
    public View removeUsers() throws Exception {
        service.deleteUsers(ids);
        return Result.success();
    }
}
