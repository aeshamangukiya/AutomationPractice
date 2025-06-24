package helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import pageBase.BasePage;

public class ElementActions extends BasePage {

	private JavascriptExecutor executor;

	public ElementActions(WebDriver driver) {
		super(driver);

		JavascriptExecutor executor;
		this.executor = (JavascriptExecutor) this.driver;
	}

	public static void click(WebDriver driver, WebElement ele) {

		Actions act = new Actions(driver);
		act.moveToElement(ele).click(ele).build().perform();
	}

	public static void mouseOverElement(WebDriver driver, WebElement element) {
		try {
			new Actions(driver).moveToElement(element).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollDown(WebElement element) {
		Actions act = new Actions(driver);
		act.keyUp(Keys.CONTROL).sendKeys(Keys.END).perform();
	}

	public static boolean findElement(WebDriver driver, WebElement ele) {
		boolean flag = true;
		try {
			ele.isDisplayed();
			flag = true;
		} catch (Exception e) {
			// System.out.println("Location not found: "+locatorName);
			flag = false;
		}
		return flag;
	}

	public static boolean isDisplayed(WebDriver driver, WebElement ele) {
		boolean flag = true;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isDisplayed();
			if (flag) {
				System.out.println("The element is Displayed");
			} else {
				System.out.println("The element is not Displayed");
			}
		}
		return flag;
	}

	public static boolean isSelected(WebDriver driver, WebElement ele) {
		boolean flag = true;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isSelected();
			if (flag) {
				System.out.println("The element is Selected");
			} else {
				System.out.println("The element is not Selected");
			}
		}
		return flag;
	}

	public static boolean isEnabled(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isEnabled();
			if (flag) {
				System.out.println("The element is Enabled");
			} else {
				System.out.println("The element is not Enabled");
			}
		}
		return flag;
	}

	public static void fluentWait(WebDriver driver, WebElement element, int timeOut) {
		Wait<WebDriver> wait = null;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20))
					.pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
		} catch (Exception e) {
		}
	}

	public static void implicitWait(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void explicitWait(WebDriver driver, WebElement element, Duration timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void pageLoadTimeOut(WebDriver driver, int timeOut) {
		driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
	}

	public static boolean type(WebElement ele, String text) {
		boolean flag = true;
		try {
			flag = ele.isDisplayed();
			ele.clear();
			ele.sendKeys(text);
			// logger.info("Entered text :"+text);
			flag = true;
		} catch (Exception e) {
			System.out.println("Location Not found");
			flag = false;
		}
		return flag;
	}

	public static boolean selectByVisibleText(String visibletext, WebElement ele) {
		try {
			Select s = new Select(ele);
			s.selectByVisibleText(visibletext);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean selectByValue(WebElement element, String value) {
		try {
			Select s = new Select(element);
			s.selectByValue(value);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public static boolean selectByIndex(WebElement element, int index) {
		try {
			Select s = new Select(element);
			s.selectByIndex(index);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean selectBySendkeys(String value, WebElement ele) {
		try {
			ele.sendKeys(value);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

}
