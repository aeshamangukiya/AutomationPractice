## Java + Selenium Hybrid Automation Framework

Welcome to the **AutomationPractice Framework** — your powerful, plug-and-play solution for scalable test automation. Built with using **Java + Selenium + TestNG**, this framework is crafted for serious QA engineers who believe in clean code, modular design, and meaningful reports.

-----------------------------------------------------------------------------------
## Prerequisites:
This framework isn't just a basic setup — it's designed for real-world automation testing, interview-ready project showcases, and enterprise-level scalability. Below is a breakdown of the key features (or prerequisites) and why they matter:

- **Page Object Model** — A design pattern that separates page-specific logic (like locators and interactions) from test scripts.
- **TestNG + Selenium Grid** — TestNG is a popular testing framework for Java; Selenium Grid allows you to run tests on different browsers/machines in parallel.
- **ExtentReports** — A powerful reporting library that generates rich HTML reports with test steps, statuses, screenshots, and system info.
- **Log4j2 Logging** — A flexible logging library for Java that captures detailed execution logs.
- **Auto Screenshot on Failure** — Automatically captures a screenshot whenever a test fails.
- **Retry Mechanism** —  Automatically re-executes failed tests using TestNG’s IRetryAnalyzer.

-----------------------------------------------------------------------------------
## Setup:
1. Clone the repo:
   HTTPS: git clone https://github.com/aeshamangukiya/AutomationPractice.git

   SSL: git clone git@github.com:aeshamangukiya/AutomationPractice.git
2. Run tests:
   mvn clean test
   
-----------------------------------------------------------------------------------
## Project Structure:
AutomationPractice/
├── src/
│   ├── main/java/
│   │   ├── pageBase/           → Core WebDriver & setup
│   │   ├── pageObjects/pages/  → Organized page classes
│   │   ├── utilities/          → Reusable helpers (WaitUtil, ConfigReader, etc.)
│   │   └── constants/          → Timeouts, Messages, Variables
│   └── resources/              → log4j2 configuration
│
├── test/
│   ├── java/
│   │   ├── testBase/           → BaseTest setup/teardown
│   │   ├── tests/              → Your actual test cases
│   │   ├── listeners/          → Screenshot, Retry, ExtentReport listeners
│   │   ├── dataProviders/      → Test data sources
│   │   └── utilitiesTest/      → ExcelReader, ScreenshotUtil, etc.
│   └── resources/config/       → config.properties & environment files
│
├── testData/                   → Excel files for test input
├── testXML/xmlFiles/           → testng.xml (defines test suites)
├── reports/                    → HTML reports output
├── screenshots/                → Captured screenshots on failure
├── logs/                       → Execution logs (organized by module)
├── pom.xml                     → Maven magic happens here
└── README.md                   → You’re reading it!

-----------------------------------------------------------------------------------
## Tech Stack
- Language: Java
- Automation Tool: Selenium WebDriver
- Test Runner: TestNG
- Build Tool: Maven
- Reporting: ExtentReports
- Logging: Log4j2
- Design Pattern: Page Object Model (POM)
- Utilities/Data Handling: Excel (Apache POI), Properties Files
- Version Control: Git (Bitbucket/GitHub)
- CI Integration: Jenkins / GitHub Actions
- Browser Support: Chrome, Firefox, Edge (local & remote)
- Retry & Listeners: Custom TestNG listeners

-----------------------------------------------------------------------------------
 ## XML Configuration
- Page Objects – Java classes modeling UI pages/elements
- Test Cases – Organized logically (login, register, etc.)
- Utilities – Setup, teardown, browser config
- testng.xml – Defines test groups, parallel execution, and suites

-----------------------------------------------------------------------------------
## Naming Conventions:
- Packages/Folders → pageObjects, utilitiesTest (camelCase)
- Classes → LoginPage.java, ConfigReader.java (PascalCase)
- Methods/Vars → getUserEmail(), isUserActive (camelCase)

-----------------------------------------------------------------------------------
 ## Next Steps
- Add GitHub badges (build, test coverage, etc.)
- Include real-world usage examples
- Document environment setup & switching
- Add CI/CD integration guide
- Attach sample ExtentReports and screenshots

-----------------------------------------------------------------------------------
## Maintained By
- Name: Aesha Mangukiya
- Email ID: aeshamangukiya1@email.com
- LinkedIn: www.linkedin.com/in/aesha-mangukiya-715535360
