package test.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.persistence.LedgerItem;

class LedgerItemTester {
	LedgerItem obj;
	String input = "xxxxxx";
	
	@BeforeEach
	void init() {
		LedgerItem o1 = new LedgerItem("2023/02/13", 0, input, input);
		LedgerItem o2 = new LedgerItem("2023/02/13", 0, input, input);
		obj = new LedgerItem("2023/02/13", 0, input, input);
	}
	
	
	@Test 
	void testSetterGetter() {
		assertEquals("xxxxxx", obj.getNote());
		assertTrue(Math.abs(obj.getAmount() - 0.00) <= 0.0001);
		assertEquals("xxxxxx", obj.getItemName());
		
		double amount = 15.63;
		String s = "Wendy's";
		String n = "double combo, with fries and coke";
		LocalDate d = LocalDate.now();
		obj.setNote(n);
		obj.setAmount(amount);
		obj.setItemName(s);
		obj.setDate(d);
		
		
		assertEquals(n, obj.getNote());
		assertTrue(Math.abs(obj.getAmount() - amount) <= 0.0001);
		assertEquals(s, obj.getItemName());
		assertEquals(d, obj.getDate());
		assertEquals("0000002", obj.getRef());
	}
}
