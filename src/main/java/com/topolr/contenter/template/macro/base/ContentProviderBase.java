package com.topolr.contenter.template.macro.base;

import com.topolr.contenter.template.macro.QueryHandler;
import java.io.Writer;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class ContentProviderBase {

    protected Writer out;
    protected QueryHandler queryHandler;
    protected HttpServletRequest request;
    protected HashMap<String, Object> tagParameters = new HashMap<String, Object>();

    public HashMap<String, Object> getTagParameters() {
        return tagParameters;
    }

    public Object getTagParamter(String key) {
        return this.tagParameters.get(key);
    }

    public boolean hasTagParameter(String key) {
        return this.tagParameters.containsKey(key);
    }

    public String getLocalPath() {
        return this.request.getSession().getServletContext().getRealPath("/");
    }

    public String getHttpPath() {
        return this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + this.request.getContextPath() + "/";
    }
}
