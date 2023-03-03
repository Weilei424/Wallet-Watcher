package persistence;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import businessLogic.Util;

public final class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String type;
	private String salt;
	private int ref;
	
	private User(String firstName, String lastName, String userName, String password, String type) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.type = type;
		this.salt = generateSalt();
	}
	
	/**
	 * This is a static factory method that generate a new instance of User. 
	 * And insert a new row to the database
	 * All info are required.
	 * @param 	firstName
	 * @param 	lastName
	 * @param 	userName
	 * @param 	password
	 * @param 	type
	 * @return  a new User object.
	 */
	public static User createUser(String firstName, String lastName, String userName, String password, String type) {
		return new User(firstName, lastName, userName, password, type);
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}

	public String getType() {
		return this.type;
	}

	public String getSalt() {
		return this.salt;
	}

	public int getRef() {
		return this.ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	private String generateSalt() {
		byte[] array = new byte[7]; 
	   	new Random().nextBytes(array);
	    	String salt = new String(array, Charset.forName("UTF-8"));

		return salt;
	}
}
