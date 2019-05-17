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

public class AddBookandDeleteBookUsingDynamicJSON {
	
	String id="";
	
	@Test(dataProvider="Booksdata")
	public void AddBook(String isbn,String aisle) {
		
		
		RestAssured.baseURI="http://216.10.245.166";
		Response res=given().
				header("Content-Type","application/json").
				body(payLoad.Addbook(isbn,aisle)).
				when().
				post("/Library/Addbook.php").
				then().assertThat().statusCode(200).
				extract().response();
		
		JsonPath jspath=ResuableMethods.rawToJSON(res);
		id=jspath.get("ID");
		System.out.println("ID->"+id);
	
		
	}
	
	@Test(dataProvider="Booksdata",priority=1)
	public void DeleteBook(String isbn,String aisle) {
		id=isbn+aisle;
		System.out.println("ID to delete->"+id);
		RestAssured.baseURI="http://216.10.245.166";
		Response res1=given().
		header("Content-Type","application/json").
		body(payLoad.Deletebook(id)).
		when().
		post("/Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response();
		
		JsonPath jspath=ResuableMethods.rawToJSON(res1);
		String msg=jspath.get("msg");
		System.out.println(msg+" with ID->"+id);
		
	}
	
	
	@DataProvider(name="Booksdata")
	public Object[][] getData() {
		
	  return new Object[][] {{"test11","1011"},{"test12","2011"},{"test13","3011"}};
	}
	
	
	
	

}
