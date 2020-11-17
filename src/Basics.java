import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import Files.Payload;
import Files.ReUsableAction;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Validate Add PLace API
		
		// given - All input details
		// when - Submit the API - resource and Http method
		// then - validate the response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add Place
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		//.body(Payload.AddPlace("This is My New location"))
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\rajiv.agarwal\\Desktop\\Udemy\\RestAPIAutomation\\RestAutomation\\TestData\\AddPlace.Json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		// Extract Place ID
		 System.out.println(response);
		 String placeId=ReUsableAction.RowToJson(response, "place_id") ;     
		 System.out.println("Place_ID: "+placeId);
		
		// Update Place with New Address
		 String NewAdd = "Kharadi By Pass , Pune , Maharastra";
		 given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		 .body("{\r\n" + 
		 		"\"place_id\":\""+placeId+"\",\r\n" + 
		 		"\"address\":\""+NewAdd+"\",\r\n" + 
		 		"\"key\":\"qaclick123\"\r\n" + 
		 		"}").when().put("maps/api/place/update/json")
		 .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		 
		// Get Place to Validate the new address
		
		String getPlaceResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		// two testing framework which are popular in Java Cucumber JUnit and Testing NUnit
		
		String actualAdress = ReUsableAction.RowToJson(getPlaceResponse, "address") ;
		System.out.println(actualAdress);
		
		Assert.assertEquals(actualAdress, NewAdd);
		
	}

}
