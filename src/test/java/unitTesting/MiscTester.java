package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.persistence.Misc;

class MiscTester {
Misc tfsa;
	@BeforeEach
	void init() {
		
		
	}
	@Test
	void test() {
		String date ="Feb 11,2021";
		double amount =1000;
		tfsa = new Misc(date,amount);
		assertEquals(tfsa.getAmount(),1000);
	}

}
