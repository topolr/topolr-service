package com.topolr.contenter.page;

import com.topolr.contenter.Contenter;
import com.topolr.contenter.ContenterOption;
import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.contenter.data.ContentPage;
import com.topolr.contenter.extend.JsonLog;
import com.topolr.contenter.extend.JsonMethod;
import com.topolr.contenter.extend.PageTransform;
import com.topolr.contenter.template.StringTemplater;
import com.topolr.contenter.template.TemplateMacroManager;
import com.topolr.contenter.template.TemplateScope;
import com.topolr.contenter.template.macro.ContentMacroData;
import com.topolr.contenter.template.macro.ContentMacroModule;
import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.file.Jile;
import com.topolr.util.file.JileEach;
import com.topolr.util.jsonx.Jsonx;
import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentPageContainer {

    private final HashMap<String, ContentPage> contentTemplatePage;
    private final BaseContentPageProvider provider;
    private final PageDebugHelper helper;
    private final StringTemplater templater;

    public ContentPageContainer(ContenterOption option) throws Exception {
        this.provider = option.getPageProvider();
        this.helper = option.getPageHelper();
        this.contentTemplatePage = new HashMap<String, ContentPage>();
        this.templater = new StringTemplater("utf-8");
        this.templater.setException(option.getExceptionHandler());
        this.templater.setDirective("module", new ContentMacroModule());
        this.templater.setDirective("data", new ContentMacroData());
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
        this.readTemplagePages(option);
        this.contentTemplatePage.putAll(provider.getAllContentPages());
        for (Entry<String, ContentPage> entry : this.contentTemplatePage.entrySet()) {
            ContentPage page = entry.getValue();
            this.templater.setTemplate(page.getId(), page.getTemplate());
        }
    }

    private void readTemplagePages(ContenterOption option) throws Exception {
        final ContentPageContainer pc = this;
        Jile.with(option.getTemplatePath() + File.separator + "pages").browse(new JileEach(pc) {
            @Override
            public boolean each(Jile file) throws Exception {
                ContentPageContainer pc = (ContentPageContainer) this.arguments[0];
                if (file.file().isFile()) {
                    if (file.getNameWithoutSuffix().equals("option")) {
                        Jsonx json = Jsonx.create(file.file());
                        ContentPage page = new ContentPage();
                        HashMap<String, Object> map = json.toHashMap();
                        String tem = Jile.with(file.file().getParentFile().getAbsolutePath() + "/template.html").read();
                        tem = tem.replaceAll("/>[\\s]+</g", "").replaceAll("/\\r\\n/g", "").replaceAll("/\\n/g", "").replaceAll("/\\r/g", "");
                        map.put("template", tem);
                        ObjectSnooper.snoop(page).sett(map);
                        pc.contentTemplatePage.put(json.get("id").toString(), page);
                    }
                }
                return false;
            }
        });
        Jile.with(option.getTemplatePath() + File.separator + "mapping" + File.separator + "pages").browse(new JileEach(pc) {
            @Override
            public boolean each(Jile file) throws Exception {
                ContentPageContainer pc = (ContentPageContainer) this.arguments[0];
                ContentPage page = new ContentPage(Jsonx.create(file.file()));
                pc.contentTemplatePage.put(page.getId(), page);
                return false;
            }
        });
    }

    public List<ContentPage> getAllContentPages() {
        List<ContentPage> list = new ArrayList<ContentPage>();
        for (Entry<String, ContentPage> entry : this.contentTemplatePage.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public ContentPage getContentPage(String pageId) {
        ContentPage page = this.contentTemplatePage.get(pageId);
        if (null == page) {
            page = this.contentTemplatePage.get("404");
        }
        return page;
    }

    public String getContentPageString(String pageId, TemplateScope scope) throws Exception {
        ContentPage page = this.getContentPage(pageId);
        return this.templater
                .getRender()
                .setSessionAttribute(scope.getSessionAttributes())
                .setData(scope.getData())
                .setData("Option", ObjectSnooper.snoop(this.getContentPage(page.getId())).toHashMap()).getContent(page.getId());
    }

    public String getContentPageString(ContentPage page, TemplateScope scope) throws Exception {
        return this.templater.getRender()
                .setSessionAttribute(scope.getSessionAttributes())
                .setData(scope.getData())
                .setData("Option", ObjectSnooper.snoop(page).toHashMap())
                .getContent(page.getId());
    }

    public void out(String pageId, TemplateScope scope, Writer writer) throws Exception {
        ContentPage page = this.getContentPage(pageId);
        this.templater.getRender()
                .setSessionAttribute(scope.getSessionAttributes())
                .setData(scope.getData())
                .setData("Option", ObjectSnooper.snoop(this.getContentPage(page.getId())).toHashMap()).out(page.getId(), writer);
    }

    public void out(ContentPage page, TemplateScope scope, Writer writer) throws Exception {
        this.templater.getRender()
                .setSessionAttribute(scope.getSessionAttributes())
                .setData(scope.getData())
                .setData("Option", ObjectSnooper.snoop(page).toHashMap())
                .out(page.getId(), writer);
    }

    public void cloneNewContentPage(String pageId, String pageTile, String pageDesc, String pagekeywords, String pagecontent, String pageurl) {
        ContentPage base = this.contentTemplatePage.get(pageId);
        if (null == base) {
            base = this.contentTemplatePage.get("base");
        }
        String temp = base.getTemplate();
        Pattern pattern = Pattern.compile("<@module id=\"[0-9A-Za-z]*\"></@module>");
        Matcher matcher = pattern.matcher(temp);
        List<HashMap<String, String>> t = new ArrayList<HashMap<String, String>>();
        StringBuffer sbr = new StringBuffer();
        while (matcher.find()) {
            String k = Contenter.getUUID();
            String kp = "";
            String[] tt = matcher.group().split("\"");
            if (tt.length > 1) {
                kp = matcher.group().split("\"")[1];
            }
            matcher.appendReplacement(sbr, "<@module id=\"" + k + "\"></@module>");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("old", kp);
            map.put("new", k);
            t.add(map);
        }
        matcher.appendTail(sbr);
        temp = sbr.toString();
        for (HashMap<String, String> map : t) {
            ContentObjectModule module = Contenter.getContenter().getContentModuleContainer().getObjectModule(map.get("old"));
            ContentObjectModule newModule = new ContentObjectModule();
            ObjectSnooper.snoop(newModule).set(ObjectSnooper.snoop(module).toHashMap());
            newModule.setId(map.get("new"));
            Contenter.getContenter().getContentModuleContainer().addObjectModule(newModule);
        }

        ContentPage page = new ContentPage();
        page.setId(Contenter.getUUID());
        page.setAuthor(base.getAuthor());
        page.setDesc(pageDesc);
        page.setTitle(pageTile);
        page.setEditable(true);
        page.setLogo(base.getLogo());
        page.setSort("customer");
        page.setTemplate(temp);
        page.setPageurl(pageurl);
        // page.setTemplate(base.getTemplate());
        page.setKeywords(pagekeywords);
        page.setContent(pagecontent);
        this.contentTemplatePage.put(page.getId(), page);
        this.templater.setTemplate(page.getId(), page.getTemplate());
        this.provider.saveContentPage(page);
        this.helper.save(page);
    }

    public void resetPageModuleId(String original, String newId) {
        for (Entry<String, ContentPage> t : this.contentTemplatePage.entrySet()) {
            String temp = t.getValue().getTemplate();
            Pattern pattern = Pattern.compile("<@module id=\"[0-9A-Za-z]*\"></@module>");
            Matcher matcher = pattern.matcher(temp);
            StringBuffer sbr = new StringBuffer();
            while (matcher.find()) {
                String kp = "";
                String[] tt = matcher.group().split("\"");
                if (tt.length > 1) {
                    kp = matcher.group().split("\"")[1];
                }
                if (kp.equals(original)) {
                    matcher.appendReplacement(sbr, "<@module id=\"" + newId + "\"></@module>");
                }
            }
            matcher.appendTail(sbr);
            t.getValue().setTemplate(sbr.toString());
        }
    }

    public void resetPageModuleId(String original, ContentPageModuleIdEach each) {
        for (Entry<String, ContentPage> t : this.contentTemplatePage.entrySet()) {
            String temp = t.getValue().getTemplate();
            Pattern pattern = Pattern.compile("<@module id=\"[0-9A-Za-z]*\"></@module>");
            Matcher matcher = pattern.matcher(temp);
            StringBuffer sbr = new StringBuffer();
            while (matcher.find()) {
                String kp = "";
                String[] tt = matcher.group().split("\"");
                if (tt.length > 1) {
                    kp = matcher.group().split("\"")[1];
                }
                if (kp.equals(original)) {
                    matcher.appendReplacement(sbr, "<@module id=\"" + each.each(original) + "\"></@module>");
                }
            }
            matcher.appendTail(sbr);
            t.getValue().setTemplate(sbr.toString());
        }
    }

    public String getBasePageString() {
        ContentPage base = this.contentTemplatePage.get("base");
        if (null != base) {
            return base.getTemplate();
        } else {
            return "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>brooder page editor</title></head></body></html>";
        }
    }

    public void editContentPageInfo(String pageId, String pageTile, String pageDesc, String pagekeywords, String pagecontent, String pageurl) {
        ContentPage base = this.contentTemplatePage.get(pageId);
        if (null != base) {
            ContentPage page = base;
            page.setDesc(pageDesc);
            page.setTitle(pageTile);
            page.setKeywords(pagekeywords);
            page.setContent(pagecontent);
            page.setPageurl(pageurl);
            this.provider.editContentPage(page);
            this.helper.save(page);
        }
    }

    public ContentPageContainer addContentPage(ContentPage page) {
        this.contentTemplatePage.put(page.getId(), page);
        this.templater.setTemplate(page.getId(), page.getTemplate());
        this.provider.saveContentPage(page);
        this.helper.save(page);
        return this;
    }

    public ContentPageContainer addContentPages(List<ContentPage> pages) {
        for (ContentPage page : pages) {
            this.contentTemplatePage.put(page.getId(), page);
            this.templater.setTemplate(page.getId(), page.getTemplate());
        }
        this.provider.saveContentPageList(pages);
        this.helper.saveAllPages(pages);
        return this;
    }

    public void removeContentPage(ContentPage page) {
        this.contentTemplatePage.remove(page.getId());
        Pattern pattern = Pattern.compile("id=\"[0-9a-zA-Z-]+\"");
        Matcher matcher = pattern.matcher(page.getTemplate());
        String ids = "";
        while (matcher.find()) {
            ids += (matcher.group().substring(4, matcher.group().length() - 1))
                    + ",";
        }
        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
        }
        Contenter.getContenter().getContentModuleContainer().removeObjectModule(ids);
        this.provider.removeContentPage(page);
        this.helper.remove(page);
    }

    public void removeContentPages(List<ContentPage> pages) {
        for (ContentPage page : pages) {
            this.removeContentPage(page);
        }
    }

    public void saveContentPage(ContentPage page) {
        this.contentTemplatePage.put(page.getId(), page);
        this.templater.setTemplate(page.getId(), page.getTemplate());
        if (null != this.contentTemplatePage.get(page.getId())) {
            this.provider.editContentPage(page);
            this.helper.save(page);
        } else {
            this.provider.saveContentPage(page);
            this.helper.save(page);
        }
    }

    public HashMap<String, List<HashMap<String, Object>>> getContentPageWithSort() {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
        for (Entry<String, ContentPage> entry : this.contentTemplatePage.entrySet()) {
            String sort = entry.getValue().getSort();
            HashMap<String, Object> mapt = ObjectSnooper.snoop(entry.getValue()).toHashMap();
            if (null == map.get(sort)) {
                List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                list.add(mapt);
                map.put(sort, list);
            } else {
                map.get(sort).add(mapt);
            }
        }
        return map;
    }

    public HashMap<String, List<HashMap<String, Object>>> getEditableContentPageWithSort() {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
        for (Entry<String, ContentPage> entry : this.contentTemplatePage.entrySet()) {
            if (entry.getValue().getEditable()) {
                String sort = entry.getValue().getSort();
                if (null == map.get(sort)) {
                    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                    list.add(ObjectSnooper.snoop(entry.getValue()).toHashMap());
                    map.put(sort, list);
                } else {
                    map.get(sort).add(ObjectSnooper.snoop(entry.getValue()).toHashMap());
                }
            }
        }
        return map;
    }

    public List<String> getAllModulesUsed() {
        List<String> list = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();
        for (Entry<String, ContentPage> page : this.contentTemplatePage.entrySet()) {
            String template = page.getValue().getTemplate();
            Pattern pattern = Pattern.compile("<@module id=\"[a-zA-Z0-9]*\"></@module>");
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                String[] t = matcher.group().split("\"");
                if (t.length > 1) {
                    map.put(matcher.group().split("\"")[1], "");
                }
            }
        }
        for (Entry<String, String> tn : map.entrySet()) {
            list.add(tn.getKey());
        }
        return list;
    }

    public List<HashMap<String, String>> getAllPagesMaping() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (Entry<String, ContentPage> page : this.contentTemplatePage.entrySet()) {
            HashMap<String, String> info = new HashMap<String, String>();
            info.put("key", page.getValue().getTitle());
            info.put("value", page.getValue().getId());
            list.add(info);
        }
        return list;
    }

    public void savePageInfo(ContentPage page) throws Exception {
        ContentPage p = this.contentTemplatePage.get(page.getId());
        if (null != p) {
            p.setAuthor(page.getAuthor());
            p.setContent(page.getContent());
            p.setDesc(page.getDesc());
            p.setKeywords(page.getKeywords());
            p.setEditable(page.getEditable());
            p.setPageurl(page.getPageurl());
            p.setSort(page.getSort());
            p.setTemplate(page.getTemplate());
            p.setTitle(page.getTitle());
            p.setLogo(page.getLogo());
            this.templater.setTemplate(p.getId(), p.getTemplate());
            this.provider.editContentPage(p);
            this.helper.save(p);
        } else {
            page.setTemplate(page.getTemplate());
            this.contentTemplatePage.put(page.getId(), page);
            this.templater.setTemplate(page.getId(), page.getTemplate());
            this.provider.saveContentPage(page);
            this.helper.save(page);
        }
    }

    public void resetProvider() throws Exception {
        this.provider.removeAllContentPage();
        this.provider.saveContentPageList(this.getAllContentPages());
        this.helper.removeAllPages();
        this.helper.saveAllPages(this.getAllContentPages());
    }
    
    public PageDebugHelper getHelper(){
        return this.helper;
    }

}
