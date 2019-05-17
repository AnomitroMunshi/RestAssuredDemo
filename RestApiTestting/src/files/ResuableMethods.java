package files;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ResuableMethods {
	
	public  static XmlPath rawToXML(Response r) {
		
		String respon=r.asString();
		XmlPath x=new XmlPath(respon);
		return x;
	}
	
public static JsonPath rawToJSON(Response r) {
		
		String respon=r.asString();
		JsonPath jspath=new JsonPath(respon);
		return jspath;
	}


public static String getSessionKey() {
	Properties prop=new Properties();
	RestAssured.baseURI="http://localhost:8040";
	Response res=given().header("Content-Type","application/json").
	body("{ \"username\": \"admin\", \"password\": \"admin\" }").
	when().post("/rest/auth/1/session").then().statusCode(200).
	extract().response();
	
	JsonPath js=rawToJSON(res);
	String sessionID=js.get("session.value");
	System.out.println("sessionID="+sessionID);
	return sessionID;
}

public static String GenerateStringFromResource(String path) throws IOException {
	return new String(Files.readAllBytes(Paths.get(path)));
}


}
