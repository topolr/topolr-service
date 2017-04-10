package com.test.site.views.tags;

import com.axes.mvc.view.page.annotation.JspxTag;
import com.axes.mvc.view.page.base.JspxTagBase;
import java.util.HashMap;

@JspxTag(name = "testxx")
public class TestJspxTag extends JspxTagBase {

    @Override
    public HashMap<String, Object> doService() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("xxxtest", "nnnnn");
        return map;
    }

}
