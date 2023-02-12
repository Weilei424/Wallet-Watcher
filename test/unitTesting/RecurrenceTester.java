package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import businessLogic.Recurrence;

class RecurrenceTester {

	@Test
	void test() {
		Date date = new Date();
		String s1 = "weekly";
		String s2 = "biweekly";
		String s3 = "monthly";
		String s4 = "yearly";
		
		Recurrence r1 = new Recurrence(date, s1);
		assertTrue(r1.getDate().equals(date) && r1.getFrequency().equals(s1));
		
		Recurrence r2 = new Recurrence(date, s2);
		assertTrue(r2.getDate().equals(date) && r2.getFrequency().equals(s2));
		
		Recurrence r3 = new Recurrence(date, s3);
		assertTrue(r3.getDate().equals(date) && r3.getFrequency().equals(s3));
		
		Recurrence r4 = new Recurrence(date, s4);
		assertTrue(r4.getDate().equals(date) && r4.getFrequency().equals(s4));
		
		Recurrence r5 = new Recurrence(date, "every second");
		assertTrue(r5.getDate().equals(date) && r5.getFrequency() == null);
	}

}
