package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.EarningInputData;
import persistence.ExpenseInputData;
import persistence.Investment;

class InvestmentTester {

	Investment saving;
	ExpenseInputData data=new ExpenseInputData();
	EarningInputData data2=new EarningInputData();
	@BeforeEach
	void init() {
		
		double rate = 0.01;
		String date="2021-02-17";
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
		saving.cashout("2023-03-21", data2);
		assertEquals(data2.income,300);
	}

}
