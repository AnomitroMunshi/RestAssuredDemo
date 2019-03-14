package demo1;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostTest {
	
	//Scenario=Add a place and delete it
	
	@Test
	public void addPlace() {
		RestAssured.baseURI="http://216.10.245.166";
		given().
		queryParam("key","qaclick123").
		body("{\r\n" + 
				"\r\n" + 
				"    \"location\":{\r\n" + 
				"\r\n" + 
				"        \"lat\" : -38.383494,\r\n" + 
				"\r\n" + 
				"        \"lng\" : 33.427362\r\n" + 
				"\r\n" + 
				"    },\r\n" + 
				"\r\n" + 
				"    \"accuracy\":50,\r\n" + 
				"\r\n" + 
				"    \"name\":\"Frontline house\",\r\n" + 
				"\r\n" + 
				"    \"phone_number\":\"(+91) 983 893 3937\",\r\n" + 
				"\r\n" + 
				"    \"address\" : \"29, side layout, cohen 09\",\r\n" + 
				"\r\n" + 
				"    \"types\": [\"shoe park\",\"shop\"],\r\n" + 
				"\r\n" + 
				"    \"website\" : \"http://google.com\",\r\n" + 
				"\r\n" + 
				"    \"language\" : \"French-IN\"\r\n" + 
				"\r\n" + 
				"}").
					when().
					post("/maps/api/place/add/json").then().
					assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
					body("status",equalTo("OK"));
		
	}
	
	@Test(priority=1)
	public void deletePlace() {
		RestAssured.baseURI="http://216.10.245.166";
		given().
		queryParam("key","qaclick123").
		body("{\r\n" + 
				"		    \"place_id\":\"bb12f77dabc436707504a99b398408e6\" \r\n" + 
				" }").
		when().
		post("/maps/api/place/delete/json").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
		
	}

}
