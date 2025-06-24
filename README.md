# Automation Testing Framework
This is a Java + Selenium automation testing framework with the following features:
- **ExtentReports**: For detailed HTML reporting.
- **TestNG + Selenium Grid**: For parallel test execution.
- **Log4j2**: For logging test execution details.
- **Screenshot Capture**: For capturing screenshots on test failure.

## Prerequisites
- Java 8 or higher
- Maven
- Selenium WebDriver
- TestNG
- ExtentReports
- Log4j2

## Setup
1. Clone the repository:
   -With https: git clone https://manishparakhiya@bitbucket.org/bbt_work/autopilotqaweb.git
   -With SSH: git clone git@bitbucket.org:bbt_work/autopilotqaweb.git

## Follow a modular and maintainable folder structure
<<<<<<< HEAD
<<<<<<< HEAD
 src
=======
src
>>>>>>> branch 'master' of git@bitbucket.org:bbt_work/autopilotqaweb.git
=======
src
>>>>>>> refs/heads/Login
├── main
│    └── java
<<<<<<< HEAD
<<<<<<< HEAD
│          └──pageBase                                                          # Base classes           
│              │  └── BasePage.java                                  
│              ├── pageObjects.pages.accounts                                   # Page Object Model (POM) classes
│              │        ├── LoginPage.java
│              │        └── ProfilePage.java
│              ├── pageObjects.pages.common                                      # Common reusable page elements
│              │        └──Dashboard.java
│              ├── utilitiesPage                                                 # Utility classes
│              │        ├── ConfigReader.java
│              │        ├── WaitUtil.java                                        # Explicit wait helper
│              │        └── WebDriverUtil.java                                   # WebDriver actions (scroll, switch tabs, etc.)
│              ├── constants                                            # Constants (avoid hardcoded values)
│              │        ├── ConstantVar.java
│              │        ├── Timeouts.java                                       
=======
│          └──pageBase                                                          # Base classes          
│              │  └── BasePage.java                                 
│              ├── pageObjects.pages.accounts                                   # Page Object Model (POM) classes
│              │        ├── LoginPage.java
│              │        └── ProfilePage.java
│              ├── pageObjects.pages.common                                      # Common reusable page elements
│              │        └──Dashboard.java
│              ├── utilitiesPage                                                 # Utility classes
│              │        ├── ConfigReader.java
│              │        ├── WaitUtil.java                                        # Explicit wait helper
│              │        └── WebDriverUtil.java                              # WebDriver actions (scroll, switch tabs, etc.)
│              ├── constants                                                     # Constants (avoid hardcoded values)
│              │        ├── ConstVariables.java
│              │        ├── Timeouts.java                                      
>>>>>>> branch 'master' of git@bitbucket.org:bbt_work/autopilotqaweb.git
│              │        └── Messages.java
=======
│          └── pageBase                                                          # Base classes          
│              │  └── BasePage.java                                 
│              ├── pageObjects.pages.accounts                                    # Page Object Model (POM) classes
│              │        ├── LoginPage.java
│              │        └── ProfilePage.java
│              ├── pageObjects.pages.common                                      # Common reusable page elements
│              │        └── Dashboard.java
│              ├── utilitiesPage                                                 # Utility classes
│              │        ├── ConfigReader.java
│              │        └── WaitUtil.java                                        # Explicit wait helper
│              ├── constants                                                     # Constants (avoid hardcoded values)
│              │        ├── ConstVariables.java
│              │        ├── Timeouts.java                                      
│              │        └── Messages.java
│              ├── helper                                                     
│              │        └── FileHelper.java
│                    
├── main
│    └── java
│          └── resources
│               └──log4j2.xml                                                # Log4j2 configuration
>>>>>>> refs/heads/Login
│
├── test
│    └── java
<<<<<<< HEAD
│          └─ testBase
│              │  └─ BaseTest.java
│              ├── tests.account.login
│              │        └── LoginTest.java
│              ├── tests.account.Register
<<<<<<< HEAD
│              │        └── RegisterTest.java           
│              ├──listeners                                                        # TestNG listeners
│              │        ├──TestListener.java                                       #Start testing 
│              │        ├── RetryAnalyzer.java                                     # Retry failed tests 
│              │        ├── ScreenshotListener.java                                # Capture screenshots on failure
│              │        └── ExtentReportListener.java                              # For Extent Reports
│              ├── dataProviders                                                   # Test data providers
│              │        ├── LoginDataProvider.java
│              │        └── ProfileDataProvider.java
│              ├── utilitiesTest                                                   # Utility classes
│              │        ├── ScreenshotUtil.java
│              │        ├── LogUtil.java 
│              │        ├──  ExcelReader.java
│              │        └── ReportUtil.java                
│
├── test
│    └── java
│          └── resources
│               └──config                                                          #Package  Name
│                     ├── config.properties                                        # Configuration file
│                     ├── testEnvironments                                         # Separate configs per environment
│                     ├── qa.properties
│                     ├── prod.properties
│                     └── log4j2.xml                                                # Log4j2 configuration
│
├── testXML                                                                         # TestNG configuration (Group, Parallel, Sanity, Regression)
│    └── xmlFiles 
│          └── testng.xml                                                    
│ 
├── testData                                                                        # Test data files
│    └── accounts 
│          └── login
│               ├── LoginTestData.xlsx
│               └── ResetPasswordData.xlsx
├── logs                                                                            # Log files
│    └── account
│          └── login
│               ├── login.log
│               └── Register.log
│ 
=======
│              │        └── RegisterTest.java          
│              ├──listeners                                                        # TestNG listeners
│              │        ├──TestListener.java                                       #Start testing
│              │        ├── RetryAnalyzer.java                                     # Retry failed tests
│              │        ├── ScreenshotListener.java                                # Capture screenshots on failure
│              │        └── ExtentReportListener.java                              # For Extent Reports
│              ├── dataProviders                                                   # Test data providers
│              │        ├── LoginDataProvider.java
│              │        └── ProfileDataProvider.java
│              ├── utilitiesTest                                                   # Utility classes
│              │        ├── ScreenshotUtil.java
│              │        ├── LogUtil.java
│              │        ├──  ExcelReader.java
│              │        └── ReportUtil.java               
│
├── test
│    └── java
│          └── resources
│               └──config                                                          #Package  Name
│                     ├── config.properties                                        # Configuration file
│                     ├── testEnvironments                                         # Separate configs per environment
│                     ├── qa.properties
│                     ├── prod.properties
│                     └── log4j2.xml                                                # Log4j2 configuration
│
├── testXML                             #RootLevel   # TestNG configuration (Group, Parallel, Sanity, Regression)
│    └── xmlFiles
│          └── testng.xml                                                   
│
├── testData                                                                        # Test data files
│    └── accounts
│          └── login
│               ├── LoginTestData.xlsx
│               └── ResetPasswordData.xlsx
├── logs                                                                            # Log files
│    └── account
│          └── login
│               ├── login.log
│               └── Register.log
│
>>>>>>> branch 'master' of git@bitbucket.org:bbt_work/autopilotqaweb.git
├── reports                                                                         # Test execution reports
│       └── account
│              └── login
│                    └── ExtentReport.html
│
├── screenshots                                                                     # Screenshots of test failures
│     └── account
│          ├──login
│          └── LoginPage_2025_01_27_03:56:49.png
│
│
├── test-output                                                                      # TestNG output folder (gitignore this)
│
├── target                                                                           # Compiled test output (gitignore this)
│
├── pom.xml                                                                          # Maven build configuration
└── README.md                                                                        # Project documentation
=======
│           └─ testBase
│              │  └─ BaseTest.java
│              ├── tests.account.login
│              │        └── LoginTest.java
│              ├── tests.account.Register
│              │        └── RegiTest.java          
│              ├──listeners                                                        # TestNG listeners
│              │        ├──TestListener.java                                       #Start testing
│              │        ├── RetryAnalyzer.java                                     # Retry failed tests
│              │        ├── ScreenshotListener.java                                # Capture screenshots on failure
│              │        └── ExtentReportListener.java                              # For Extent Reports
│              ├── dataProviders                                                   # Test data providers
│              │        ├── LoginDataProvider.java
│              │        └── ProfileDataProvider.java
│              ├── utilitiesTest                                                   # Utility classes
│              │        ├── ScreenshotUtil.java
│              │        ├── LoggerUtility.java
│              │        └──ExcelReader.java             
│
├── test
│    └── java
│          └── resources
│               └──config                                                          #Package  Name → Folder name
│                     ├── config.properties                                        # Configuration file
│                     ├── testEnvironments                                         # Separate configs per environment
│                     ├── qa.properties
│                     └──prod.properties
│
├── testXML                                                                         #RootLevel   # TestNG configuration (Group, Parallel, Sanity, Regression)
│    └── xmlFiles
│          └── testng.xml                                                   
│
├── testData                                                                        # Test data files
│    └── accounts
│          └── login
│               ├── LoginTestData.xlsx
│               └── ResetPasswordData.xlsx
├── logs                                                                            # Log files
│    └── class tests.account.login.LoginTest
│               └── 20_02_2025_17_46_59.log                                         # Date_hour_min_sec
│
├── reports                                                                         # Test execution reports
│    └── class tests.account.login.LoginTest
│               └── 20_02_2025_17_46_59.log                                         # Date_hour_min_sec
│
├── screenshots                                                                    # Screenshots of test failures
│    └── class tests.account.login.LoginTest
│               └── testLogin_20_02_2025_17_46_59.log                              # MothodName_Date_hour_min_sec
│
│
├── test-output                                                                    # TestNG output folder (gitignore this)
│
├── target                                                                         # Compiled test output (gitignore this)
│
├── pom.xml                                                                        # Maven build configuration
└── README.md                                                                      # Project documentation
>>>>>>> refs/heads/Login

		   
##XML Files 
1) pageObjects: Contains the Page Object Model (POM) classes representing web pages and their elements.
2) testCases: Contains the actual test files. You can organize your tests into subdirectories as needed.
3) Utilities: Contains the Base for every test (setup and tearDown methods).
4) testng.xml: Tests execution configuration file


## Naming Conventions
1) Packages and Folders
-CamelCase: Package and Folder names should be in CamelCase.
-Example: utilitiesTest, pageBase

2) Classes
-PascalCase: Class names should be in PascalCase. 
-Example: ConfigReader.java, LoginPage.java 

3) Methods and Variable 
-CamelCase: Methods and Variable names should be in CamelCase. 
-Example for Methods: getUserDetails(), sendEmailNotification()
-Example for Variable: isActive, totalAmount
		       