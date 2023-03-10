package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import businessLogic.Recurrence;

class RecurrenceTester {

	@SuppressWarnings("deprecation")
	@Test
	void test() {
		Date d1 = new Date();
		Date d2 = new Date();
		d2.setMonth(11);
		String s1 = "weekly";
		String s2 = "biweekly";
		String s3 = "monthly";
		String s4 = "yearly";
		
		Recurrence r1 = new Recurrence(d1, d2, s1);
		assertTrue(r1.getStartDate().equals(d1) && r1.getEndDate().equals(d2) && r1.getFrequency().equals(s1));
		
		Recurrence r2 = new Recurrence(d1, d2, s2);
		assertTrue(r2.getStartDate().equals(d1) && r2.getEndDate().equals(d2) && r2.getFrequency().equals(s2));
		
		Recurrence r3 = new Recurrence(d1, d2, s3);
		assertTrue(r3.getStartDate().equals(d1) && r3.getEndDate().equals(d2) && r3.getFrequency().equals(s3));
		
		Recurrence r4 = new Recurrence(d1, d2, s4);
		assertTrue(r4.getStartDate().equals(d1) && r4.getEndDate().equals(d2) && r4.getFrequency().equals(s4));
		
		Recurrence r5 = new Recurrence(d1, d2, "every second");
		assertTrue(r5.getStartDate().equals(d1) && r5.getEndDate().equals(d2) && r5.getFrequency() == null);
	}

	/* uncomment this when UI can handle exceptions */
//	@SuppressWarnings("deprecation")
//	@Test
//	void testException() {
//		Date d1 = new Date();
//		Date d2 = new Date();
//		d2.setMonth(0);
//		String s1 = "weekly";
//		assertThrows(InvalidDateException.class, ()-> new Recurrence(d1, d2, s1));
//	}

}
