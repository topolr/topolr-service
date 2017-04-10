package com.topolr.contenter.module;

import com.topolr.contenter.data.ContentModule;
import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.file.Jile;
import com.topolr.util.file.JileEach;
import com.topolr.util.jsonx.Jsonx;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class FileContentModuleProvider extends BaseContentModuleProvider {

    @Override
    public HashMap<String, ContentObjectModule> getAllObjectModules() {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        String moduleTemplatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter" + File.separator + "_moduleobject_";
        HashMap<String, ContentObjectModule> map = new HashMap<String, ContentObjectModule>();
        try {
            Jile.with(moduleTemplatePath).each(new JileEach(map) {
                @Override
                public boolean each(Jile file) throws Exception {
                    HashMap<String, ContentObjectModule> map = (HashMap<String, ContentObjectModule>) this.arguments[0];
                    Jsonx json = Jsonx.create(file.file());
                    String name = json.get("id").toString();
                    ContentObjectModule module = new ContentObjectModule();
                    ObjectSnooper.snoop(module).sett(json.toHashMap());
                    map.put(name, module);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void saveObjectModule(ContentObjectModule module) {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        String moduleTemplatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter" + File.separator + "_moduleobject_";
        String id = module.getId();
        try {
            Jsonx.create(module).toFile(moduleTemplatePath + File.separator + id + ".json");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeObjectModule(ContentObjectModule module) {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        String moduleTemplatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter" + File.separator + "_moduleobject_";
        String id = module.getId();
        Jile.with(moduleTemplatePath + File.separator + id + ".json").remove();
    }

    @Override
    public void saveObjectModuleList(List<ContentObjectModule> modules) {
        for (ContentObjectModule module : modules) {
            this.saveObjectModule(module);
        }
    }

    @Override
    public void removeObjectModuleList(List<ContentObjectModule> modules) {
        for (ContentObjectModule module : modules) {
            this.removeObjectModule(module);
        }
    }

    @Override
    public void removeAllObjectModule() {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        String moduleTemplatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter" + File.separator + "_moduleobject_";
        try {
            Jile.with(moduleTemplatePath).each(new JileEach() {
                @Override
                public boolean each(Jile file) throws Exception {
                    file.remove();
                    return false;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void editObjectModule(ContentObjectModule module) {
        this.saveObjectModule(module);
    }

    @Override
    public void saveTemplateModule(ContentModule module) {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        String moduleTemplatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter" + File.separator + "_module_";
        String id = module.getType();
        try {
            Jsonx.create(module).toFile(moduleTemplatePath + File.separator + id + ".json");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void editTemplateModule(ContentModule module) {
        this.saveTemplateModule(module);
    }

    @Override
    public void removeTemplateModule(ContentModule module) {
        File xmt = new File(this.getClass().getClassLoader().getResource("/").getPath());
        String moduleTemplatePath = xmt.getParentFile().getAbsolutePath() + File.separator + "contenter" + File.separator + "_moduleobject_";
        String id = module.getType();
        Jile.with(moduleTemplatePath + File.separator + id + ".json").remove();
    }

    @Override
    public HashMap<String, ContentModule> getAllTemplateModules() {
        return null;
    }

    @Override
    public void removeAllTemplateModules() {
    }

    @Override
    public void saveTemplateModuleList(List<ContentModule> list) {
        for (ContentModule module : list) {
            this.saveTemplateModule(module);
        }
    }

}
