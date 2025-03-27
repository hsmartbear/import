package com.qmetry.util;

/**
 * This class defines QMetryProperties functionality.
 * 
 * @author yogesh.pathrabe
 */
public class QMetryProperties {

	/**
	 * This is the protected constructor.
	 * 
	 * @author yogesh.pathrabe
	 */
	protected QMetryProperties() {

	}

	// JSON attribute properties
	public static final String ATTRIBUTE_IS_ARCHIVED = "isArchived";
	public static final String ATTRIBUTE_IS_ACTIVE = "isActive";

	// Execution status properties
	public static final String EXECUTION_STATUS_NOT_RUN = "Not Run";
	public static final String EXECUTION_STATUS_PASSED = "Passed";
	public static final String EXECUTION_STATUS_FAILED = "Failed";
	public static final String EXECUTION_STATUS_BLOCKED = "Blocked";

	// Operational properties
	public static final String FIREFOX = "firefox";
	public static final String VALID = "valid";
	public static final String INVALID = "invalid";
	public static final String ARRAY = "array";
	public static final String ALL = "all";

	// Status code properties
	public static final int STATUS_CODE_200 = 200;
	public static final int STATUS_CODE_201 = 201;
	public static final int STATUS_CODE_204 = 204;
	public static final int STATUS_CODE_400 = 400;
	public static final int STATUS_CODE_403 = 403;
	public static final int STATUS_CODE_404 = 404;

	// Storage properties
	public static final String KEY_CURRENT_WINDOW_HANDLE = "key.current.window.handle";
	public static final String KEY_EXECUTION_STATUS = "key.execution.status";

	// Update results utility properties
	public static final String KEY_UPDATE_RESULTS_PARENT_TEST_SUITE_ID =
			"update.results.parent.test.suite.id";
	public static final String KEY_UPDATE_RESULTS_LATEST_VIEWS_TE_VIEW_ID =
			"update.results.latest.views.test.execution.view.id";
	public static final String KEY_UPDATE_RESULTS_ALL_STATUS_PASSED_ID =
			"update.results.all.status.passed.id";
	public static final String KEY_UPDATE_RESULTS_ALL_STATUS_FAILED_ID =
			"update.results.all.status.failed.id";
	public static final String KEY_UPDATE_RESULTS_ALL_STATUS_NOTRUN_ID =
			"update.results.all.status.notrun.id";

	/**
	 * Below property used in update results utility.
	 * Append value to module name at the end, for example, if module name is
	 * Cycle then it should be CycleAPITestSuite which is the suite name in QTM.
	 */
	public static final String API_TEST_SUITE = "APITestSuite";
}
