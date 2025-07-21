package utilitiesTest;

import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class ApiUtils {

    /**
     * Sends a GET request and returns the response
     */
    public static Response getRequest(String endpoint) {
        return given()
                   .when()
                   .get(endpoint);
    }

    /**
     * Extracts a JSON object (e.g., "data") from response
     */
    public static Map<String, Object> extractJsonObject(Response response, String objectPath) {
        return response.jsonPath().getMap(objectPath);
    }

    /**
     * Prints a section of the JSON response
     */
    public static void printJsonSection(Map<String, Object> section, String label) {
        System.out.println(label + ": " + section);
    }
}
