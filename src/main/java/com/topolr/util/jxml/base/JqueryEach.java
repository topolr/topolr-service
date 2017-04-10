package com.topolr.util.jxml.base;

public abstract class JqueryEach {

    protected Object[] arguments;

    public JqueryEach(Object... args) {
        this.arguments = args;
    }

    public abstract boolean each(Jquery node, int index);
}
