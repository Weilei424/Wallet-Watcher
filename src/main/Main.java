package main;

import mainUI.MainUi;
import pages.ExpensePageForm;
import persistence.UserStub;

public class Main {

	/**
	 * This method is used for init.
	 * 
	 */
	public static void main(String[] args) {
		
		MainUi login = new MainUi();
	//	new MainUi();
		
		/*  */
		UserStub userDB = new UserStub();
		userDB.demo();
	}

}
