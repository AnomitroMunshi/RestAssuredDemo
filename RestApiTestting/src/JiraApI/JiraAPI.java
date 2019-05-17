package JiraApI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.ResuableMethods;


public class JiraAPI {
	
	Properties prop=new Properties();
	String id;
	String commentID;
		
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis=new FileInputStream("C:\\Users\\Anomitro\\git\\RestAssured\\RestApiTestting\\src\\JiraApI\\data.properties");
		prop.load(fis);
	}
	
	
	
	@Test(priority=1)
	public void createIssue() throws IOException {
		
		//creating Issue
		RestAssured.baseURI=prop.getProperty("JIRAHOST");
		Response res=given().header("Content-Type","application/json").
		header("Cookie","JSESSIONID="+ResuableMethods.getSessionKey()+"").
		body(ResuableMethods.GenerateStringFromResource(prop.getProperty("createJSONPath"))).
		when().post("/rest/api/2/issue").then().statusCode(201).
		extract().response();
		JsonPath js=ResuableMethods.rawToJSON(res);
		id=js.get("id");
		System.out.println("IssueId="+id);
				
		
	}
	
	@Test(priority=2)
	public void addComment() throws IOException{
		RestAssured.baseURI=prop.getProperty("JIRAHOST");
		Response res=given().header("Content-Type","application/json").
		header("Cookie","JSESSIONID="+ResuableMethods.getSessionKey()+"").
		body(ResuableMethods.GenerateStringFromResource(prop.getProperty("commentJSONPath"))).
		when().post("/rest/api/2/issue/"+id+"/comment").then().statusCode(201).
		extract().response();
		JsonPath js=ResuableMethods.rawToJSON(res);
		commentID=js.get("id");
		System.out.println("CommentId="+commentID);
		
	}
	
	@Test(priority=3)
	public void updateComment()throws IOException{
		RestAssured.baseURI=prop.getProperty("JIRAHOST");
		Response res=given().header("Content-Type","application/json").
		header("Cookie","JSESSIONID="+ResuableMethods.getSessionKey()+"").
		body(ResuableMethods.GenerateStringFromResource(prop.getProperty("UpdatecommentJSONPath"))).
		when().put("/rest/api/2/issue/"+id+"/comment/"+commentID).then().statusCode(200).
		extract().response();
		JsonPath js=ResuableMethods.rawToJSON(res);
		commentID=js.get("id");
		System.out.println("UpdatedcommentID="+commentID);
		
	}
	
	

}
