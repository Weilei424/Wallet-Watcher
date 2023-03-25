package persistence;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BudgetData {

	public double budget; 
	public LocalDate startDate; 
	public LocalDate endDate;
	
    public BudgetData(double budget) {
    	this.budget=budget;
    	startDate=LocalDate.now(); 	
    }

    public double getMoneyLeft(ExpenseInputData expenseInputData) {
        return budget - expenseInputData.cost;
    }
    public boolean prompt()
    { 
    	boolean promptUser=ChronoUnit.SECONDS.between(startDate,endDate)>0L; 
    	return promptUser;
    }
    

}
