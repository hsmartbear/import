package com.qmetry.listener;

import java.util.Arrays;

import org.openqa.selenium.remote.DriverCommand;

import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElementCommandAdapter;
import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.util.StringUtil;

/**
 * This class defines WECommandListener functionality.
 * 
 * @author yogesh.pathrabe
 */
public class WECommandListener extends QAFWebElementCommandAdapter {

	@Override
	public void beforeCommand(QAFExtendedWebElement element,
			CommandTracker commandTracker) {
		if (commandTracker.getCommand().equalsIgnoreCase(DriverCommand.CLICK_ELEMENT)) {
			Reporter.log("Click on " + element.getDescription());
		}
		if (commandTracker.getCommand().equalsIgnoreCase(DriverCommand.CLEAR_ELEMENT)) {
			Reporter.log("Clear " + element.getDescription());
		}
		if (commandTracker.getCommand()
				.equalsIgnoreCase(DriverCommand.SEND_KEYS_TO_ELEMENT)) {
			String value = Arrays.toString(
					(CharSequence[]) commandTracker.getParameters().get("value"));
			if (!StringUtil.isBlank(value)) {
				Reporter.log("Type/Set " + value.substring(1, (value.length() - 1))
						+ " into " + element.getDescription());
			}
		}
	}
}
