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
	
	@Order(2)
	@Test
	void testDuplicateUser() {
		assertThrows(IllegalArgumentException.class, ()-> DBUtil.createUser(u));	
	}
	
	@Order(3)
	@Test
	void testChangePW() {
		byte[] array = new byte[7]; 
	   	new Random().nextBytes(array);
	    String newPW = new String(array, Charset.forName("UTF-8"));
	    String oldPW = "pwpwpw";
	    assertTrue(DBUtil.changePW("changepw", oldPW, newPW));
	    assertTrue(DBUtil.changePW("changepw", newPW, oldPW));
	}
}
