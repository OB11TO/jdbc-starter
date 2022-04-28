package com.ob11to.jdbc.starter.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties(); // для чтения ресурсов (application.properties)

    private PropertiesUtil() {
    }

    static {
        loadResource();
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key); //по ключу вернем значение
    }

    private static void loadResource() {
        try (var resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream); // получаем данные из application.pr
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
