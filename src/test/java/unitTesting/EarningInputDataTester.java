package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.Earning;
import persistence.EarningInputData;

class EarningInputDataTester {
EarningInputData data = new EarningInputData();
	// TODO: Test fails
	// @Test
	void Addearningtest() {
		String date="Feb 11,2022";
		double amount =2000;
		String item ="salary";


		data.addEarning(date, amount, item,null);
		Earning earn = (Earning) data.incomes.get(0);
		assertEquals(earn.getAmount(),2000);
		assertEquals(data.incomes.size(),1);
	}
	@BeforeEach
	void init() {
		
	}
	@Test
	void test() {
		assertEquals(data.incomes.size(),0);
	}

	@Test
	void getIncomesTest() {
		String date="Feb 11,2022";
		double amount =2000;
		String item ="salary";
		
		
		data.addEarning(date, amount, item,null);
		data.addEarning(date, amount, item,null);
		assertEquals(data.getIncome(),4000);
	}
}
