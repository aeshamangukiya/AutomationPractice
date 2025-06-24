package pageObjects.pages.accounts;

import java.lang.System.Logger;
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
	@FindBy(xpath = "//form[@class='ng-untouched ng-pristine ng-invalid']//input[@placeholder='Enter username or email']")
	WebElement txtEmailUsername;

	@FindBy(xpath = "(//input[@placeholder='Password'])[3]")
	WebElement txtPassWord;

	@FindBy(xpath = "(//button[@type='submit'])[7]")
	WebElement btnSignIn;

	@FindBy(xpath = "//a[@class='d-flex align-items-center ng-tns-c95-1']//i[@class='far fa-chevron-up up-arrow ng-tns-c95-1']")
	WebElement ddlMyAccount;

	@FindBy(xpath = "//span[normalize-space()='Logout']")
	WebElement btnLogout;

	// Actions
	public void setEmailUsername(String Email) {
		wait.until(ExpectedConditions.visibilityOf(txtEmailUsername)).clear();
		txtEmailUsername.sendKeys(Email);
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

	public boolean isSignInBtnVisible() {
		try {
			return btnSignIn.isDisplayed();
		} catch (Exception e) {
			return true;
		}
	}
	//demo
	public void clearUsernameEmailTxt() {
		txtEmailUsername.sendKeys(Keys.CONTROL + "a"); // Select all text
		txtEmailUsername.sendKeys(Keys.DELETE); // Delete selected text
	}

	public void clearPasswordTxt() {
		txtPassWord.sendKeys(Keys.CONTROL + "a"); // Select all text
		txtPassWord.sendKeys(Keys.DELETE); // Delete selected text
	}

	public void clickMyAccountDdl() {
		try {
			ddlMyAccount.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ddlMyAccount);

		}
	}

	public void clickLogoutOpt() {
		try {
			btnLogout.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnLogout);
		}

	}

}