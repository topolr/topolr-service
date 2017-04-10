package com.test.site.views.provider;

import com.axes.mvc.view.page.annotation.JspxProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JspxProvider(name = "test")
public class TestJspxProvider {

    public List<HashMap<String, String>> test() {
        List<HashMap<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("title", "title-" + i);
            list.add(map);
        }
        return list;
    }
}
