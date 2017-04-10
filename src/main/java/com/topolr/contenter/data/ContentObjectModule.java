package com.topolr.contenter.data;

import com.topolr.contenter.Contenter;
import com.topolr.util.jsonx.JsonEach;
import com.topolr.util.jsonx.JsonEachArray;
import com.topolr.util.jsonx.Jsonx;

public class ContentObjectModule {

    private String moduleType;
    private String values;
    private String id;
    private String option;

    public ContentObjectModule() {
    }

    public ContentObjectModule(Jsonx json) throws Exception {
        this.moduleType = json.get("moduleType").toString();
        this.id = json.get("id").toString();
        this.values = json.get("values").toString();
        this.option = json.get("option").toString();
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
        try {
            Jsonx n = Jsonx.create("{}");
            ContentModule module = Contenter.getContenter().getContentModuleContainer().getTemplateModule(moduleType);
            if (null != module) {
                Jsonx.create(this.values).each(new JsonEach(n, module) {
                    @Override
                    public boolean each(String key, Jsonx json) throws Exception {
                        Jsonx t = (Jsonx) arguments[0];
                        ContentModule m = (ContentModule) arguments[1];
                        if (null != m.getFields()) {
                            Jsonx.create(m.getFields()).each(new JsonEachArray(t, key) {
                                @Override
                                public boolean each(int index, Jsonx json) throws Exception {
                                    Jsonx tt = (Jsonx) arguments[0];
                                    String name = arguments[1].toString();
                                    if (json.get("name") != null && json.get("isoption") != null && json.get("isoption").toBoolean() != null) {
                                        if (json.get("name").toString().equals(name) && json.get("isoption").toBoolean() == true) {
                                            tt.add(json.get("name").toString(), json.get("value"));
                                        }
                                    }
                                    return false;
                                }
                            });
                        }
                        return false;
                    }
                });
            }
            this.option = n.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getOption() {
        return this.option;
    }

}
