package com.qmetry.bean;

import com.qmetry.qaf.automation.data.BaseDataBean;
import com.qmetry.qaf.automation.util.Randomizer;

/**
 * This class defines APIDataBean functionality.
 * 
 * @author yogesh.pathrabe
 */
public class APIDataBean extends BaseDataBean {

	@Randomizer(skip = true)
	private String apiKey;
	@Randomizer(skip = true)
	private int statusCode;
	@Randomizer(skip = true)
	private String message;
	@Randomizer(skip = true)
	private String fieldName;
	@Randomizer(skip = true)
	private String errorMessage;
	@Randomizer(skip = true)
	private String errors;
	@Randomizer(skip = true)
	private int recId;

	public String getAPIKey() {
		return apiKey;
	}
	public void setAPIKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public int getRecId() {
		return recId;
	}
	public void setRecId(int recId) {
		this.recId = recId;
	}
}
