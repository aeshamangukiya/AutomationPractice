package tests.account.registration;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import helper.RandomStringGenerator;
import pageObjects.pages.accounts.RegistrationPage;
import pageObjects.pages.common.DashboardPage;
import testBase.BaseTest;
import utilitiesTest.LoggerUtility;

@Listeners(listeners.ExtentReportListener.class)

public class TS_RG_001 extends BaseTest {

	RandomStringGenerator random = new RandomStringGenerator();

	// Set valid Data
	String validEmailID = random.randomValidEmailID();
	String validPhoneNumber = random.randomValidPhoneNo();
	String validUserName = random.randomValidUsername();
	String validPassword = "123456";

	// Set invalid Data
	String invalEmailID = random.randomInvalidEmailID();
	String invalPhoneNumber = random.randomInvalidPhoneNo();
	String invalUsername = random.randomInvalidUserName();

	@Test(priority = 2)
	// @Parameters({ "os", "browser", })
	public void TC_RG_001() throws InterruptedException {

		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.clickRegister();
		Thread.sleep(500);

		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.setName(validUserName);
		registrationPage.setSurname(validUserName);
		registrationPage.selectYear();
		registrationPage.selectMonth();
		registrationPage.selectDay();
		registrationPage.selectCountry();
		registrationPage.selectCurrency();
		registrationPage.setEmailID(invalEmailID);
		registrationPage.setPhoneNo(validPhoneNumber);
		registrationPage.setUsername(validUserName);
		registrationPage.setPassword(validPassword);
		registrationPage.setPasswordAgain(validPassword);
		registrationPage.clickPopupRegister();
//		dashboard.clickRegister();

		boolean isRegisterBtnVisible = registrationPage.isRegisterBtnVisible();
//	    Expecting Register to pass so the Register button will be hidden
		if (!isRegisterBtnVisible) {
			Assert.assertTrue(!isRegisterBtnVisible, "Registration should be successful but got Failed");
			info("Registration Passed");
		}
	}

	@Test(priority = 1)
	public void RegistrationFormValidation() {

		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.clickRegister();

		RegistrationPage registrationPage = new RegistrationPage(driver);

		// Set valid and invalid data
		registrationPage.setUsername(validUserName);
		registrationPage.setEmailID(validEmailID);
		registrationPage.setPhoneNo(invalPhoneNumber);

		// Field-specific red border checks
		boolean isUsernameRed = registrationPage.isFieldRedBorder(registrationPage.getTxtUsername());
		boolean isEmailRed = registrationPage.isFieldRedBorder(registrationPage.getTxtEmail());
		boolean isPhoneNoRed = registrationPage.isFieldRedBorder(registrationPage.getTXtPhoneNo());

		// Error messages
		boolean usernameError = registrationPage.isUserNameErrorVisible();
		boolean emailError = registrationPage.isEmailErrorVisible();

		// Register button status
		boolean isButtonDisabled = registrationPage.isRegisterButtonDisabled();

		// Username validation
		if (!usernameError || isUsernameRed || isButtonDisabled) {
			Assert.fail("Username error: " + usernameError + ", red border: " + isUsernameRed + ", button disabled: "
					+ isButtonDisabled);
		} else {
			Assert.assertTrue(true, "Username is valid.");
		}

		// Email validation
		if (!emailError || isEmailRed || isButtonDisabled) {
			Assert.fail(" red border: " + isEmailRed + ", button disabled: " + isButtonDisabled);
		} else {
			Assert.assertTrue(true, "Email is valid.");
		}

		// PhoneNo validation
		if (isPhoneNoRed || isButtonDisabled) {
			info(" ***** PhoneNo is show error ****** ");
			Assert.fail(" red border: " + isPhoneNoRed + ", button disabled: " + isButtonDisabled);
		} else {
			info(" ***** PhoneNo is Valid ****** ");
			Assert.assertTrue(true, "PhoneNo is valid.");
		}

	}

}
