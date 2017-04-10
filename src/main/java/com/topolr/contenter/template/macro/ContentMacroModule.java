package com.topolr.contenter.template.macro;

import com.topolr.contenter.Contenter;
import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.contenter.template.TemplateScope;
import java.io.IOException;
import java.util.Map;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ContentMacroModule implements TemplateDirectiveModel {

    @Override
    public void execute(Environment e, Map map, TemplateModel[] tms, TemplateDirectiveBody tdb) throws TemplateException, IOException {
        String moduleid = map.get("id").toString();
        try {
            ContentObjectModule module = Contenter.getContenter().getContentModuleContainer().getObjectModule(moduleid);
            if(null!=module){
                TemplateScope scope = new TemplateScope();
                RequestScope rscope = (RequestScope) e.getCustomAttribute("requestScope");
                scope.setSessionAttribute("requestScope", rscope);
                scope.setSessionAttribute("queryHandler", (QueryHandler) e.getCustomAttribute("request"));
                scope.setSessionAttribute("objectModule",module);
                scope.setData(rscope.getRequestScope());
                String t = Contenter.getContenter().getContentModuleContainer().getObjectModuleClientString(moduleid, scope);
                e.getOut().write(t);
            }else{
                String t = "<div module=''><div style='line-height:200px;border:1px dotted #D7D7D7;margin:5px;text-align:center;'>module with id of '"+moduleid+"' can not find</div></div>";
                e.getOut().write(t);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (tdb != null) {
            tdb.render(e.getOut());
        }
    }
}
