package com.qmetry.util;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

/**
 * This class defines FileProperties functionality.
 * 
 * @author yogesh.pathrabe
 */
public class FileProperties {

	/**
	 * This is the protected constructor.
	 * 
	 * @author yogesh.pathrabe
	 */
	protected FileProperties() {

	}

	// Data properties file path
	public static final String FILE_PATH_DATA_PROPERTIES =
			"resources/" + getBundle().getString("environment.name") + "/data.properties";
}
