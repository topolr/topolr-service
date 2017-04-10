package com.test.site.contenter.tag;

import com.axes.contenter.template.annotation.ContenterTag;
import com.axes.contenter.template.macro.base.ContentMacroTagBase;
import java.util.HashMap;

@ContenterTag(name = "testTag")
public class TestTag extends ContentMacroTagBase {

    @Override
    public HashMap<String, Object> doService() {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("aaa", "aaa");
        map.put("bbb", "bbb");
        map.put("ccc", "ccc");
        return map;
    }
}
