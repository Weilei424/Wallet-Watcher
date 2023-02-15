package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.businessLogic.Util;
import main.java.persistence.LedgerItem;

class UtilTester {
	LedgerItem obj;
	
	@BeforeEach
	void init() throws Exception {
		obj = new LedgerItem("2020/01/01", 0.00, "", "");
		obj.setAmount(10000.00);
		Date d1 = new Date();
		obj.setDate(d1);
		obj.setItemName("lottery winning");
		obj.setNote("lucky me!");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testCalcMonth1() {
		Date d2 = new Date();
		d2.setYear(124);
		d2.setMonth(5);
		assertEquals(16, Util.calcMonth(obj.getDate(), d2));
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	void testCalcMonth2() {
		Date d2 = new Date();
		d2.setYear(103);
		d2.setMonth(5);
		assertEquals(-236, Util.calcMonth(obj.getDate(), d2));
		//should assertThrows when main.java.exceptions can be correctly handled
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCalcYear1() {
		Date d2 = new Date();
		d2.setYear(99);
		d2.setMonth(5);
		assertEquals(-24, Util.calcYear(obj.getDate(), d2));
		//should assertThrows when main.java.exceptions can be correctly handled
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCalcYear2() {
		Date d2 = new Date();
		d2.setYear(133);
		d2.setMonth(11);
		assertEquals(10, Util.calcYear(obj.getDate(), d2));
	}

	@SuppressWarnings("deprecation")
	@Test
	void testCalcBiweek1() {
		Date d2 = new Date();
		d2.setMonth(2);
		assertEquals(2, Util.calcBiweek(obj.getDate(), d2));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCalcBiweek2() {
		Date d2 = new Date();
		d2.setYear(124);
		assertEquals(26, Util.calcBiweek(obj.getDate(), d2));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCalcBiweek3() {
		Date d2 = new Date();
		d2.setYear(143);
		d2.setMonth(0);
		assertEquals(519, Util.calcBiweek(obj.getDate(), d2));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCalcBiweek4() {
		Date d2 = new Date();
		d2.setYear(3);
		assertEquals(-3130, Util.calcBiweek(obj.getDate(), d2));
		//should assertThrows when main.java.exceptions can be correctly handled
	}
}
