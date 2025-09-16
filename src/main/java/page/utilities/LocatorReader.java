package page.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

/**
 * LocatorReader
 * ------------
 * Loads a .properties file of key=xpath pairs and exposes:
 *  - get(String)  -> raw XPath string
 *  - getBy(String)-> By.xpath for the key
 */
public class LocatorReader {

    private final Properties props;
    private final String filePath;

    public LocatorReader(String filePath) {
        this.props = new Properties();
        this.filePath = filePath;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            this.props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load locator file: " + filePath, e);
        }
    }

    /** Returns the raw XPath for a key (for logging or advanced use). */
    public String get(String key) {
        String xpath = props.getProperty(key);
        if (xpath == null || xpath.trim().isEmpty()) {
            throw new RuntimeException("XPath for key '" + key + "' not found in " + filePath);
        }
        return xpath.trim();
    }

    /** Returns a By built from the key's XPath. */
    public By getBy(String key) {
        return By.xpath(get(key));
    }
}
