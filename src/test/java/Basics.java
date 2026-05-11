import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import files.Payload;
import files.ReusableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// validate if Add Place API is working as expected
		// given all input details
		// when - Submit the API - resource,http methods
		// Then - validate the response

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		// 200 for pass or OK , try 209
		System.out.println("----------------------------");
		System.out.println(response);

		// Add place-> Update Place with New Address-> Get place to validate if NEW
		// address is present in response

		JsonPath js=ReusableMethods.rawToJson(response);// for parsing Json ,from string to json
		String placeID = js.getString("place_id");
		System.out.println("----------------------------");
		System.out.println(placeID);

		String newAddress = "Summer Walk, Africa";

		// Update Place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeID + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get PlaceId
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();

		JsonPath js1=ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		Assert.assertEquals(actualAddress, newAddress);

	}

}
