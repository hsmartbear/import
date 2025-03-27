package com.qmetry.common;

import com.qmetry.qaf.automation.core.MessageTypes;
import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.ws.WSTestCase;

/**
 * This class defines QMetryWSTestCase functionality.
 * 
 * @author yogesh.pathrabe
 */
public class QMetryWSTestCase extends WSTestCase {

	/**
	 * This method will print the precondition log.
	 * 
	 * @param status
	 *            as boolean (true for start and false for end)
	 * @author yogesh.pathrabe
	 */
	public void preConditionLog(boolean status) {
		if (status) {
			Reporter.log("Precondition: Start", MessageTypes.Warn);
		} else {
			Reporter.log("Precondition: End", MessageTypes.Warn);
		}
	}

	/**
	 * This method will print the postcondition log.
	 * 
	 * @param status
	 *            as boolean (true for start and false for end)
	 * @author yogesh.pathrabe
	 */
	public void postConditionLog(boolean status) {
		if (status) {
			Reporter.log("Postcondition: Start", MessageTypes.Warn);
		} else {
			Reporter.log("Postcondition: End", MessageTypes.Warn);
		}
	}
}
