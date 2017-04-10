package com.topolr.contenter;

import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.util.jsonx.Jsonx;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContenterAdapter {

    public static void renderPage(String pageId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Contenter.getContenter().renderPage(pageId, request, response);
    }

    public static void renderModule(String moduleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Contenter.getContenter().renderModule(moduleId, request, response);
    }

    public static HashMap<String, Object> getNewClientModule(String moduleType, HttpServletRequest request) throws Exception {
        return Contenter.getContenter().getNewClientModule(moduleType, request);
    }

    public static HashMap<String, Object> getClientModuleInfo(String moduleId, HttpServletRequest request) throws Exception {
        return Contenter.getContenter().getClientModuleInfo(moduleId, request);
    }

    public static HashMap<String, Object> getClientMoudleInfo(String moduleId, String moduleType, String values, HttpServletRequest request) throws Exception {
        ContentObjectModule module = new ContentObjectModule();
        module.setId(moduleId);
        module.setModuleType(moduleType);
        module.setValues(values);
        return Contenter.getContenter().getClientModuleInfo(module, request);
    }

    public static void refreshContenter() throws Exception {
        Contenter.getContenter().refresh();
    }
    
    public static void save(String info) throws Exception{
        Contenter.getContenter().save(Jsonx.create(info));
    }
    
}
