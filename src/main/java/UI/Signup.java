package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Signup{
	
	JFrame page;
	JPanel panel;
	
	//title on the page
	JLabel title;
	JPanel titlePanel; 
	
	//name input
	JPanel namePanel;
	JTextField nameInput; 
	JLabel name;
	
	//password input
	JPanel passwordPanel;
	JTextField passwordInput; 
	JLabel password;
	
	//submit button 
	JButton submit; 
	JButton back;
	JPanel buttons;
	
	MainUi redirect=new MainUi();
	ExpensePage complete=new ExpensePage();

	//action listeners for the menu
	//action listener for going back to menu
	ActionListener backToMenu=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			redirect.mainFrame.setVisible(true);
			page.setVisible(false);
		}
	};
	
	ActionListener submitItems=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			complete.mainEpFrame.setVisible(true); 	
		}
	};
	
	
	public Signup()
	{ 
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
		title.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
		titlePanel.add(title);
		
		namePanel=new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
		nameInput=new JTextField(20);
		
		//creates padding around 
		namePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		name=new JLabel("Please a username: ");
		name.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));

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
		
		
		panel.add(title);
		panel.add(namePanel);
		panel.add(passwordPanel);
		panel.add(buttons);
		
		page.setVisible(true);

		
		
	}
	public static void main(String[] args) {
		new Signup();
	}
}
