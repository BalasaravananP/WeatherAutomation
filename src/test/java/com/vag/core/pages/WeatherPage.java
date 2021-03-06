package com.vag.core.pages;

import com.vag.reporter.TestReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vag.core.WebDriverRunner.getDriver;

public class WeatherPage {
	private static WeatherPage instance = null;

	@FindBy(css = "#searchBox")
	WebElement searchBox;

	@FindBy(css = "#messages div:not([style*='none']) label")
	List<WebElement> cityDropDownItems;

	@FindBy(css = "div.leaflet-pane.leaflet-marker-pane div.my-div-icon div.cityText")
	List<WebElement> mapCityTexts;

	@FindBy(css = "div.leaflet-popup-content-wrapper span.heading")
	List<WebElement> weatherDetails;

	private WeatherPage() {
		PageFactory.initElements(getDriver(), this);
	}

	public static WeatherPage getInstance() {
		if (instance != null) instance = null;
		return instance = new WeatherPage();
	}

	public void verifyPageLoaded() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.leaflet-control-zoom")));
		TestReporter.writePass("Verification", "Weather Page Loaded Successfully");

	}


	public boolean searchCity(String city) {
		searchBox.sendKeys(city);
		return TestReporter.writeInfo("Info", "Entered city: " + city + " in search box");
	}

	public boolean verifyCityDisplayedInPinCity(String city) {
		cityDropDownItems.stream().map(webElement -> webElement.getText()).forEach(System.out::println);
		final Optional<WebElement> searchListElement = cityDropDownItems.stream().filter(webElement -> city.equalsIgnoreCase(webElement.getText().trim())).findAny();
		if (searchListElement.isPresent()) {
			return TestReporter.writePass("Verification", city + " displayed in drop down list");

		} else {
			return TestReporter.writeFail("Verification", city + " Not displayed in drop down list");
		}
	}

	public boolean selectCityFromPinCity(String city) {
		boolean selectedCity = false;
		if (verifyCityDisplayedInPinCity(city)) {
			if (!checkCityDisplayedInMap(city)) {
				List<WebElement> inputElements = getDriver().findElements(By.xpath("//div[@id='messages']//input[@id='" + city + "']"));
				if (inputElements.size() == 1) {
					inputElements.get(0).click();
					TestReporter.writeInfo("Info", city + " selected from drop down list");
					selectedCity = true;
				} else {
					TestReporter.writeFail("Error", city + " Not displayed in drop down list");
					selectedCity = false;
				}
			}
		} else {
			TestReporter.writeFail("Verification", city + " NOT displayed in search result");
			selectedCity = false;
		}
		return selectedCity;
	}

	public boolean checkCityDisplayedInMap(String city) {
		final Optional<WebElement> mapCityText = mapCityTexts.stream().filter(webElement -> city.equalsIgnoreCase(webElement.getText().trim())).findAny();
		return mapCityText.isPresent();
	}

	public Map<String, String> getCityWeatherInformationFromMap(String city) {
		Map<String, String> weatherInfo = new HashMap<>();
		if (checkCityDisplayedInMap(city)) {
			List<WebElement> cityContainer = getDriver().findElements(By.xpath("//div[@class='leaflet-pane leaflet-marker-pane']//div[contains(@class,'my-div-icon')]//div[@title='" + city + "']"));
			if (cityContainer.size() > 0) {
				cityContainer.get(0).click();
				TestReporter.writePass("Info", "Clicked on City in Map");

				weatherInfo = weatherDetails.stream().map(WebElement::getText).collect(Collectors.toMap(text -> text.split(":")[0].trim(), text -> text.split(":")[1].trim()));
			} else {
				TestReporter.writeFail("Error", city + " is not displayed in Map");
			}

		} else {
			TestReporter.writeFail("Error", city + " is not displayed in Map");
		}
		return weatherInfo;
	}
}
