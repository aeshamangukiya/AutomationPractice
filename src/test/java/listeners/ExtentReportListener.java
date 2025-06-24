package listeners;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.ConstVariables;
import utilitiesTest.LoggerUtility;
import utilitiesTest.ScreenshotUtility;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentReportListener implements ITestListener {
	private static ExtentReports extent;
	private static ExtentSparkReporter sparkReporter;
	private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
	private static Map<String, ExtentTest> testSuiteMap = new ConcurrentHashMap<>();
	private static String absoluteRepPath;

	public synchronized static ExtentReports getExtentInstance(String className) {
		if (extent == null) {

			absoluteRepPath = ConstVariables.ReportPath + className + "//" + ConstVariables.ReportName;

			sparkReporter = new ExtentSparkReporter(absoluteRepPath);
			sparkReporter.config().setDocumentTitle("Automated Test Report");
			sparkReporter.config().setReportName("Test Execution Summary");
			sparkReporter.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Host Name", "Localhost");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User", System.getProperty("user.name"));
		}
		return extent;
	}

	@Override
	public void onStart(ITestContext context) {
		String suiteName = context.getSuite().getName();
		ExtentTest suiteNode = getExtentInstance(suiteName).createTest(suiteName);
		testSuiteMap.put(context.getName(), suiteNode);
		suiteNode.info("Suite started: " + suiteName);
		suiteNode.assignCategory("Suite: " + suiteName);

		getExtentInstance(suiteName).setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os"));
		getExtentInstance(suiteName).setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
	}

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		LoggerUtility.info("****** Start " + methodName + " Test Case *****");

		ExtentTest suiteNode = testSuiteMap.get(result.getTestContext().getName());
		ExtentTest methodNode = suiteNode.createNode(methodName);
		methodNode.assignCategory(result.getTestContext().getName());
		testNode.set(methodNode);
	}

	public void onTestSuccess(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.PASS, result.getMethod().getMethodName() + " passed.");
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	public void onTestFailure(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.FAIL, result.getMethod().getMethodName() + " failed.");
		test.log(Status.FAIL, result.getThrowable());
		try {
			String screenshotPath = ScreenshotUtility.captureScreen(result.getTestClass().getName(), result.getName());
			test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.SKIP, result.getMethod().getMethodName() + " skipped.");
		test.info("Reason: " + result.getThrowable());
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	public void onFinish(ITestContext testContext) {

		ExtentTest suiteNode = testSuiteMap.get(testContext.getName());
		suiteNode.info("Suite finished: " + testContext.getSuite().getName());
		suiteNode.info("Passed: " + testContext.getPassedTests().size());
		suiteNode.info("Failed: " + testContext.getFailedTests().size());
		suiteNode.info("Skipped: " + testContext.getSkippedTests().size());

		getExtentInstance(testContext.getName()).flush();
		File extentReport = new File(absoluteRepPath);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private long getExecutionTime(ITestResult result) {
		return result.getEndMillis() - result.getStartMillis();
	}
}

