package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//UserEndPoints.java
//Created for perform create,read,update,delete requests user API
public class UserEndpoints {

	public static Response createuser(User payload)
	{
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
			
		.when()
			.post(Routes.post_url);
		
		return response;
		
	}
	
	public static Response readuser(String userName)
	{
		Response response=given()
				.pathParam("username", userName)
			
		.when()
			.get(Routes.get_url);
		
		return response;
		
	}
	
	public static Response updateuser(String userName,User payload)
	{
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
			.pathParam("username", userName)
			
		.when()
			.put(Routes.update_url);
		
		return response;
		
	}
	
	public static Response deleteuser(String userName)
	{
		Response response=given()
				.pathParam("username", userName)
			
		.when()
			.delete(Routes.delete_url);
		
		return response;
		
	}
}
