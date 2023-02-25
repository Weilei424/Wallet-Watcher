package test.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.persistence.Earning;

class EarningTester {

	Earning earn;
	@BeforeEach
	void initt() {
	String data="Feb 1,2021";
	double amount=1000;
	String itemname="Tips";
	earn = new Earning(data,amount,itemname,null);
	}
	
	@Test
	void testconstructor() {
		assertEquals(earn.getAmount(),1000);
		
		
	}
	
	@Test
	void deductTest() {
		earn.deduction(200);
		assertEquals(earn,800);
	}

}
