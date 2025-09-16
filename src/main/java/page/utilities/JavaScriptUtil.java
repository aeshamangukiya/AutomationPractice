package page.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Utility class to perform JavaScript-based operations using Selenium WebDriver.
 * All methods are static for direct usage without object creation.
 */
public final class JavaScriptUtil {

    // Private constructor to prevent object creation
    private JavaScriptUtil() {}

    // ==========================
    // Page Utility Methods
    // ==========================

    public static void launchURL(WebDriver driver, String url) {
        ((JavascriptExecutor) driver).executeScript("window.location = arguments[0];", url);
    }

    public static String getCurrentPageURL(WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript("return window.location.href;");
    }

    /* ==========================================================
       SCROLLING
       ========================================================== */

    public static void scrollPageDown(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollPageUp(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scrolls the page until the element is in view.
     * Useful when element is off-screen and Selenium can't interact with it.
     *
     * @param driver  WebDriver instance
     * @param element Target element
     */
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    // ==========================
    // Element Interaction Methods
    // ==========================

    public static void clickElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void setValue(WebDriver driver, WebElement element, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', arguments[1]);", element, value);
    }

    public static String getInnerText(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
    }

    public static void highlightElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", element);
    }

    public static void flashElement(WebDriver driver, WebElement element) {
        String originalColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 3; i++) {
            changeElementColor(driver, "#ff0000", element);
            changeElementColor(driver, originalColor, element);
        }
    }

    public static void changeElementColor(WebDriver driver, String color, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor = arguments[1];", element, color);
        try {
            Thread.sleep(200); // short delay for visual effect
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==========================
    // Alert Methods
    // ==========================

    public static void generateAlert(WebDriver driver, String message) {
        ((JavascriptExecutor) driver).executeScript("alert(arguments[0]);", message);
    }

    public static void disableAlerts(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.alert = function(){};");
    }
}
