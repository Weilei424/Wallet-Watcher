package src.main.java.persistence;

public class BudgetData extends LedgerItem {

    public BudgetData(String date, double amount, String itemName, String note) {
        super(date, amount, itemName, note);
    }

    public double getMoneyLeft(ExpenseInputData expenseInputData) {
        return amount - expenseInputData.cost;
    }

}
