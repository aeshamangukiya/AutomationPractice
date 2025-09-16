package page.base;

import org.openqa.selenium.*;

import configuration.ConfigReader;
import page.utilities.LocatorReader;
import page.utilities.WaitUtil;

/**
 * BasePage
 * --------
 * Abstract parent class for all Page Objects.
 * Provides:
 *  - WebDriver instance
 *  - LocatorReader for property-based locators
 *  - Utility methods to safely get elements with waits
 *
 * All page classes (e.g., LoginPage, DashboardPage) should extend this.
 */
public abstract class BasePage {

    // Core driver used across page classes
    protected final WebDriver driver;

    // Reads locators from .properties files (via LocatorReader)
    protected final LocatorReader locatorReader;

    // Default wait timeout (in seconds)
    private final int defaultTimeout = 10;

    /* ==========================================================
       CONSTRUCTOR
       ========================================================== */

    /**
     * Initializes a Page Object.
     *
     * @param driver          WebDriver instance
     * @param propertiesPath  Path to locator .properties file
     */
    protected BasePage(WebDriver driver, String propertiesPath) {
        this.driver = driver;
        this.locatorReader = new LocatorReader(propertiesPath);
    }
    
    /**
     * Initializes with ConfigFile enum (new behavior).
     *
     * @param driver     WebDriver instance
     * @param configFile ConfigFile enum (REGISTRATION, DASHBOARD, etc.)
     */
    protected BasePage(WebDriver driver, ConfigReader.ConfigFile configFile) {
        this(driver, configFile.getPath()); // call existing constructor
    }

    /* ==========================================================
       LOCATOR RESOLVERS
       ========================================================== */

    /**
     * Resolves a By locator using a locator key from the .properties file.
     *
     * @param key Locator key defined in .properties
     * @return By locator
     */
    protected By by(String key) {
        return locatorReader.getBy(key);
    }

    /**
     * Fetches element immediately (no waits).
     * Use cautiously – may throw NoSuchElementException
     * if element is not yet present in DOM.
     *
     * @param key Locator key defined in .properties
     * @return WebElement
     */
    protected WebElement getElement(String key) {
        return driver.findElement(by(key));
    }

    /* ==========================================================
       WAIT FOR VISIBILITY
       ========================================================== */

    /**
     * Waits until element is visible (using default timeout).
     *
     * @param key Locator key
     * @return WebElement once visible
     */
    protected WebElement elVisible(String key) {
        return elVisible(key, defaultTimeout);
    }

    /**
     * Waits until element is visible (custom timeout).
     *
     * @param key             Locator key
     * @param timeoutSeconds  Timeout in seconds
     * @return WebElement once visible
     */
    protected WebElement elVisible(String key, int timeoutSeconds) {
        return WaitUtil.waitForElementVisibleByLocator(driver, by(key), timeoutSeconds);
    }

    
    
    
    /* ==========================================================
       WAIT FOR CLICKABILITY
       ========================================================== */

    /**
     * Waits until element is clickable (using default timeout).
     *
     * @param key Locator key
     * @return WebElement once clickable
     */
    protected WebElement elClickable(String key) {
        return elClickable(key, defaultTimeout);
    }

    /**
     * Waits until element is clickable (custom timeout).
     *
     * @param key             Locator key
     * @param timeoutSeconds  Timeout in seconds
     * @return WebElement once clickable
     */
    protected WebElement elClickable(String key, int timeoutSeconds) {
        return WaitUtil.waitForElementClickableByLocator(driver, by(key), timeoutSeconds);
    }
    
    protected WebElement elPresent(String key, int timeoutSeconds) {
        return WaitUtil.waitForElementPresentByLocator(driver, by(key), timeoutSeconds);
    }
    
}
