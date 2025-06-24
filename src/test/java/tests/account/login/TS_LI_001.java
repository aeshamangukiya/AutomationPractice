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
import pageObjects.pages.common.SlotCasinoPage;
import pageObjects.pages.common.DashboardPage;
import testBase.BaseTest;
import utilitiesTest.ExcelReader;
import utilitiesTest.LoggerUtility;

@Listeners(listeners.ExtentReportListener.class)

//TS_LI_001: Verify Sign in With UserName/Email and password.
public class TS_LI_001 extends BaseTest {

	// Verify that the user can login with valid Username and password.
	@Test(dataProvider = "LoginDataStatic", dataProviderClass = dataProviders.DataProviderUtils.class, priority = 1)
	@Parameters({ "os", "browser" })
	public void tc_LI_001(String username, String password, String exp) throws InterruptedException {
		try {
			// Perform login
			DashboardPage dashboard = new DashboardPage(driver);
			dashboard.setEmailUsername(username);
			dashboard.setPassword(password);
			dashboard.clickSignInBtn();

			// Add wait if needed for the result to appear
			Thread.sleep(200);

			boolean isNotificationVisible = dashboard.isNotificationIconVisible();

			if (exp.equalsIgnoreCase("Success")) {
				Thread.sleep(200);
				Assert.assertTrue(isNotificationVisible, "Expected login to fail, but it passed.");
			} else if (exp.equalsIgnoreCase("Fail")) {
				if (isNotificationVisible) {
					dashboard.clearUsernameEmailTxt();
					dashboard.clearPasswordTxt();
				}
				Assert.assertFalse(isNotificationVisible, "Login should have passed, and it did.");
			} else {
				Assert.fail("Invalid expected result value: " + exp);
			}

		} catch (Exception e) {
			LoggerUtility.error("Test failed due to exception: " + e.getMessage());
			Assert.fail("Test case failed due to exception: " + e.getMessage());
		}
		
		Thread.sleep(1000);
	}

	// Verify that user can or cannot login with different test cases.
	@Test(dataProvider = "LoginDataExcel", dataProviderClass = dataProviders.DataProviderUtils.class, groups = {
			TestGroups.SANITY }, priority = 2)
	@Parameters({ "os", "browser", }) //

	public void tc_LI_002(String username, String password, String exp) throws InterruptedException {
		try {
			// Perform login
			DashboardPage dashboard = new DashboardPage(driver);
			dashboard.setEmailUsername(username);
			dashboard.setPassword(password);
			dashboard.clickSignInBtn();

			Thread.sleep(300);
			// Validation of login state
			boolean isNotificationVisible = dashboard.isNotificationIconVisible();

			if (exp.equalsIgnoreCase("Fail")) {
				if (!isNotificationVisible) {
					dashboard.clearUsernameEmailTxt();
					dashboard.clearPasswordTxt();
				}
				Assert.assertFalse(isNotificationVisible, "Login should have passed, and it did.");

			} else {
				if (isNotificationVisible) {
					// Logged in successfully, perform logout
					dashboard.clickMyAccountDdl();
					dashboard.clickLogoutOpt();
				}
				Assert.assertTrue(isNotificationVisible, "Login should have failed, but it Passed.");
			}
		} catch (Exception e) {
			LoggerUtility.error("Test failed due to exception: " + e.getMessage());
			Assert.fail("Test case failed due to exception: " + e.getMessage());
		}
		Thread.sleep(2000);
	}

}
