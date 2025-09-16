package test.base;

import java.io.IOException;    
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Properties;

import org.apache.logging.log4j.ThreadContext;
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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import constants.ConstEnum.Browser;
import constants.ConstEnum.OperatingSystem;
import constants.ConstEnum.TestGroup;
import helper.PageURLs;
import io.restassured.RestAssured;
import configuration.ConfigReader;
import test.utilities.LoggerUtil;
import constants.FrameworkConstants;

public class BaseTest extends LoggerUtil {

    // Thread-safe WebDriver holder for multi-threaded execution
    protected static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
    protected static WebDriver driver;

    // Properties and soft assertions
    private Properties properties;
    protected SoftAssert softAssert = new SoftAssert();

    // Initializes ThreadContext and configures logging before test starts
    @BeforeTest(groups = {
            TestGroup.SANITY_CONST,
            TestGroup.REGRESSION_CONST,
            TestGroup.DATA_DRIVEN_CONST,
            TestGroup.MASTER_CONST
    })
    
    public void beforeTest(ITestContext context) {
        final var suiteName = context.getSuite().getName();
        final var className = getClass().getSimpleName();

        // Storing test suite and class information for contextual logging
        ThreadContext.put("suite", suiteName);
        ThreadContext.put("class", className);

        // Setting log file path dynamically based on class name
        final var logFilePath = FrameworkConstants.LOG_FILE_PATH + className + "/" + FrameworkConstants.LOG_FILE_NAME;
        setLogFilePath(logFilePath);

        info("===== Starting Test Suite: '%s', Class: '%s' =====", suiteName, className);

        // Load properties and set environment value for logging
        properties = ConfigReader.getAllProperties();
        final var runtimeEnv = properties.getProperty("runtime.environment", "local");
        ThreadContext.put("env", runtimeEnv);

        debug("Loaded configuration (runtime.environment=%s).", runtimeEnv);
        info("===== Test Setup Completed =====");
    }

    // Initializes WebDriver and browser environment before each test class runs
    @BeforeClass(groups = {
            TestGroup.SANITY_CONST,
            TestGroup.REGRESSION_CONST,
            TestGroup.DATA_DRIVEN_CONST,
            TestGroup.MASTER_CONST
    })
    @Parameters({"os", "browser"})
    public void setup(@Optional("WINDOWS") OperatingSystem os, @Optional("CHROME") Browser br) throws IOException {
        info("===== Test Class Setup Started =====");

        // Set OS and browser information for logging context
        ThreadContext.put("os", os.name());
        ThreadContext.put("browser", br.name());

        final var runtimeEnv = properties.getProperty("runtime.environment", "local");
        final var isRemote = "remote-server".equalsIgnoreCase(runtimeEnv);

        info("Test Execution Mode: %s", isRemote ? "REMOTE" : "LOCAL");

        // Initialize the WebDriver based on environment and browser
        driver = isRemote ? createRemoteDriver(os, br) : createLocalDriver(br);

        if (driver == null) {
            error("WebDriver initialization failed for browser=%s, os=%s.", br, os);
            throw new IllegalStateException("WebDriver is null in setup()");
        }

        tdriver.set(driver); // Store WebDriver in ThreadLocal for thread safety
        driver = getTDriver();

        // Prepare browser environment and navigate to the homepage
        prepareBrowserEnvironment();
        navigateToPage();

        info("===== Test Class Setup Completed =====");
    }

    // Creates a Remote WebDriver instance with the specified capabilities
    private WebDriver createRemoteDriver(OperatingSystem os, Browser br) {
        info("Creating RemoteWebDriver with specified capabilities...");

        var caps = new DesiredCapabilities();
        configureOSCapabilities(os, caps);
        configureBrowserCapabilities(br, caps);

        var remoteUrl = properties.getProperty("remote.server.url", "http://192.168.1.102:4444");
        ThreadContext.put("remoteUrl", remoteUrl);

        try {
            info("Connecting to Selenium Grid at: %s", remoteUrl);
            var remote = new RemoteWebDriver(URI.create(remoteUrl).toURL(), caps);

            info("Remote WebDriver session created successfully (Session ID: %s).", safeSessionId(remote));
            return remote;
        } catch (MalformedURLException e) {
            error("Malformed remote URL: %s", e.getMessage());
        } catch (WebDriverException e) {
            error("Failed to create Remote WebDriver: %s", e.getMessage());
        }
        return null;
    }

