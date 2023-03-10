package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.Earning;
import persistence.EarningInputData;

class EarningInputDataTester {
EarningInputData data = new EarningInputData();
	@BeforeEach
	void init() {
		
	}
	@Test
	void test() {
		assertEquals(data.incomes.size(),0);
	}
	@Test
	void Addearningtest() {
		String date="2022-01-01";
		double amount =2000;
		String item ="salary";
		
		
		data.addEarning(date, amount, item,null);
		Earning earn = (Earning) data.incomes.get(0);
		assertEquals(earn.getAmount(),2000);
		assertEquals(data.incomes.size(),1);
	}

	@Test
	void getIncomesTest() {
		String date="2022-01-01";
		double amount =2000;
		String item ="salary";
		
		
		data.addEarning(date, amount, item,null);
		data.addEarning(date, amount, item,null);
		assertEquals(data.getIncome(),4000);
	}
}
