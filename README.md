Java + Selenium Automation Testing Framework

A modular and maintainable Java + Selenium framework including:
- ExtentReports – detailed HTML reporting
- TestNG + Selenium Grid – parallel test execution
- Log4j2 – robust test logging
- Screenshot Capture – captures screenshots on test failure

Prerequisites:
- Java 8 or higher
- Maven
- Selenium WebDriver
- TestNG
- ExtentReports
- Log4j2

Setup:
1. Clone the repo:
   git clone https://github.com/aeshamangukiya/AutomationPractice.git
   ‑ or ‑
   git clone git@github.com:aeshamangukiya/AutomationPractice.git
2. Run tests:
   mvn clean test

Project Structure:
src/
  main/
    java/
      pageBase/
        BasePage.java
      pageObjects/
        pages/
          accounts/
            LoginPage.java
            ProfilePage.java
          common/
            Dashboard.java
      utilities/
        ConfigReader.java
        WaitUtil.java
        WebDriverUtil.java
      constants/
        ConstVariables.java
        Timeouts.java
        Messages.java
    resources/
      log4j2.xml

test/
  java/
    testBase/
      BaseTest.java
    tests/
      account/
        login/
          LoginTest.java
        register/
          RegisterTest.java
    listeners/
      TestListener.java
      RetryAnalyzer.java
      ScreenshotListener.java
      ExtentReportListener.java
    dataProviders/
      LoginDataProvider.java
      ProfileDataProvider.java
    utilitiesTest/
      ScreenshotUtil.java
      LogUtil.java
      ExcelReader.java
      ReportUtil.java
  resources/
    config/
      config.properties
      testEnvironments/
        qa.properties
        prod.properties
      log4j2.xml

testXML/
  xmlFiles/
    testng.xml

testData/
  accounts/
    login/
      LoginTestData.xlsx
      ResetPasswordData.xlsx

logs/
  account/
    login/
      login.log
      register.log

reports/
  account/
    login/
      ExtentReport.html

screenshots/
  account/
    login/
      LoginPage_YYYY_MM_DD_HH_MM_SS.png

test-output/      # TestNG output (ignored)
target/           # Build output (ignored)
pom.xml           # Maven configuration
README.txt        # Project documentation

XML Files:
- Page Objects – classes modeling web pages/elements
- Test Cases – organized test files
- Utilities – setup/teardown logic
- testng.xml – defines suite groups, parallelization

Naming Conventions:
- Packages/Folders: CamelCase (e.g., utilitiesTest)
- Classes: PascalCase (e.g., ConfigReader.java)
- Methods/Variables: camelCase (e.g., getUserDetails(), isActive)

Next Steps:
- Add badges (build, coverage, etc.)
- Include usage examples or environment variable details
- Document CI/CD integration
- Provide sample test reports or screenshots

Contact & Credits:
- Maintained by [Your Name/Team]
- For queries, reach out at: your.email@example.com
- License: [Add license information here]
