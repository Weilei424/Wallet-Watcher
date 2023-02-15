package main.java.persistence;

import java.util.ArrayList;
import java.util.List;

public class Misc {
	private double TFSA_room;
	private double RRSP_room;
	
	private List<Investment>TFSA_list;
	private List<Investment>RRSP_list;
	private List<Investment>RESP_list;
	
	public Misc(double room1,double room2) {
		this.TFSA_room=room1;
		this.RRSP_room=room2;
		this.TFSA_list=new ArrayList<Investment>();
		this.RRSP_list=new ArrayList<Investment>();
		this.RESP_list=new ArrayList<Investment>();
	}
}
