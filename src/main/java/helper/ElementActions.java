package helper;

import org.openqa.selenium.*; 
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import page.utilities.JavaScriptUtil;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * ElementActions -------------- Utility class providing safe and reusable
 * wrappers around common WebElement interactions in Selenium.
 *
 * - All methods are static (no instantiation needed). - Each method aims to
 * provide fallbacks / better stability. - Groups: Clicks, Typing, State Checks,
 * Scrolling, Dropdowns.
 */
public final class ElementActions {

	// Prevent instantiation
	private ElementActions() {
	}

	/*
	 * ========================================================== 
	 * CLICK ACTIONS
	 * ==========================================================
	 */

	/**
	 * Attempts a normal click using Actions class. If normal click fails (element
	 * not interactable, etc.), it falls back to JS click.
	 *
	 * @param driver  WebDriver instance
	 * @param element Target element
	 */
	public static void click(WebDriver driver, WebElement element) {
		try {
			// Plan A: Normal Selenium click
			element.click();
		} catch (Exception e1) {
			System.out.println("[WARN] Normal click failed. Trying JavaScript click.");

			try {
				// Plan B: Fallback using JS
				JavaScriptUtil.clickElement(driver, element);
			} catch (Exception e2) {
				System.err.println("[ERROR] Both normal click and JS click failed.");
				throw e2; // test fails here
			}
		}
	}

	/*
	 * ========================================================== 
	 * TYPING & TEXT INPUT 
	 * ==========================================================
	 */

	/**
	 * Types given text into an element using sendKeys(). Does nothing if text is
	 * null.
	 *
	 * @param driver  WebDriver instance
	 * @param element Input element
	 * @param text    Text to type
	 */
	public static void typeText(WebDriver driver, WebElement element, String text) {
		if (text == null)
			return;
		element.sendKeys(text);
	}

	/**
	 * Clears existing text from an input field. Uses element.clear(), and if that
	 * fails, falls back to CTRL+A + DELETE.
	 *
	 * @param driver  WebDriver instance
	 * @param element Input element
	 */
	public static void clearText(WebDriver driver, WebElement element) {
		try {
			element.clear();
		} catch (Exception ignored) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.DELETE);
		}
	}

	/**
	 * Clears existing text, then types new text.
	 *
	 * @param driver  WebDriver instance
	 * @param element Input element
	 * @param text    Text to type
	 */
	public static void clearAndTypeText(WebDriver driver, WebElement element, String text) {
		clearText(driver, element);
		typeText(driver, element, text);
	}

	/*
	 * ========================================================== 
	 * STATE CHECKS
	 * ==========================================================
	 */

	/**
	 * Checks if element is displayed (visible). Returns false if element is null,
	 * stale, or not found.
	 *
	 * @param element WebElement to check
	 * @return true if displayed, false otherwise
	 */
	public static boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * ========================================================== 
	 * DROPDOWN HANDLING
	 * (<select> elements)
	 * ==========================================================
	 */

	/**
	 * Utility method to wrap a WebElement into a Select object.
	 *
	 * @param selectElement The dropdown WebElement
	 * @return Select object for the given element
	 */
	public static Select asSelect(WebElement dropdownElement) {
		return new Select(dropdownElement);
	}

	/*
	 * --------------------------- Retrieval Helpers ---------------------------
	 */
	public static List<String> getAllOptions(WebElement dropdownElement) {
		return asSelect(dropdownElement).getOptions().stream().map(option -> option.getText().trim())
				.collect(Collectors.toList());
	}

	/*
	 * --------------------------- Core Selections ---------------------------
	 */
	public static Select selectByVisibleText(WebElement dropdownElement, String text) {
		asSelect(dropdownElement).selectByVisibleText(text);
		return asSelect(dropdownElement);
	}

	public static Select selectByValue(WebElement dropdownElement, String value) {
		asSelect(dropdownElement).selectByValue(value);
		return asSelect(dropdownElement);
	}

	public static Select selectByIndex(WebElement dropdownElement, int index) {
		asSelect(dropdownElement).selectByIndex(index);
		return asSelect(dropdownElement);
	}

	/**
	 * Selects a random option from a dropdown (native <select>).
	 *
	 * @param selectElement The dropdown WebElement
	 */
	public static Select selectRandomDropdownOption(WebElement dropdownElement) {
		Select select = asSelect(dropdownElement);
		List<WebElement> options = select.getOptions();
		if (options.isEmpty())
			throw new IllegalStateException("Dropdown is empty.");
		int index = new Random().nextInt(options.size());
		select.selectByIndex(index);

		return select;
	}

	/**
	 * Selects a specific option by visible text from a dropdown and returns the
	 * selected WebElement. Throws error if option not found.
	 *
	 * @param selectElement The dropdown WebElement
	 * @param visibleText   Option text to select
	 * @return The selected option WebElement
	 */
	public static Select selectSpecificDropdownOption(WebElement dropdownElement, String visibleText) {
		Select select = asSelect(dropdownElement);
		List<String> allTexts = getAllOptions(dropdownElement);
		if (!allTexts.contains(visibleText)) {
			throw new IllegalArgumentException("Value '" + visibleText + "' not found. Available: " + allTexts);
		}
		select.selectByVisibleText(visibleText);
		return select;
	}
}
