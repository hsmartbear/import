package com.qmetry.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.qmetry.qaf.automation.core.MessageTypes;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.util.Validator;
import com.qmetry.qaf.automation.ws.rest.RestTestBase;
import com.qmetry.util.QMetryProperties;

/**
 * This class defines QMetryWSValidator functionality.
 * 
 * @author yogesh.pathrabe
 */
public class QMetryWSValidator extends Validator {

	// Below variables are used repeatedly - Do not modify
	public static final String STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE =
			"Response doesn't have attribute ";
	public static final String STRING_IN_A_LIST = " in a list";
	public static final String STRING_FOR_ATTRIBUTE = " for attribute ";
	public static final String STRING_WITH_VALUE = " with value ";
	public static final String STRING_ATTRIBUTE = "Attribute ";
	public static final String STRING_RESPONSE_IS_HAVING_VALUE =
			"Response is having value ";
	public static final String STRING_RESPONSE_DONT_HAVE_VALUE =
			"Response don't have value ";

	/**
	 * This method will print step log in report.
	 * 
	 * @author yogesh.pathrabe
	 */
	public static void stepLog(String message) {
		Reporter.log(message);
	}

	/**
	 * This method will validate the response should have attribute
	 * {attributeName} and the value.
	 * 
	 * @param attributeName
	 *            as String
	 * @author yogesh.pathrabe
	 */
	@QAFTestStep(description = "response should have attribute {attributeName} and the value")
	public static void responseShouldHaveAttributeAndValue(String attributeName) {
		String actualAttribute = attributeName;
		if (attributeName.contains(".")) {
			String[] actualAttributes = attributeName.split("\\.");
			actualAttribute = actualAttributes[actualAttributes.length - 1];
		}
		try {
			Object actual =
					JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(attributeName));
			Reporter.log("Response is having attribute " + actualAttribute
					+ " and the value is " + actual, MessageTypes.Pass);
		} catch (Exception exception) {
			Reporter.log(STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE + actualAttribute,
					MessageTypes.Fail);
		}
	}

	/**
	 * This method will validate the response should have attributes
	 * and the values.
	 * 
	 * @param parentName
	 *            as String
	 * @param attributeName
	 *            as String
	 * @author yogesh.pathrabe
	 */
	@QAFTestStep(description = "response should have attributes and the values")
	public static void responseShouldHaveAttributesAndValues(String parentName,
			List<String> attributeNames) {
		for (String attribute : attributeNames) {
			responseShouldHaveAttributeAndValue(parentName + "." + attribute);
		}
	}

	/**
	 * This method will validate the response should have attributes
	 * and the values.
	 * 
	 * @param attributeName
	 *            as String
	 * @author bhakti.patil
	 */
	@QAFTestStep(description = "response should have attributes and the values")
	public static void responseShouldHaveAttributesAndValues(
			List<String> attributeNames) {
		for (String attribute : attributeNames) {
			responseShouldHaveAttributeAndValue(attribute);
		}
	}

	/**
	 * This method will validate the response should have value {expectedValue}
	 * for attribute {attributeName} in a list associated with required value
	 * {expectedRequiredValue} and attribute {requiredAttributeName}.
	 * 
	 * @param expectedRequiredValue
	 *            as Object
	 * @param requiredAttributeName
	 *            as String
	 * @param expectedValue
	 *            as Object
	 * @param attributeName
	 *            as String
	 * @author sonal.gawade
	 */
	@QAFTestStep(description = "response should have value {expectedValue} for attribute {attributeName} in a list associated with required value {expectedRequiredValue} and attribute {requiredAttributeName}")
	public static void responseShouldHaveValueInList(Object expectedValue,
			String attributeName, Object expectedRequiredValue,
			String requiredAttributeName) {
		String actualAttribute = requiredAttributeName;
		if (requiredAttributeName.contains(".")) {
			String[] actualAttributes = requiredAttributeName.split("\\.");
			actualAttribute = actualAttributes[actualAttributes.length - 1];
		}
		try {
			ArrayList<Object> actualValues =
					JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(requiredAttributeName));
			int foundCount = 0;
			for (Object value : actualValues) {
				if (expectedRequiredValue.toString().equals(value.toString())) {
					if (actualAttribute.equals(attributeName)) {
						Reporter.log(STRING_RESPONSE_IS_HAVING_VALUE
								+ expectedRequiredValue + STRING_FOR_ATTRIBUTE
								+ actualAttribute + STRING_IN_A_LIST, MessageTypes.Pass);
						return;
					} else {
						String currentAttribute;
						if (requiredAttributeName.split("\\.")[0].contains("*")) {
							currentAttribute = requiredAttributeName.split("\\.")[0]
									.replace("*", String.valueOf(foundCount)) + "."
									+ attributeName;
						} else {
							String[] attributeArray = requiredAttributeName.split("\\.");
							currentAttribute = attributeArray[0] + "."
									+ attributeArray[1].replace("*",
											String.valueOf(foundCount))
									+ "." + attributeName;
						}
						Object currentAttributeValue = JsonPath.read(
								new RestTestBase().getResponse().getMessageBody(),
								QMetryWSSupport.getPath(currentAttribute));
						if (expectedValue.toString()
								.equals(currentAttributeValue.toString())) {
							Reporter.log(STRING_RESPONSE_IS_HAVING_VALUE + expectedValue
									+ STRING_FOR_ATTRIBUTE + attributeName
									+ STRING_IN_A_LIST, MessageTypes.Pass);
							return;
						} else {
							Reporter.log("Response is not having value " + expectedValue
									+ STRING_FOR_ATTRIBUTE + attributeName
									+ STRING_IN_A_LIST, MessageTypes.Fail);
							return;
						}
					}
				}
				foundCount++;
			}
			Reporter.log(
					STRING_RESPONSE_DONT_HAVE_VALUE + expectedRequiredValue
							+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
					MessageTypes.Fail);
		} catch (Exception exception) {
			Reporter.log(STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE + actualAttribute,
					MessageTypes.Fail);
		}
	}

	/**
	 * This method will validate the response should have value {expectedValue}
	 * for attribute {attributeName} in a list.
	 * 
	 * @param expectedValue
	 *            as Object
	 * @param attributeName
	 *            as String
	 * @author sonal.gawade
	 */
	@QAFTestStep(description = "response should have value {expectedValue} for attribute {attributeName} in a list")
	public static void responseShouldHaveValueInList(Object expectedValue,
			String attributeName) {
		String actualAttribute = attributeName;
		if (attributeName.contains(".")) {
			String[] actualAttributes = attributeName.split("\\.");
			actualAttribute = actualAttributes[actualAttributes.length - 1];
		}
		try {
			ArrayList<Object> actualValues =
					JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(attributeName));
			boolean success = false;
			for (Object value : actualValues) {
				if (expectedValue.toString().equals(value.toString())) {
					success = true;
					break;
				}
			}
			if (success) {
				Reporter.log(STRING_RESPONSE_IS_HAVING_VALUE + expectedValue
						+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
						MessageTypes.Pass);
			} else {
				Reporter.log(STRING_RESPONSE_DONT_HAVE_VALUE + expectedValue
						+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
						MessageTypes.Fail);
			}
		} catch (Exception exception) {
			Reporter.log(STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE + actualAttribute,
					MessageTypes.Fail);
		}
	}

	/**
	 * This method will validate the response should have values for attributes
	 * in a list.
	 * 
	 * @param parentName
	 *            as String
	 * @param requiredAttributesAndValues
	 *            as Map<String, Object>
	 * @author yogesh.pathrabe
	 */
	@QAFTestStep(description = "response should have values for attributes in a list")
	public static void responseShouldHaveValuesInList(String parentName,
			Map<String, Object> requiredAttributesAndValues) {
		for (Map.Entry<String, Object> requiredAttributeAndValue : requiredAttributesAndValues
				.entrySet()) {
			responseShouldHaveValueInList(requiredAttributeAndValue.getValue(),
					parentName + "." + requiredAttributeAndValue.getKey());
		}
	}

	/**
	 * This method will validate the response should have values for attributes
	 * in a list associated with required value and attribute.
	 * 
	 * @param expectedRequiredValue
	 *            as Object
	 * @param requiredAttributeName
	 *            as String
	 * @param requiredAttributesAndValues
	 *            as Map<String, Object>
	 * @author sonal.gawade
	 */
	@QAFTestStep(description = "response should have values for attributes in a list associated with required value and attribute")
	public static void responseShouldHaveValuesInList(Object expectedRequiredValue,
			String requiredAttributeName,
			Map<String, Object> requiredAttributesAndValues) {
		for (Map.Entry<String, Object> requiredAttributeAndValue : requiredAttributesAndValues
				.entrySet()) {
			responseShouldHaveValueInList(requiredAttributeAndValue.getValue(),
					requiredAttributeAndValue.getKey(), expectedRequiredValue,
					requiredAttributeName);
		}
	}

	/**
	 * This method will validate the response should not have value
	 * {expectedValue} for attribute {attributeName} in a list.
	 * 
	 * @param expectedValue
	 *            as Object
	 * @param attributeName
	 *            as String
	 * @author sonal.gawade
	 */
	@QAFTestStep(description = "response should not have value {expectedValue} for attribute {attributeName} in a list")
	public static void responseShouldNotHaveValueInList(Object expectedValue,
			String attributeName) {
		String actualAttribute = attributeName;
		if (attributeName.contains(".")) {
			String[] actualAttributes = attributeName.split("\\.");
			actualAttribute = actualAttributes[actualAttributes.length - 1];
		}
		try {
			ArrayList<Object> actualValues =
					JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(attributeName));
			boolean success = true;
			for (Object value : actualValues) {
				if (expectedValue.toString().equals(value.toString())) {
					success = false;
					break;
				}
			}
			if (success) {
				Reporter.log("Response is not having value " + expectedValue
						+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
						MessageTypes.Pass);
			} else {
				Reporter.log(STRING_RESPONSE_IS_HAVING_VALUE + expectedValue
						+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
						MessageTypes.Fail);
			}
		} catch (Exception exception) {
			Reporter.log(STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE + actualAttribute,
					MessageTypes.Fail);
		}
	}

	/**
	 * response should have is archived status as {isArchived} for value
	 * {expectedValue} of attribute {attributeName}
	 * 
	 * @param expectedValue
	 *            as Object
	 * @param attributeName
	 *            as String
	 * @param isArchived
	 *            as boolean
	 * @author sonal.gawade
	 */
	@QAFTestStep(description = "response should have is archived status for value {expectedValue} of attribute {attributeName} as {isArchived}")
	public static void responseShouldHaveIsArchivedStatus(Object expectedValue,
			String attributeName, boolean isArchived) {
		String actualAttribute = attributeName;
		if (attributeName.contains(".")) {
			String[] actualAttributes = attributeName.split("\\.");
			actualAttribute = actualAttributes[actualAttributes.length - 1];
		}
		try {
			ArrayList<Object> actualValues =
					JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(attributeName));
			int foundCount = 0;
			for (Object value : actualValues) {
				if (expectedValue.toString().equals(value.toString())) {
					String archivedAttribute = attributeName.split("\\.")[0].replace("*",
							String.valueOf(foundCount)) + "."
							+ QMetryProperties.ATTRIBUTE_IS_ARCHIVED;
					boolean actualIsArchived = JsonPath.read(
							new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(archivedAttribute));
					if (isArchived) {
						if (actualIsArchived) {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue + " is successfully archived",
									MessageTypes.Pass);
						} else {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue + " is unarchived",
									MessageTypes.Fail);
						}
					} else {
						if (actualIsArchived) {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue + " is archived",
									MessageTypes.Fail);
						} else {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue
											+ " is successfully unarchived",
									MessageTypes.Pass);
						}
					}
					return;
				}
				foundCount++;
			}
			Reporter.log(STRING_RESPONSE_DONT_HAVE_VALUE + expectedValue
					+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
					MessageTypes.Fail);
		} catch (Exception exception) {
			Reporter.log(STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE + actualAttribute,
					MessageTypes.Fail);
		}
	}

	/**
	 * response should have is activated status as {isActive} for value
	 * {expectedValue} of attribute {attributeName}
	 * 
	 * @param expectedValue
	 *            as Object
	 * @param attributeName
	 *            as String
	 * @param isActive
	 *            as boolean
	 * @author sonali.kudmethe
	 */
	@QAFTestStep(description = "response should have is active status for value {expectedValue} of attribute {attributeName} as {isActive}")
	public static void responseShouldHaveIsActiveStatus(Object expectedValue,
			String attributeName, boolean isActive) {
		String actualAttribute = attributeName;
		if (attributeName.contains(".")) {
			String[] actualAttributes = attributeName.split("\\.");
			actualAttribute = actualAttributes[actualAttributes.length - 1];
		}
		try {
			ArrayList<Object> actualValues =
					JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(attributeName));
			int foundCount = 0;
			for (Object value : actualValues) {
				if (expectedValue.toString().equals(value.toString())) {
					String activeAttribute = attributeName.split("\\.")[0].replace("*",
							String.valueOf(foundCount)) + "."
							+ QMetryProperties.ATTRIBUTE_IS_ACTIVE;
					boolean actualIsActive = JsonPath.read(
							new RestTestBase().getResponse().getMessageBody(),
							QMetryWSSupport.getPath(activeAttribute));
					if (isActive) {
						if (actualIsActive) {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue
											+ " is successfully activated",
									MessageTypes.Pass);
						} else {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue + " is deactivated",
									MessageTypes.Fail);
						}
					} else {
						if (actualIsActive) {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue + " is activated",
									MessageTypes.Fail);
						} else {
							Reporter.log(
									STRING_ATTRIBUTE + actualAttribute + STRING_WITH_VALUE
											+ expectedValue
											+ " is successfully deactivated",
									MessageTypes.Pass);
						}
					}
					return;
				}
				foundCount++;
			}
			Reporter.log(STRING_RESPONSE_DONT_HAVE_VALUE + expectedValue
					+ STRING_FOR_ATTRIBUTE + actualAttribute + STRING_IN_A_LIST,
					MessageTypes.Fail);
		} catch (Exception exception) {
			Reporter.log(STRING_RESPONSE_DOESNT_HAVE_ATTRIBUTE + actualAttribute,
					MessageTypes.Fail);
		}
	}
}
