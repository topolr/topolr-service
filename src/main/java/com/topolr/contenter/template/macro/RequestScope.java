package com.topolr.contenter.template.macro;

import com.topolr.contenter.Contenter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestScope {

    private final HttpServletRequest request;

    public RequestScope(HttpServletRequest request) {
        this.request = request;
    }

    public HashMap<String, Object> getRequestScope() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Request", this.getRequestArributes());
        map.put("Session", this.getSessionArributes());
        map.put("Parameter", this.getParameters());
//        map.put("httpPath", this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + this.request.getContextPath() + "/");
        map.put("httpPath", Contenter.getContenter().getContenterOption().getProjectHost());
        map.put("filePath", request.getSession().getServletContext().getRealPath("/"));
        map.put("Resource", this.getResourcePathMapping());
//        System.out.println(">>>===>>>"+map);
        return map;
    }

    public HashMap<String, Object> getRequestArributes() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (request != null) {
            Enumeration x = request.getAttributeNames();
            while (x.hasMoreElements()) {
                String name = (String) x.nextElement();
                Object t = request.getAttribute(name);
                map.put(name, t);
            }
        }
        return map;
    }

    public HashMap<String, Object> getSessionArributes() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (request != null) {
            Enumeration x = request.getSession().getAttributeNames();
            while (x.hasMoreElements()) {
                String name = (String) x.nextElement();
                Object t = request.getSession().getAttribute(name);
                map.put(name, t);
            }
        }
        return map;
    }

    public HashMap<String, Object> getParameters() {
        HashMap<String, Object> hasmap = new HashMap<String, Object>();
        if (request != null) {
            Map xt = request.getParameterMap();
            Iterator i = xt.keySet().iterator();
            while (i.hasNext()) {
                String key = i.next().toString();
                hasmap.put(key, request.getParameter(key));
            }
        }
        return hasmap;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HashMap<String, String> getResourcePathMapping() {
        HashMap<String, String> map = new HashMap<>();
        String http = this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + this.request.getContextPath() + "/";
        map.put("image", http + "assets/packets/page/css/images/");
        map.put("js", http + "assets/packets/page/");
        map.put("css", http + "assets/packets/page/css/");
        return map;
    }
}
