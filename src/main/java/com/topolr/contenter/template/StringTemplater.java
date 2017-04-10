package com.topolr.contenter.template;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map.Entry;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class StringTemplater {

    private final Configuration cfg;
    private final StringTemplateLoader stringLoader;

    public StringTemplater(String encoding) {
        this.cfg = new Configuration();
        this.cfg.setDefaultEncoding(encoding);
        this.stringLoader = new StringTemplateLoader();
        this.cfg.setTemplateLoader(stringLoader);
        this.cfg.setTemplateExceptionHandler(new TemplaterException());
    }

    public StringTemplater setException(TemplateExceptionHandler exception) {
        this.cfg.setTemplateExceptionHandler(exception);
        return this;
    }

    public StringTemplater setDirective(String name, String classPath) throws Exception {
        this.cfg.setSharedVariable(name, Class.forName(classPath).newInstance());
        return this;
    }

    public StringTemplater setDirective(String name, Object obj) throws Exception {
        this.cfg.setSharedVariable(name, obj);
        return this;
    }

    public StringTemplater setTemplate(String templateName, String template) {
        this.stringLoader.putTemplate(templateName, template);
        return this;
    }

    public Render getRender() throws Exception {
        return new Render();
    }

    public class Render {

        private final HashMap<String, Object> evnobj = new HashMap<String, Object>();
        private final HashMap<String, Object> data = new HashMap<String, Object>();

        public Render setData(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        public Render setData(HashMap<String, Object> data) {
            this.data.putAll(data);
            return this;
        }

        public Render removeData(String key) {
            this.data.remove(key);
            return this;
        }

        public Render setSessionAttribute(String key, Object value) {
            this.evnobj.put(key, value);
            return this;
        }

        public Render setSessionAttribute(HashMap<String, Object> map) {
            this.evnobj.putAll(map);
            return this;
        }

        public Render removeSessionAttribute(String key) {
            this.evnobj.remove(key);
            return this;
        }

        public void print(String tempName) throws Exception {
            Writer r = new PrintWriter(System.out);
            Environment evn = cfg.getTemplate(tempName).createProcessingEnvironment(this.data, r);
            for (Entry<String, Object> x : this.evnobj.entrySet()) {
                evn.setCustomAttribute(x.getKey(), x.getValue());
            }
            evn.process();
        }

        public String getContent(String tempName) {
            StringWriter stringWriter = new StringWriter();
            BufferedWriter writer = new BufferedWriter(stringWriter);
            try {
                Environment evn = cfg.getTemplate(tempName).createProcessingEnvironment(data, writer);
                for (Entry<String, Object> x : this.evnobj.entrySet()) {
                    evn.setCustomAttribute(x.getKey(), x.getValue());
                }
                evn.process();
                writer.flush();
                writer.close();
                return stringWriter.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        }

        public void out(String tempName, Writer writer) throws Exception {
            Environment evn = cfg.getTemplate(tempName).createProcessingEnvironment(data, writer);
            for (Entry<String, Object> x : this.evnobj.entrySet()) {
                evn.setCustomAttribute(x.getKey(), x.getValue());
            }
            evn.process();
            writer.flush();
            writer.close();
        }

        public void out(String tempName, Writer writer, boolean isclose) throws Exception {
            Environment evn = cfg.getTemplate(tempName).createProcessingEnvironment(data, writer);
            for (Entry<String, Object> x : this.evnobj.entrySet()) {
                evn.setCustomAttribute(x.getKey(), x.getValue());
            }
            evn.process();
            if (isclose) {
                writer.flush();
                writer.close();
            }
        }
    }
}
