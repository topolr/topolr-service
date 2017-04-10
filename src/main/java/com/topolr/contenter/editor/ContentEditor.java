package com.topolr.contenter.editor;

public abstract class ContentEditor {
    public abstract void newModule(String moduleType);
    public abstract void removeModule(String moduleId);
    public abstract void editModuleOption(String moduleId,String json);
    
    public abstract void newPage(String pageType);
    public abstract void removePage(String pageId);
    public abstract void editPageOption(String pageId,String json);
    
    public abstract void savePage(String json);
}
