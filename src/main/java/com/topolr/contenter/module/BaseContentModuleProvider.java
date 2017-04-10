package com.topolr.contenter.module;

import com.topolr.contenter.data.ContentModule;
import com.topolr.contenter.data.ContentObjectModule;
import java.util.HashMap;
import java.util.List;

public abstract class BaseContentModuleProvider {

    public abstract HashMap<String, ContentObjectModule> getAllObjectModules();

    public abstract void saveObjectModule(ContentObjectModule module);

    public abstract void removeObjectModule(ContentObjectModule module);

    public abstract void saveObjectModuleList(List<ContentObjectModule> modules);

    public abstract void removeObjectModuleList(List<ContentObjectModule> modules);

    public abstract void removeAllObjectModule();

    public abstract void editObjectModule(ContentObjectModule module);

    public abstract void saveTemplateModule(ContentModule module);
    
    public abstract void saveTemplateModuleList(List<ContentModule> list);

    public abstract void editTemplateModule(ContentModule module);

    public abstract void removeTemplateModule(ContentModule module);
    
    public abstract void removeAllTemplateModules();

    public abstract HashMap<String, ContentModule> getAllTemplateModules();
}
