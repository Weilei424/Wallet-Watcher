package main.persistence;

import java.util.ArrayList;

/**
 * This class should be removed once DB is ready
 * @author mason
 *
 */
public class UserStub {
	public ArrayList<User> dbUser = new ArrayList<>();
	
	public void demo() {
		User demo = new User();
		
		demo.setFirstName("Jeff");
		demo.setLastName("Bezos");
		demo.setUserName("ceojeff");
		demo.setPassword("WhoDoesntUseAmazon?");
		
		this.dbUser.add(demo);
	}
	
	public void addUser(User user) {
		dbUser.add(user);
	}
}
