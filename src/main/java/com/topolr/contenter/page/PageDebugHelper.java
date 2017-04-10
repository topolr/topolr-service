package com.topolr.contenter.page;

import com.topolr.contenter.ContenterOption;
import com.topolr.contenter.data.ContentPage;
import com.topolr.util.file.Jile;
import com.topolr.util.file.JileEach;
import com.topolr.util.jsonx.Jsonx;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class PageDebugHelper {

    private final boolean debug;
    private final String pagePath;
    private final String jsPath;
    private final String cssPath;
    private final String imagePath;

    public PageDebugHelper(ContenterOption option) {
        this.debug = option.isDebug();
        this.pagePath = option.getDebugPagePath();
        this.jsPath = option.getDebugJsPath();
        this.cssPath = option.getDebugCssPath();
        this.imagePath = option.getDebugImagePath();
    }

    public void save(ContentPage page) {
        if (this.debug) {
            try {
                Jsonx.create(page).toFile(this.pagePath + File.separator + page.getId() + ".json");
            } catch (Exception ex) {
                Logger.getLogger(PageDebugHelper.class).debug(ex.getMessage());
            }
        }
    }

    public void saveAllPages(List<ContentPage> pages) {
        if (this.debug) {
            for (ContentPage page : pages) {
                this.save(page);
            }
        }
    }

    public void remove(ContentPage page) {
        if (this.debug) {
            Jile.with(this.pagePath + File.separator + page.getId() + ".json").remove();
        }
    }

    public void removeAllPages(List<ContentPage> pages) {
        if (this.debug) {
            for (ContentPage page : pages) {
                this.remove(page);
            }
        }
    }

    public void removeAllPages() {
        if (this.debug) {
            String moduleTemplatePath = this.pagePath;
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

    public void saveJs(String content, String fileName) {
        if (this.debug) {
            try {
                Jile.with(this.jsPath + File.separator + fileName).write(content);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveCss(String content, String fileName) {
        if (this.debug) {
            try {
                Jile.with(this.cssPath + File.separator + fileName).write(content);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveImage(File file) {
        if (this.debug) {
            try {
                Jile.with(file).copy(this.imagePath + File.separator + file.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
