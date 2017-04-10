package com.topolr.contenter.template.macro;

import com.topolr.contenter.template.TemplateMacroManager;
import com.topolr.util.base.reflect.ObjectSnooper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ContentMacroData implements TemplateDirectiveModel {

    @Override
    public void execute(Environment e, Map map, TemplateModel[] tms, TemplateDirectiveBody tdb) throws TemplateException, IOException {
        RequestScope rscope = (RequestScope) e.getCustomAttribute("requestScope");
        QueryHandler queryHandler = (QueryHandler) e.getCustomAttribute("request");

        String providerName = map.get("provider").toString();
        String actionName = map.get("method").toString();
        String dataname = map.get("return").toString();

        try {
            Object provider = TemplateMacroManager.getTemplateMacroManager().getProviderInstance(providerName);
            if (provider != null) {
                HashMap<String, Object> mapx = new HashMap<String, Object>();
                mapx.put("out", e.getOut());
                mapx.put("request", rscope.getRequest());
                mapx.put("queryHandler", queryHandler);
                mapx.put("tagParameters", map);

                ObjectSnooper obj = ObjectSnooper.snoop(provider);
                obj.sett(mapx);
                obj.set(rscope.getParameters());
                Object result = obj.trigger(actionName);

                HashMap<String, Object> xxtmap = new HashMap<String, Object>();
                Object xtt = obj.gett("local");
                if (xtt != null) {
                    xxtmap = (HashMap<String, Object>) xtt;
                }
                e.setVariable(dataname, ObjectWrapper.DEFAULT_WRAPPER.wrap(result));
                e.setVariable(dataname + "_local", ObjectWrapper.DEFAULT_WRAPPER.wrap(xxtmap));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null != tdb) {
            tdb.render(e.getOut());
        }
    }
}
