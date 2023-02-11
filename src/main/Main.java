package main;

import mainUI.MainUi;
import persistence.UserStub;

public class Main {

	/**
	 * This method is used for init.
	 * 
	 */
	public static void main(String[] args) {
		new MainUi();
		
		/*  */
		UserStub userDB = new UserStub();
		userDB.demo();
	}

}
