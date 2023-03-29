package persistence;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BudgetData {

	public double budget; 
	public LocalDate startDate; 
	public LocalDate endDate;
	
    public BudgetData(double budget,LocalDate endDate) {
    	this.budget=budget;
    	startDate=LocalDate.now(); 	
    	this.endDate=endDate;
    }

    public double getMoneyLeft(ExpenseInputData expenseInputData) {
        return budget - expenseInputData.cost;
    }
    

}
