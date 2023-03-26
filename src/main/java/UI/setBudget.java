package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import persistence.BudgetData;
import persistence.User;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class setBudget {
	public JFrame popup;  
	
	public navigatorPage nav;
	
	private JPanel page;
	
	private JPanel budgetDate;
	
	private JLabel budget; 

	private JLabel budgetAmount;
	private JTextField budgetAmountInput;
	
	public JComboBox <Integer> year;
	public JComboBox <Integer>  month; 
	public JComboBox <Integer> day;
	
	private JButton inputBudget;
	
	public YearMonth yearMonth; 
	public int daysPerMonth;
	private ActionListener yearUpdate=(new ActionListener()
	{ 
	    public void actionPerformed(ActionEvent e) {
	    	 yearMonth=YearMonth.of((Integer)year.getSelectedItem(), (Integer)month.getSelectedItem());
	 	     daysPerMonth=yearMonth.lengthOfMonth(); 
	 	    Integer [] days=new Integer[daysPerMonth];
		    for(int x=0; x < daysPerMonth ; x++)
		    { 
		    	days[x]=x+1;
		    }
	 		  day.setModel(new DefaultComboBoxModel<>(days));
	    }    
	});

	private ActionListener monthUpdate=(new ActionListener()
	{ 
	    public void actionPerformed(ActionEvent e) {
	    	 	yearMonth=YearMonth.of((Integer)year.getSelectedItem(), (Integer)month.getSelectedItem());
	 	    	daysPerMonth=yearMonth.lengthOfMonth(); 
	 	    	Integer [] days=new Integer[daysPerMonth];
	 		    for(int x=0; x < daysPerMonth ; x++)
	 		    { 
	 		    	days[x]=x+1;
	 		    }
	 		   day.setModel(new DefaultComboBoxModel<>(days));
	    }    
	});
	
	private ActionListener BudgetInputActionListener=(new ActionListener()
	{ 
	    public void actionPerformed(ActionEvent e) 
	    {
	    	int budgetAmount=Integer.parseInt(budgetAmountInput.getText());
	    	LocalDate curr=LocalDate.now();
	    	LocalDate endDate=LocalDate.of((int)year.getSelectedItem(),
	    			(int)month.getSelectedItem(),(int)day.getSelectedItem());
	    	if(ChronoUnit.DAYS.between(curr,endDate)>0L)
	    	{
	    		User.setBudget(new BudgetData(budgetAmount,endDate));
	    		nav=new navigatorPage();
	    		popup.dispose();
	    	}
	    	else
	    	{ 
	    		JOptionPane.showMessageDialog(null, "Set a later end date");
	    	}
	    }
	});
	
	
	
	public setBudget()
	{ 
		popup=new JFrame();
		popup.setSize(new Dimension(800,622));
		popup.setLocationRelativeTo(null);
		
		page=new JPanel();
		page.setLayout(new GridLayout(7,1));
		
		JPanel title=new JPanel();
		title.setLayout(new BorderLayout());
				
		budget=new JLabel("New Budget"); 
		budget.setHorizontalAlignment(JLabel.CENTER);
	    budget.setVerticalAlignment(JLabel.CENTER);
	    budget.setFont(new Font("Garamond",Font.BOLD,36));
	    
		title.add(budget,BorderLayout.NORTH);
		page.add(title);
	    
	    JPanel budgetInput=new JPanel();
	    budgetInput.setLayout(new FlowLayout());
	    budgetAmount=new JLabel("Enter your budget: "); 
	    budgetAmount.setFont(new Font("Comic Sans",Font.BOLD,24));
	    
	    budgetAmountInput=new JTextField(); 
		budgetAmountInput.setPreferredSize(new Dimension(400,50));

	    budgetInput.add(budgetAmount);
	    budgetInput.add(budgetAmountInput);
	    
	    JPanel Date=new JPanel();
	    
	    LocalDate curr=LocalDate.now();
	    
	    
	    JPanel yearInput=new JPanel();
	    yearInput.setLayout(new BoxLayout(yearInput,BoxLayout.Y_AXIS));
	    Integer [] years=new Integer[5];
	    for(int x=0; x < 5; x++)
	    { 
	    	years[x]=curr.getYear()+x;
	    }
	    year= new JComboBox(years);
	    year.setSelectedIndex(0);

	    JLabel chooseYear=new JLabel("Year:");

	    yearInput.add(chooseYear);
	    yearInput.add(year);
	    
	    JPanel monthInput=new JPanel();
	    monthInput.setLayout(new BoxLayout(monthInput,BoxLayout.Y_AXIS));
	    Integer [] months=new Integer[12];
	    for(int x=0; x < 12; x++)
	    { 
	    	months[x]=x+1;
	    }
	    month= new JComboBox(months);
	    month.setSelectedIndex(0);
	    
	    JLabel chooseMonth=new JLabel("Month:");

	    monthInput.add(chooseMonth);
	    monthInput.add(month);
	    
	    
	    //amount of days per month
	 	yearMonth=YearMonth.of((Integer)year.getSelectedItem(), (Integer)month.getSelectedItem());
	 	daysPerMonth=yearMonth.lengthOfMonth(); 

	    JPanel dayInput=new JPanel();
	    dayInput.setLayout(new BoxLayout(dayInput,BoxLayout.Y_AXIS));
	    Integer [] days=new Integer[daysPerMonth];
	    for(int x=0; x < daysPerMonth ; x++)
	    { 
	    	days[x]=x+1;
	    }
	    day= new JComboBox(days);
	    
	    JLabel chooseDay=new JLabel("Day:");

	    dayInput.add(chooseDay);
	    dayInput.add(day);
	    
	    //adding date modification 
	    year.addActionListener(yearUpdate);
	    month.addActionListener(monthUpdate);
	    
	    budgetDate=new JPanel(new GridLayout(1,3,10,0));
	    
	    Border leftPadding=BorderFactory.createEmptyBorder(0,10,0,0);
	    Border rightPadding=BorderFactory.createEmptyBorder(0,0,0,10);
	    
	    yearInput.setBorder(leftPadding);
	    dayInput.setBorder(rightPadding);
	    
	    //set color of comboboxes
	    year.setBackground(Color.white);
	    month.setBackground(Color.white);
	    day.setBackground(Color.white);

	    budgetDate.add(yearInput);
	    budgetDate.add(monthInput);
	    budgetDate.add(dayInput);

	    page.add(budgetInput);
	    page.add(budgetDate);	    		
		popup.add(page);
		
		page.add(new JPanel());

		JPanel button=new JPanel();
		inputBudget=new JButton("Enter Budget");
		inputBudget.setPreferredSize(new Dimension(300,75));
		inputBudget.addActionListener(BudgetInputActionListener);
		button.add(inputBudget);
		
		page.add(button);
		
		popup.setVisible(true);
	}
	
	public static void main(String[] args) {
		new setBudget();
	}
}
