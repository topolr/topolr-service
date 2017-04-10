package com.topolr.contenter.module;

import com.topolr.contenter.ContenterOption;
import com.topolr.contenter.data.ContentModule;
import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.util.file.Jile;
import com.topolr.util.file.JileEach;
import com.topolr.util.jsonx.Jsonx;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class ModuleDebugHelper {

    private final boolean debug;
    private final String moduleObjectPath;
    private final String modulePath;
    private final String iconPath;

    public ModuleDebugHelper(ContenterOption option) {
        this.debug = option.isDebug();
        this.moduleObjectPath = option.getDebugModuleObjectPath();
        this.modulePath = option.getDebugModulePath();
        this.iconPath = option.getDebugIconPath();
    }

    public void save(ContentObjectModule objectmodule) {
        if (this.debug) {
            try {
                Jsonx.create(objectmodule).toFile(this.moduleObjectPath + File.separator + objectmodule.getId() + ".json");
            } catch (Exception ex) {
                Logger.getLogger(ModuleDebugHelper.class).debug(ex.getMessage());
            }
        }
    }

    public void saveAllModuleObjects(List<ContentObjectModule> objectmodules) {
        if (this.debug) {
            for (ContentObjectModule module : objectmodules) {
                this.save(module);
            }
        }
    }

    public void remove(ContentObjectModule objectmodule) {
        if (this.debug) {
            Jile.with(this.moduleObjectPath + File.separator + objectmodule.getId() + ".json").remove();
        }
    }

    public void removeAllModuleObjects(List<ContentObjectModule> objectmodules) {
        if (this.debug) {
            for (ContentObjectModule objectmodule : objectmodules) {
                this.remove(objectmodule);
            }
        }
    }

    public void removeAllModuleObjects() {
        if (this.debug) {
            String moduleTemplatePath = this.moduleObjectPath;
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
    }

    public void save(ContentModule module) {
        if (this.debug) {
            try {
                Jsonx.create(module).toFile(this.modulePath + File.separator + module.getType() + ".json");
            } catch (Exception ex) {
                Logger.getLogger(ModuleDebugHelper.class).debug(ex.getMessage());
            }
        }
    }

    public void saveAllModules(List<ContentModule> modules) {
        if (this.debug) {
            for (ContentModule module : modules) {
                this.save(module);
            }
        }
    }

    public void remove(ContentModule module) {
        if (this.debug) {
            Jile.with(this.modulePath + File.separator + module.getType() + ".json").remove();
        }
    }

    public void removeAllObjects(List<ContentModule> modules) {
        if (this.debug) {
            for (ContentModule module : modules) {
                this.remove(module);
            }
        }
    }

    public void removeAllObjects() {
        if (this.debug) {
            String moduleTemplatePath = this.modulePath;
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
    }

    public void setIcon(File file, String name) {
        if (this.debug) {
            try {
                Jile.with(file).copy(this.iconPath + File.separator + name);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(ModuleDebugHelper.class).debug(ex.getMessage());
            }
        }
    }
}
