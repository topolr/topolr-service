package com.topolr.contenter.extend;

import com.topolr.util.jsonx.Jsonx;
import java.util.List;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class JsonLog implements TemplateMethodModelEx {

    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() == 2) {
            Object t = args.get(1);
            return "<script>console.log('%o---%o','" + args.get(0).toString() + "',window.JSON.parse('" + Jsonx.create(t).toString() + "'));</script>";
        } else if (args.size() == 1) {
            Object t = args.get(0);
//			return "<script>console.log(window.JSON.parse("
//					+ Jsonx.create(t).toString() + "));</script>";
            return "<script>console.log("
                    + Jsonx.create(t).toString() + ");</script>";
        } else {
            return "";
        }
    }

}
