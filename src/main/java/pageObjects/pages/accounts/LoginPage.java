package pageObjects.pages.accounts;

import java.lang.System.Logger;
import java.sql.DriverAction;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageBase.BasePage;

//Constructor
public class LoginPage extends BasePage {

	JavascriptExecutor executor;
	WebDriverWait wait;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.executor = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Locators
	@FindBy(xpath = "//input[@name='username']")
	WebElement txtUsername;

	@FindBy(xpath = "//input[@name='password']")
	WebElement txtPassWord;

	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement btnSignIn;

	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement ddlLogin;

//	@FindBy(xpath = "//span[normalize-space()='Logout']")
//	WebElement btnLogout;

	// Actions
	public void setUsername(String User) {
		wait.until(ExpectedConditions.visibilityOf(txtUsername)).clear();
		txtUsername.sendKeys(User);
	}

	public void setPassword(String Password) {
		wait.until(ExpectedConditions.visibilityOf(txtPassWord)).clear();
		txtPassWord.sendKeys(Password);
	}

	public void clickSignInBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(btnSignIn));
		executor.executeScript("arguments[0].click();", btnSignIn);
		// btnSign.click();
	}
	
	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}
	
	public boolean isSignInBtnVisible() {
		try {
			return btnSignIn.isDisplayed();
		} catch (Exception e) {
			return true;
		}
	}
	//demo
	public void clearUsernameEmailTxt() {
		txtUsername.sendKeys(Keys.CONTROL + "a"); // Select all text
		txtUsername.sendKeys(Keys.DELETE); // Delete selected text
	}

	public void clearPasswordTxt() {
		txtPassWord.sendKeys(Keys.CONTROL + "a"); // Select all text
		txtPassWord.sendKeys(Keys.DELETE); // Delete selected text
	}

	public void clickMyAccountDdl() {
		try {
			ddlLogin.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ddlLogin);

		}
	}

//	public void clickLogoutOpt() {
//		try {
//			btnLogout.click();
//		} catch (Exception e) {
//			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnLogout);
//		}
//
//	}

}