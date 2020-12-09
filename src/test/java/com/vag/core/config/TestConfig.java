package com.vag.core.config;

import com.vag.core.BaseClass;
import org.testng.annotations.BeforeSuite;

public class TestConfig extends BaseClass {

	@BeforeSuite
	public void initialize() {
		setBrowser(Browsers.CHROME);
		setImplicitWaitTimeOut(15);
		setPageWaitTimeOut(20);
	}
}
