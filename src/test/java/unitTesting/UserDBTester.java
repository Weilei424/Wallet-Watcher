package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import businessLogic.DBUtil;
import persistence.User;

class UserDBTester {
	User u;
	
	@BeforeEach
	void setUp() throws Exception {
		u = User.createUser("Jeff", "Bezos", "ceojeff", "UseAmazon!", "personal");
	}

	@Order(1)
	@Test
	void testDemoInfo() {
		assertEquals("Jeff", u.getFirstName());
		assertEquals("Bezos", u.getLastName());
		assertEquals("ceojeff", u.getUserName());
		assertTrue("UseAmazon!".equals(u.getPassword()));
	}
	
	@Test
	void testDuplicateUser() {
		assertThrows(Exception.class, ()-> DBUtil.createUser(u));	
	}
	
}
