package com.vag.core.pages;

import com.vag.reporter.TestReporter;
import org.openqa.selenium.support.PageFactory;

import static com.vag.core.WebDriverRunner.getDriver;
import static com.vag.core.config.TestConfig.getTestProperty;

public class BasePage {
	private static BasePage instance = null;

	private BasePage() {
		PageFactory.initElements(getDriver(), this);
	}

	public static BasePage getInstance() {
		if (instance != null) instance = null;
		return instance = new BasePage();
	}

	public boolean openPage() {
		try {
			String url = getTestProperty("URL");
			getDriver().get(url);
			return TestReporter.writePass("Info", url + "Opened Successfully");
		} catch (Exception exception) {
			return TestReporter.writeFail("Error", "Error in opening URL. Exception: " + exception.getMessage());
		}
	}


	public HomePage homePage() {
		return HomePage.getInstance();
	}
}
