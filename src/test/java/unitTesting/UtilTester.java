package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import businessLogic.Util;
import persistence.LedgerItem;

class UtilTester {
	LedgerItem obj;
	
	@BeforeEach
	void init() throws Exception {
		obj = new LedgerItem("2020/01/01", 0.00, "", "");
		obj.setAmount(10000.00);
		LocalDate d1 = LocalDate.of(2020, 1, 1);
		obj.setDate(d1);
		obj.setItemName("lottery winning");
		obj.setNote("lucky me!");
	}

	@Test
	void testCalcMonth1() {
		LocalDate d2 = LocalDate.of(2020, 6, 1);
		assertEquals(5, Util.calcMonth(obj.getDate(), d2));
	}
	
	@Test
	void testCalcMonth2() {
		LocalDate d2 = LocalDate.of(2003, 5, 1);
		assertEquals(-200, Util.calcMonth(obj.getDate(), d2));

	}
	
	@Test
	void testCalcYear1() {
		LocalDate d2 = LocalDate.of(1999, 5, 1);
		assertEquals(-21, Util.calcYear(obj.getDate(), d2));

	}
	
	@Test
	void testCalcYear2() {
		LocalDate d2 = LocalDate.of(2033, 5, 1);
		assertEquals(13, Util.calcYear(obj.getDate(), d2));
	}

	@Test
	void testCalcBiweek1() {
		LocalDate d2 = LocalDate.of(2020, 2, 20);
		assertEquals(3, Util.calcBiweek(obj.getDate(), d2));
	}
	
	@Test
	void testCalcBiweek2() {
		LocalDate d2 = LocalDate.of(2021, 1, 1);
		assertEquals(26, Util.calcBiweek(obj.getDate(), d2));
	}
	
	@Test
	void testCalcBiweek3() {
		LocalDate d2 = LocalDate.of(2043, 1, 1);
		assertEquals(598, Util.calcBiweek(obj.getDate(), d2));
	}
	
	@Test
	void testCalcBiweek4() {
		LocalDate d2 = LocalDate.of(1903, 7, 26);
		assertEquals(-3027, Util.calcBiweek(obj.getDate(), d2));

	}
}
