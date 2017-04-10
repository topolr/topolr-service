package com.topolr.contenter.template;

import java.util.HashMap;

public class TemplateScope {

    private HashMap<String, Object> data;
    private HashMap<String, Object> session;

    public TemplateScope() {
        this.data = new HashMap<String, Object>();
        this.session = new HashMap<String, Object>();
    }

    public static TemplateScope getTemplateScope() {
        return new TemplateScope();
    }

    public TemplateScope setData(HashMap<String, Object> data) {
        this.data.putAll(data);
        return this;
    }

    public TemplateScope setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public TemplateScope removeData(String key) {
        this.data.remove(key);
        return this;
    }

    public TemplateScope removeAllData() {
        this.data.clear();
        return this;
    }

    public TemplateScope setSessionAttributes(HashMap<String, Object> data) {
        this.session.putAll(data);
        return this;
    }

    public TemplateScope setSessionAttribute(String key, Object value) {
        this.session.put(key, value);
        return this;
    }

    public TemplateScope removeSessionAttribute(String key) {
        this.session.remove(key);
        return this;
    }

    public TemplateScope removeAllSessionAttributes() {
        this.session.clear();
        return this;
    }

    public HashMap<String, Object> getData() {
        return this.data;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return this.session;
    }
}
