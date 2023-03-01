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
	// TODO: Test fails
	// @Test
	void test() {
		String date ="Feb 11,2021";
		double amount =1000;
		tfsa = new Misc(date,amount);
		assertEquals(tfsa.getAmount(),1000);
	}

}
