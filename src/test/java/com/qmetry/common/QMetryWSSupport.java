package com.qmetry.common;

import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import com.jayway.jsonpath.JsonPath;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ws.WsRequestBean;
import com.qmetry.qaf.automation.ws.rest.RestTestBase;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * This class defines QMetryWSSupport functionality.
 * 
 * @author yogesh.pathrabe
 */
public class QMetryWSSupport {

	/**
	 * This is the protected constructor.
	 * 
	 * @author yogesh.pathrabe
	 */
	protected QMetryWSSupport() {

	}

	/**
	 * This method will return the path by attaching $ sign if missed.
	 * 
	 * @param pathName
	 *            as String
	 * @return String
	 * @author yogesh.pathrabe
	 */
	public static String getPath(String pathName) {
		if (!pathName.startsWith("$")) {
			pathName = "$." + pathName;
		}
		return pathName;
	}

	/**
	 * This method will return the value from response.
	 * 
	 * @param attributeName
	 *            as String
	 * @author yogesh.pathrabe
	 */
	public static Object getValueFromResponse(String attributeName) {
		return JsonPath.read(new RestTestBase().getResponse().getMessageBody(),
				getPath(attributeName));
	}

	/**
	 * This method requests for the given parameters.
	 * 
	 * @param request
	 *            as key or map
	 * @author amit.borania
	 * @return ClientResponse
	 */
	@QAFTestStep(description = "user requests {0}")
	public static ClientResponse userRequests(Object request) {
		WsRequestBean wsRequestBean = new WsRequestBean();
		wsRequestBean.fillData(request);
		wsRequestBean.resolveParameters(null);

		WebResource webResource = new RestTestBase()
				.getWebResource(wsRequestBean.getBaseUrl(), wsRequestBean.getEndPoint());

		// Add headers
		Builder builder = webResource.getRequestBuilder();
		for (Entry<String, Object> header : wsRequestBean.getHeaders().entrySet()) {
			if (header.getKey().equalsIgnoreCase("Accept")) {
				builder.accept((String) header.getValue());
			}
			builder.header(header.getKey(), header.getValue());
		}

		// Add body
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		wsRequestBean.getFormParameters().forEach(
				(key, value) -> formDataMultiPart.field(key, String.valueOf(value)));
		return builder.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class,
				formDataMultiPart);
	}
}
