package LibraryAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ResuableMethods;
import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AddBookandDeleteBookUsingStaticJSON {
	
	String id="";
	
	@Test
	public void AddBook() throws IOException {
		
		
		RestAssured.baseURI="http://216.10.245.166";
		Response res=given().
				header("Content-Type","application/json").
				body(GenerateStringFromResource("C:\\Users\\Anomitro\\git\\RestAssured\\RestApiTestting\\AddBook.json")).
				when().
				post("/Library/Addbook.php").
				then().assertThat().statusCode(200).
				extract().response();
		
		JsonPath jspath=ResuableMethods.rawToJSON(res);
		id=jspath.get("ID");
		System.out.println("ID->"+id);
	
		
	}
	
	@Test
	public void DeleteBook() throws IOException {
		
		System.out.println("ID to delete->"+id);
		RestAssured.baseURI="http://216.10.245.166";
		Response res1=given().
		header("Content-Type","application/json").
		body(GenerateStringFromResource("C:\\Users\\Anomitro\\git\\RestAssured\\RestApiTestting\\DeleteBook.json")).
		when().
		post("/Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response();
		
		JsonPath jspath=ResuableMethods.rawToJSON(res1);
		String msg=jspath.get("msg");
		System.out.println(msg+" with ID->"+id);
		
	}
	
	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));



	}
	
	
	
	

}
