package com.topolr.contenter.template;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.Writer;

public class TemplaterException implements TemplateExceptionHandler {

    @Override
    public void handleTemplateException(TemplateException te, Environment e, Writer writer) throws TemplateException {
        try {
            writer.write("[NODATA]");
        } catch (Exception ez) {
            throw new TemplateException("Failed to print error message. Cause: " + writer, e);
        }
    }
}
