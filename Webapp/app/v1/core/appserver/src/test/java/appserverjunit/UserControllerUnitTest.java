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
	
	 	@Before
	    public void setUp() {
	 		object = new UserControllerImpl();
	    }

}
