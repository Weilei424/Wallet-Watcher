package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;


import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import persistence.Outputform;
import persistence.User;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Outputformtest {	
	User demo = User.createUser("Jeff", "Bezos", "ceojeff", "UseAmazon!", "personal");
	User pwTest = User.createUser("test", "changepw", "testpw", "pwpwpw", "personal");
	User deleteTest = User.createUser("deee", "DDDD", "testDele", "789uij", "personal");
	String[] tagArr = {"bill", "expense", "earning", "investment", "stock", "misc", "card"};

	@Test
	void Constructortest() {
//		try {
//			Outputform form = new Outputform(demo.getUserName());
//			Object value = form.form.getValueAt(23, 8);
//			assertFalse(value.equals(null));
//		} catch(Exception e) {
//			fail();
//		}
	}

	@Test
	void outputFiletest() {
//		try {
//			Outputform form = new Outputform(demo.getUserName());
//			form.outputFile("fatcatt2.csv");
//			File file = new File("./csvfile/fatcatt2.csv");
//			assertTrue(file.exists());
//		} catch(Exception e) {
//				fail();
//		}
	}
}
	
