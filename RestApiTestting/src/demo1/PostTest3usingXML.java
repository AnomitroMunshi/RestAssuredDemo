package demo1;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostTest3usingXML {
	String placeId="";
	//Scenario=Add a place and delete it
	
	@Test
	public void addPlace() {
		String b="{\r\n" + 
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
				"}";
		RestAssured.baseURI="http://216.10.245.166";
		Response res=given().
		queryParam("key","qaclick123").
		body(b).
					when().
					post("/maps/api/place/add/xml").then().
					assertThat().statusCode(200).and().contentType(ContentType.XML).and().
					body("status",equalTo("OK"))
					.extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);
		JsonPath jspath=new JsonPath(responseString);
		placeId=jspath.get("place_id");
		System.out.println(placeId);
		
	}
	
	@Test(priority=1,enabled=true)
	public void deletePlace() {
		System.out.println(placeId);
		String d="{\r\n" + 
				"		    \"place_id\":\""+placeId+"\" \r\n" + 
				" }";
		RestAssured.baseURI="http://216.10.245.166";
		given().
		queryParam("key","qaclick123").
		body(d).
		when().
		post("/maps/api/place/delete/json").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
		
	}

}
