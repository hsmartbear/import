package com.qmetry.listener;

import com.qmetry.qaf.automation.core.QAFListenerAdapter;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;

/**
 * This class defines WDCommandListener functionality.
 * 
 * @author yogesh.pathrabe
 */
public class WDCommandListener extends QAFListenerAdapter {

	@Override
	public void onInitialize(QAFExtendedWebDriver driver) {
		super.onInitialize(driver);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}
}
