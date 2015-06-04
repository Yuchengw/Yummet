package appserverjunit;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.yummet.bean.rest.controller.UserControllerImpl;
/**
 * This is strict unit test for @link{UserControllerImpl}, this should be replaced by TestNG
 * @author yucheng
 * @since 1
 * */
public class UserControllerUnitTest {
	    
		UserControllerImpl object;
		String userOneJason = "{\"firstname\":\"yucheng\",\"lastname\":\"wang\",\"email\":\"ycwmike@gmail.com\",\"password\":\"yummet\"}";
	
	 	@Before
	    public void setUp() {
	 		object = new UserControllerImpl();
	    }

	    @Test
	    public void testParseUserRequest() {
	        Assert.assertNull("We should return null if user request is empty", object.parseUserRequest(""));
	        Map<String, String> result = object.parseUserRequest(userOneJason);
	        Assert.assertNotNull("The user request is not null", result);
	        Assert.assertEquals("yucheng", result.get("firstname"));
	        Assert.assertEquals("wang", result.get("lastname"));
	        Assert.assertEquals("ycwmike@gmail.com", result.get("email"));
	        Assert.assertEquals("yummet", result.get("password"));
	    }
}
