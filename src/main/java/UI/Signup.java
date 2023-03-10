package UI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import persistence.User;
import persistence.DBUtil;

public class Signup{
	
	JFrame page;
	JPanel panel;
	
	//title on the page
	JLabel title;
	JPanel titlePanel; 
	//name input
	
	
	
	JTextField firstNameInput; 
	JLabel firstName;
	JPanel firstNamePanel;
	
	JTextField lastNameInput; 
	JLabel lastName;
	JPanel lastNamePanel;
	
	JPanel namePanel;
	JTextField nameInput; 
	JLabel name;
	
	//password input
	JPanel passwordPanel;
	JTextField passwordInput; 
	JLabel password;
	
	//type input
	JTextField typeInput; 
	JLabel type;
	
	
	//submit button 
	JButton submit; 
	JButton back;
	JPanel buttons;
	
	//Personal or business account options 
	JLabel account;
	JRadioButton personal;
	JRadioButton business;
	ButtonGroup accountType;
	JPanel centerRadio; 
		
	MainUi redirect=new MainUi();
	ExpensePage complete=new ExpensePage();
	navigatorPage nav=new navigatorPage();

	//action listeners for the menu
	//action listener for going back to menu
	ActionListener backToMenu=new ActionListener(){

		public void actionPerformed(ActionEvent e)
		{
			redirect.mainFrame.setVisible(true);
			page.setVisible(false);
			complete.mainEpFrame.dispose();
			page.dispose();	
		}
	};
	
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
			
			User assumedUser=User.createUser(firstName,lastName,userName,userPassword,acc);
		
			try
			{
				
				//change the redirect to the nav page
				DBUtil.createUser(assumedUser);
				nav.navigator.setVisible(true);
				redirect.mainFrame.dispose();
				page.setVisible(false);
				complete.mainEpFrame.dispose();
				page.dispose();	
			}
			catch(IllegalArgumentException exception)
			{
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
		nav.navigator.setVisible(false);
		redirect.mainFrame.setVisible(false);
		complete.mainEpFrame.setVisible(false);
		page=new JFrame("Signup Page"); 
		page.setSize(500,500);
		panel=new JPanel(new GridLayout(8,8));
		//panel.setBackground(new Color());
		page.add(panel);
		
		
		JLabel title=new JLabel("Account registration");
		title.setFont(new Font("Garamond",Font.BOLD,30));
		
		titlePanel=new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.X_AXIS));
		title.setBorder(BorderFactory.createEmptyBorder(0,500,0,0));
		titlePanel.add(title);
		
		namePanel=new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
		nameInput=new JTextField(20);
		
		firstNameInput=new JTextField(20);
		lastNameInput=new JTextField(20);
		firstName=new JLabel("Please enter your first name: ");
		lastName=new JLabel("Please enter your last name: ");

		firstNamePanel=new JPanel();
		firstNamePanel.setLayout(new BoxLayout(firstNamePanel,BoxLayout.X_AXIS));
		
		lastNamePanel=new JPanel();
		lastNamePanel.setLayout(new BoxLayout(lastNamePanel,BoxLayout.X_AXIS));
		
		firstNamePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		lastNamePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//creates padding around 
		namePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		name=new JLabel("Please a username: ");
		name.setBorder(BorderFactory.createEmptyBorder(0,0,0,69));

		firstNamePanel.add(firstName);
		firstNamePanel.add(firstNameInput);

		lastNamePanel.add(lastName);
		lastNamePanel.add(lastNameInput);


		namePanel.add(name);
		namePanel.add(nameInput);
		
		
		passwordInput=new JTextField(20);
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
		
		personal=new JRadioButton();
		business=new JRadioButton();

		account=new JLabel("What account type would you like:");
		personal.setText("Personal");
		business.setText("Business");
		accountType=new ButtonGroup();
		
		accountType.add(personal);
		accountType.add(business);
		
		
		firstName.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		lastName.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		name.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		password.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		account.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,24));
		personal.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));
		business.setFont(new Font(account.getFont().getFontName(),Font.PLAIN,16));

		personal.setSelected( true);
		
		JPanel centerRadio = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerRadio.add(account);
		centerRadio.add(personal);
		centerRadio.add(business);
		
		centerRadio.setBorder(BorderFactory.createEmptyBorder(20,0,0,0) );

		
		panel.add(title);
		panel.add(firstNamePanel);
		panel.add(lastNamePanel);
		panel.add(namePanel);
		panel.add(passwordPanel);
		panel.add(centerRadio);
		panel.add(buttons);
		
		page.setVisible(true);
		page.setExtendedState (java.awt.Frame.MAXIMIZED_BOTH);

		
		
	}
	public static void main(String[] args) {
		new Signup();
	}
}
