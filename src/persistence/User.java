package persistence;

import java.util.ArrayList;
import businessLogic.Util;

public class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private ArrayList<LedgerItem> list;
	
	private User(String firstName, String lastName, String userName, String password, ArrayList<LedgerItem> list) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.list = list;
	}
	
	/**
	 * This is method is the static factory method.
	 * @param 	input string received from UI.
	 * @return	the instance of User object.
	 */
	public static User getInstanceOf(String input) {
		try {
//			Tokenizer static method to check if the input is legit.
//			Tokenizer.checkUser(input);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		String firstName = Tokenizer.getFirstName();
//		String lastName = Tokenizer.getLastName();
//		String userName = Tokenizer.getUserName();
//		String password = Util.encrypt(Tokenizer.getPassword());
		ArrayList<LedgerItem> list = new ArrayList<>();
		User obj = new User("", "", "", "", list);
		return obj;
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
