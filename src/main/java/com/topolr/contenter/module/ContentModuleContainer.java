package com.topolr.contenter.module;

import com.topolr.contenter.Contenter;
import com.topolr.contenter.ContenterOption;
import com.topolr.contenter.data.ContentModule;
import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.contenter.extend.JsonLog;
import com.topolr.contenter.extend.JsonMethod;
import com.topolr.contenter.extend.PageTransform;
import com.topolr.contenter.page.ContentPageModuleIdEach;
import com.topolr.contenter.template.StringTemplater;
import com.topolr.contenter.template.TemplateMacroManager;
import com.topolr.contenter.template.TemplateScope;
import com.topolr.contenter.template.macro.ContentMacroData;
import com.topolr.contenter.template.macro.ContentMacroSubModule;
import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.file.Jile;
import com.topolr.util.file.JileEach;
import com.topolr.util.jsonx.Jsonx;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ContentModuleContainer {

    protected HashMap<String, ContentModule> templateModules;
    protected HashMap<String, ContentObjectModule> objectModules;
    protected BaseContentModuleProvider provider;
    protected ModuleDebugHelper helper;
    protected StringTemplater templater;

    public ContentModuleContainer(ContenterOption option) throws Exception {
        this.provider = option.getModuleProvider();
        this.helper = option.getModuleHelper();
        this.templater = new StringTemplater("utf-8");
        this.templater.setException(option.getExceptionHandler());
        this.templater.setDirective("submodule", new ContentMacroSubModule());
        this.templater.setDirective("data", new ContentMacroData());
        this.objectModules = new HashMap<String, ContentObjectModule>();
        this.templateModules = new HashMap<String, ContentModule>();
        HashMap<String, ContentModule> t = this.provider.getAllTemplateModules();
        if (null != t) {
            this.templateModules.putAll(t);
        }
        this.readTemplateModules(option);
        this.objectModules.putAll(provider.getAllObjectModules());
        HashMap<String, String> tagMapping = TemplateMacroManager.getTemplateMacroManager().getTagMapping();
        HashMap<String, String> methodMapping = TemplateMacroManager.getTemplateMacroManager().getMethodMapping();
        for (Entry<String, String> entry : tagMapping.entrySet()) {
            this.templater.setDirective(entry.getKey(), ObjectSnooper.snoop(entry.getValue()).instance());
        }
        for (Entry<String, String> entry : methodMapping.entrySet()) {
            this.templater.setDirective(entry.getKey(), ObjectSnooper.snoop(entry.getValue()).instance());
        }
        this.templater.setDirective("json", new JsonMethod());
        this.templater.setDirective("log", new JsonLog());
        this.templater.setDirective("pageURL", new PageTransform());
        for (Entry<String, ContentModule> entry : this.templateModules.entrySet()) {
            ContentModule module = entry.getValue();
            this.templater.setTemplate(module.getType(), module.getTemplate());
        }
    }

    private void readTemplateModules(ContenterOption option) throws Exception {
        final ContentModuleContainer md = this;
        Jile.with(option.getTemplatePath() + File.separator + "modules").browse(new JileEach(md) {
            @Override
            public boolean each(Jile file) throws Exception {
                ContentModuleContainer md = (ContentModuleContainer) this.arguments[0];
                if (file.file().isFile()) {
                    if (file.getNameWithoutSuffix().equals("option")) {
                        Jsonx json = Jsonx.create(file.file());
                        HashMap<String, Object> map = json.toHashMap();
                        Jile icon = Jile.with(file.file().getParentFile().getAbsolutePath() + File.separator + "icon.png");
                        if (icon.file().exists()) {
                            icon.copy(getWebInfoPath() + File.separator + "assets" + File.separator + "icons" + File.separator + json.get("type").toString() + ".png");
                            map.put("icon", "assets/icons/" + json.get("type").toString() + ".png");
                        } else {
                            map.put("icon", "assets/icons/__default__.png");
                        }
                        Jile assets = Jile.with(file.file().getParentFile().getAbsolutePath() + File.separator + "assets");
                        if (assets.file().exists()) {
                            assets.copy(getWebInfoPath() + File.separator + "assets" + File.separator + "packets" + File.separator + "page" + File.separator + json.get("type").toString());
                        }
                        ContentModule module = new ContentModule(json);
                        module.setTemplate(Jile.with(file.file().getParentFile().getAbsolutePath() + "/template.html").read());
                        md.templateModules.put(json.get("type").toString(), module);
                    }
                }
                return false;
            }
        });
        Jile.with(option.getTemplatePath() + File.separator + "mapping" + File.separator + "modules").browse(new JileEach(md) {
            @Override
            public boolean each(Jile file) throws Exception {
                ContentModuleContainer md = (ContentModuleContainer) this.arguments[0];
                ContentModule module = new ContentModule(Jsonx.create(file.file()));
                md.templateModules.put(module.getType(), module);
                return false;
            }
        });
        Jile.with(option.getTemplatePath() + File.separator + "mapping" + File.separator + "moduleObjects").browse(new JileEach(md) {
            @Override
            public boolean each(Jile file) throws Exception {
                ContentModuleContainer md = (ContentModuleContainer) this.arguments[0];
                ContentObjectModule module = new ContentObjectModule(Jsonx.create(file.file()));
                md.objectModules.put(module.getId(), module);
                return false;
            }
        });
    }

    public List<ContentModule> getAllTemplateModules() {
        List<ContentModule> list = new ArrayList<ContentModule>();
        for (Entry<String, ContentModule> entry : this.templateModules.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public ContentObjectModule getObjectModule(String moduleId) {
        return this.objectModules.get(moduleId);
    }

    public List<ContentObjectModule> getAllObjectModules() {
        List<ContentObjectModule> c = new ArrayList<ContentObjectModule>();
        for (Entry<String, ContentObjectModule> k : this.objectModules.entrySet()) {
            c.add(k.getValue());
        }
        return c;
    }

    public void cleanUnusedModule(List<String> usemoduleIds) {
        for (String id : usemoduleIds) {
            if (!this.objectModules.containsKey(id)) {
                this.objectModules.remove(id);
            }
        }
    }

    public void removeObjectModule(String moduleIds) {
        String[] ids = moduleIds.split(",");
        for (String a : ids) {
            if (this.objectModules.containsKey(a)) {
                this.provider.removeObjectModule(this.objectModules.get(a));
                this.helper.remove(this.objectModules.get(a));
                this.objectModules.remove(a);
            }
        }
    }

    public ContentModule getTemplateModule(String moduleType) {
        return this.templateModules.get(moduleType);
    }

    public String getObjectModuleString(String moduleId, TemplateScope scope) throws Exception {
        ContentObjectModule module = this.getObjectModule(moduleId);
        if (null != module) {
            HashMap<String, Object> map = ObjectSnooper.snoop(this.getTemplateModule(module.getModuleType())).toHashMap();
            return this.templater.getRender()
                    .setSessionAttribute(scope.getSessionAttributes())
                    .setData(scope.getData()).setData("Option", map).setData("Values", Jsonx.create(module.getValues()).toHashMap())
                    .getContent(module.getModuleType());
        } else {
            return "[no module]";
        }
    }

    public String getObjectModuleString(ContentObjectModule module, TemplateScope scope) throws Exception {
        HashMap<String, Object> map = ObjectSnooper.snoop(this.getTemplateModule(module.getModuleType())).toHashMap();
        return this.templater.getRender()
                .setSessionAttribute(scope.getSessionAttributes())
                .setData(scope.getData()).setData("Option", map).setData("Values", Jsonx.create(module.getValues()).toHashMap())
                .getContent(module.getModuleType());
    }

    public String getObjectModuleClientString(String moduleId, TemplateScope scope) throws Exception {
        ContentObjectModule module = this.getObjectModule(moduleId);
        if (null != module) {
            ContentModule md = this.getTemplateModule(module.getModuleType());
            HashMap<String, Object> map = ObjectSnooper.snoop(md).toHashMap();
            return "<div module='"
                    + module.getModuleType()
                    + "' removeable='"
                    + md.isRemoveable()
                    + "' data-view='"
                    + md.getView()
                    + "' data-parent-view='root' data-option='"
                    + module.getOption()
                    + "' data-view-id='"
                    + module.getId()
                    + "'>"
                    + this.templater.getRender()
                    .setSessionAttribute(scope.getSessionAttributes())
                    .setData(scope.getData()).setData("Option", map).setData("Values", Jsonx.create(module.getValues()).toHashMap())
                    .getContent(module.getModuleType()) + "</div>";
        } else {
            return "[no module]";
        }
    }

    public String getObjectModuleClientString(ContentObjectModule module, TemplateScope scope) throws Exception {
        ContentModule md = this.getTemplateModule(module.getModuleType());
        HashMap<String, Object> map = ObjectSnooper.snoop(md).toHashMap();
        return "<div module='"
                + module.getModuleType()
                + "' removeable='"
                + md.isRemoveable()
                + "' data-view='"
                + md.getView()
                + "' data-parent-view='root' data-option='"
                + module.getOption()
                + "' data-view-id='"
                + module.getId()
                + "'>"
                + this.templater.getRender()
                .setSessionAttribute(scope.getSessionAttributes())
                .setData(scope.getData()).setData("Option", map).setData("Values", Jsonx.create(module.getValues()).toHashMap())
                .getContent(module.getModuleType()) + "</div>";
    }

    public void out(String moduleId, TemplateScope scope, Writer writer) throws Exception {
        writer.write(this.getObjectModuleString(moduleId, scope));
        writer.close();
    }

    public void out(ContentObjectModule module, TemplateScope scope, Writer writer) throws Exception {
        writer.write(this.getObjectModuleString(module, scope));
        writer.close();
    }

    public ContentModuleContainer addObjectModule(ContentObjectModule module) {
        this.objectModules.put(module.getId(), module);
        this.provider.saveObjectModule(module);
        this.helper.save(module);
        return this;
    }

    public ContentModuleContainer editObjectModule(ContentObjectModule module) {
        this.objectModules.put(module.getId(), module);
        this.provider.editObjectModule(module);
        this.helper.save(module);
        return this;
    }

    public ContentModuleContainer addObjectModules(List<ContentObjectModule> modules) {
        for (ContentObjectModule module : modules) {
            this.objectModules.put(module.getId(), module);
        }
        this.provider.saveObjectModuleList(modules);
        this.helper.saveAllModuleObjects(modules);
        return this;
    }

    public HashMap<String, List<HashMap<String, Object>>> getTemplateModulesWithSort() {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
        for (Entry<String, ContentModule> modules : this.templateModules.entrySet()) {
            ContentModule module = modules.getValue();
            if (map.containsKey(module.getSort()) == false) {
                List<HashMap<String, Object>> moduleList = new ArrayList<HashMap<String, Object>>();
                moduleList.add(ObjectSnooper.snoop(module).toHashMap());
                map.put(module.getSort(), moduleList);
            } else {
                map.get(module.getSort()).add(ObjectSnooper.snoop(module).toHashMap());
            }
        }
        return map;
    }

    public HashMap<String, List<HashMap<String, Object>>> getTemplateModuleInfosWithSort() {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
        for (Entry<String, ContentModule> modules : this.templateModules.entrySet()) {
            ContentModule module = modules.getValue();
            HashMap<String, Object> t = new HashMap<String, Object>();
            t.put("icon", module.getIcon());
            t.put("desc", module.getDesc());
            t.put("version", module.getVersion());
            t.put("values", module.getValues());
            t.put("single", module.isSingle());
            t.put("editable", module.getEditable());
            t.put("view", module.getView());
            t.put("fields", module.getFields());
            t.put("template", module.getTemplate());
            t.put("type", module.getType());
            t.put("editSetting", module.getEditSetting());
            t.put("sort", module.getSort());
            t.put("template", module.getTemplate());
            t.put("removeable", module.isRemoveable());
            if (map.containsKey(module.getSort()) == false) {
                List<HashMap<String, Object>> moduleList = new ArrayList<HashMap<String, Object>>();
                moduleList.add(t);
                map.put(module.getSort(), moduleList);
            } else {
                map.get(module.getSort()).add(t);
            }
        }
        return map;
    }

    public void editOrAddModule(String moduleId, String moduleType, String values) {
        ContentObjectModule module = this.getObjectModule(moduleId);
        if (null == values || "".equals(values) || "null".equals(values)) {
            if (null != module) {
                values = module.getValues();
            } else {
                values = this.getTemplateModule(moduleType).getValues();
            }
        }
        if (null == module) {
            ContentObjectModule me = new ContentObjectModule();
            me.setId(moduleId);
            me.setValues(values);
            me.setModuleType(moduleType);
            this.addObjectModule(me);
        } else {
            ContentObjectModule modul = this.getObjectModule(moduleId);
            modul.setValues(values);
            this.provider.editObjectModule(modul);
            this.helper.save(modul);
        }
    }

    private String getWebInfoPath() {
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        String path = url.toString();
        int index = path.indexOf("WEB-INF");

        if (index == -1) {
            index = path.indexOf("classes");
        }

        if (index == -1) {
            index = path.indexOf("bin");
        }

        path = path.substring(0, index);

        if (path.startsWith("zip")) {
            path = path.substring(4);
        } else if (path.startsWith("file")) {
            path = path.substring(6);
        } else if (path.startsWith("jar")) {
            path = path.substring(10);
        }
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return path;
    }

    public void saveTemplateModuleInfo(ContentModule module) {
        ContentModule a = this.getTemplateModule(module.getType());
        boolean has = true;
        if (null == a) {
            a = new ContentModule();
            a.setSingle(module.isSingle());
            a.setFields(module.getFields());
            has = false;
        } else {
            if (a.isSingle() == true && module.isSingle() == false) {
                this.setObjectModulesToUnsingle(a);
            }
            if (a.isSingle() == false && module.isSingle() == true) {
                this.setObjectModulesToSingle(a);
            }
            if (!module.getFields().equals(a.getFields())) {
                a.setFields(module.getFields());
                this.resetObjectModules(a);
            }
        }
        a.setEditable(module.getEditable());
        a.setDesc(module.getDesc());
        a.setIcon(module.getIcon());
        a.setSingle(module.isSingle());
        a.setSort(module.getSort());
        a.setTemplate(null != module.getTemplate() && !module.getTemplate().equals("") ? module.getTemplate() : "<div></div>");
        a.setType(module.getType());
        a.setVersion(module.getVersion());
        a.setView(module.getView());
        a.setEditSetting(module.getEditSetting());
        a.setRemoveable(module.isRemoveable());
        this.templateModules.put(a.getType(), a);
        this.templater.setTemplate(a.getType(), a.getTemplate());
        if (!has) {
            this.provider.saveTemplateModule(a);
            this.helper.save(a);
        } else {
            this.provider.editTemplateModule(a);
            this.helper.save(a);
        }
    }

    private void resetObjectModules(ContentModule module) {
        for (Entry<String, ContentObjectModule> k : this.objectModules.entrySet()) {
            if (k.getValue().getModuleType().equals(module.getType())) {
                k.getValue().setValues(module.getValues());
                this.helper.save(k.getValue());
            }
        }
    }

    private void setObjectModulesToSingle(ContentModule module) {
        module.setSingle(true);
        List<String> t = new ArrayList<>();
        for (Entry<String, ContentObjectModule> k : this.objectModules.entrySet()) {
            if (k.getValue().getModuleType().equals(module.getType())) {
                Contenter.getContenter().getContentPageContainer().resetPageModuleId(k.getValue().getId(), module.getType());
                t.add(k.getValue().getId());
                this.provider.removeObjectModule(k.getValue());
                this.helper.remove(k.getValue());
            }
        }
        for (String p : t) {
            this.objectModules.remove(p);
        }
        ContentObjectModule om = new ContentObjectModule();
        om.setModuleType(module.getType());
        om.setId(module.getType());
        om.setValues(module.getValues());
        this.objectModules.put(module.getType(), om);
        this.provider.saveObjectModule(om);
        this.helper.save(om);
    }

    private void setObjectModulesToUnsingle(ContentModule module) {
        module.setSingle(false);
        ContentObjectModule md = this.objectModules.get(module.getType());
        if (null != md) {
            Contenter.getContenter().getContentPageContainer().resetPageModuleId(md.getId(), new ContentPageModuleIdEach(module, this) {
                @Override
                public String each(String original) {
                    ContentModule m = (ContentModule) arguments[0];
                    ContentModuleContainer c = (ContentModuleContainer) arguments[1];
                    ContentObjectModule om = new ContentObjectModule();
                    String id = Contenter.getUUID();
                    om.setModuleType(m.getType());
                    om.setId(id);
                    om.setValues(m.getValues());
                    c.objectModules.put(id, om);
                    c.provider.saveObjectModule(om);
                    c.helper.save(om);
                    return id;
                }
            });
            this.objectModules.remove(module.getType());
            this.provider.removeObjectModule(md);
            this.helper.remove(md);
        }
    }

    public void resetProvider() throws Exception {
        this.provider.removeAllObjectModule();
        this.helper.removeAllModuleObjects();
        this.provider.saveObjectModuleList(this.getAllObjectModules());
        this.helper.saveAllModuleObjects(this.getAllObjectModules());
        this.provider.removeAllObjectModule();
        this.helper.removeAllObjects();
        this.provider.saveTemplateModuleList(this.getAllTemplateModules());
        this.helper.saveAllModules(this.getAllTemplateModules());
    }

    public String resetModuleIcon(File file) throws IOException {
        String name = Contenter.getUUID() + ".png";
        Jile.with(file).move(Contenter.getContenter().getContenterOption().getAssetsPath() + File.separator + "icons" + File.separator + name);
        this.helper.setIcon(Jile.with(Contenter.getContenter().getContenterOption().getAssetsPath() + File.separator + "icons" + File.separator + name).file(), name);
        return name;
    }
}
