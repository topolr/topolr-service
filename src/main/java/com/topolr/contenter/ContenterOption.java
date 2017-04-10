package com.topolr.contenter;

import com.topolr.contenter.module.BaseContentModuleProvider;
import com.topolr.contenter.module.ModuleDebugHelper;
import com.topolr.contenter.page.BaseContentPageProvider;
import com.topolr.contenter.page.PageDebugHelper;
import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.jsonx.Jsonx;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;

public class ContenterOption {

    private final String templatePath;
    private final BaseContentModuleProvider moduleProvider;
    private final BaseContentPageProvider pageProvider;
    private final ModuleDebugHelper moduleHelper;
    private final PageDebugHelper pageHelper;
    private final String providerPackage;
    private final String tagPackage;
    private final String methodPackage;
    private final String assetsPath;
    private final String clientCssPath;
    private final String clientJsPath;
    private final String clientImagePath;
    private final String clientTemplatePath;
    private final boolean debug;
    private final String debugModulePath;
    private final String debugModuleObjectPath;
    private final String debugPagePath;
    private final String debugIconPath;
    private final String debugResourcePath;
    private final TemplateExceptionHandler exceptionHandler;
    private final String projectHost;

    public ContenterOption(Jsonx option) throws Exception {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        this.moduleProvider = ObjectSnooper.snoop(option.get("moduleProviderName").toString()).newInstance();
        this.pageProvider = ObjectSnooper.snoop(option.get("pageProviderName").toString()).newInstance();
        this.templatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter";
        this.tagPackage = option.get("tagPakage").toString();
        this.methodPackage = option.get("methodPakage").toString();
        this.providerPackage = option.get("providerPakage").toString();
        this.assetsPath = xmt.getParentFile().getParentFile().getAbsolutePath() + File.separator + "assets";
        this.debug = option.get("debug").toBoolean();
        this.clientCssPath = assetsPath + File.separator + "packets" + File.separator + "page" + File.separator + "css";
        this.clientImagePath = assetsPath + File.separator + "packets" + File.separator + "page" + File.separator + "css" + File.separator + "images";
        this.clientJsPath = assetsPath + File.separator + "packets" + File.separator + "page";
        this.clientTemplatePath = assetsPath + File.separator + "packets" + File.separator + "page" + File.separator + "template";
        this.debugModuleObjectPath = option.get("debugHelper").get("moduleObjectPath").toString();
        this.debugModulePath = option.get("debugHelper").get("modulePath").toString();
        this.debugPagePath = option.get("debugHelper").get("pagePath").toString();
        this.debugIconPath = option.get("debugHelper").get("iconPath").toString();
        this.debugResourcePath = option.get("debugHelper").get("resourcePath").toString();
        this.moduleHelper = new ModuleDebugHelper(this);
        this.pageHelper = new PageDebugHelper(this);
        this.exceptionHandler = ObjectSnooper.snoop(option.get("exceptionHandlerName").toString()).newInstance();
        this.projectHost = option.get("projectHost").toString();
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public BaseContentModuleProvider getModuleProvider() {
        return moduleProvider;
    }

    public BaseContentPageProvider getPageProvider() {
        return pageProvider;
    }

    public String getProviderPackage() {
        return providerPackage;
    }

    public String getTagPackage() {
        return tagPackage;
    }

    public String getMethodPackage() {
        return methodPackage;
    }

    public String getAssetsPath() {
        return assetsPath;
    }

    public String getClientCssPath() {
        return clientCssPath;
    }

    public String getClientJsPath() {
        return clientJsPath;
    }

    public String getClientImagePath() {
        return clientImagePath;
    }

    public String getClientTemplatePath() {
        return clientTemplatePath;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getDebugModulePath() {
        return debugModulePath;
    }

    public String getDebugModuleObjectPath() {
        return debugModuleObjectPath;
    }

    public String getDebugPagePath() {
        return debugPagePath;
    }

    public ModuleDebugHelper getModuleHelper() {
        return moduleHelper;
    }

    public PageDebugHelper getPageHelper() {
        return pageHelper;
    }

    public String getDebugIconPath() {
        return this.debugIconPath;
    }

    public TemplateExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public String getDebugResourcePath() {
        return debugResourcePath;
    }

    public String getDebugJsPath() {
        return debugResourcePath;
    }

    public String getDebugCssPath() {
        return this.debugResourcePath + File.separator + "css";
    }

    public String getDebugImagePath() {
        return this.debugResourcePath + File.separator + "css" + File.separator + "images";
    }

    public String getProjectHost() {
        return projectHost;
    }
}
