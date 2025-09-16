package page.utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WaitUtil
 * --------
 * Centralized utility for handling explicit waits in Selenium.
 *
 * - Provides safe, reusable wait methods
 * - Helps avoid flakiness in UI tests
 * - Encourages explicit waits instead of hard sleeps
 */
public class WaitUtil {

    /* ==========================================================
       ELEMENT-BASED WAITS
       ========================================================== */

    /**
     * Waits until the given WebElement is visible on the DOM.
     *
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutSeconds max time to wait
     * @return visible WebElement
     */
    public static WebElement waitForElementVisible(WebDriver driver, WebElement element, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the given WebElement is clickable.
     *
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutSeconds max time to wait
     * @return clickable WebElement
     */
    public static WebElement waitForElementClickable(WebDriver driver, WebElement element, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until the given WebElement becomes invisible.
     *
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutSeconds max time to wait
     * @return true if invisible within timeout
     */
    public static boolean waitForElementInvisible(WebDriver driver, WebElement element, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Waits until the given text is present in the WebElement.
     *
     * @param driver WebDriver instance
     * @param element WebElement to check
     * @param text expected text
     * @param timeoutSeconds max time to wait
     * @return true if text appears within timeout
     */
    public static boolean waitForTextPresent(WebDriver driver, WebElement element, String text, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    
   
    /* ==========================================================
       LOCATOR-BASED WAITS
       ========================================================== */

    /**
     * Waits until element (found by locator) is visible on the DOM.
     *
     * @param driver WebDriver instance
     * @param locator locator strategy
     * @param timeoutSeconds max time to wait
     * @return visible WebElement
     */
    public static WebElement waitForElementVisibleByLocator(WebDriver driver, By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    /**
     * Waits until element (found by locator) is clickable.
     *
     * @param driver WebDriver instance
     * @param locator locator strategy
     * @param timeoutSeconds max time to wait
     * @return clickable WebElement
     */
    public static WebElement waitForElementClickableByLocator(WebDriver driver, By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    
    public static WebElement waitForElementPresentByLocator(WebDriver driver, By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /* ==========================================================
       DEBUGGING / HARD PAUSE
       ========================================================== */

    /**
     * Hard pause — halts execution for a fixed number of seconds.
     * 
     * ⚠ Use only for debugging or visual observation. Prefer explicit waits
     *    for stable automation tests.
     *
     * @param seconds duration in seconds
     */
    public static void pause(int seconds) {
        try {
            System.out.println("Pausing for " + seconds + " seconds...");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
