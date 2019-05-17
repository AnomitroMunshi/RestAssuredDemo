package demo1;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import files.*;

public class PostTest2 {
	String placeId="";
	Properties prop=new Properties();
	//Scenario=Add a place and delete it
	
	@BeforeTest
	public void getdata() throws IOException {
		
		
		FileInputStream fis=new FileInputStream("C:\\Users\\Anomitro\\git\\RestAssured\\RestApiTestting\\src\\files\\env.properties");
		prop.load(fis);
		
	}
	
	@Test
	public void addPlace() {
		
		RestAssured.baseURI=prop.getProperty("Host");;
		Response res=given().
		queryParam("key",prop.getProperty("Key")).
		body(payLoad.getPostdata()).
					when().
					post(resources.placePostData()).then().
					assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
					body("status",equalTo("OK"))
					.extract().response();
		
		JsonPath jspath=ResuableMethods.rawToJSON(res);
		
		/*String responseString=res.asString();
		System.out.println(responseString);
		JsonPath jspath=new JsonPath(responseString);*/
		placeId=jspath.get("place_id");
		System.out.println(placeId);
		
	}
	
	@Test(priority=1,enabled=true)
	public void deletePlace() {
		System.out.println(placeId);
		RestAssured.baseURI=prop.getProperty("Host");
		given().log().all().
		queryParam("key",prop.getProperty("Key")).
		body(payLoad.deletedata(placeId)).
		when().
		post("/maps/api/place/delete/json").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
		
	}

}
