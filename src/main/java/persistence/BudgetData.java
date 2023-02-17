package main.java.persistence;

public class BudgetData {

	public double budget; 
    public BudgetData(double budget) {
    	this.budget=budget; 
    }

    public double getMoneyLeft(ExpenseInputData expenseInputData) {
        return budget - expenseInputData.cost;
    }

}
