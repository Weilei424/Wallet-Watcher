package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import persistence.DBUtil;
import persistence.LedgerItem;
import persistence.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDBTester {
	User demo = User.createUser("Jeff", "Bezos", "ceojeff", "UseAmazon!", "personal");
	User pwTest = User.createUser("test", "changepw", "testpw", "pwpwpw", "personal");
	User deleteTest = User.createUser("deee", "DDDD", "testDele", "789uij", "personal");
	
	@Order(0)
	@Test
	void setUp() throws Exception {
		try {
			//DBUtil.createUser(demo);
			//DBUtil.createUser(pwTest);
			DBUtil.createUser(deleteTest);
		} catch (IllegalArgumentException e) {
			
		}
	}

	@Order(1)
	@Test
	void testValidate() {
		try {
			assertTrue(DBUtil.validateUser("ceojeff", "UseAmazon!"));
			assertTrue(DBUtil.validateUser("testpw", "pwpwpw"));
			assertTrue(DBUtil.validateUser("testDele", "789uij"));
			assertFalse(DBUtil.validateUser("no one", "no pw"));
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Order(2)
	@Test
	void testDemoInfo() {
		assertEquals("Jeff", demo.getFirstName());
		assertEquals("Bezos", demo.getLastName());
		assertEquals("ceojeff", demo.getUserName());
		assertTrue("UseAmazon!".equals(demo.getPassword()));
	}
	
	@Order(3)
	@Test
	void testDuplicateUser() {
		assertThrows(IllegalArgumentException.class, ()-> DBUtil.createUser(demo));	
	}
	
	@Order(4)
	@Test
	void testChangePW() {
		try {
			byte[] array = new byte[7]; 
		   	new Random().nextBytes(array);
		    String newPW = new String(array, Charset.forName("UTF-8"));
		    String oldPW = "pwpwpw";
		    assertTrue(DBUtil.changePW("testpw", oldPW, newPW));
		    assertTrue(DBUtil.changePW("testpw", newPW, oldPW));
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Order(5)
	@Test
	void testDeleteUser() {
		try {
			assertTrue(DBUtil.deleteUser("testDele", "789uij"));
			assertFalse(DBUtil.deleteUser("yorku", "lol"));
		} catch (IllegalArgumentException e) {
			
		} 
	}
	
	@Order(6)
	@Test
	void testInsert() {
		byte[] array = new byte[7]; 
	   	new Random().nextBytes(array);
	    String random1 = new String(array, Charset.forName("UTF-8"));
		new Random().nextBytes(array);
		String random2 = new String(array, Charset.forName("UTF-8"));
		Random r = new Random();
		double randomValue = 0.00 + 100.00 * r.nextDouble();
		LedgerItem item = new LedgerItem("2023-03-04", randomValue, random1, random2);
		try {
			assertTrue(DBUtil.insert("ceojeff", item, DBUtil.EXPENSE));
		} catch (IllegalArgumentException e) {
			
		}
	}
}
