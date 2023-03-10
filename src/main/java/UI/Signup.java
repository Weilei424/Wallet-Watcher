package UI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import DB.DBUtil;
import persistence.User;

public class Signup{
	
	//the actual page and the panel containing all elements on the page
	JFrame page;
	JPanel panel;
	
	//title on the page
	JLabel title;
	JPanel titlePanel; 
	
	//stuff for the firstname fetch 
	JTextField firstNameInput; 
	JLabel firstName;
	JPanel firstNamePanel;
	
	//stuff for the last name fetch 
	JTextField lastNameInput; 
	JLabel lastName;
	JPanel lastNamePanel;
	
	//stuff for username fetch
	JPanel namePanel;
	JTextField nameInput; 
	JLabel name;
	
	//stuff for password fetch
	JPanel passwordPanel;
	JPasswordField passwordInput;
	JLabel password;
	
	
	
	//submit button 
	JButton submit;
	//button to go to menu
	JButton back;
	//grouping the buttons so theyre next to one another 
	JPanel buttons;
	
	//Personal or business account options 
	JLabel account;
	JRadioButton personal;
	JRadioButton business;
	//groups buttons 
	ButtonGroup accountType;
	//panel just to make it easier to lay everything out 
	JPanel centerRadio; 
	
	//UI's that are initialized onclick
	MainUi redirect;
	navigatorPage nav;

	//action listeners for going to the menu
	ActionListener backToMenu=new ActionListener(){

		public void actionPerformed(ActionEvent e)
		{
			redirect=new MainUi();
			redirect.mainFrame.setVisible(true);
			page.dispose();	
		}
	};
	
	//action listener for submitting the username
	ActionListener submitItems=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			
			String userName=nameInput.getText();
			String firstName=firstNameInput.getText(); 
			String lastName=lastNameInput.getText();
			String userPassword=passwordInput.getText();
			String acc="";
			if(business.isSelected())
			{
				acc="business";
			}
			if(personal.isSelected())
			{
				acc="personal";
			}
			//create the user with this static factory method
			User assumedUser=User.createUser(firstName,lastName,userName,userPassword,acc);
		
			try
			{
				//change the redirect to the nav page and adds the user to the database
				DBUtil.createUser(assumedUser);
				nav=new navigatorPage();
				nav.navigator.setVisible(true);
				page.dispose();	
			}
			catch(IllegalArgumentException exception)
			{
				//popupmessage for the user to know the name is taken 
				JOptionPane.showMessageDialog(page, "Username already exists!");
			}
			catch(SQLException exception)
			{
				JOptionPane.showMessageDialog(page, "Username already exists!");
			}
		}
	};
	
	
	public Signup()
	{ 

		page=new JFrame("Signup Page"); 
		page.setSize(500,500);
		panel=new JPanel(new GridLayout(8,8));
		page.add(panel);
		
		//makes title
		JLabel title=new JLabel("Account registration");
		title.setFont(new Font("Garamond",Font.BOLD,30));
		
		
		//centers title and separates it from the rest
		titlePanel=new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.X_AXIS));
		title.setBorder(BorderFactory.createEmptyBorder(0,500,0,0));
		titlePanel.add(title);
		
		//Input username
		namePanel=new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
		nameInput=new JTextField(20);
		
		//first name and lastname
		firstNameInput=new JTextField(20);
		lastNameInput=new JTextField(20);
		firstName=new JLabel("Please enter your first name: ");
		lastName=new JLabel("Please enter your last name: ");

		//box layout that lays everything out horizontally
		firstNamePanel=new JPanel();
		firstNamePanel.setLayout(new BoxLayout(firstNamePanel,BoxLayout.X_AXIS));
		
		//box layout that lays everything out horizontally
		lastNamePanel=new JPanel();
		lastNamePanel.setLayout(new BoxLayout(lastNamePanel,BoxLayout.X_AXIS));
		
		
		//creates padding
		firstNamePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		lastNamePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//creates padding around 
		namePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		name=new JLabel("Please a username: ");
		name.setBorder(BorderFactory.createEmptyBorder(0,0,0,69));

		//add stuff to the panels 
		firstNamePanel.add(firstName);
		firstNamePanel.add(firstNameInput);

		lastNamePanel.add(lastName);
		lastNamePanel.add(lastNameInput);

		namePanel.add(name);
		namePanel.add(nameInput);
		
		//Password
		passwordInput = new JPasswordField(20);		
		password=new JLabel("Please enter your password: ");
		passwordPanel=new JPanel();
		passwordPanel.setLayout(new BoxLayout(passwordPanel,BoxLayout.X_AXIS));
		passwordPanel.add(password);
		passwordPanel.add(passwordInput);
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		buttons=new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		back=new JButton("Back to main menu");
		submit=new JButton("Submit");

		back.addActionListener(backToMenu);
		submit.addActionListener(submitItems);
		buttons.add(back);
		buttons.add(submit);
		
		//create radio buttons and add them to a button group so only one can be selected
		personal=new JRadioButton();
		business=new JRadioButton();

		account=new JLabel("What account type would you like:");
		personal.setText("Personal");
		business.setText("Business");
		accountType=new ButtonGroup();
		
		accountType.add(personal);
		accountType.add(business);
		
		//change fonts to make all more clear 
		firstName.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		lastName.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		name.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		password.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		account.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,24));
		personal.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		business.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));

		//make it so personal acc is default
		personal.setSelected(true);
		
		//centers radio buttons 
		JPanel centerRadio = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerRadio.add(account);
		centerRadio.add(personal);
		centerRadio.add(business);
		
		centerRadio.setBorder(BorderFactory.createEmptyBorder(20,0,0,0) );

		//adds all to panels 
		panel.add(title);
		panel.add(firstNamePanel);
		panel.add(lastNamePanel);
		panel.add(namePanel);
		panel.add(passwordPanel);
		panel.add(centerRadio);
		panel.add(buttons);
		
		//makes page visible and maximizes page
		page.setVisible(true);
		page.setExtendedState (java.awt.Frame.MAXIMIZED_BOTH);

		
		
	}
	public static void main(String[] args) {
		new Signup();
	}
}
