package com.tx.filedown.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MapRestResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public MapRestResponse() {
        this.put((String)"code", 200);
        this.put((String)"msg", "正常响应");
    }

    public static MapRestResponse error() {
        return error(500, "内部系统异常,请联系管理人员");
    }

    public static MapRestResponse error(String msg) {
        return error(500, msg);
    }

    public static MapRestResponse error(int code, String msg) {
        MapRestResponse r = new MapRestResponse();
        r.put((String)"code", code);
        r.put((String)"msg", msg);
        return r;
    }

    public static MapRestResponse ok(String msg) {
        MapRestResponse r = new MapRestResponse();
        r.put((String)"msg", msg);
        return r;
    }

    public static MapRestResponse ok(Map<String, Object> map) {
        MapRestResponse r = new MapRestResponse();
        r.putAll(map);
        return r;
    }

    public static MapRestResponse ok() {
        return new MapRestResponse();
    }

    public MapRestResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
