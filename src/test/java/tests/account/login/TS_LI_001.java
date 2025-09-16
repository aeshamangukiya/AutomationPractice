package tests.account.login;


import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import constants.FrameworkConstants;
import helper.PageURLs;
import constants.ConstEnum.TestGroup;
import pageObjects.pages.accounts.LoginPage;
import test.base.BaseTest;
import test.utilities.*;

@Listeners(listeners.ExtentReportListener.class)

//TS_LI_001: Verify Sign in With UserName/Email and password.
public class TS_LI_001 extends BaseTest {

	public static final String PAGE_CURRENT_URL_EXP = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
	
	// Verify that the user can login with valid Username and password.
	@Test(dataProvider = "LoginDataStatic", dataProviderClass = data.providers.DataProviderUtil.class, priority = 1, groups = {
			TestGroup.SANITY_CONST, TestGroup.MASTER_CONST, TestGroup.DATA_DRIVEN_CONST })
	public void tc_LI_001(String username, String password, String exp) {

		// Perform login
		LoginPage login = new LoginPage(driver);
		login.setUsername(username);
		login.setPassword(password);
		login.clickSignInBtn();

		// Validation	
		String pageCurrentURLActual = login.getCurrentURL();
		String pageCurrentURLExp = PAGE_CURRENT_URL_EXP;

		info("pageCurrentURLActual: " + pageCurrentURLActual);
		info("pageCurrentURLExp: " + pageCurrentURLExp);

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

}
