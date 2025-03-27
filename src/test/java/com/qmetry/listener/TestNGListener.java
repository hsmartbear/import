package com.qmetry.listener;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qmetry.util.QMetryProperties;

/**
 * This class defines TestNGListener functionality.
 * 
 * @author yogesh.pathrabe
 */
public class TestNGListener implements ITestListener {

	@Override
	public void onStart(ITestContext iTestContext) {
		// Implement body if required.
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		// Implement body if required.
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		getBundle().setProperty(QMetryProperties.KEY_EXECUTION_STATUS,
				QMetryProperties.EXECUTION_STATUS_PASSED);
		clearProperties();
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		getBundle().setProperty(QMetryProperties.KEY_EXECUTION_STATUS,
				QMetryProperties.EXECUTION_STATUS_FAILED);
		clearProperties();
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		getBundle().setProperty(QMetryProperties.KEY_EXECUTION_STATUS,
				QMetryProperties.EXECUTION_STATUS_NOT_RUN);
		clearProperties();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		// Implement body if required.
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		// Implement body if required.
	}

	/**
	 * This method will clear stored properties.
	 * 
	 * @author yogesh.pathrabe
	 */
	private void clearProperties() {
		getBundle().clearProperty(QMetryProperties.KEY_CURRENT_WINDOW_HANDLE);
	}
}
