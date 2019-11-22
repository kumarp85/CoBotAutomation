package com.cobot.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * The Class AppData.
 */
public class AppData {

    /** The properties. */
    public static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader(System.getProperty("user.dir") + "/Configuration/Config.properties"));
        } catch (IOException e) {
        }
    }

    /**
     * Gets the property.
     * @param property the property
     * @return the property
     */
    public static String getProperty(String property) {
        try {
            return properties.getProperty(property).toString();
        } catch (Exception e) {
            return "";
        }
    }

}
