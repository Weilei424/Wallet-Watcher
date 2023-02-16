package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.persistence.Stock_Fund;
import main.java.persistence.ExpenseInputData;

class Stock_FundTester {
	
	Stock_Fund Apple;
	ExpenseInputData data= new ExpenseInputData();
	@BeforeEach
	
	
	void init() {
	String date="Feb 15,2023";
	String itemname="Apple Inc";
	double amount = 1700;
	String note="Purchase stock";
	Apple=new Stock_Fund(date,amount,itemname,note,data);
	
	}
	
	@Test
	void constructortest() {
		;
	}

}
