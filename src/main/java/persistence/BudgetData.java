package persistence;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import businessLogic.DateParser;

public class BudgetData {
	public static int counter=0;
	public int ref;
	public double budget; 
	public LocalDate startDate; 
	public LocalDate endDate;
	
    public BudgetData(double budget,String endDate) {
    	this.budget=budget;
    	startDate=LocalDate.now(); 	
    	this.endDate=DateParser.getDateFromString(endDate);
    	this.ref=counter++;
    }
    public BudgetData(double budget,String startDate, String endDate,int ref) {
    	this.budget=budget;
    	this.startDate=DateParser.getDateFromString(startDate); 	
    	this.endDate=DateParser.getDateFromString(endDate);
    	this.ref=ref;
    }

    public double getMoneyLeft(ExpenseInputData expenseInputData) {
        return budget - expenseInputData.cost;
    }
    

}
