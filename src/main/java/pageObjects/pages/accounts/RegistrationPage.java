package pageObjects.pages.accounts;

import java.lang.ProcessHandle.Info;
import java.nio.channels.SelectableChannel;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import helper.ElementActions;
import helper.RandomStringGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import net.bytebuddy.implementation.bind.annotation.Super;
import pageBase.BasePage;

public class RegistrationPage extends BasePage {

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}

	RandomStringGenerator random = new RandomStringGenerator();

	// Form Field Locators
	@FindBy(xpath = "(//input[@id='FirstName'])[1]")
	WebElement txtName;

	@FindBy(xpath = "//input[@name=\"LastName\"]")
	WebElement txtSurname;

	@FindBy(xpath = "//select[@name=\"BirthDay\"]")
	WebElement ddlDay;

	@FindBy(xpath = "//select[@name='BirthMonth']")
	WebElement ddlMonth;

	@FindBy(xpath = "//select[@name=\"BirthYear\"]")
	WebElement ddlYear;

	@FindBy(xpath = "//select[@name=\"Country\"]")
	WebElement ddlCountry;

	@FindBy(xpath = "//select[@name=\"Currency\"]")
	WebElement ddlCurrency;

	@FindBy(xpath = "//input[@name=\"Email\"]")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@name=\"PhoneNo\"]")
	WebElement txtPhoneNo;

	@FindBy(xpath = "//input[@name=\"UserName\"]")
	WebElement txtUsername;

	@FindBy(xpath = "//input[@name=\"Password\"]")
	WebElement txtPassword;

	@FindBy(xpath = "//input[@name=\"ConfirmPassword\"]")
	WebElement txtPasswordAgain;
	
	// Action Button
	@FindBy(xpath = "//div[@class=\"text-right register-loader-right-dir\"]//button[@type=\"submit\"]")
	WebElement btnRegister;
	
	//  Error Message Elements
	@FindBy(xpath = "//span[@class=\"text-danger UserNameMinLength ng-star-inserted\"]")
	WebElement errorUserName;

	@FindBy(xpath = "//span[@class=\"text-danger email ng-star-inserted\"]")
	WebElement errorEmail;

	
	// Input Field Setters
	public void setName(String name) {
		ElementActions.selectBySendkeys(name, txtName);
	}

	public void setSurname(String surName) {
		ElementActions.selectBySendkeys(surName, txtSurname);
	}
	
	public void setEmailID(String email) {
		ElementActions.selectBySendkeys(email, txtEmail);
	}

	public void setPhoneNo(String phoneNumber) {
		ElementActions.selectBySendkeys(phoneNumber, txtPhoneNo);
	}

	public void setUsername(String username) {
		ElementActions.selectBySendkeys(username, txtUsername);
	}

	public void setPassword(String password) {
		ElementActions.selectBySendkeys(password, txtPassword);
	}

	public void setPasswordAgain(String passwordAgain) {
		ElementActions.selectBySendkeys(passwordAgain, txtPasswordAgain);
	}


	// Dropdown Selectors (Random)
	public void selectDay() {
		random.randomOption(ddlDay);
	}

	public void selectMonth() {

		random.randomOption(ddlMonth);
	}

	public void selectYear() {
		random.randomOption(ddlYear);
	}

	public void selectCountry() {
		random.randomOption(ddlCountry);
	}

	public void selectCurrency() {
		random.randomOption(ddlCurrency);
	}

	// Click Register Button
	public void clickPopupRegister() { 
		ElementActions.click(driver, btnRegister);
	}

	// Button & Error Validations
	public boolean isRegisterBtnVisible() {
		return ElementActions.isDisplayed(driver, btnRegister);
	}

	public boolean isUserNameErrorVisible() {
		return ElementActions.isDisplayed(driver, errorUserName);
	}

	public boolean isEmailErrorVisible() {
		return ElementActions.isDisplayed(driver, errorEmail);
	}

	// Generic method to check red border for any input field
	public boolean isFieldRedBorder(WebElement field) {
		try {
			String borderColor = field.getCssValue("border-color");
			System.out.println("Field border color: " + borderColor);
			return borderColor.equals("rgb(255, 56, 84)"); // Red border
		} catch (Exception e) {
			System.out.println("Error while checking red border: " + e.getMessage());
			return false;
		}
	}
	

	// Getter Methods for Input Fields
	public WebElement getTxtUsername() {
		return txtUsername;
	}

	public WebElement getTxtEmail() {
		return txtEmail;
	}
	
	public WebElement getTXtPhoneNo() {
		return txtPhoneNo;
	}
	

	// Check if register button is disabled
	public boolean isRegisterButtonDisabled() {
		try {
			return !btnRegister.isEnabled(); // Returns true if the button is disabled
		} catch (Exception e) {
			System.out.println("Exception in checking if Register button is disabled: " + e.getMessage());
			return true; // Assuming button is not interactable, so treat as disabled
		}
	}

	// Clear Form Fields
	public void clearRegistrationForm() {
		txtName.clear();
		txtSurname.clear();
		ddlDay.clear();
		ddlMonth.clear();
		ddlYear.clear();
		ddlCountry.clear();
		ddlCurrency.clear();
		txtEmail.clear();
		txtPhoneNo.clear();
		txtUsername.clear();
		txtPassword.clear();
		txtPasswordAgain.clear();

	}

}
