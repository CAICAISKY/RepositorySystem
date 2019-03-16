package com.repository.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json相关工具类
 * @author schuyler
 */
public class JsonUtil {

    public static String object2Json(Object object) {
        if (object != null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            return gson.toJson(object);
        }
        return null;
    }
}
