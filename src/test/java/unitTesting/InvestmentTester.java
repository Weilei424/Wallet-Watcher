package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.persistence.Investment;
import main.java.persistence.ExpenseInputData;

class InvestmentTester {

	Investment saving;
	@BeforeEach
	void init() {
		ExpenseInputData data=new ExpenseInputData();
		double rate = 0.01;
		String date="Feb 17,2021";
		double amount =300;
		String itemname ="TD saving";
		String note;
		
		saving = new Investment(date,amount,itemname,null,rate,data);
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
