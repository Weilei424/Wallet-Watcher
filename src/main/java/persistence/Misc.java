package main.java.persistence;

import java.util.ArrayList;
import java.util.List;

public class Misc extends LedgerItem {
	public Misc(String date, double amount, String itemName, String note) {
		super(date, amount, itemName, note);
		// TODO Auto-generated constructor stub
	}
	private double TFSA_room;
	private double RRSP_room;
	
	private List<Investment>TFSA_list;
	private List<Investment>RRSP_list;
	private List<Investment>RESP_list;
	
	
}
