package main.java.persistence;

import java.util.ArrayList;
import java.util.List;

public class Misc extends LedgerItem {
	public Misc(String name, double amount) {
		super(null, amount, name, null);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		String message="Your "+this.itemName+"have amount of "+this.amount;
		return message;
	}
	
	
}
