package amazonTest;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import amazonPage.SignInPage;

public class TC_SignIn_001 {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
	}

	@Test
	public void tc_001() {
		SignInPage signIn = new SignInPage(driver);
		System.out.println(" browser luching ****");
		signIn.signInClick();
		System.out.println(" click on signin txt ****");
		signIn.setEmailOrMobile("Test@email.com");
		System.out.println(" Enter Email ****");
		signIn.continueBtnClick();
		System.out.println(" click on continue button ****");
		signIn.setPassword("123456");
		System.out.println(" enter password-- ****");
		signIn.signInBtnClick();

		/*
		 * boolean isInvalidEmailMessage = signIn.isInvalidEmailMessageTxtVisible();
		 * 
		 * if (isInvalidEmailMessage) { Assert.assertTrue(true); } else {
		 * Assert.fail("Entered Email ID is Valid"); }
		 */
	}

	@AfterClass
	public void driverClose() {
		driver.quit();
	}
}
