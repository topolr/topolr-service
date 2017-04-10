package com.topolr.util.jsonx;

public abstract class JsonEach {

    protected Object[] arguments;

    public JsonEach(Object... args) {
        this.arguments = args;
    }

    public abstract boolean each(String key, Jsonx json) throws Exception;
}
