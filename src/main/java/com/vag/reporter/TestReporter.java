package com.vag.reporter;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Reporter;

import static com.vag.core.WebDriverRunner.extentTestThreadLocal;

public class TestReporter {

	public static boolean writeFail(final String description, final String actual, String expected) {
		extentTestThreadLocal.get().log(LogStatus.FAIL, description, "Actual: " + actual + " Expected: " + expected);
		Reporter.getCurrentTestResult().setStatus(2);
		return false;
	}

	public static boolean writeFail(final String description, final String actual) {
		extentTestThreadLocal.get().log(LogStatus.FAIL, description, "Actual: " + actual);
		Reporter.getCurrentTestResult().setStatus(2);
		return false;
	}

	public static boolean writePass(final String description, final String actual, String expected) {
		extentTestThreadLocal.get().log(LogStatus.PASS, description, "Actual: " + actual + " Expected: " + expected);
		return true;
	}

	public static boolean writePass(final String description, final String info) {
		extentTestThreadLocal.get().log(LogStatus.PASS, description, info);
		return true;
	}

	public static boolean writeInfo(final String description, final String info) {
		extentTestThreadLocal.get().log(LogStatus.INFO, description, info);
		return true;
	}

	public static boolean writeWarning(final String description, final String info) {
		extentTestThreadLocal.get().log(LogStatus.WARNING, description, info);
		return true;
	}

	public static boolean writeSkip(final String description, final String info) {
		extentTestThreadLocal.get().log(LogStatus.SKIP, description, info);
		return false;
	}
}
