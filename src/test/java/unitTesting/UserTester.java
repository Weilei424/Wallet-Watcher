package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import persistence.User;

@TestInstance(Lifecycle.PER_CLASS)
class UserTester {
	User u;
	
	@BeforeEach
	void setUp() throws Exception {
		u = User.createUser("Jeff", "Bezos", "ceojeff", "WhoDoesntUseAmazon?", "personal");
	}

	@Order(1)
	@Test
	void testDemoInfo() {
		assertEquals("Jeff", u.getFirstName());
		assertEquals("Bezos", u.getLastName());
		assertEquals("ceojeff", u.getUserName());
		assertTrue("WhoDoesntUseAmazon?".equals(u.getPassword()));
	}


}
