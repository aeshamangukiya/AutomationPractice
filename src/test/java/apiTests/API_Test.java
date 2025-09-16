package apiTests;

import static org.hamcrest.Matchers.equalTo;  

import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import test.base.BaseTest;
import test.utilities.ApiUtils;

public class API_Test  {

	@BeforeClass
	public void setupAPIURL() {
		
		BaseTest basetest = new BaseTest();
		basetest.setupAPI();
	}
	
    @Test
    public void testGetUserDetails() {

        // Reusable GET request
        Response response = ApiUtils.getRequest("/api/users/2");

        // Reusable JSON object extraction
        Map<String, Object> data = ApiUtils.extractJsonObject(response, "data");

        
        // Print the "data" section
        ApiUtils.printJsonSection(data, "Data object");

        // Assertion using Rest Assured
        response.then()
                .statusCode(200)
                .body("data.id", equalTo(2));
    }
}
