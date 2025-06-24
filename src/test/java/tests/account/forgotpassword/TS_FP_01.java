package tests.account.forgotpassword;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import constants.ConstVariables;
import constants.ConstEnum.TestGroups;
import pageObjects.pages.accounts.ForgotPasswordPage;
import pageObjects.pages.common.DashboardPage;
import testBase.BaseTest;
import utilitiesTest.LoggerUtility;

@Listeners(listeners.ExtentReportListener.class)

public class TS_FP_01 extends BaseTest {
	String EmailID = ConstVariables.getConfigPropertiesValue("emailID");
	String MobileNo = ConstVariables.getConfigPropertiesValue("mobileNo");

	@Test
	public void TC_FP_01() {
		// Perform Forgot Password via Email
		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.clickForgotPwdLnk();
		ForgotPasswordPage forgotPwd = new ForgotPasswordPage(driver);
		forgotPwd.setEmail(EmailID);
		forgotPwd.clickResetPwdBtn();
	}
}
