package com.iamhere.entities;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.joda.time.DateTime;

import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBUserObject;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/***
 * 
 * Plastform entity for User
 * @author jassica
 *
 */
public class UserObject  extends EntityObject {
	// private fields
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private double creditInfo; // the number of likes fro all the posts
	private double activeScore; // the sum of provide posts, request posts and
								// post comments
	private String password;
	private String alias;
	private String role;
	private boolean isEmailAuthorized;

	// db object
	DBUserObject dbUser;
	
	public UserObject(String firstName, String lastName, String email,
			String password) {
		dbUser = new DBUserObject();
		// TODO: Assume caller will give no-null-empty values
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setRole("normal");
	}
	
	public UserObject(DBUserObject dbUser) {
		this.dbUser = dbUser;
		reloadAllFieldInformationFromDbObject();
	}

	public UserObject(String email) {
		this.dbUser = new DBUserObject();
		setEmail(email);
	}

	// Getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		dbUser.setFirstName(firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		dbUser.setLastName(lastName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		dbUser.setEmail(email);
	}

	public double getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(double creditInfo) {
		this.creditInfo = creditInfo;
		dbUser.setCreditInfo(creditInfo);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		dbUser.setPhone(phone);
	}

	public double getActiveScore() {
		return activeScore;
	}

	public void setActiveScore(double activeScore) {
		this.activeScore = activeScore;
		dbUser.setActiveScore(activeScore);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		dbUser.setPassword(password);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
		dbUser.setAlias(alias);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
		dbUser.setRole(role);
	}

	public boolean isEmailAuthorized() {
		return isEmailAuthorized;
	}

	public void setEmailAuthorized(boolean isEmailAuthorized) {
		this.isEmailAuthorized = isEmailAuthorized;
	}
	/**
	 * =============== Validation of Fields
	 */
	/*
	 * Verify the email.
	 * 1. The format of the email should be correct
	 * 2. The email should not be used by any one else
	 */
	@SuppressWarnings("finally")
	public boolean validateEmail() {
		boolean isValid = false;
		try {
			//
			// Create InternetAddress object and validated the supplied
			// address which is this case is an email address.
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			isValid = !isAlreadyExist();
		} catch (AddressException e) {
			// TODO: need change it to log
			System.out
					.println(" Email Address Exception for: "
							+ email);
		} finally {
			return isValid;
		}
	}

	/**
	 * Validates the password and get the strength score of it 
	 * Z = characters, S = numbers, T = special characters
	 * @return
	 */
	public double validatePassword() {
		String regexZ = "\\d*";  
        String regexS = "[a-zA-Z]+";  
        String regexT = "\\W+$";  
        String regexZT = "\\D*";  
        String regexST = "[\\d\\W]*";  
        String regexZS = "\\w*";  
        String regexZST = "[\\w\\W]*";  
  
        if (password.matches(regexZ) || password.matches(regexS) || password.matches(regexT)) {  
        	return 1.0;
        }  
        if (password.matches(regexZT) || password.matches(regexST) || password.matches(regexZS) ) {  
            return 3.0;
        }  
        if (password.matches(regexZST)) {  
            return 5.0;
        }  
        return 0;
	}

	/**
	 * Verify if the current user already exists in the db
	 * @return
	 */
	public boolean isAlreadyExist() {
		return dbUser.isEmailUsed();
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		// Do the object validations
		if ( TextUtil.isNullOrEmpty(getLastName())) {
			dml.addError("The last name cannot be empty!");
		}
		if (TextUtil.isNullOrEmpty(getEmail())) {
			dml.addError("Email cannot be empty!");
		}
		if (TextUtil.isNullOrEmpty(getPassword())) {
			dml.addError("Password cannot be empty!");
		}
		if (TextUtil.isNullOrEmpty(getAlias())) {
			dml.addError("Alias cannot be empty!");
		}
		
		// The default role information is normal user
		if (TextUtil.isNullOrEmpty(getRole())) {
			setRole("normal");
		}
		super.saveHook_Validate(dml);
	}

	@Override
	public DBEntityObject getDbObject() {
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbUser.setId(getId());
		}
//		dbUser.setActiveScore(getActiveScore());
//		dbUser.setAlias(getAlias());
//		dbUser.setCreatedDate(getCreatedDate());
//		dbUser.setCreditInfo(getCreditInfo());
//		dbUser.setLastModifiedDate(getLastModifiedDate());
//		dbUser.setPhone(getPhone());
//		dbUser.setRole(getRole());
		return dbUser;
	}
	
	@Override
	public UserObject load() throws Exception {
		return (UserObject) super.load();
	}
	
	@Override
	public void reloadAllFieldInformationFromDbObject() {
		setActiveScore(dbUser.getActiveScore());
		setAlias(dbUser.getAlias());
		setCreatedDate(new DateTime(dbUser.getCreatedDate()));
		setCreditInfo(dbUser.getCreditInfo());
		setEmail(dbUser.getEmail());
		setFirstName(getFirstName());
		setId(dbUser.getId());
		setLastModifiedDate(new DateTime(dbUser.getLastModifiedDate()));
		setLastName(dbUser.getLastName());
		setPassword(dbUser.getPassword());
		setPhone(dbUser.getPhone());
		setRole(dbUser.getRole());
	}

	
}
