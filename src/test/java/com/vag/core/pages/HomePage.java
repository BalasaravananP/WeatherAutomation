package com.vag.core.pages;

import com.vag.reporter.TestReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.vag.core.WebDriverRunner.getDriver;

public class HomePage {
	private static HomePage instance = null;

	@FindBy(css = "div.newcont div.newcont2")
	WebElement contentSection;

	@FindBy(css = "#header2 div.topnav_cont")
	WebElement headerTopNavigators;

	@FindBy(css = "#header2 div.topnav_extra")
	WebElement headerTopNavigatorsExtra;

	@FindBy(css = "div.noti_wrap")
	WebElement notification;

	@FindBy(css = "div.noti_wrap a.notnow")
	WebElement notificationNoThanks;

	@FindBy(css = "#h_sub_menu")
	WebElement headerSubMenu;

	private HomePage() {
		PageFactory.initElements(getDriver(), this);
	}

	public static HomePage getInstance() {
		if (instance != null) instance = null;
		return instance = new HomePage();
	}

	public void verifyHomePageLoaded() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		wait.until(ExpectedConditions.visibilityOf(notificationNoThanks));
		TestReporter.writeInfo("Info", "Notification Displayed");

		notificationNoThanks.click();
		TestReporter.writeInfo("Info", "Notification - No Thanks clicked");

		if (contentSection.isDisplayed() && headerTopNavigators.isDisplayed() && headerTopNavigatorsExtra.isDisplayed()) {
			TestReporter.writePass("Verification", "Home Page Loaded Successfully");
		} else {
			TestReporter.writeFail("Verification", "Home Page Not Loaded Successfully");
		}
	}

	public void navigateToWeather() {
		headerSubMenu.click();
		TestReporter.writeInfo("CLICK", "Clicked on submenu");
		clickByLinkText("WEATHER");
	}

	public WeatherPage weatherPage() {
		return WeatherPage.getInstance();
	}

	private boolean clickByLinkText(String text) {
		getDriver().findElement(By.linkText(text)).click();
		return TestReporter.writeInfo("CLICK", "Clicked on " + text + " hyperlink");
	}

}
