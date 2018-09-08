package utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Jianhua Sun on 2018/9/6.
 */
public class PropertyReader {

    public static String read(String key) {
        Properties prop;
        try {
            prop = new Properties();
            prop.load(PropertyReader.class.getClassLoader().getResourceAsStream("bank.properties"));
            if (prop.containsKey(key))
                return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
