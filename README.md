# WeatherAutomation

**Introduction:**
This is Assignment of Weather Comparison of given city captured from NDTV Weather Map Web Ui and against "
api.openweathermap.org" api request. This Test Automation Framework is developed with
Selenium+RestAssured+TestNG+ExtentReport+Maven.

**Prerequisites:**

* java jdk-1.8 or higher
* Apache Maven 3 or higher
* Eclipse / Intellij IDE

**Execution:**

- Clone the repository.
    * git clone https://github.com/BalasaravananP/WeatherAutomation.git
    * will clone the project under currentPath\WeatherAutomation

- Open command prompt and go to WeatherAutomation directory.

- Can run the weather comparison test following ways,
    * mvn test
    * mvn test -Dtest=WeatherComparisonTest or
        * mvn test -Dsurefire.suiteXmlFile=src/test/resources/TestSuites/WeatherComparisonSuite.xml

**Reporting:**
This framework creates extent report and will be placed at ../WeatherAutomation/HtmlReport/Extent.html * Attached sample
test execution report 
