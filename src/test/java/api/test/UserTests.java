package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class UserTests {

	Faker faker;
	User userPayload;
	public Logger logger;
	@BeforeClass
	public void setup()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().lastName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*************Creating user************");
		Response response=UserEndpoints.createuser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************Created user************");
	}
	@Test(priority=2)
	public void testGetUserByName()
	{
		Response response=UserEndpoints.readuser(this.userPayload.getUsername());
		response.then().log().all();
		//response.getStatusCode();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************Userinfo reading************");
	}
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		//update data using payload
		userPayload.setFirstName(faker.name().lastName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndpoints.updateuser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after updation
		
		Response responseafterupdate=UserEndpoints.readuser(this.userPayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
		
		logger.info("*************Updating user info************");
	}
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		Response response=UserEndpoints.deleteuser(this.userPayload.getUsername());
		//response.getStatusCode();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************Deleting user************");
	}
}
