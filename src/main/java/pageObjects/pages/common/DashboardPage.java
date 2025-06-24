package pageObjects.pages.common;				
				
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
				
import helper.ElementActions;				
import pageBase.BasePage;				
				
// Constructor				
public class DashboardPage extends BasePage {				
				
	JavascriptExecutor executor;			
				
	public DashboardPage(WebDriver driver) {			
		super(driver);		
		this.executor = (JavascriptExecutor) this.driver;		
	}			
				
	// Locators			
	@FindBy(xpath = "//input[@formcontrolname=\"Username\"]")			
	WebElement txtEmailUsername;			
				
	@FindBy(xpath = "//input[@formcontrolname=\"Password\"]")			
	WebElement txtPassWord;			
				
	@FindBy(xpath = "//button[@type='submit']")			
	WebElement btnSignIn;			
				
	@FindBy(xpath = "//a[@class='d-flex align-items-center ng-tns-c95-1']//i[@class='far fa-chevron-up up-arrow ng-tns-c95-1']")			
	WebElement ddlMyAccount;			
				
	@FindBy(xpath = "//span[normalize-space()='Logout']")			
	WebElement btnLogout;			
				
	@FindBy(xpath = "//a[@class='notification-icon ng-tns-c95-1']")			
	WebElement imgNotificationIcon;			
				
	@FindBy(xpath = "//div[@class=\"ng-star-inserted\"]//a[@class=\"p-ripple p-element btn btn-secondary\"]")			
	WebElement btnRegister;			
				
	@FindBy(xpath = "//a[normalize-space()='Forgot Password ?']")			
	WebElement lnkForgotPwd;			
				
	// Actions			
	public void setEmailUsername(String Email) {			
		ElementActions.selectBySendkeys(Email, txtEmailUsername);		
	}			
				
	public void setPassword(String Password) {			
		ElementActions.selectBySendkeys(Password, txtPassWord);		
	}			
				
	public boolean isNotificationIconVisible() {			
		return ElementActions.isDisplayed(driver, imgNotificationIcon);		
	}			
				
	public void clearUsernameEmailTxt() {			
		txtEmailUsername.sendKeys(Keys.CONTROL + "a"); // Select all text		
		txtEmailUsername.sendKeys(Keys.DELETE); // Delete selected text		
	}			
				
	public void clearPasswordTxt() {			
		txtPassWord.sendKeys(Keys.CONTROL + "a"); // Select all text		
		txtPassWord.sendKeys(Keys.DELETE); // Delete selected text		
	}			
				
	public void clickSignInBtn() {			
		executor.executeScript("arguments[0].click();", btnSignIn);		
	}			
				
	public boolean isSignInBtnVisible() {			
		return ElementActions.isDisplayed(driver, btnSignIn);		
	}			
				
	public void clickRegisterBtn() {			
		ElementActions.click(driver, btnRegister);		
	}			
				
	public boolean isRegisterBtnVisible() {			
		return ElementActions.isDisplayed(driver, btnRegister);		
	}			
				
	public void clickMyAccountDdl() {			
		ElementActions.click(driver, ddlMyAccount);		
	}			
				
	public void clickLogoutOpt() {			
		ElementActions.click(driver,btnLogout);	
	}			
				
	public void clickRegister() {			
		ElementActions.click(driver, btnRegister);		
	}			
				
	public void clickForgotPwdLnk() {			
		ElementActions.click(driver, lnkForgotPwd);		
	}
}