package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import persistence.DBUtil;
import persistence.LedgerItem;
import persistence.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBUtilTester {
	User demo = User.createUser("Jeff", "Bezos", "ceojeff", "UseAmazon!", "personal");
	User pwTest = User.createUser("test", "changepw", "testpw", "pwpwpw", "personal");
	User deleteTest = User.createUser("deee", "DDDD", "testDele", "789uij", "personal");
	String[] tagArr = {"bill", "expense", "earning", "investment", "stock", "misc", "card"};
	
	@Order(0)
	@Test
	void setUp() throws Exception {
		try {
			/* These 2 lines should be commented unless running on a fresh database,
			 * If so, uncomment the first 2 lines and comment out the 3rd line and run.
			 * Then comment out these 2 lines and uncomment the 3rd line, 
			 * the test should run normal. */
//			DBUtil.createUser(demo);
//			DBUtil.createUser(pwTest);
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
		String s1 = "testitem ";
		String s2 = "testnote ";
		Random r = new Random();
		
		try {
			assertTrue(DBUtil.insert("ceojeff", new LedgerItem(LocalDate.now().toString(), 300.00 * r.nextDouble(), s1 + (int) (300.00 * r.nextDouble()),  s2 + (int) (300.00 * r.nextDouble())), tagArr[(int) (Math.random() * 7)]));
			/**
			 * DO NOT UNCOMMENT THESE LINES BELOW!
			 * These lines are just for initializing only!
			 */
			
//			for (int i = 0; i < 20; i++) {
//				assertTrue(DBUtil.insert("ceojeff", new LedgerItem(LocalDate.now().toString(), 300.00 * r.nextDouble(), s1 + (int) (300.00 * r.nextDouble()),  s2 + (int) (300.00 * r.nextDouble())), tagArr[(int) (Math.random() * 6)]));
//			}
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Order(7)
	@Test
	void testDelete() {
		
	}
	
	@Order(8)
	@Test
	void testQuery() {
		
		try {
			JTable table = DBUtil.query(demo.getUserName(), "tag", "misc");
			Object value = table.getValueAt(0, 9);
			assertFalse(value.equals(null));
			String result = DBUtil.query("ceojeff", "ref", "22").getValueAt(0, 4) + "";
			assertEquals(251.98 + "", result);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Order(9)
	@Test
	void testUpdate() throws SQLException {
		
			assertTrue(DBUtil.update("ceojeff", 30, "item", "testupdate"));
			assertEquals("testupdate", (String)DBUtil.query("ceojeff", "ref", "30").getValueAt(0, 1));
			assertTrue(DBUtil.update("ceojeff", 30, "item", "reset for testing"));
		
	}
}
