package com.topolr.contenter.template.macro.base;

import com.topolr.contenter.template.TemplateMacroManager;
import com.topolr.contenter.template.macro.QueryHandler;
import com.topolr.contenter.template.macro.RequestScope;
import com.topolr.util.base.reflect.ObjectSnooper;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class ContentMacroTagBase implements TemplateDirectiveModel {

    protected Map<String, Object> tagParameter;
    protected Writer out;
    protected RequestScope requestScope;
    protected QueryHandler queryHandler;

    @Override
    public void execute(Environment e, Map map, TemplateModel[] tms,TemplateDirectiveBody tdb) throws TemplateException, IOException {
        tagParameter = map;
        requestScope = (RequestScope) e.getCustomAttribute("requestScope");
        queryHandler = (QueryHandler) e.getCustomAttribute("request");
        out = e.getOut();
        HashMap<String, Object> mapx = this.doService();
        if (mapx != null) {
            for (Entry<String, Object> c : mapx.entrySet()) {
                e.setVariable(c.getKey(),ObjectWrapper.DEFAULT_WRAPPER.wrap(c.getValue()));
            }
        }
        if (tdb != null) {
            tdb.render(e.getOut());
        }
    }

    public Object getProviderData(String providerName, String methodName)throws Exception {
        Object provider = TemplateMacroManager.getTemplateMacroManager().getProviderInstance(providerName);
        if (provider instanceof ContentProviderBase) {
            HashMap<String, Object> mapx = new HashMap<String, Object>();
            mapx.put("out", out);
            mapx.put("request", requestScope.getRequest());
            mapx.put("queryHandler", queryHandler);
            mapx.put("tagParameters", tagParameter);
            ObjectSnooper snooper = ObjectSnooper.snoop(provider);
            snooper.sett(mapx).set(requestScope.getParameters());
            Object result = null;
            try {
                result = snooper.trigger(methodName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            throw new Exception("[JpageTag:Provider can not find.]");
        }
    }

    protected Object getTagParameter(String key) {
        return this.tagParameter.get(key);
    }

    public abstract HashMap<String, Object> doService();
}
