package com.vag.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.vag.reporter.TestReporter;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.internal.IResultListener;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Listeners(WebDriverRunner.class)
public class WebDriverRunner implements IResultListener {
	public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
	public static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
	static int implicitWaitTimeOut = 10;
	static int pageWaitTimeOut = 10;
	static String BType = "CHROME";
	ExtentReports extentReports;
	ExtentTest extentTest;
	WebDriver webDriver;

	public static WebDriver getDriver() {
		return driverThreadLocal.get();
	}

	public static void setImplicitWaitTimeOut(int timeOut) {
		implicitWaitTimeOut = timeOut;
	}

	public static void setPageWaitTimeOut(int timeOut) {
		pageWaitTimeOut = timeOut;
	}

	public static void setBrowser(Browsers browser) {
		BType = browser.toString();
	}

	public synchronized static void exitTest(String reason) {
		TestReporter.writeFail("Exit", reason);
		Assert.assertTrue(false, reason);
	}

	private void openDriver() {
		switch (BType.toUpperCase()) {
			case "CHROME":
			default:
				webDriver = DriverManagerFactory.getDriver("chrome");
				driverThreadLocal.set(webDriver);
				break;

			case "FIREFOX":
				webDriver = DriverManagerFactory.getDriver("firefox");
				driverThreadLocal.set(webDriver);
				break;

		}
		getDriver().manage().timeouts().implicitlyWait(implicitWaitTimeOut, TimeUnit.SECONDS);
		getDriver().manage().timeouts().pageLoadTimeout(pageWaitTimeOut, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
	}

	@BeforeSuite(alwaysRun = true)
	public synchronized void initializeBeforeSuite() {
		extentReports = new ExtentReports(System.getProperty("user.dir") + "/HtmlReport/Extent.html");
	}

	@BeforeMethod(alwaysRun = true)
	public synchronized void initializeBeforeMethod(Method method, Object[] testData, ITestContext ctx) {
		String testCaseName = method.getName();

		if (testData.length > 0) {
			extentTest = extentReports.startTest(method.getName() + "-" + testData[0].toString());
		} else {
			extentTest = extentReports.startTest(testCaseName);
		}
		extentTestThreadLocal.set(extentTest);
		openDriver();

	}

	@AfterMethod(alwaysRun = true)
	public synchronized void initializeAfterMethod() {
		extentReports.endTest(extentTestThreadLocal.get());
		extentReports.flush();
		getDriver().close();
		getDriver().quit();
	}

	@AfterSuite(alwaysRun = true)
	public synchronized void initializeAfterSuite() {
		extentReports.close();

	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public void onTestFailure(ITestResult result) {

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTestThreadLocal.get().log(LogStatus.SKIP, result.getName() + " got skipped");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {

	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {

	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {

	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {

	}

	public enum Browsers {CHROME, FIREFOX}
}
