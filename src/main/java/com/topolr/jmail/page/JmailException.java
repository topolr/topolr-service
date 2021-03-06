package com.topolr.jmail.page;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.Writer;
import org.apache.log4j.Logger;

public class JmailException implements TemplateExceptionHandler {

    @Override
    public void handleTemplateException(TemplateException te, Environment e, Writer writer) throws TemplateException {
        try {
            writer.write("[ERROR:]");
            Logger.getLogger(JmailException.class).debug(te.getMessage());
        } catch (Exception ez) {
            throw new TemplateException("Failed to print error message. Cause: " + writer, e);
        }
    }
}
