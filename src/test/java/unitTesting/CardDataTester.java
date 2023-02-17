package test.java.unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.persistence.CardData;
import main.java.persistence.CardData.*;


class CardDataTester {
	CardData data;
	bankingCard ex1;
	CreditCard ex2;
	PointsCard ex3 ;
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
	

	@BeforeEach
	void init()
	{
		data=data=new CardData();
		data.addBankingCard(card1, quantity1, quantity4,note1 );
		data.addCreditCard(card2, quantity2, quantity5,new Date(),note2, interest);
		data.addPointsCard(card3, quantity3, quantity6,note3 );
	}
	@Test
	void AddCardTest() {
		
		assertEquals(data.Cards.get(0).getAmount(), quantity1);
		assertEquals(data.Cards.get(1).getAmount(), quantity2);
		assertEquals(data.Cards.get(2).getAmount(), quantity3);

		assertEquals(data.Cards.get(0).getName(), card1);
		assertEquals(data.Cards.get(1).getName(), card2);
		assertEquals(data.Cards.get(2).getName(), card3);
	}
	@Test
	void getCardTest()
	{
		assertEquals(data.getCard(card1).getAmount(),quantity1);
		assertEquals(data.getCard(card1).getName(),card1);
	}
	
	@Test
	void removeandAddAmountTest()
	{
		data.getCard(card1).removeAmount(100.0);
		assertEquals(data.getCard(card1).getAmount(),quantity1-100);
		data.getCard(card1).addAmount(100.0);		
		assertEquals(data.getCard(card1).getAmount(),quantity1);
	}
	

}