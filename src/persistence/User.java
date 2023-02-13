package persistence;

import java.util.ArrayList;
import businessLogic.Util;

public class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private ArrayList<LedgerItem> list;
	
	public User() {
		this("", "", "", "");
	}
	
	public User(String firstName, String lastName, String userName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.list = new ArrayList<>();
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = Util.encrypt(password);
	}
	
	public String getPassword() {
		return this.password;
	}
}
