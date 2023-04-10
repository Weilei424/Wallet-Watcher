package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.Misc;

class MiscTester {
Misc tfsa;
	@BeforeEach
	void init() {
		
		
	}

	@Test
	void test() {
		String date ="2021-02-11";
		double amount =1000;
		tfsa = new Misc(date,amount,"misc test");
		assertEquals(tfsa.getAmount(),1000);
	}

}
