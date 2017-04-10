package com.test.site.contenter.provider;

import com.axes.contenter.template.annotation.ContenterProvider;
import com.axes.mvc.annotation.ControllerService;
import com.axes.util.base.reflect.ObjectSnooper;
import com.test.site.contenter.BrooderProvider;
import com.test.site.models.News;
import com.test.site.service.NewsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContenterProvider(name = "newsProvider")
public class NewsProvider extends BrooderProvider {

    @ControllerService(name = "news")
    private NewsService service;
    private int newsid;

    public List<HashMap<String, Object>> getNewsList() {
        List<HashMap<String, Object>> r = new ArrayList<>();
        try {
            r = this.service.findNewsPage("select * from news order by ntimeis desc", 0, 12, null).getRows();
        } catch (Exception ex) {
        }
        return r;
    }

    public HashMap<String, Object> getNews() throws Exception {
        if (newsid != 0) {
            HashMap<String, Object> t = this.service.find("select * from news where id=?", new Object[]{newsid});
            if (null != t) {
                return t;
            } else {
                HashMap<String, Object> tp = new HashMap<String, Object>();
                tp.put("title", "module show");
                tp.put("content", "<div style='height:600px;line-height:200px;'>module content show</div>");
                return tp;
            }
        } else {
            HashMap<String, Object> t = new HashMap<String, Object>();
            t.put("title", "module show");
            t.put("content", "<div style='height:600px;line-height:200px;'>module content show</div>");
            return t;
        }
    }
}