    // Configures OS capabilities for Remote WebDriver
    private void configureOSCapabilities(OperatingSystem os, DesiredCapabilities capabilities) {
        switch (os) {
            case WINDOWS -> capabilities.setPlatform(Platform.WIN11);
            case LINUX -> capabilities.setPlatform(Platform.LINUX);
            case MAC -> capabilities.setPlatform(Platform.MAC);
            default -> warn("Unknown OS: %s. Defaulting to current platform.", os);
        }
        debug("Remote WebDriver capabilities set (OS: %s).", os);
    }

    // Configures browser capabilities for Remote WebDriver
    private void configureBrowserCapabilities(Browser browser, DesiredCapabilities capabilities) {
        switch (browser) {
            case CHROME -> capabilities.setBrowserName("chrome");
            case EDGE -> capabilities.setBrowserName("MicrosoftEdge");
            case FIREFOX -> capabilities.setBrowserName("firefox");
            default -> warn("Unknown browser: %s.", browser);
        }
        debug("Remote WebDriver capabilities set (Browser: %s).", browser);
    }

    // Creates a local WebDriver instance based on the specified browser
    private WebDriver createLocalDriver(Browser browser) {
        info("Creating local WebDriver for browser: %s...", browser);
        switch (browser) {
            case CHROME -> {
                var options = new ChromeOptions();
                var drv = new ChromeDriver(options);
                info("Local ChromeDriver session created successfully.");
                return drv;
            }
            case EDGE -> {
                var options = new EdgeOptions();
                options.addArguments("--disable-notifications", "--disable-popup-blocking", "--ignore-certificate-errors");
                var drv = new EdgeDriver(options);
                info("Local EdgeDriver session created successfully.");
                return drv;
            }
            case FIREFOX -> {
                var options = new FirefoxOptions();
                options.addArguments("--disable-notifications", "--disable-popup-blocking");
                var drv = new FirefoxDriver(options);
                info("Local FirefoxDriver session created successfully.");
                return drv;
            }
            default -> {
                error("Unsupported browser for local execution: %s", browser);
                return null;
            }
        }
    }

    // Safely retrieves the session ID of the Remote WebDriver
    private static String safeSessionId(RemoteWebDriver remote) {
        try {
            return String.valueOf(remote.getSessionId());
        } catch (Exception e) {
            return "n/a";
        }
    }

    // Prepares the browser environment (e.g., maximizing window)
    private void prepareBrowserEnvironment() {
        try {
            driver.manage().window().maximize();
            info("Browser window maximized successfully.");
        } catch (Exception e) {
            warn("Failed to maximize browser window: %s", e.getMessage());
        }
    }
    
	public void setupAPI() {
		 // Set base URI for all API tests
       RestAssured.baseURI = "https://reqres.in";
       System.out.println("Base URI set for API: " + RestAssured.baseURI);
	}

    // Navigates to the specified homepage URL
    private void navigateToPage() {
        final var pageURL = PageURLs.buildURL(PageURLs.Login_PAGE_PATH);
        info("Navigating to: %s", pageURL);
        try {
            driver.get(pageURL);
            info("Navigation to homepage successful.");
        } catch (Exception e) {
            error("Navigation failed to '%s': %s", pageURL, e.getMessage());
            throw e;
        }
    }

    // Returns the current WebDriver instance from the ThreadLocal storage
    public static WebDriver getTDriver() {
        return tdriver.get();
    }

    // Cleans up WebDriver instance and clears ThreadLocal context after class execution
    @AfterClass(groups = {
            TestGroup.SANITY_CONST,
            TestGroup.REGRESSION_CONST,
            TestGroup.DATA_DRIVEN_CONST,
            TestGroup.MASTER_CONST
    })
    public void teardown() {
        info("===== Test Class Teardown Started =====");

        var drv = getTDriver();
        if (drv != null) {
            try {
                drv.quit();
                info("WebDriver quit successfully.");
            } catch (Exception e) {
                error("Error quitting WebDriver: %s", e.getMessage());
            } finally {
                tdriver.remove();
                info("ThreadLocal WebDriver instance removed.");
                ThreadContext.clearMap();
                ThreadContext.clearStack();
            }
        } else {
            warn("No WebDriver instance found; skipping teardown.");
            ThreadContext.clearMap();
            ThreadContext.clearStack();
        }

        info("===== Test Class Teardown Completed =====");
    }
}
