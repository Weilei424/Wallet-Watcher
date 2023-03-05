package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import persistence.DBUtil;
import persistence.User;

class UserDBTester {
	User u;
	User utest;
	
	@BeforeEach
	@Test
	void setUp() throws Exception {
		u = User.createUser("Jeff", "Bezos", "ceojeff", "UseAmazon!", "personal");
		utest = User.createUser("test", "changepw", "testpw", "pwpwpw", "personal");
		try {
			DBUtil.createUser(u);
			DBUtil.createUser(utest);
		} catch (IllegalArgumentException e) {
			
		}
	}

	@Order(2)
	@Test
	void testDemoInfo() {
		assertEquals("Jeff", u.getFirstName());
		assertEquals("Bezos", u.getLastName());
		assertEquals("ceojeff", u.getUserName());
		assertTrue("UseAmazon!".equals(u.getPassword()));
	}
	
	@Order(3)
	@Test
	void testDuplicateUser() {
		assertThrows(IllegalArgumentException.class, ()-> DBUtil.createUser(u));	
	}
	
	@Order(4)
	@Test
	void testChangePW() {
		byte[] array = new byte[7]; 
	   	new Random().nextBytes(array);
	    String newPW = new String(array, Charset.forName("UTF-8"));
	    String oldPW = "pwpwpw";
	    assertTrue(DBUtil.changePW("testpw", oldPW, newPW));
	    assertTrue(DBUtil.changePW("testpw", newPW, oldPW));
	}
}
