package com.test.site.views.method;

import com.axes.mvc.view.page.annotation.JspxMethod;
import com.axes.mvc.view.page.base.JspxMethodBase;
import freemarker.template.TemplateModelException;
import java.util.List;

@JspxMethod(name = "test")
public class TestJspxMethod extends JspxMethodBase {

    @Override
    public Object exec(List list) throws TemplateModelException {
        return list.get(0).toString().toUpperCase();
    }

}