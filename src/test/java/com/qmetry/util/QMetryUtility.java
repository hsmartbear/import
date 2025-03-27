package com.qmetry.util;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;

/**
 * This class defines QMetryUtility functionality.
 * 
 * @author yogesh.pathrabe
 */
public class QMetryUtility {

	/**
	 * This is the protected constructor.
	 * 
	 * @author yogesh.pathrabe
	 */
	protected QMetryUtility() {

	}

	// Method to move mouse on element
	public static void moveMouseOnElement(QAFWebElement element) {
		try {
			Actions actions = new Actions(new WebDriverTestBase().getDriver());
			actions.moveToElement(element).perform();
			String mouseOverScript =
					"if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) new WebDriverTestBase().getDriver())
					.executeScript(mouseOverScript, element);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	// Method to to scroll element into center
	public static void scrollToCenter(QAFWebElement element) {
		QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
		try {
			Point point = element.getLocation();
			long height = (long) ((JavascriptExecutor) driver)
					.executeScript("return document.documentElement.clientHeight");
			((JavascriptExecutor) driver).executeScript("window.scroll(" + point.getX()
					+ "," + (point.getY() - (height / 2)) + ");");
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	// Method to to scroll element into view
	public static void scrollToView(Object data) {
		try {
			QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			if (data instanceof String) {
				WebElement element = driver.findElement(data.toString());
				javascriptExecutor.executeScript("arguments[0].scrollIntoView();",
						element);
			} else if (data instanceof WebElement) {
				javascriptExecutor.executeScript("arguments[0].scrollIntoView();",
						(WebElement) data);
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	// Method to to scroll element into view
	public static void scrollToBottom() {
		try {
			QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			javascriptExecutor
					.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	// Method to switch to new window
	public static void switchToNewWindow() {
		QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
		ConfigurationManager.getBundle().setProperty(
				QMetryProperties.KEY_CURRENT_WINDOW_HANDLE, driver.getWindowHandle());
		driver.waitForNoOfWindows(2);
		if (driver.getWindowHandles().size() > 1) {
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
			}
		}
	}

	// Method to switch back to old window
	public static void switchToOldWindow() {
		QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
		String oldWindow = ConfigurationManager.getBundle()
				.getString(QMetryProperties.KEY_CURRENT_WINDOW_HANDLE);
		if (driver.getWindowHandles().size() > 1) {
			for (String window : driver.getWindowHandles()) {
				if (!window.equals(oldWindow)) {
					driver.switchTo().window(window);
					driver.close();
				}
			}
			driver.switchTo().window(oldWindow);
		}
	}

	// Method to switch on frame
	public static void switchOnFrame(String frameName) {
		QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
		driver.switchTo().frame(frameName);
	}

	// Method to switch back from frame
	public static void switchBackFromFrame() {
		QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
		driver.switchTo().defaultContent();
	}

	// Method to navigate back
	public static void navigateBack() {
		new WebDriverTestBase().getDriver().navigate().back();
	}

	/**
	 * @param element
	 *            as QAFWebElement (Element to enter value)
	 * @param value
	 *            as String (Value to enter)
	 */
	public static void enterValueUsingActions(QAFWebElement element, String value) {
		QMetryUtility.scrollToView(element);
		element.executeScript("focus();");
		Actions actions = new Actions(new WebDriverTestBase().getDriver());
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(value);
		actions.build().perform();
	}

	/**
	 * @param element
	 *            as QAFWebElement (Element to click)
	 */
	public static void clickUsingJavaScript(QAFWebElement element) {
		QMetryUtility.scrollToCenter(element);
		element.executeScript("focus();");
		if (new WebDriverTestBase().getBrowser().contains(QMetryProperties.FIREFOX)) {
			element.click();
		} else {
			JavascriptExecutor javascriptExecutor =
					(JavascriptExecutor) new WebDriverTestBase().getDriver();
			javascriptExecutor.executeScript("arguments[0].click()", element);
		}
	}

	/**
	 * @param element
	 *            as QAFWebElement (Element to click)
	 */
	public static void clickUsingActions(QAFWebElement element) {
		if (new WebDriverTestBase().getBrowser().contains(QMetryProperties.FIREFOX)) {
			element.click();
		} else {
			Actions actions = new Actions(new WebDriverTestBase().getDriver());
			actions.click(element).perform();
		}
	}

	/**
	 * @param element
	 *            as QAFWebElement (Element to click)
	 */
	public static void click(QAFWebElement element) {

		FluentWait<QAFExtendedWebDriver> wait =
				new FluentWait<>(new WebDriverTestBase().getDriver())
						.withTimeout(30, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);

		WebElement _element = (WebElement) element;

		wait.until(ExpectedConditions.elementToBeClickable(_element));
		_element.click();

	}
}
