package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.LedgerItem;

class LedgerItemTester {
	LedgerItem obj;
	String input = "xxxxxx";
	
	@BeforeEach
	void init() {
		obj = LedgerItem.getInstanceOf(input);
	}
	
	@Test
	void testStaticFactory() {
		
		assertTrue(LedgerItem.getInstanceOf(input) instanceof LedgerItem, "static factory method failed");
	}
	
	@Test 
	void testSetterGetter() {
		assertEquals("", obj.getNote());
		assertTrue(Math.abs(obj.getAmount() - 0.00) <= 0.0001);
		assertEquals("", obj.getItemName());
		assertEquals(null, obj.getDate());
	}
}
