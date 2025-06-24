package tests.account.login;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import constants.ConstVariables;
import helper.PageURLs;
import constants.ConstEnum.TestGroups;
import pageObjects.pages.accounts.LoginPage;
import testBase.BaseTest;
import utilitiesTest.ExcelReader;
import utilitiesTest.LoggerUtility;

@Listeners(listeners.ExtentReportListener.class)

//TS_LI_001: Verify Sign in With UserName/Email and password.
public class TS_LI_001 extends BaseTest {

	// Verify that the user can login with valid Username and password.
	@Test(dataProvider = "LoginDataStatic", dataProviderClass = dataProviders.DataProviderUtils.class, priority = 1, groups = {
			TestGroups.SANITY, TestGroups.MASTER, TestGroups.DATA_DRIVEN })
	public void tc_LI_001(String username, String password, String exp) {

		// Perform login
		LoginPage login = new LoginPage(driver);
		login.setUsername(username);
		login.setPassword(password);
		login.clickSignInBtn();

		// Validation
		String pageCurrentURLActual = login.getCurrentURL();
		String pageCurrentURLExp = ConstVariables.pageCurrentURLExp;

		logger.info("pageCurrentURLActual: " + pageCurrentURLActual);
		logger.info("pageCurrentURLExp: " + pageCurrentURLExp);

		if (exp.equalsIgnoreCase("Success")) {
			// When login is expected to succeed, exact match
			Assert.assertEquals(pageCurrentURLActual, pageCurrentURLExp,
					"URL mismatch: expected [" + pageCurrentURLExp + "] but was [" + pageCurrentURLActual + "]");
		} else if (exp.equalsIgnoreCase("Fail")) {
			// When login is expected to fail, ensure URL is *not* exactly what a success
			// would give
			Assert.assertNotEquals(pageCurrentURLActual, pageCurrentURLExp,
					"Login failed, but URL matches the success URL! Actual: [" + pageCurrentURLActual + "]");
		} else {
			Assert.fail("Invalid expected result value: \"" + exp + "\"");
		}
	}

	// Verify that the user can login with DataDriven.
	@Test(dataProvider = "LoginDataStatic", dataProviderClass = dataProviders.DataProviderUtils.class, priority = 1, groups = {
			TestGroups.SANITY, TestGroups.MASTER, TestGroups.DATA_DRIVEN })
	public void tc_LI_002(String username, String password, String exp) {

		// Perform login
		LoginPage login = new LoginPage(driver);
		login.setUsername("gjhgj");
		login.setPassword("5566");
		login.clickSignInBtn();

		// Validation
		String pageCurrentURLActual = login.getCurrentURL();
		String pageCurrentURLExp = ConstVariables.pageCurrentURLExp;

		logger.info("pageCurrentURLActual: " + pageCurrentURLActual);
		logger.info("pageCurrentURLExp: " + pageCurrentURLExp);

		if (exp.equalsIgnoreCase("Success")) {
			// When login is expected to succeed, exact match
			Assert.assertEquals(pageCurrentURLActual, pageCurrentURLExp,
					"URL mismatch: expected [" + pageCurrentURLExp + "] but was [" + pageCurrentURLActual + "]");
		} else if (exp.equalsIgnoreCase("Fail")) {
			if (!pageCurrentURLActual.equalsIgnoreCase(pageCurrentURLExp)) {
				login.clearUsernameEmailTxt();
				login.clearPasswordTxt();
			}
			// When login is expected to fail, ensure URL is *not* exactly what a success
			// would give
			Assert.assertNotEquals(pageCurrentURLActual, pageCurrentURLExp,
					"Login failed, but URL matches the success URL! Actual: [" + pageCurrentURLActual + "]");
		} else {
			Assert.fail("Invalid expected result value: \"" + exp + "\"");
		}
	}

}
