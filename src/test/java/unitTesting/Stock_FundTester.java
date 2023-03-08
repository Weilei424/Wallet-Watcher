package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.ExpenseInputData;
import persistence.LedgerItem;
import persistence.Stock_Fund;

class Stock_FundTester {
	
	Stock_Fund Apple;
	ExpenseInputData data= new ExpenseInputData();
	@BeforeEach
	
	
	void init() {
	String date="2023-02-15";
	String itemname="Apple Inc";
	double amount = 1700;
	String note="Purchase stock";
	Apple=new Stock_Fund(date,amount,itemname,note,data);
	
	}
	
	@Test
	void constructortest1() {
		
		assertEquals(data.ledgerItems.size(),1);
		LedgerItem item= data.ledgerItems.get(0);
		
		
	}
	@Test
	void constructortest2() {
		assertEquals(Apple.getCurrent(), 1700);
	}
	@Test
	void setcurrentTest(){
		Apple.priceChange(600);
		assertEquals(Apple.getAmount(),1700);
		
	}

}
