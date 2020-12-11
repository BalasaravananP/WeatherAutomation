package com.vag.core.config;

import com.vag.core.BaseClass;
import com.vag.core.pages.BasePage;
import com.vag.reporter.TestReporter;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig extends BaseClass {
	public static Properties testProperty = new Properties();

	public static String getTestProperty(String key) {
		return testProperty.getProperty(key);
	}

	@BeforeSuite
	public void initialize() {
		setBrowser(Browsers.CHROME);
		setImplicitWaitTimeOut(15);
		setPageWaitTimeOut(20);
		initializeProperty();
	}

	private void initializeProperty() {
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("TestConfig/InputConfig.properties");
			testProperty.load(inputStream);
		} catch (IOException exception) {
			TestReporter.writeFail("Error", "Error in loading test properties. Exception: " + exception.getMessage());
		}
	}

	public boolean compareWeatherValuesInRange(double value1, double value2, int tolerance) {
		double differenceValue = Math.abs(value1 - value2);
		if (differenceValue <= tolerance) {
			return TestReporter.writePass("Pass", "The difference of temp values are within tolerance(" + tolerance + ")- Difference: " + differenceValue);
		} else {
			return TestReporter.writePass("Pass", "The difference of temp values are not within tolerance(" + tolerance + ")- Difference: " + differenceValue);
		}
	}

	public BasePage ndtv() {
		return BasePage.getInstance();
	}
}
