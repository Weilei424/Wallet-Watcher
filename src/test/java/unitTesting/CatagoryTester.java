package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import businessLogic.Category;

class CatagoryTester {

	@Test
	void testDefaultConstructor() {
		Category c = new Category();
		assertTrue(c.getName().equals("default"));
	}
	
	@Test
	void testOverloadedConstructor() {
		String s = "this is a tag";
		Category c = new Category(s);
		assertTrue(c.getName().equals(s));
	}
	
	@Test
	void testSetter() {
		String s = "gg";
		Category c = new Category(s);
		assertTrue(c.getName().equals(s));
		c.setName("good");
		assertFalse(c.getName().equals(s));
		assertTrue(c.getName().equals("good"));
	}

}
