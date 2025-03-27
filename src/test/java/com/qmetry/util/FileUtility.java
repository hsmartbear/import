package com.qmetry.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class defines FileUtility functionality.
 * 
 * @author yogesh.pathrabe
 */
public class FileUtility {

	/**
	 * This is the protected constructor.
	 * 
	 * @author yogesh.pathrabe
	 */
	protected FileUtility() {

	}

	/**
	 * This method will store the property in data.properties file
	 * under specified environment.
	 * 
	 * @param key
	 *            as String
	 * @param value
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static void storeProperties(String key, Object value) {
		String filePath = FileProperties.FILE_PATH_DATA_PROPERTIES;
		try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
			// Load the file into properties object
			Properties currentProperties = new Properties();
			currentProperties.load(fileInputStream);
			try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
				// Set the properties value
				currentProperties.setProperty(key, String.valueOf(value));
				currentProperties.store(fileOutputStream, null);
			}
		} catch (IOException ioException) {
			ioException.fillInStackTrace();
		}
	}

	/**
	 * This method will store the provided properties in data.properties file
	 * under specified environment.
	 * 
	 * @param properties
	 *            as Map<String, String>
	 * @author yogesh.pathrabe
	 */
	public static void storeProperties(Map<String, Object> properties) {
		String filePath = FileProperties.FILE_PATH_DATA_PROPERTIES;
		try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
			// Load the file into properties object
			Properties currentProperties = new Properties();
			currentProperties.load(fileInputStream);
			try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
				// Set the properties value
				for (Map.Entry<String, Object> property : properties.entrySet()) {
					currentProperties.setProperty(property.getKey(),
							String.valueOf(property.getValue()));
				}
				currentProperties.store(fileOutputStream, null);
			}
		} catch (IOException ioException) {
			ioException.fillInStackTrace();
		}
	}

	/**
	 * This method will update provided text file.
	 * 
	 * @param filePath
	 *            as String
	 * @param text
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static void updateTextFile(String filePath, String text) {
		try (FileWriter fileWriter = new FileWriter(filePath)) {
			fileWriter.write(text);
		} catch (IOException ioException) {
			ioException.fillInStackTrace();
		}
	}

	/**
	 * This method will return the content from text file.
	 * 
	 * @param filePath
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static String getFileContent(String filePath) {
		StringBuilder oldContent = new StringBuilder();
		try (BufferedReader bufferedReader =
				new BufferedReader(new FileReader(filePath))) {
			String currentContent = "";
			currentContent = bufferedReader.readLine();
			while (currentContent != null) {
				oldContent.append(currentContent);
				currentContent = bufferedReader.readLine();
			}
		} catch (IOException ioException) {
			ioException.fillInStackTrace();
		}
		return oldContent.toString();
	}

	/**
	 * This method will replace old character with new character in provided
	 * text file.
	 * 
	 * @param filePath
	 *            as String
	 * @param oldCharacter
	 *            as String
	 * @param newCharacter
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static void replaceTextFile(String filePath, String oldCharacter,
			String newCharacter) {
		String oldContent = getFileContent(filePath);
		String newContent = "";
		if (oldCharacter.equals("\\/") && newCharacter.equals("/")) {
			newContent = oldContent.replace(oldCharacter, newCharacter).replace("//",
					newCharacter);
		} else {
			newContent = oldContent.replace(oldCharacter, newCharacter);
		}
		String https = "https:/";
		String http = "http:/";
		String ldap = "ldaps:/";
		if (newContent.contains(https) || newContent.contains(http)
				|| newContent.contains(ldap)) {
			String splitValue = https;
			String splitValue1 = ldap;
			if (newContent.contains(http)) {
				splitValue = http;
			}
			newContent = newContent.replace(splitValue, splitValue + "/");
			newContent = newContent.replace(splitValue1, splitValue1 + "/");
		}
		updateTextFile(filePath, newContent);
	}

	/**
	 * This method will append start and end character to provided text file.
	 * 
	 * @param filePath
	 *            as String
	 * @param startCharacter
	 *            as String
	 * @param endCharacter
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static void appendTextFile(String filePath, String startCharacter,
			String endCharacter) {
		String oldContent = getFileContent(filePath);
		String newContent = "";
		newContent = startCharacter + oldContent + endCharacter;
		updateTextFile(filePath, newContent);
	}

	/**
	 * This method will update provided JSON file.
	 * 
	 * @param filePath
	 *            as String
	 * @param attribute
	 *            as String
	 * @param value
	 *            as Object
	 * @author yogesh.pathrabe
	 */
	public static void updateJSONFile(String filePath, String attribute, Object value) {
		try (FileReader fileReader = new FileReader(filePath)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
			putJSONObject(jsonObject, attribute, value);
			try (FileWriter fileWriter = new FileWriter(filePath)) {
				fileWriter.write(jsonObject.toJSONString());
			}
		} catch (IOException | ParseException exception) {
			exception.fillInStackTrace();
		}
	}

	/**
	 * This method will update provided JSON file.
	 * 
	 * @param filePath
	 *            as String
	 * @param data
	 *            as Map<String, Object>
	 * @author yogesh.pathrabe
	 */
	public static void updateJSONFile(String filePath, Map<String, Object> data) {
		try (FileReader fileReader = new FileReader(filePath)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
			for (Map.Entry<String, Object> currentData : data.entrySet()) {
				putJSONObject(jsonObject, currentData.getKey(), currentData.getValue());
			}
			try (FileWriter fileWriter = new FileWriter(filePath)) {
				fileWriter.write(jsonObject.toJSONString());
			}
		} catch (IOException | ParseException exception) {
			exception.fillInStackTrace();
		}
	}

	/**
	 * This method will update provided JSON file.
	 * 
	 * @param readerFilePath
	 *            as String
	 * @param writerFilePath
	 *            as String
	 * @param data
	 *            as Map<String, Object>
	 * @author yogesh.pathrabe
	 */
	public static void updateJSONFile(String readerFilePath, String writerFilePath,
			Map<String, Object> data) {
		try (FileReader fileReader = new FileReader(readerFilePath)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
			for (Map.Entry<String, Object> currentData : data.entrySet()) {
				putJSONObject(jsonObject, currentData.getKey(), currentData.getValue());
			}
			try (FileWriter fileWriter = new FileWriter(writerFilePath)) {
				fileWriter.write(jsonObject.toJSONString());
			}
		} catch (IOException | ParseException exception) {
			exception.fillInStackTrace();
		}
	}

	/**
	 * This method will return the value of attribute from provided JSON file.
	 * 
	 * @param filePath
	 *            as String
	 * @param attributeName
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static String getValueFromJSONFile(String filePath, String attributeName) {
		Object attributeValue = null;
		try (FileReader fileReader = new FileReader(filePath)) {
			JSONParser jsonParser = new JSONParser();
			attributeValue =
					((JSONObject) jsonParser.parse(fileReader)).get(attributeName);
		} catch (IOException | ParseException exception) {
			exception.fillInStackTrace();
		}
		return String.valueOf(attributeValue);
	}

	/**
	 * This method will put the data in JSON object.
	 * 
	 * @param jsonObject
	 *            as JSONObject
	 * @param attribute
	 *            as String
	 * @param value
	 *            as Object
	 * @author yogesh.pathrabe
	 */
	@SuppressWarnings("unchecked")
	private static void putJSONObject(JSONObject jsonObject, String attribute,
			Object value) {
		boolean isTypeArray = false;
		boolean isMiddleTypeArray = false;
		boolean isValueTypeArray = false;
		if (attribute.startsWith(QMetryProperties.ARRAY)) {
			isTypeArray = true;
		}
		if (attribute.endsWith(QMetryProperties.ARRAY)) {
			isValueTypeArray = true;
		}
		if (attribute.contains(".")) {
			String[] currentAttributes = attribute.split("\\.");
			if (currentAttributes.length == 3
					&& currentAttributes[1].startsWith(QMetryProperties.ARRAY)) {
				isMiddleTypeArray = true;
			}
		}
		if (isTypeArray || isMiddleTypeArray || isValueTypeArray) {
			attribute = attribute.replace(QMetryProperties.ARRAY, "");
		}
		if (attribute.contains(".")) {
			String[] currentAttributes = attribute.split("\\.");
			if (currentAttributes.length == 2) {
				if (isTypeArray) {
					JSONArray currentJSONArray =
							((JSONArray) jsonObject.get(currentAttributes[0]));
					JSONObject requiredJSONObject =
							((JSONObject) currentJSONArray.get(0));
					if (isValueTypeArray) {
						requiredJSONObject.put(currentAttributes[1], getJSONArray(value));
					} else {
						requiredJSONObject.put(currentAttributes[1], value);
					}
				} else if (isValueTypeArray) {
					JSONObject currentJSONObject =
							((JSONObject) jsonObject.get(currentAttributes[0]));
					currentJSONObject.put(currentAttributes[1], getJSONArray(value));
				} else {
					JSONObject currentJSONObject =
							((JSONObject) jsonObject.get(currentAttributes[0]));
					currentJSONObject.put(currentAttributes[1], value);
				}
			} else if (currentAttributes.length == 3) {
				if (isTypeArray) {
					JSONObject requiredJSONObject = null;
					if (isMiddleTypeArray) {
						JSONArray firstJSONArray =
								((JSONArray) jsonObject.get(currentAttributes[0]));
						JSONObject firstJSONObject = ((JSONObject) firstJSONArray.get(0));
						JSONArray secondJSONArray =
								((JSONArray) firstJSONObject.get(currentAttributes[1]));
						requiredJSONObject = ((JSONObject) secondJSONArray.get(0));
					} else {
						JSONObject currentJSONObject =
								((JSONObject) jsonObject.get(currentAttributes[0]));
						JSONArray currentJSONArray =
								((JSONArray) currentJSONObject.get(currentAttributes[1]));
						requiredJSONObject = ((JSONObject) currentJSONArray.get(0));
					}
					if (isValueTypeArray) {
						requiredJSONObject.put(currentAttributes[2], getJSONArray(value));
					} else {
						requiredJSONObject.put(currentAttributes[2], value);
					}
				} else if (isValueTypeArray) {
					JSONObject currentJSONObject =
							((JSONObject) jsonObject.get(currentAttributes[0]));
					JSONObject requiredJSONObject =
							((JSONObject) currentJSONObject.get(currentAttributes[1]));
					requiredJSONObject.put(currentAttributes[2], getJSONArray(value));
				} else {
					JSONObject requiredJSONObject = null;
					if (currentAttributes[0].endsWith(QMetryProperties.ARRAY)) {
						String firstAttribute =
								currentAttributes[0].replace(QMetryProperties.ARRAY, "");
						JSONArray firstJSONArray =
								((JSONArray) jsonObject.get(firstAttribute));
						JSONObject firstJSONObject = ((JSONObject) firstJSONArray.get(0));
						requiredJSONObject =
								((JSONObject) firstJSONObject.get(currentAttributes[1]));
					} else {
						JSONObject currentJSONObject =
								((JSONObject) jsonObject.get(currentAttributes[0]));
						requiredJSONObject = ((JSONObject) currentJSONObject
								.get(currentAttributes[1]));
					}
					requiredJSONObject.put(currentAttributes[2], value);
				}
			}
		} else {
			if (isValueTypeArray) {
				jsonObject.put(attribute, getJSONArray(value));
			} else {
				jsonObject.put(attribute, value);
			}
		}
	}

	/**
	 * This method will return the JSON array.
	 * 
	 * @param value
	 *            as Object
	 * @author yogesh.pathrabe
	 */
	@SuppressWarnings("unchecked")
	private static JSONArray getJSONArray(Object value) {
		JSONArray arrayValue = new JSONArray();
		if (value instanceof ArrayList) {
			List<Object> actualValues = (ArrayList<Object>) value;
			for (Object actualValue : actualValues) {
				arrayValue.add(actualValue);
			}
		} else {
			arrayValue.add(value);
		}
		return arrayValue;
	}
}
