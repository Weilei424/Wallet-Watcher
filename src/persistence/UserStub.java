package persistence;

import java.util.ArrayList;

/**
 * This class should be removed once DB is available
 * @author mason
 *
 */
public class UserStub {
	ArrayList<User> userDB = new ArrayList<>();
	User demoUser = User.getInstanceOf("");
	
	public void init() {
		demoUser.setFirstName("Jeff");
		demoUser.setLastName("Bezos");
		demoUser.setPassword("amazonrulestheworld!");
		demoUser.setUserName("CEOJeff!");
	}
	
	public void addUser() {
		
	}
}
