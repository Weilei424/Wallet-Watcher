package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.persistence.Investment;
import main.java.persistence.EarningInputData;
import main.java.persistence.ExpenseInputData;

class InvestmentTester {

	Investment saving;
	ExpenseInputData data=new ExpenseInputData();
	EarningInputData data2=new EarningInputData();
	@BeforeEach
	void init() {
		
		double rate = 0.01;
		String date="Feb 17,2021";
		double amount =300;
		String itemname ="TD saving";
		String note;
		
		saving = new Investment(date, amount, itemname, null, rate, data);
	}
	@Test
	void constructortest() {
		assertEquals(saving.getRate(),0.01);
		assertEquals(data.ledgerItems.size(),1);
		
	}
	@Test
	void CashoutTest() {
		saving.cashout("March 21,2023", data2);
		assertEquals(data2.income,300);
	}

}
