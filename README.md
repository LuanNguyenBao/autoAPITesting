# AutoTestReportPortal

## How to run:
#### 1 - Clone the Project
#### 2 - Open CMD in Project folder
#### 3 - Enter the commands:
- **Run API test cases:**
> mvn -Dtest=TestRunnerAPI clean test


## Allure Report:
Allure results will appear in "target/allure-results" folder. To generate html report and automatically open it in a web browser, run the following command:
> allure serve target/allure-results
