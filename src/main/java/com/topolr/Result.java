package com.topolr;

import com.topolr.mvc.view.JsonView;
import java.util.HashMap;
import java.util.Map;

public class Result {

    private static Map<String, Object> map(String code, Object obj, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("data", obj);
        map.put("msg", msg);
        return map;
    }

    public static JsonView success(Object obj) {
        return new JsonView(map("1", obj, ""));
    }

    public static JsonView success() {
        return new JsonView(map("1", null, ""));
    }

    public static JsonView error(String msg) {
        return new JsonView(map("0", null, msg));
    }

    public static JsonView error() {
        return new JsonView(map("0", null, ""));
    }

    public static JsonView set(String code, String msg) {
        return new JsonView(map(code, null, msg));
    }

    public static JsonView set(String code, Object obj, String msg) {
        return new JsonView(map(code, obj, msg));
    }
}
