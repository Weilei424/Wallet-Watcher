package src.main.java.persistence;

public class BillData {
	//meant to be where we do all the work with the bill data base however, no database to be used so stub database for now : 
	
	public static void main(String[] args) {
		ExpenseInputData stub=new ExpenseInputData(); 
		
		//just storing items similar to JUnit Tests
		LedgerItem ex = new LedgerItem("2022-06-08",20.50, "Food", "Bought it cause i was hungry"); 
		LedgerItem ex1 = new LedgerItem("2023-06-08",15.50, "Toy", "Luxury"); 
		LedgerItem ex2 = new LedgerItem("2024-06-08",16.50, "Mcdonalds", "needed a snack"); 
		LedgerItem ex3 = new LedgerItem("2025-06-08",12.50, "Car", "major purchase"); 
		LedgerItem ex4 = new LedgerItem("2026-06-08",1.50, "Food", "Bought it cause i was hungry"); 
		stub.addExpense(ex);
		stub.addExpense(ex1);
		stub.addExpense(ex2);
		stub.addExpense(ex3);
		stub.addExpense(ex4);
		System.out.println(stub.generateReceipt());

	}
}
