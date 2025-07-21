package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import testBase.BaseTest;
import utilitiesTest.ApiUtils;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

public class API_Tests extends BaseTest {

	@Test
	public void testGetUserDetails() {

		// Reusable GET request
		Response response = ApiUtils.getRequest("/api/users/2");

		// Reusable JSON object extraction
		Map<String, Object> data = ApiUtils.extractJsonObject(response, "data");

		// Print the "data" section
		ApiUtils.printJsonSection(data, "Data object");

		// Assertion
        response.then()
                .statusCode(200)
                .body("data.id", equalTo(2));
	}
}
