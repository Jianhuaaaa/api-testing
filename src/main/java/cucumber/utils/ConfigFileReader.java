package cucumber.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Jianhua Sun on 2018/9/8.
 */
public class ConfigFileReader {
    public String getReportConfigPath(){
        Properties properties = new Properties();
        try {
            properties.load(ConfigFileReader.class.getClassLoader().getResourceAsStream("configuration.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String reportConfigPath = properties.getProperty("reportConfigPath");
        if (reportConfigPath != null)
            return reportConfigPath;
        else
            throw new RuntimeException("Report Config Path not specified in configuration.properties file for key: reportConfigPath");
    }
}













