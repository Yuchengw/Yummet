package user.cli;

import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlOperationWrapper;

/**
 * A CLI tool to setup a User
 * 
 * @author yucheng
 * @version 1
 * */
public final class CLIUserSetup {

	/**
	 * Main entry point when calling from command line
	 * @param args
	 * @throws Exception
	 * */
	public static void main(String[] args) throws Exception {
		try {
			// TODO: extract the user fields from command line later
			createCLIUser();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static boolean createCLIUser() {
		UserObject user = new UserObject("George", "admin",
				"test2@hotmail.com", "test1234");
		user.setActiveScore(10000);
		user.setAlias("gadmin");
		user.setCreditInfo(10000);
		user.setRole("admin");
		user.setEmailAuthorized(true);
		DmlOperationWrapper dmlOperationState = user.save();
		return dmlOperationState.isBulkSuccess();
	}

}
