package com.vag.core.tests;

import com.vag.core.APIRequest;
import com.vag.core.config.TestConfig;
import com.vag.reporter.TestReporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class WeatherComparisonTest extends TestConfig {


	@Test(dataProvider = "inputCity")
	public void compareWeather(String city) {
		TestReporter.writeInfo("Info", "Executing test case for city: " + city);
		final boolean openPage = ndtv().openPage();
		if (!openPage) exitTest("Page Not loaded");
		ndtv().homePage().verifyHomePageLoaded();
		ndtv().homePage().navigateToWeather();
		ndtv().homePage().weatherPage().verifyPageLoaded();
		ndtv().homePage().weatherPage().searchCity(city);
		ndtv().homePage().weatherPage().verifyCityDisplayedInPinCity(city);
		ndtv().homePage().weatherPage().selectCityFromPinCity(city);
		final boolean mapDisplayed = ndtv().homePage().weatherPage().checkCityDisplayedInMap(city);
		if (!mapDisplayed)
			exitTest(city + " Selected City is not displayed in Map");

		final Map<String, String> weatherInformationFromMap = ndtv().homePage().weatherPage().getCityWeatherInformationFromMap(city);
		if (weatherInformationFromMap == null || weatherInformationFromMap.isEmpty()) {
			exitTest("Error in collecting weather information");
		}
		double uiTempValue = Double.valueOf(weatherInformationFromMap.get("Temp in Degrees"));
		TestReporter.writeInfo("Weather Information Details", weatherInformationFromMap.toString());


		// Make API request
		String baseUri = testProperty.getProperty("API_BASE_URI");
		String weatherEndPoint = testProperty.getProperty("API_WEATHER_ENDPOINT");
		HashMap<String, String> inputQueryParams = new HashMap<>();
		inputQueryParams.put("q", city);
		inputQueryParams.put("appid", "7fe67bf08c80ded756e598d6f8fedaea");
		inputQueryParams.put("units", "metric");

		final String response = APIRequest.make_get_request(baseUri, weatherEndPoint, inputQueryParams, 200);
		if (response == null || response.isEmpty())
			exitTest("Error from retrieving weather information from API for city: " + city);

		TestReporter.writeInfo("API Response", response);

		final Object tempValue = APIRequest.getValueByPath(response, "main.temp");
		double apiTempValue = Double.valueOf(tempValue.toString());
		TestReporter.writeInfo("API Temp Value for city:" + city, String.valueOf(apiTempValue));

		final boolean valuesInRange = compareWeatherValuesInRange(uiTempValue, apiTempValue, 2);
		if (!valuesInRange)
			TestReporter.writeFail("Error", "The UI and API temp values are Not in given tolerance range for city: " + city);

	}

	@DataProvider
	public Object[][] inputCity() {
		return new Object[][]{{"Ahmedabad"}, {"Bengaluru"}};

	}

}
