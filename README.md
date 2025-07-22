# Hybrid Test Automation Framework  
### *Built with Java, Selenium WebDriver, Rest Assured, TestNG, Maven & Log4j2*

---

## Overview

This is an **End-to-End Hybrid Test Automation Framework** designed for comprehensive testing across both **UI** and **API** layers of a web application. The framework is developed using **Java**, **Selenium**, **Rest Assured**, **TestNG**, **Maven**, and **Log4j2**, enabling teams to write, manage, and execute reliable automated test cases efficiently.

It follows best practices and design patterns like **Page Object Model (POM)** and emphasizes **modularity**, **scalability**, and **maintainability**. With integrated logging, parallel execution, and rich HTML reporting, this framework offers a robust solution for automation testing and continuous integration pipelines.

---

## Key Features

-  **End-to-End UI & API Test Automation**
-  **Modular and Scalable Architecture**
-  **Parallel Execution Support** using TestNG and Maven Surefire
-  **Page Object Model (POM)** design pattern for UI automation
-  **Data-Driven Testing** with Excel, JSON, or Property files
-  **Cross-Environment Execution** via configuration files
-  **Detailed Logging** using **Log4j2**
-  **Rich HTML Reports** (ExtentReports or Allure)
-  **CI/CD Integration Ready** (Jenkins, GitHub Actions, etc.)

---

## Tech Stack

| Technology     | Purpose                         |
|----------------|----------------------------------|
| Java           | Core programming language       |
| Selenium       | UI automation                   |
| Rest Assured   | API testing                     |
| TestNG         | Test orchestration              |
| Maven          | Build and dependency management |
| Log4j2         | Logging and debugging           |
| Extent Reports | Rich HTML reporting             |

---

## Getting Started

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/aeshamangukiya/AutomationPractice.git
   # or
   git clone git@github.com:aeshamangukiya/AutomationPractice.git

2. **Run Tests:**

   ```bash
   mvn clean test

## Project Structure
<details> <summary>Click to expand</summary>
src/
├── main/
│   ├── java/
│   │   ├── pageBase/
│   │   │   └── BasePage.java
│   │   ├── pageObjects/
│   │   │   ├── pages/
│   │   │   │   ├── accounts/
│   │   │   │   │   ├── LoginPage.java
│   │   │   │   │   └── ProfilePage.java
│   │   │   │   └── common/
│   │   │   │       └── Dashboard.java
│   │   ├── utilities/
│   │   │   ├── ConfigReader.java
│   │   │   ├── WaitUtil.java
│   │   │   └── WebDriverUtil.java
│   │   ├── constants/
│   │   │   ├── ConstVariables.java
│   │   │   ├── Timeouts.java
│   │   │   └── Messages.java
│   └── resources/
│       └── log4j2.xml

test/
├── java/
│   ├── testBase/
│   │   └── BaseTest.java
│   ├── tests/
│   │   ├── account/
│   │   │   ├── login/
│   │   │   │   └── LoginTest.java
│   │   │   └── register/
│   │   │       └── RegisterTest.java
│   │   └── api/
│   │       └── users/
│   │           └── GetUsersAPITest.java
│   ├── listeners/
│   │   ├── TestListener.java
│   │   ├── RetryAnalyzer.java
│   │   ├── ScreenshotListener.java
│   │   └── ExtentReportListener.java
│   ├── dataProviders/
│   │   ├── LoginDataProvider.java
│   │   └── ProfileDataProvider.java
│   └── utilitiesTest/
│       ├── ScreenshotUtil.java
│       ├── LogUtil.java
│       ├── ExcelReader.java
│       └── ReportUtil.java
├── resources/
│   └── config/
│       ├── config.properties
│       ├── testEnvironments/
│       │   ├── qa.properties
│       │   └── prod.properties
│       └── log4j2.xml

testXML/
└── xmlFiles/
    └── testng.xml

testData/
└── accounts/
    └── login/
        ├── LoginTestData.xlsx
        └── ResetPasswordData.xlsx

logs/
└── account/
    └── login/
        ├── login.log
        └── register.log

reports/
└── account/
    └── login/
        └── ExtentReport.html

screenshots/
└── account/
    └── login/
        └── LoginPage_YYYY_MM_DD_HH_MM_SS.png

test-output/      # TestNG output (ignored)
target/           # Build output (ignored)
pom.xml           # Maven configuration
README.md         # Project documentation
</details>

## API Testing with Rest Assured
1. **API test sample (GetUsersAPITest.java):**
```bash
@Test
public void verifyGetUsersAPI() {
    given()
        .baseUri("https://reqres.in")
        .when()
        .get("/api/users?page=2")
        .then()
        .statusCode(200)
        .body("data", not(empty()));
}
```

2. **Location:**
```bash
test/java/tests/api/users/GetUsersAPITest.java
```
3. **Extend with:** 
- JSON Schema validation
- Token authentication
- Data-driven APIs using Excel or JSON

## Naming Conventions
| Component        | Convention | Example                               |
| ---------------- | ---------- | ------------------------------------- |
| Packages/Folders | camelCase  | `utilitiesTest`, `dataProviders`      |
| Classes          | PascalCase | `ConfigReader.java`, `LoginTest.java` |
| Methods/Vars     | camelCase  | `getUserDetails()`, `isActive`        |

## Parallel Execution
Configure via testng.xml:
```bash 
<suite name="RegressionSuite" parallel="methods" thread-count="5">
```
Use with Selenium Grid for remote browsers.

## Reporting & Logging
- ExtentReports: Saved in /reports
- Screenshots: On failure in /screenshots
- Logs: Module-specific under /logs
- Log4j2: Config in resources/log4j2.xml

## Retry & Listeners
- Retry failed tests using RetryAnalyzer
- Custom TestNG listeners: screenshots, logs, reports

## Contact & Credits
- Maintained by: Aesha Mangukiya
- Email: aeshamangukiya1@gmail.com
- GitHub: [GitHub: aeshamangukiya](https://github.com/aeshamangukiya)
