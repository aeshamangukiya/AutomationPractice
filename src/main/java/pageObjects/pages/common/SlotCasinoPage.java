package pageObjects.pages.common;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageBase.BasePage;

//Constructor
public class SlotCasinoPage extends BasePage {

	JavascriptExecutor executor;
	WebDriverWait wait;

	public SlotCasinoPage(WebDriver driver) {
		super(driver);
		this.executor = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// TODO Auto-generated constructor stub
	}

	// Locators
	@FindBy(xpath = "(//div[@class='p-skeleton p-component'])[3]")
	WebElement hoverOnGameBtn;

	@FindBy(xpath = "//div[@id='casino_main_wrapper']//div[1]//ul[1]//li[3]//div[1]//div[1]//div[2]//div[1]//a[1]//span[1]")
	WebElement btnRealPlay;

	// Actions
	public void clickRealPlayBtn() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(hoverOnGameBtn));
			executor.executeScript("arguments[0].scrollIntoView(true);", hoverOnGameBtn);

			Actions actions = new Actions(driver);
			actions.moveToElement(hoverOnGameBtn).pause(Duration.ofMillis(10)).perform();

			wait.until(ExpectedConditions.elementToBeClickable(btnRealPlay));

			btnRealPlay.click();

		} catch (Exception e) {
			System.out.println("Failed to click Real Play button: " + e.getMessage());
		}
	}

}
