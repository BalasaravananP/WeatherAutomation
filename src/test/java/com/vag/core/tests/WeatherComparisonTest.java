package com.vag.core.tests;

import com.vag.core.config.TestConfig;
import org.testng.annotations.Test;

public class WeatherComparisonTest extends TestConfig {

	@Test
	public void compareBangaloreWeather() {

		final boolean openPage = ndtv().openPage();
		if (!openPage) exitTest("Page Not loaded");
		ndtv().homePage().verifyHomePageLoaded();
		ndtv().homePage().navigateToWeather();
		ndtv().homePage().weatherPage().verifyPageLoaded();
		ndtv().homePage().weatherPage().checkCityDisplayedInMap("Bengaluru");
		ndtv().homePage().weatherPage().searchCity("Bengaluru");
		ndtv().homePage().weatherPage().verifyCityDisplayedInPinCity("Bengaluru");
		ndtv().homePage().weatherPage().selectCityFromPinCity("Bengaluru");

	}

}
