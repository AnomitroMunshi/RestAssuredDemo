package demo1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetTest {

	@Test
	public  void Test() {
		
		RestAssured.baseURI="http://216.10.245.166";
		given().
				param("location","-33.8670522,151.1957362").
				param("radius","1500").
				param("key","qaclick123").
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat()
				.statusCode(200).and().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney")).and().
				body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
				header("server","scaffolding on HTTPServer");
				
		
				
				
	}

}
