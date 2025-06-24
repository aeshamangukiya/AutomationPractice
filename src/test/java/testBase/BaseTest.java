package testBase;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
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
import utilitiesPage.ConfigReader;
import utilitiesTest.LoggerUtility;

public class BaseTest extends LoggerUtility {
	protected static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
	protected static WebDriver driver;
	private Properties properties;
	protected SoftAssert softAssert = new SoftAssert();

	@BeforeSuite(groups = { "sanity" })
	public void beforeSuite() {
		String logFilePath = ConstVariables.logFilePath + this.getClass().getSimpleName() + "/"
				+ ConstVariables.logFileName;
		setLogFilePath(logFilePath);
		logger = LogManager.getLogger(this.getClass());
		properties = new ConstVariables().getProperties();
		info("Call DataProvider: *beforeSuite* Method");
	}

		@BeforeClass(groups = {TestGroups.SANITY, TestGroups.MASTER, TestGroups.DATA_DRIVEN})
		@Parameters({ "os", "browser" })
		public void setup(@Optional("WINDOWS") OperatingSystem os, @Optional("CHROME")Browser br) throws IOException {
			info("Call DataProvider: *setup* Method");
			 System.out.println("Running setup for Sanity and Master group");
			// Determine execution environment and initialize WebDriver accordingly
			if (properties.getProperty("runtime.environment").equalsIgnoreCase("remote-server")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				configureOSCapabilities(os, capabilities);
				configureBrowserCapabilities(br, capabilities);
				try {
					driver = new RemoteWebDriver(new URL("http://192.168.1.102:4444"), capabilities);
				} catch (Exception e) {
					error("Error initializing RemoteWebDriver: " + e.getMessage());
				}
				
			} else {
				driver = initializeLocalDriver(br);
			}
	
			if (driver != null) {
				tdriver.set(driver);
				driver = getDriver();
				// Choose the page you want to navigate to (e.g., Sport Page)
				String pageURL = PageURLs.buildURL(PageURLs.Home_PAGE_PATH);
				//String pageURL = PageURLs.HOME_PAGE;
				
			    // Open the desired page in the browser
				driver.manage().window().maximize();
			    driver.get(pageURL);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} else {
				error("Failed to initialize WebDriver for browser: " + br);
			}
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

	private WebDriver initializeLocalDriver(Browser browser) {
		info("Executing tests on local environment...");
		switch (browser) {
		case CHROME:	
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--disable-notifications", "--disable-popup-blocking","--ignore-certificate-errors");
			//chromeOptions.addArguments("--auto-open-devtools-for-tabs");
			
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

	public static WebDriver getDriver() {
		return tdriver.get();
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master", "Datadriven" })
	public void teardown() {
		info("Call DataProvider: *teardown* Method");
		WebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
			tdriver.remove();
			info("WebDriver quit and removed from ThreadLocal.");
		}
	}
}
