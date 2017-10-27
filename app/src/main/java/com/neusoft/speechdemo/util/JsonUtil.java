package com.neusoft.speechdemo.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * JSON解析工具
 * <p>
 * Created by yangming on 17-10-26.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        return new Gson().fromJson(json, clz);
    }

    public static <T> T fromJson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }
}
