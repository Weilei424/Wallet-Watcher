package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import businessLogic.Util;
import persistence.User;
import persistence.UserStub;

@TestInstance(Lifecycle.PER_CLASS)
class UserTester {
	User u;
	
	@BeforeEach
	void setUp() throws Exception {
		UserStub userDB = new UserStub();
		userDB.demo();
		u = userDB.dbUser.get(0);
	}

	@Order(1)
	@Test
	void testDemoInfo() {
		assertEquals("Jeff", u.getFirstName());
		assertEquals("Bezos", u.getLastName());
		assertEquals("ceojeff", u.getUserName());
		assertFalse("WhoDoesntUseAmazon?".equals(u.getPassword()));
		assertTrue(Util.encrypt("WhoDoesntUseAmazon?").equals(u.getPassword()));
	}

	@Order(2)
	@Test
	void testGetterSetter() {
		u.setFirstName("Jon");
		u.setLastName("Snow");
		u.setUserName("bastard");
		u.setPassword("King!Of!The!North");
		assertEquals("Jon", u.getFirstName());
		assertEquals("Snow", u.getLastName());
		assertEquals("bastard", u.getUserName());
		assertEquals(Util.encrypt("King!Of!The!North"), u.getPassword());
	}
}
