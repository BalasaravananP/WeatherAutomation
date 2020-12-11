package com.vag.core;

import com.vag.reporter.TestReporter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class APIRequest {

	public static String make_get_request(String baseUri, String endpointUrl, HashMap<String, String> queryParameters, int statusCode) {
		TestReporter.writeInfo("Info", "Get Request for " + baseUri + endpointUrl);
		final Response response = given().baseUri(baseUri).queryParams(queryParameters).get(endpointUrl).then().extract().response();
		if (response.statusCode() == statusCode) {
			TestReporter.writePass("Pass", String.valueOf(response.statusCode()), String.valueOf(statusCode));
		} else {
			TestReporter.writeFail("Fail", String.valueOf(response.statusCode()), String.valueOf(statusCode));
		}
		return response.asString();

	}

	public static <T> T getValueByPath(String response, String path) {
		JsonPath jsonPath = new JsonPath(response);
		return jsonPath.get(path);
	}
}
