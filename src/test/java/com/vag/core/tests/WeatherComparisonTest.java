package com.vag.core.tests;

import com.vag.core.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class WeatherComparisonTest extends TestConfig {

	@Test
	public void compareBangaloreWeather() {
		getDriver().get("https://www.ndtv.com/");

		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("h_sub_menu")));
		System.out.println("Loaded Successfully");

		getDriver().findElement(By.id("h_sub_menu")).click();

		getDriver().findElement(By.linkText("WEATHER")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.leaflet-control-zoom")));


	}

}
