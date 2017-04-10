package com.topolr.contenter.template;

import com.topolr.contenter.ContenterOption;
import com.topolr.contenter.template.annotation.ContenterMethod;
import com.topolr.contenter.template.annotation.ContenterProvider;
import com.topolr.contenter.template.annotation.ContenterTag;
import com.topolr.util.base.PackageBrowser;
import com.topolr.util.base.reflect.ObjectSnooper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class TemplateMacroManager {

    private final HashMap<String, String> providerMapping;
    private final HashMap<String, String> tagMapping;
    private final HashMap<String, String> methodMapping;
    private static TemplateMacroManager manager;

    private TemplateMacroManager(ContenterOption option) throws Exception {
        this.providerMapping = new HashMap<String, String>();
        this.tagMapping = new HashMap<String, String>();
        this.methodMapping = new HashMap<String, String>();
        String path = option.getProviderPackage();
        if (path != null && !path.equals("")) {
            List<String> classNames = PackageBrowser.getClassNames(path);
            if (classNames != null) {
                for (String classname : classNames) {
                    if (Class.forName(classname).isAnnotationPresent(ContenterProvider.class)) {
                        String name = Class.forName(classname).getAnnotation(ContenterProvider.class).name();
                        this.providerMapping.put(name, classname);
                        Logger.getLogger(TemplateMacroManager.class).info("[contenter provider]["+name+"]:" + classname);
                    }
                }
            }
        }
        List<String> service = PackageBrowser.getClassNames(option.getTagPackage());
        if (service != null && service.size() > 0) {
            for (String x : service) {
                if (Class.forName(x).isAnnotationPresent(ContenterTag.class)) {
                    String xname = Class.forName(x).getAnnotation(ContenterTag.class).name();
                    this.tagMapping.put(xname, x);
                    Logger.getLogger(TemplateMacroManager.class).info("[contenter tag]["+xname+"]:" + x);
                }
            }
        }
        List<String> method = PackageBrowser.getClassNames(option.getMethodPackage());
        if (service != null && method.size() > 0) {
            for (String x : method) {
                if (Class.forName(x).isAnnotationPresent(ContenterMethod.class)) {
                    String xname = Class.forName(x).getAnnotation(ContenterMethod.class).name();
                    this.methodMapping.put(xname, x);
                    Logger.getLogger(TemplateMacroManager.class).info("[contenter tag]["+xname+"]:" + x);
                }
            }
        }
    }

    public static TemplateMacroManager init(ContenterOption option) throws Exception {
        if (null == TemplateMacroManager.manager) {
            TemplateMacroManager.manager = new TemplateMacroManager(option);
        }
        return TemplateMacroManager.manager;
    }

    public static TemplateMacroManager getTemplateMacroManager() {
        return TemplateMacroManager.manager;
    }

    public Object getProviderInstance(String providerName) throws Exception {
        String className = this.getProviderMapping().get(providerName);
        if (null != className) {
            return ObjectSnooper.snoop(className).instance();
        } else {
            return null;
        }
    }

    public List<String> getContentTagList() {
        List<String> list = new ArrayList<String>();
        for (Entry<String, String> entry : this.getTagMapping().entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public List<String> getContenterMethodList() {
        List<String> list = new ArrayList<String>();
        for (Entry<String, String> entry : this.getMethodMapping().entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public HashMap<String, String> getProviderMapping() {
        return providerMapping;
    }

    public HashMap<String, String> getTagMapping() {
        return tagMapping;
    }

    public HashMap<String, String> getMethodMapping() {
        return methodMapping;
    }

}
