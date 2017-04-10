package com.topolr.contenter;

import com.topolr.contenter.data.ContentModule;
import com.topolr.contenter.data.ContentObjectModule;
import com.topolr.contenter.data.ContentPage;
import com.topolr.contenter.module.ContentModuleContainer;
import com.topolr.contenter.page.ContentPageContainer;
import com.topolr.contenter.template.TemplateMacroManager;
import com.topolr.contenter.template.TemplateScope;
import com.topolr.contenter.template.macro.QueryHandler;
import com.topolr.contenter.template.macro.RequestScope;
import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.file.Jile;
import com.topolr.util.file.JileEach;
import com.topolr.util.jsonx.JsonEachArray;
import com.topolr.util.jsonx.Jsonx;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Contenter {
    
    private ContentModuleContainer moduleContainer;
    private ContentPageContainer pageContainer;
    private static Contenter contenter;
    private ContenterOption option;
    
    public static String getUUID() {
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-", "").substring(0, 16);
    }
    
    private Contenter(ContenterOption option) throws Exception {
        TemplateMacroManager.init(option);
        this.moduleContainer = new ContentModuleContainer(option);
        this.pageContainer = new ContentPageContainer(option);
        this.option = option;
    }
    
    public static Contenter init(ContenterOption option) throws Exception {
        if (null == Contenter.contenter) {
            Contenter.contenter = new Contenter(option);
        }
        return Contenter.contenter;
    }
    
    public static Contenter getContenter() {
        return Contenter.contenter;
    }
    
    public ContenterOption getContenterOption() {
        return this.option;
    }
    
    public ContentModuleContainer getContentModuleContainer() {
        return this.moduleContainer;
    }
    
    public ContentPageContainer getContentPageContainer() {
        return this.pageContainer;
    }
    
    public void refresh() throws Exception {
        TemplateMacroManager.init(option);
        this.moduleContainer = new ContentModuleContainer(option);
        this.pageContainer = new ContentPageContainer(option);
        this.moduleContainer.cleanUnusedModule(this.pageContainer.getAllModulesUsed());
        this.pageContainer.resetProvider();
        this.moduleContainer.resetProvider();
        this.cleanModulesNoUse();
    }
    
    public void renderPage(String pageId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        RequestScope rscope = new RequestScope(request);
        TemplateScope scope = new TemplateScope();
        scope.setSessionAttribute("requestScope", rscope);
        scope.setSessionAttribute("queryHandler", new QueryHandler(request));
        scope.setData(rscope.getRequestScope());
        this.pageContainer.out(pageId, scope, response.getWriter());
    }
    
    public String getPageString(String pageId, HttpServletRequest request) throws Exception {
        RequestScope rscope = new RequestScope(request);
        TemplateScope scope = new TemplateScope();
        scope.setSessionAttribute("requestScope", rscope);
        scope.setSessionAttribute("queryHandler", new QueryHandler(request));
        scope.setData(rscope.getRequestScope());
        return this.pageContainer.getContentPageString(pageId, scope);
    }
    
    public void renderModule(String moduleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        RequestScope rscope = new RequestScope(request);
        TemplateScope scope = new TemplateScope();
        scope.setSessionAttribute("requestScope", rscope);
        scope.setSessionAttribute("queryHandler", new QueryHandler(request));
        scope.setData(rscope.getRequestScope());
        this.moduleContainer.out(moduleId, scope, response.getWriter());
    }
    
    public HashMap<String, Object> getNewClientModule(String moduleType, HttpServletRequest request) throws Exception {
        ContentModule md = this.moduleContainer.getTemplateModule(moduleType);
        ContentObjectModule module = new ContentObjectModule();
        if (md.isSingle()) {
            module.setId(md.getType());
        } else {
            module.setId(Contenter.getUUID());
        }
        module.setModuleType(moduleType);
        if(md.isSingle()){
            if(null!=this.moduleContainer.getObjectModule(md.getType())){
                module.setValues(this.moduleContainer.getObjectModule(md.getType()).getValues());
            }else{
                module.setValues(md.getValues());
            }
        }else{
            module.setValues(md.getValues());
        }
        RequestScope rscope = new RequestScope(request);
        TemplateScope scope = new TemplateScope();
        scope.setSessionAttribute("requestScope", rscope);
        scope.setSessionAttribute("queryHandler", new QueryHandler(request));
        scope.setData(rscope.getRequestScope());
        HashMap<String, Object> info = ObjectSnooper.snoop(md).toHashMap();
        info.put("id", module.getId());
        info.put("values", module.getValues());
        info.put("type", module.getModuleType());
        info.put("sort", md.getSort());
        info.put("templateString",this.moduleContainer.getObjectModuleString(module, scope));
        return info;
    }
    
    public HashMap<String, Object> getClientModuleInfo(String moduleId, HttpServletRequest request) throws Exception {
        ContentObjectModule module = this.moduleContainer.getObjectModule(moduleId);
        ContentModule md = this.moduleContainer.getTemplateModule(module.getModuleType());
        RequestScope rscope = new RequestScope(request);
        TemplateScope scope = new TemplateScope();
        scope.setSessionAttribute("requestScope", rscope);
        scope.setSessionAttribute("queryHandler", new QueryHandler(request));
        scope.setData(rscope.getRequestScope());
        HashMap<String, Object> info = ObjectSnooper.snoop(md).toHashMap();
        info.put("id", module.getId());
        info.put("values", module.getValues());
        info.put("type", module.getModuleType());
        info.put("sort", md.getSort());
        info.put("templateString",this.moduleContainer.getObjectModuleString(module, scope));
        return info;
    }
    
    public HashMap<String, Object> getClientModuleInfo(ContentObjectModule module, HttpServletRequest request) throws Exception {
        ContentModule md = this.moduleContainer.getTemplateModule(module.getModuleType());
        String values = "";
        ContentObjectModule realmodule = this.moduleContainer.getObjectModule(module.getId());
        if (null == module.getValues() || "".equals(module.getValues()) || "null".equals(module.getValues())) {
            if (null != realmodule) {
                values = realmodule.getValues();
            } else {
                values = md.getValues();
            }
            module.setValues(values);
        }
        RequestScope rscope = new RequestScope(request);
        TemplateScope scope = new TemplateScope();
        scope.setSessionAttribute("requestScope", rscope);
        scope.setSessionAttribute("queryHandler", new QueryHandler(request));
        scope.setSessionAttribute("objectModule", module);
        scope.setData(rscope.getRequestScope());
        HashMap<String, Object> info = ObjectSnooper.snoop(md).toHashMap();
        info.put("templateString", this.moduleContainer.getObjectModuleString(module, scope));
        info.put("id", module.getId());
        info.put("type", module.getModuleType());
        info.put("values", module.getValues());
        info.put("sort", md.getSort());
        return info;
    }
    
    public void save(Jsonx json) throws Exception {
        String pageName = json.get("pagename").toString();
        String template = json.get("template").toString();
        String deleteModules = json.get("deleteModules").toString();
        this.moduleContainer.removeObjectModule(deleteModules);
        ContentPage page = this.pageContainer.getContentPage(pageName);
        ContentPage p = new ContentPage();
        p.setTitle(page.getTitle());
        p.setId(page.getId());
        p.setAuthor(page.getAuthor());
        p.setContent(page.getContent());
        p.setDesc(page.getDesc());
        p.setEditable(page.getEditable());
        p.setKeywords(page.getKeywords());
        p.setLogo(page.getLogo());
        p.setSort(page.getSort());
        p.setTemplate(page.getTemplate());
        template = template.replaceAll("oomoduleoo", "@module");
        Matcher m = Pattern.compile("(?is)<body(.*?)>(.*?)</body>").matcher(p.getTemplate());
        if (m.find()) {
            StringBuilder sb = new StringBuilder();
            int start = m.toMatchResult().start();
            char end = p.getTemplate().charAt(start);
            while (end != '>') {
                end = p.getTemplate().charAt(start);
                sb.append(end);
                start += 1;
            }
            String contentxt = m.replaceAll(sb.toString() + template + "</body>");
            p.setTemplate(contentxt);
        }
        this.pageContainer.saveContentPage(p);
        Jsonx modules = json.get("modules");
        final Contenter contenter = this;
        modules.each(new JsonEachArray() {
            @Override
            public boolean each(int index, Jsonx json) throws Exception {
                String moduleType = json.get("moduleType").toString();
                String moduleId = json.get("moduleId").toString();
                String values = json.get("values").toString();
                contenter.moduleContainer.editOrAddModule(moduleId, moduleType, values);
                return false;
            }
        });
    }
    
    public void cleanModulesNoUse() {
        StringBuilder sb = new StringBuilder();
        List<String> list = this.pageContainer.getAllModulesUsed();
        List<ContentObjectModule> modules = this.moduleContainer.getAllObjectModules();
        for (ContentObjectModule md : modules) {
            if (!list.contains(md.getId())) {
                sb.append(md.getId());
                sb.append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        this.moduleContainer.removeObjectModule(sb.toString());
    }
    
    public void exportDevResource(HttpServletResponse response) throws Exception {
        List<ContentObjectModule> modules = this.moduleContainer.getAllObjectModules();
        List<ContentPage> pages = this.pageContainer.getAllContentPages();
        String basePath = Contenter.getWebInfoPath() + "__cache__";
        for (ContentObjectModule module : modules) {
            Jsonx.create(module).toFile(basePath + File.separator + "module" + File.separator + module.getId() + ".json");
        }
        for (ContentPage page : pages) {
            Jsonx.create(page).toFile(basePath + File.separator + "pages" + File.separator + page.getId() + ".json");
        }
        Jile file = Jile.with(basePath).zip(Contenter.getWebInfoPath() + "__resource__.zip");
        byte[] buffer = file.bytes();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=resource.zip");
        response.addHeader("Content-Length", "" + file.file().length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        Jile.with(basePath).remove();
        file.remove();
    }
    
    public void exportsAllPages(HttpServletResponse response) throws Exception {
        List<ContentPage> pages = this.pageContainer.getAllContentPages();
        String basePath = Contenter.getWebInfoPath() + "__cache__pages__";
        for (ContentPage page : pages) {
            Jsonx.create(page).toFile(basePath + File.separator + page.getId() + File.separator + "option.json");
            Jile.with(basePath + File.separator + page.getId() + File.separator + "template.html").write(page.getTemplate());
        }
        Jile file = Jile.with(basePath).zip(Contenter.getWebInfoPath() + "__resourcepages__.zip");
        byte[] buffer = file.bytes();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=pages.zip");
        response.addHeader("Content-Length", "" + file.file().length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        Jile.with(basePath).remove();
        file.remove();
    }
    
    public void exports(HttpServletResponse response) throws Exception {
        List<ContentPage> pages = this.pageContainer.getAllContentPages();
        String path = Contenter.getWebInfoPath();
        String assets = path + File.separator + "assets";
        String basePath = assets + File.separator + "mapping";
        for (ContentPage page : pages) {
            Jsonx.create(ObjectSnooper.snoop(page).toHashMapp()).toFile(basePath + File.separator + "pages" + File.separator + page.getId() + ".json");
        }
        
        List<ContentObjectModule> modules = this.moduleContainer.getAllObjectModules();
        for (ContentObjectModule module : modules) {
            Jsonx.create(ObjectSnooper.snoop(module).toHashMapp()).toFile(basePath + File.separator + "module" + File.separator + module.getId() + ".json");
        }
        
        List<ContentModule> template = this.getContentModuleContainer().getAllTemplateModules();
        for (ContentModule module : template) {
            Jsonx.create(ObjectSnooper.snoop(module).toHashMapp()).toFile(basePath + File.separator + "template" + File.separator + module.getType() + ".json");
        }
        Jile.with(assets).zip("D:\\__resource__.zip");
        Jile file = Jile.with(assets).zip(path + "__resource__.zip");
        byte[] buffer = file.bytes();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=backup.zip");
        response.addHeader("Content-Length", "" + file.file().length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        Jile.with(basePath).remove();
        file.remove();
    }
    
    public static String getWebInfoPath() {
        URL url = Contenter.class.getProtectionDomain().getCodeSource().getLocation();
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
            path = path.substring(5);
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
    
    public HashMap<String, String> getClientCssFiles() throws Exception {
        final HashMap<String, String> map = new HashMap<String, String>();
        final String path = Jile.with(this.option.getAssetsPath()).file().getParentFile().getAbsolutePath();
        Jile.with(this.option.getClientCssPath()).each(new JileEach() {
            @Override
            public boolean each(Jile file) throws Exception {
                if (file.file().getName().endsWith(".css")) {
                    map.put(file.file().getName(), file.file().getPath()
                            .substring(path.length() + 1).replace("\\", "/"));
                }
                return false;
            }
        });
        return map;
    }
    
    public void saveClientCssFile(String fileName, String content) throws Exception {
        Jile.with(this.option.getClientCssPath() + File.separator + fileName).write(content);
        this.pageContainer.getHelper().saveCss(content, fileName);
    }
    
    public HashMap<String, String> getClientJsFiles() throws Exception {
        final HashMap<String, String> map = new HashMap<String, String>();
        final String path = Jile.with(this.option.getAssetsPath()).file().getParentFile().getAbsolutePath();
        Jile.with(this.option.getClientJsPath()).each(new JileEach() {
            @Override
            public boolean each(Jile file) throws Exception {
                if (file.file().getName().endsWith(".js")) {
                    map.put(file.file().getName(), file.file().getPath().substring(path.length() + 1).replace("\\", "/"));
                }
                return false;
            }
        });
        return map;
    }
    
    public void saveClientJsFile(String fileName, String content) throws Exception {
        Jile.with(this.option.getClientJsPath() + File.separator + fileName).write(content);
        this.pageContainer.getHelper().saveJs(content, fileName);
    }
    
    public List<HashMap<String, String>> getClientImageFiles() throws Exception {
        final List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        final String path = Jile.with(this.option.getAssetsPath()).file().getParentFile().getAbsolutePath();
        Jile.with(this.option.getClientImagePath()).each(new JileEach() {
            @Override
            public boolean each(Jile file) throws Exception {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", file.file().getName());
                map.put("url", file.file().getPath().substring(path.length() + 1).replace("\\", "/"));
                list.add(map);
                return false;
            }
        });
        return list;
    }
    
    public void saveClientImage(File file) throws Exception {
        this.pageContainer.getHelper().saveImage(file);
        Jile.with(file).move(this.option.getClientImagePath() + File.separator + file.getName());
    }
    
    public void saveTemplateModuleInfo(String moduledesc,
            boolean moduleeditable, String modulefields, String moduleicon,
            boolean modulesingle, String modulesort, String moduletemplate,
            String moduletype, String moduleversion, String moduleview,
            String moduleeditsetting, boolean removeable) {
        ContentModule module = new ContentModule();
        module.setDesc(moduledesc);
        module.setEditSetting(moduleeditsetting);
        module.setEditable(moduleeditable);
        module.setFields(modulefields);
        module.setIcon(moduleicon);
        module.setSingle(modulesingle);
        module.setSort(modulesort);
        module.setTemplate(moduletemplate);
        module.setType(moduletype);
        module.setVersion(moduleversion);
        module.setView(moduleview);
        module.setRemoveable(removeable);
        this.getContentModuleContainer().saveTemplateModuleInfo(module);
    }
    
    public void downLoadModule(String type, HttpServletResponse response) throws Exception {
        ContentModule module = this.getContentModuleContainer().getTemplateModule(type);
        HashMap<String, Object> map = ObjectSnooper.snoop(module).toHashMapp();
        if (map.containsKey("localAttributes")) {
            map.remove("localAttributes");
        }
        String content = Jsonx.create(map).toString();
//        System.out.println(content);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + module.getType() + ".json");
        // response.addHeader("Content-Length", "" + content.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(content.getBytes("utf-8"));
        toClient.flush();
        toClient.close();
    }
    
    public void downloadPage(String id, HttpServletResponse response) throws Exception {
        ContentPage page = this.getContentPageContainer().getContentPage(id);
        HashMap<String, Object> map = ObjectSnooper.snoop(page).toHashMapp();
        if (map.containsKey("localAttributes")) {
            map.remove("localAttributes");
        }
        String content = Jsonx.create(map).toString();
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + page.getId() + ".json");
        // response.addHeader("Content-Length", "" + content.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(content.getBytes("utf-8"));
        toClient.flush();
        toClient.close();
    }
}
