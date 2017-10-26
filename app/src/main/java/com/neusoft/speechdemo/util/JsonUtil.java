package com.neusoft.speechdemo.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * JSON解析工具
 * <p>
 * Created by yangming on 17-10-26.
 */
public class JsonUtil {

    public static String toJson(Object pObject) {
        return new Gson().toJson(pObject);
    }

    public static <T> T fromJson(String pJson, Class<T> pClass) {
        return new Gson().fromJson(pJson, pClass);
    }

    public static <T> T fromJson(String pJson, Type pType) {
        return new Gson().fromJson(pJson, pType);
    }
}
