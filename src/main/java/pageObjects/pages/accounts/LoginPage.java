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

import configuration.ConfigReader;
import helper.ElementActions;
import page.base.BasePage;

//Constructor
public class LoginPage extends BasePage {

	JavascriptExecutor executor;
	WebDriverWait wait;

	public LoginPage(WebDriver driver) {
		super(driver, ConfigReader.ConfigFile.LOGIN);
	}

	// Locators
	private WebElement txtUsername() {
		return elVisible("txtUsername", 10);
	}

	private WebElement txtPassWord() {
		return elVisible("txtPassWord", 10);
	}

	private WebElement txtUserName() {
		return elVisible("txtUserName", 10);
	}

	private WebElement btnSignIn() {
		return elVisible("btnSignIn", 10);
	}

	private WebElement ddlLogin() {
		return elVisible("ddlLogin", 10);
	}

	/*
	 * --------------------------- Actions ---------------------------
	 */
	public void setUsername(String Username) {
		ElementActions.clearAndTypeText(driver, txtUsername(), Username);
	}

	public void setPassword(String password) {
		ElementActions.clearAndTypeText(driver, txtPassWord(), password);
	}

	public void clickSignInBtn() {
		ElementActions.click(driver, btnSignIn());
	}

	private org.openqa.selenium.WebElement btnLogout() {
		return elVisible("btnLogout", 10);
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public void clearUsernameEmailTxt() {
		txtUsername().sendKeys(Keys.chord(Keys.CONTROL, "a"));
		txtUsername().sendKeys(Keys.DELETE);
	}

	public void clearPasswordTxt() {
		txtPassWord().sendKeys(Keys.chord(Keys.CONTROL, "a"));
		txtPassWord().sendKeys(Keys.DELETE);
	}

	public void clickLogoutOpt() {
		ElementActions.click(driver, btnLogout());
	}
}
