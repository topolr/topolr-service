package com.test.site.contenter.method;

import com.axes.contenter.template.annotation.ContenterMethod;
import com.axes.contenter.template.macro.base.ContentMethodBase;
import freemarker.template.TemplateModelException;
import java.util.List;

@ContenterMethod(name = "test")
public class TestMethod extends ContentMethodBase {

    @Override
    public Object exec(List list) throws TemplateModelException {
        return "call method";
    }
}
