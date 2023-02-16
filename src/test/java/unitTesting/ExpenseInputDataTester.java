package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.persistence.ExpenseInputData;
import main.java.persistence.LedgerItem;

class ExpenseInputDataTester {
	LedgerItem item1;
	LedgerItem item2;
	LedgerItem item3;
	LedgerItem item4;
	ExpenseInputData data; 
	@BeforeEach
	void init()
	{ 
		item1=new LedgerItem("2022-03-06",200,"Car", "Needed for pubic transportation");
		item2=new LedgerItem("2022-03-06",20, "public transport", "needed to get home");
		item3=new LedgerItem("2022-03-06",150,"Climbing shoes", "needed to buy i swear");
		item4=new LedgerItem("2022-03-06",5,"tim hortons","snack for school");
		data=new ExpenseInputData();
		data.addExpense(item1);
		data.addExpense(item2);
		data.addExpense(item3);		
	}
	@Test
	void CostConsistencyTest() {
	assertEquals((double)370,data.cost);
	data.addExpense(item4);
	assertEquals((double)375,data.cost);
	}
	

}