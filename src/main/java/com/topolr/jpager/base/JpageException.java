package com.topolr.jpager.base;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.Writer;
import org.apache.log4j.Logger;

public class JpageException implements TemplateExceptionHandler {

    @Override
    public void handleTemplateException(TemplateException te, Environment e, Writer writer) throws TemplateException {
        try {
            writer.write("[NODATA]");
            Logger.getLogger(JpageException.class).debug(te.getMessage());
        } catch (Exception ez) {
            throw new TemplateException("Failed to print error message. Cause: " + writer, e);
        }
    }
}
