package com.topolr.contenter.page;

public abstract class ContentPageModuleIdEach {

    protected Object[] arguments;

    public ContentPageModuleIdEach(Object... args) {
        this.arguments = args;
    }

    public abstract String each(String original);
}
