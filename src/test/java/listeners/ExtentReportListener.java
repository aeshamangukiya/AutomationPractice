package listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import constants.FrameworkConstants;
import test.utilities.LoggerUtil;
import test.utilities.ScreenshotUtil;

public class ExtentReportListener implements ITestListener {

    // ===============================
    // Extent Report Initialization
    // ===============================
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    private static Map<String, ExtentTest> suiteMap = new ConcurrentHashMap<>();
    private static String reportFilePath;

    public synchronized static ExtentReports getExtentInstance(String suiteName) {
        if (extent == null) {
            reportFilePath = FrameworkConstants.REPORT_PATH + suiteName + File.separator + FrameworkConstants.REPORT_NAME;

            sparkReporter = new ExtentSparkReporter(reportFilePath);
            sparkReporter.config().setDocumentTitle("Automated Test Report");
            sparkReporter.config().setReportName("Test Execution Summary");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Host Name", "Localhost");
            extent.setSystemInfo("Environment", System.getProperty("app.environment", "QA"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }

    // ===============================
    // Suite Level Events
    // ===============================
    @Override
    public void onStart(ITestContext context) {
        String suiteName = context.getSuite().getName();
        ExtentTest suiteNode = getExtentInstance(suiteName).createTest(suiteName);
        suiteNode.info("Suite started: " + suiteName);
        suiteNode.assignCategory("Suite: " + suiteName);

        suiteMap.put(context.getName(), suiteNode);

        extent.setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os"));
        extent.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTest suiteNode = suiteMap.get(context.getName());
        if (suiteNode != null) {
            suiteNode.info("Suite finished: " + context.getSuite().getName());
            suiteNode.info("Passed: " + context.getPassedTests().size());
            suiteNode.info("Failed: " + context.getFailedTests().size());
            suiteNode.info("Skipped: " + context.getSkippedTests().size());
        }

        extent.flush();
        openReportFile();
    }

    // ===============================
    // Test Level Events
    // ===============================
    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        LoggerUtil.info("****** Starting Test: " + methodName + " *****");

        ExtentTest suiteNode = suiteMap.get(result.getTestContext().getName());
        ExtentTest methodNode = suiteNode.createNode(methodName)
                                         .assignCategory(result.getTestContext().getName());

        currentTest.set(methodNode);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        currentTest.get().log(Status.PASS, result.getMethod().getMethodName() + " passed.")
                              .info(formatExecutionTime(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = currentTest.get();
        test.log(Status.FAIL, result.getMethod().getMethodName() + " failed.")
            .log(Status.FAIL, result.getThrowable())
            .info(formatExecutionTime(result));

        try {
            // Retrieve WebDriver from test class instance
            Object testInstance = result.getInstance();
            Field driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            WebDriver driver = (WebDriver) driverField.get(testInstance);

            // Capture screenshot
            String screenshotPath = ScreenshotUtil.captureScreen(driver,
                    result.getTestClass().getName(), result.getName());

            test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");

        } catch (Exception e) {
            LoggerUtil.error("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        currentTest.get().log(Status.SKIP, result.getMethod().getMethodName() + " skipped.")
                              .info("Reason: " + result.getThrowable())
                              .info(formatExecutionTime(result));
    }

    // ===============================
    // Utility Methods
    // ===============================
    private String formatExecutionTime(ITestResult result) {
        long timeMs = result.getEndMillis() - result.getStartMillis();
        double timeSec = timeMs / 1000.0;
        return String.format("Execution Time: %d ms (%.2f seconds)", timeMs, timeSec);
    }

    private void openReportFile() {
        File reportFile = new File(reportFilePath);
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(reportFile.toURI());
            } catch (IOException e) {
                LoggerUtil.warn("Unable to open report automatically: " + e.getMessage());
            }
        }
    }
}
