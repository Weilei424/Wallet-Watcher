package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.*;
import java.util.ArrayList;



class CardDataTester {
	String card1="BankingCard";
	String card2="CreditCard";
	String card3="SearsCard";
	double quantity1=200.0;
	double quantity2=200.0;
	double quantity3=200.0;
	double quantity4=200.0;
	double quantity5=200.0;
	double quantity6=200.0;
	String note1="This is my Banking Card";
	String note2="This is my Credit Card";
	String note3="This is my Points Card";
	Double interest=10.0;
	BankingCard ex1 = new BankingCard(card1, quantity1, quantity4, note1);
	CreditCard ex2 = new CreditCard(card2, quantity2, quantity5, new Date(), note2, interest);
	PointsCard ex3 = new PointsCard(card3, quantity3, quantity6, note3);
	ArrayList<Card> data;

	@BeforeEach
	void init()
	{
		data = new ArrayList<>();
		data.add(ex1);
		data.add(ex2);
		data.add(ex3);
	}
	@Test
	void AddCardTest() {
		
		assertEquals(data.get(0).getAmount(), quantity1);
		assertEquals(data.get(1).getAmount(), quantity2);
		assertEquals(data.get(2).getAmount(), quantity3);

		assertEquals(data.get(0).getName(), card1);
		assertEquals(data.get(1).getName(), card2);
		assertEquals(data.get(2).getName(), card3);
	}
	@Test
	void getCardTest()
	{
		assertEquals(data.get(0).getAmount(),quantity1);
		assertEquals(data.get(0).getName(),card1);
	}
	
	@Test
	void removeandAddAmountTest()
	{
		data.get(0).removeAmount(100.0);
		assertEquals(data.get(0).getAmount(),quantity1-100);
		data.get(0).addAmount(100.0);		
		assertEquals(data.get(0).getAmount(),quantity1);
	}
	

}