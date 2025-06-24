package pageObjects.pages.accounts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageBase.BasePage;

//Constructor
public class ForgotPasswordPage extends BasePage {

	JavascriptExecutor executor;

	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
		this.executor = (JavascriptExecutor) this.driver;
	}

	// Locators
	@FindBy(xpath = "//label[normalize-space()='Email']")
	WebElement chkEmail;

	@FindBy(xpath = "(//input[@formcontrolname='email'])[1]")
	WebElement txtEmail;

	@FindBy(xpath = "(//button[@type='submit'])[3]")
	WebElement btnResetPwd;

	// Actions
	public void tickEmail() {
		chkEmail.click();
	}

	public void setEmail(String EmailID) {
		txtEmail.sendKeys(EmailID);
	}

	public void clickResetPwdBtn() {
		btnResetPwd.click();
	}

}
