package testBase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.model.Test;

import constants.ConstEnum;
import constants.ConstEnum.Browser;
import constants.ConstEnum.OperatingSystem;
import constants.ConstEnum.TestGroups;
import constants.ConstVariables;
import helper.PageURLs;
import io.restassured.RestAssured;
import utilitiesPage.ConfigReader;
import utilitiesTest.LoggerUtility;

public class BaseTest extends LoggerUtility {
	protected static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
	protected static WebDriver driver;
	private Properties properties;
	protected SoftAssert softAssert = new SoftAssert();

	@BeforeTest(groups = { TestGroups.SANITY, TestGroups.MASTER, TestGroups.DATA_DRIVEN })
	public void beforeTest(ITestContext context) {
		logger = LogManager.getLogger(this.getClass());

		String suiteName = context.getSuite().getName();
		System.out.println("Starting @BeforeTest – Method: *beforeTest* - SuiteName: " + suiteName);

		String className = getClass().getSimpleName();
		String logFilePath = ConstVariables.logFilePath + className + "/" + ConstVariables.logFileName;
		setLogFilePath(logFilePath);

		properties = new ConstVariables().getProperties();

		info("Completed @BeforeTest setup");

	}

	@BeforeClass(groups = { TestGroups.SANITY, TestGroups.MASTER, TestGroups.DATA_DRIVEN })
	@Parameters({ "os", "browser" })
	public void setup(@Optional("WINDOWS") OperatingSystem os, @Optional("CHROME") Browser br) throws IOException {
		info("Starting @BeforeClass – Method: *setup*");
		
		setupAPI();
		
		// Determine execution environment and initialize WebDriver accordingly
		boolean isRemote = "remote-server".equalsIgnoreCase(properties.getProperty("runtime.environment"));
		driver = isRemote ? createRemoteDriver(os, br) : createLocalDriver(br);

		  if (driver == null) {
		        error("Failed to initialize WebDriver for browser and os: " + br + "and" + os);
		        throw new IllegalStateException("WebDriver is null in setup()");
		    }
		  
		   tdriver.set(driver);
		   driver = getTDriver();
		   
		   prepareBrowserEnvironment();
		   navigateToPage();
		   
		   info("Completed @BeforeClass setup");
	}
	
	private WebDriver createRemoteDriver(OperatingSystem os, Browser br) {
		DesiredCapabilities caps = new DesiredCapabilities();
		configureOSCapabilities(os, caps);
		configureBrowserCapabilities(br, caps);

		try {
			return new RemoteWebDriver(
					new URL(properties.getProperty("remote.server.url", "http://192.168.1.102:4444")), caps);
		} catch (MalformedURLException e) {
			error("Invalid remote URL: " + e.getMessage());
		} catch (WebDriverException e) {
			error("RemoteWebDriver init failed: " + e.getMessage());
		}
		return null;
	}

	private void configureOSCapabilities(OperatingSystem os, DesiredCapabilities capabilities) {
		switch (os) {
		case WINDOWS:
			capabilities.setPlatform(Platform.WIN11);
			break;
		case LINUX:
			capabilities.setPlatform(Platform.LINUX);
			break;
		case MAC:
			capabilities.setPlatform(Platform.MAC);
			break;
		default:
			error("Invalid OS: " + os);
		}
	}

	private void configureBrowserCapabilities(Browser browser, DesiredCapabilities capabilities) {
		switch (browser) {
		case CHROME:
			capabilities.setBrowserName("chrome");
			break;
		case EDGE:
			capabilities.setBrowserName("MicrosoftEdge");
			break;
		case FIREFOX:
			capabilities.setBrowserName("firefox");
			break;
		default:
			error("No matching browser: " + browser);
		}
	}

	private WebDriver createLocalDriver(Browser browser) {
		info("Executing tests on local environment...");
		switch (browser) {
		case CHROME:
			ChromeOptions chromeOptions = new ChromeOptions();
			// chromeOptions.addArguments("--disable-notifications",
			// "--disable-popup-blocking","--ignore-certificate-errors");
			// chromeOptions.addArguments("--auto-open-devtools-for-tabs");

			return new ChromeDriver(chromeOptions);
		case EDGE:
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--disable-notifications", "--disable-popup-blocking",
					"--ignore-certificate-errors");
			return new EdgeDriver(edgeOptions);
		case FIREFOX:
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--disable-notifications", "--disable-popup-blocking");
			return new FirefoxDriver(firefoxOptions);
		default:
			info("Invalid browser for local execution: " + browser);
			return null;
		}
	}

	public static WebDriver getTDriver() {
		return tdriver.get();
	}
	
	private void prepareBrowserEnvironment() {
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public void setupAPI() {
		 // Set base URI for all API tests
        RestAssured.baseURI = "https://reqres.in";
        System.out.println("Base URI set for API: " + RestAssured.baseURI);
	}
	
	private void navigateToPage() {
		 // Choose the page you want to navigate to (e.g., Login Page)
	    String url = PageURLs.buildURL(PageURLs.Login_PAGE_PATH);
	    // String pageURL = PageURLs.HOME_PAGE;
	    driver.get(url);
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master", "Datadriven" })
	public void teardown() {
	    info("Starting @AfterClass – Method: *teardown*");

	    WebDriver driver = getTDriver();
	    if (driver != null) {
	        try {
	            driver.quit();
	            info(" WebDriver quit successfully.");
	        } catch (Exception e) {
	            error(" Error quitting WebDriver: " + e.getMessage());
	        } finally {
	            tdriver.remove();
	            info(" ThreadLocal WebDriver removed.");
	        }
	    } else {
	        info("No WebDriver instance found in ThreadLocal; skipping teardown.");
	    }
	}

}
