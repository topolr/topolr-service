package com.topolr.contenter.data;

import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.jsonx.JsonEachArray;
import com.topolr.util.jsonx.Jsonx;
import java.util.HashMap;

public class ContentModule extends BaseContentData {

    private String icon;
    private String desc;
    private String version = "1.0";
    private String values;
    private String view = "";
    private String type;
    private String fields = "";
    private String template;
    private String editSetting;
    private boolean single = false;
    private boolean editable = true;
    private boolean removeable;
    private String sort;

    public ContentModule() {
    }

    public ContentModule(Jsonx json) throws Exception {
        this.icon = json.get("icon").toString();
        this.desc = json.get("desc").toString();
        this.version = json.get("version").toString();
        this.single = json.get("single").toBoolean();
        this.editable = json.get("editable").toBoolean();
        this.removeable = json.get("removeable").toBoolean();
        this.template = json.get("template").toString();
        this.type = json.get("type").toString();
        this.view = json.get("view").toString();
        this.editSetting = json.get("editSetting").toString();
        this.sort = json.get("sort").toString();
        HashMap<String, String> map = new HashMap<>();
        Jsonx.create(json.get("fields").toString()).each(new JsonEachArray(map) {
            @Override
            public boolean each(int index, Jsonx json) throws Exception {
                HashMap<String, String> opp = (HashMap<String, String>) arguments[0];
                opp.put(json.get("name").toString(), json.get("value").toString());
                return false;
            }
        });
        this.fields = json.get("fields").toString();
        this.values = Jsonx.create(map).toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public BaseContentData cloneModule() {
        ContentModule module = new ContentModule();
        ObjectSnooper.snoop(module).sett(ObjectSnooper.snoop(this).toHashMap());
        return module;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        try {
            HashMap<String, String> map = new HashMap<>();
            Jsonx.create(fields).each(new JsonEachArray(map) {
                @Override
                public boolean each(int index, Jsonx json) throws Exception {
                    HashMap<String, String> opp = (HashMap<String, String>) arguments[0];
                    opp.put(json.get("name").toString(), json.get("value").toString());
                    return false;
                }
            });
            this.values = Jsonx.create(map).toString();
            this.fields = fields;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValues() {
        return values;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getEditSetting() {
        return editSetting;
    }

    public void setEditSetting(String editSetting) {
        this.editSetting = editSetting;
    }

    public boolean isRemoveable() {
        return removeable;
    }

    public void setRemoveable(boolean removeable) {
        this.removeable = removeable;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
