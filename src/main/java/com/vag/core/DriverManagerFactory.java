package com.vag.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DriverManagerFactory {

	static final Map<String, Supplier<WebDriver>> driverMap = new HashMap<>();
	static final Supplier<WebDriver> chromeSupplier = () -> {
		WebDriverManager.chromedriver().setup();
		return new ChromeDriver();
	};
	static final Supplier<WebDriver> firefoxSupplier = () -> {
		WebDriverManager.firefoxdriver().setup();
		return new FirefoxDriver();
	};

	static {
		driverMap.put("chrome", chromeSupplier);
		driverMap.put("firefox", firefoxSupplier);
	}

	static WebDriver getDriver(String browser) {
		return driverMap.get(browser).get();
	}

}
