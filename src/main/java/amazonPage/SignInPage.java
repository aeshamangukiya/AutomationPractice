package amazonPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SignInPage {

	WebDriver driver;

	public SignInPage(WebDriver driver) {
		this.driver = driver;
		System.out.println("** SignInPage Con Called: " + driver);
		PageFactory.initElements(driver, this);
	}

	// Locator
	@FindBy(xpath = "//span[@id='nav-link-accountList-nav-line-1']")
	WebElement signinTxt;

	@FindBy(xpath = "//input[@id='ap_email_login']")
	WebElement emailOrMobileField;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement continueBtn;

	@FindBy(xpath = "//div[contains(text(),'Invalid email address.')]")
	WebElement invalidEmailMessageTxt;

	@FindBy(xpath = "//input[@id='ap_password']")
	WebElement PasswordField;
	
	@FindBy(xpath = "//input[@id='signInSubmit']")
	WebElement signInBtn;

	// Action
	public void signInClick() {
		signinTxt.click();
	}

	public void setEmailOrMobile(String email) {
		emailOrMobileField.sendKeys(email);
	}

	public void continueBtnClick() {
		continueBtn.click();
	}

	public boolean isInvalidEmailMessageTxtVisible() {
		return invalidEmailMessageTxt.isDisplayed();
	}

	public void setPassword(String Password) {
		PasswordField.sendKeys(Password);
	}
	
	public void signInBtnClick() {
		signInBtn.click();
	}
}
