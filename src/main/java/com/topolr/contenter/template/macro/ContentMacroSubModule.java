package com.topolr.contenter.template.macro;

import com.topolr.contenter.Contenter;
import com.topolr.contenter.data.ContentObjectModule;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;

public class ContentMacroSubModule implements TemplateDirectiveModel {

    @Override
    public void execute(Environment e, Map map, TemplateModel[] tms, TemplateDirectiveBody tdb) throws TemplateException, IOException {
        ContentObjectModule om = (ContentObjectModule) e.getCustomAttribute("objectModule");
        String moduleType = null != map.get("type") ? map.get("type").toString() : "";
        String id = null != map.get("id") ? map.get("id").toString() : Contenter.getUUID();
        String parent = null != map.get("parent") ? map.get("parent").toString() : (null!=om?om.getId():"");
        String option = null != map.get("option") ? map.get("option").toString() : "";
        String t = "<div data-view='" + moduleType + "' data-parent-view='" + parent + "' data-view-id='" + id + "' data-option='" + option + "'>";
        e.getOut().write(t);
        if (tdb != null) {
            tdb.render(e.getOut());
        }
        e.getOut().write("</div>");
    }

}
