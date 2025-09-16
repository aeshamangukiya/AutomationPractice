package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import constants.FrameworkConstants;

public class ConfigReader {

    private static final Properties properties = new Properties();

    public enum ConfigFile {
        LOGIN("loginPage"),
        DASHBOARD("dashboard"),
        CONFIG("config");

        private final String fileName;

        ConfigFile(String fileName) {
            this.fileName = fileName;
        }

        // Full path (e.g., src/test/resources/config/config.properties)
        public String getPath() {
            return FrameworkConstants.CONFIG_BASE_PATH + fileName + ".properties";
        }
    }

    /**
     * Load a properties file into memory
     */
    public static void loadProperties(ConfigFile configFile) {
        try (FileInputStream fis = new FileInputStream(configFile.getPath())) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + configFile.getPath(), e);
        }
    }

    /**
     * Get property value by key from loaded properties
     */
    public static String getConfigPropertyValue(String key) {
        return properties.getProperty(key, "");
    }

    /**
     * Direct one-shot load + read (if you don’t want to preload)
     */
    public static String getProperty(String fileName, String key) {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_BASE_PATH + fileName)) {
            Properties tempProps = new Properties();
            tempProps.load(fis);
            return tempProps.getProperty(key, "");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read property: " + key + " from file: " + fileName, e);
        }
    }

    public static Properties getAllProperties() {
        return properties;
    }
}
