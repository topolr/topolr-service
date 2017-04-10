package com.test.site.controller;

import com.axes.Result;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.file.Jile;
import java.io.File;

@Controller(basePath = "/marteditor")
public class MarteditorController extends BaseController {

    private File file;
    private String packetName;
    private String packetContent;

    @Action(path = "/editor/upload")
    public View upload() throws Exception {
        String filename = System.currentTimeMillis() + ".png";
        String t = File.separator + "upload" + File.separator + "editor" + File.separator + filename;
        Jile.with(file).move(this.basePath() + t);
        return Result.success(this.httpPath() + "upload/editor/" + filename);
    }

    @Action(path = "/savepacket")
    public View savepacket() throws Exception {
        String filepath = request.getSession().getServletContext().getRealPath("/") + File.separator
                + "assets" + File.separator
                + "packets" + File.separator + this.packetName.replaceAll("\\.", "\\\\") + ".js";
        Jile.with(filepath).write(packetContent);
        return Result.success();
    }
}
