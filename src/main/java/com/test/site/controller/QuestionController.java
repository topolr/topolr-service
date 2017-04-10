package com.test.site.controller;

import com.axes.Result;
import com.axes.jmail.Jmail;
import com.axes.jmail.data.JmailInfo;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.jsonx.Jsonx;
import com.test.site.MBTI;
import com.test.site.question.QuestionOption;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller(basePath = "/question")
public class QuestionController extends BaseController {

    private String html;
    private String values;
    private String complete;
    private String id;

    @Action(path = "/page")
    public View question() throws Exception {
        System.out.println("======================>>>pppp");
        return this.getJspxView("question");
    }

    @Action(path = "/post")
    public View post() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("html", html);
        map.put("values", values);
        map.put("id", id);
        map.put("complete", complete);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Jsonx.create(map).toFile(QuestionOption.getOption().getPath() + File.separator + "[" + id + "]调查问卷[" + System.currentTimeMillis() + "]" + ".json");
        String result = new MBTI(Jsonx.create(values), new String[]{"09","1001", "1002", "1003", "1004"}).getResultString();
        html += "<br/><br/><br/>";
        html += result;
        JmailInfo info = new JmailInfo();
        info.addTo(QuestionOption.getOption().getSendTo());
        info.setType(JmailInfo.HTMLMAIL);
        info.setContent(html);
        info.setSubject("[" + id + "]调查问卷[" + dateString + "]");
        Jmail.sentToService(info);
        return Result.success();
    }

}
