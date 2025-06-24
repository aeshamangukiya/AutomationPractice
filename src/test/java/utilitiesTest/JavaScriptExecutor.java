package utilitiesTest;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptExecutor {

	WebDriver driver; // Instance of WebDriver
	static JavascriptExecutor js; // Instance of JavascriptExecutor

	// Constructor that initializes the WebDriver and JavascriptExecutor
	public JavaScriptExecutor(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}

	// ==========================
	// Page Utility Methods
	// ==========================

	// Launch the URL on the Browser window
	public static void launchURL(String url) {
		
		js.executeScript("window.location = '"+url+"'");	
		
	}
	
	
	// Retrieves the title of the current page
	public String getPageTitle() {
		return js.executeScript("return document.title;").toString();
	}

	// Retrieves the URL of the current page
	public String getCurrentPageURL() {
		return js.executeScript("return window.location.href;").toString();
	}

		
	// Vertical Scroll - Top to Bottom 
	public static void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
		
	// Vertical Scroll - Bottom to Top 
	public static void scrollPageUp() {
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
	}

	
	// Vertical Scroll - Top to Bottom by specified amount
	public static void scrollPageVerticalByAmount(int deltaY) {
		js.executeScript("window.scrollBy(0, " + deltaY + ");");
	}
	
	// Horizontal Scroll - Left to Right
	public static void scrollPageRight() {
		js.executeScript("window.scrollTo(document.body.scrollWidth,0);");
	}
	
	// Horizontal Scroll -  Right to Left
	public static void scrollPageLeft() {
		js.executeScript("window.scrollTo(-document.body.scrollWidth,0);");
	}
		
	
	// Horizontal Scroll - Left to Right by a specified amount
	public static void scrollPageHorizontalByAmount(int deltaX) {
		js.executeScript("window.scrollBy(" + deltaX + ", 0);");
	}

		
	// Scrolls the page to bring the specified element into view
	public static void scrollPageToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	
	// Zooms in the page to the specified value
	public static void zoomPage(String value) {
		js.executeScript("document.body.style.zoom='" + value + "';");
	}

	// ==========================
	// Element Interaction Methods
	// ==========================

	// Clicks on the specified element
	public static void clickElement(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	// Sets a specified value to the given element
	public static void setValue(WebElement element, String text) {
		js.executeScript("arguments[0].setAttribute('value', arguments[1]);", element, text);
	}

	// Retrieves the inner text of the specified element
	public String getInnerText(WebElement element) {
		return js.executeScript("return arguments[0].innerText;", element).toString();
	}

	// Make a hidden element visible
	public static void makeHiddenElementVisible(WebElement element) {
		js.executeScript("arguments[0].style.display = 'block';", element); // Make it visible first
	}
	
	// Clicks on a hidden element after making it visible
	public static void clickHiddenElement(WebElement element) {
		js.executeScript("arguments[0].style.display = 'block';", element); // Make it visible first
		element.click(); // Now it's visible, so we can click it
	}

	// Highlights the specified element by changing its border style
	public static void highlightElement(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red';", element);
	}

	// Flashes the specified element by alternating its background color
	public static void flash(WebElement element) {
		String originalColor = element.getCssValue("backgroundColor"); // Get original background color
		for (int i = 0; i < 300; i++) {
			changeColor("#ff0000", element); // Change to green
			changeColor(originalColor, element); // Change back to original color
		}
	}

	// Changes the background color of the specified element
	public static void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "';", element);
		try {
			Thread.sleep(1000); // Brief pause for visual effect
		} catch (InterruptedException e) {
			// Handle any exception that may occur
			e.printStackTrace(); // Log the exception for debugging
		}
	}

	// ==========================
	// Alert Methods
	// ==========================

	// Generates an alert with a specified message
	public static void generateAlert(String message) {
		js.executeScript("alert('" + message + "');");
	}

	// Closes the alert dialog by overriding the default alert function
	public static void closeAlert() {
		try {
			js.executeScript("window.alert=function(){};"); // Override alert function
		} catch (Exception e) {
			
		}
	}
	

}



