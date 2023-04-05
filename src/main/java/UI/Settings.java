package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DB.DBUtil;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import persistence.User;

public class Settings { 
	JFrame settingsFrame; 
	NavigatorPage redirect;
	LoginPage main;
	//Different main titles for user
	JLabel titleMain; 
	JLabel titleAcc;
	
	//add a JPanel for different types of settings you want within this page
	JPanel topMost; 
	JPanel mainPage;
	JPanel button1; 
	JPanel button2; 
	
	
		
	//redirects to different pages within the main settings page
	JButton toAcc; 
	JButton toNav;
	//JButton other;
	
	//redirects back to the different parts of the settings page 
	JButton toMain; 
	
	//holds all the elements in the acocuntSetings page
	JPanel holder;
	
	//Account Settings page
	JLabel accountSettings;
	
	//Buttons
	JButton deleteAcc; 
	JButton merge; 
	JButton changePassword;
	JButton toSettings;
	
	//panels containing all of the subsettings 
	JPanel mergePage;
	JPanel changeUserPassword; 
	JPanel deleteAccount;
	
	//mergeAcc page
	JPasswordField passwordInput;
	JTextField username2Input;
	JPasswordField password2Input;
	JTextField newFirstName;
	JTextField newLastName;
	JTextField newUserInput; 
	JPasswordField newPasswordInput;
	
	//change passwordPage
	JTextField oldPasswordInput;

	JLabel themeLabel;
	JComboBox<String> themes;
	private static int curIndex = 0;

	ActionListener themeListener = event -> {
		JComboBox box = (JComboBox) event.getSource();
		String selected = (String) box.getSelectedItem();
		if (selected == null) return;

		switch (selected) {
			case "Light":
				FlatLightLaf.setup();
				curIndex = 0;
				break;
			case "Dark":
				FlatDarkLaf.setup();
				curIndex = 1;
				break;
			case "macOS Light":
				FlatMacLightLaf.setup();
				curIndex = 2;
				break;
			case "macOS Dark":
				FlatMacDarkLaf.setup();
				curIndex = 3;
				break;
			case "IntelliJ Light":
				FlatIntelliJLaf.setup();
				curIndex = 4;
				break;
			case "Darcula":
				FlatDarculaLaf.setup();
				curIndex = 5;
				break;
		}

		SwingUtilities.updateComponentTreeUI(settingsFrame);
	};

	//adding action listeners to do the redirects 
	ActionListener AccDirect=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			topMost.removeAll();
			topMost.revalidate();
			topMost.repaint();
			topMost.setLayout(new BorderLayout());
			holder=new JPanel();
			holder.setLayout(new GridLayout(6,1,10,10));
			accountSettings=new JLabel("Account Settings");
			accountSettings.setFont(new Font("Arial",Font.BOLD,32));
			accountSettings.setBorder(new EmptyBorder(0,500,0,0));
	        deleteAcc=new JButton("Delete account");
	        deleteAcc.addActionListener(deleteAccountStateChange);
	        merge=new JButton("Merge two accounts");
	        merge.addActionListener(mergeAccountStateChange);
	        changePassword=new JButton("Change password");
	        changePassword.addActionListener(passwordStateChange);
	        toSettings=new JButton("Back to settings");
	        toSettings.addActionListener(settingsStateChange);
	        holder.add(accountSettings);
	        holder.add(deleteAcc);
	        holder.add(merge);
	        holder.add(changePassword);	    
	        holder.add(toSettings);
	        topMost.add(holder);
	        settingsFrame.add(topMost);

		}
	};
	
	
	//to go back to settings
	ActionListener settingsStateChange=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			topMost.removeAll();
			topMost.revalidate();
			topMost.repaint();
			topMost.setLayout(new GridLayout(5,3));
			topMost.add(mainPage);
			topMost.add(button1);			
		}
	};	
	
	
	
	//creates a page for account merging
	ActionListener mergeAccountStateChange=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			topMost.removeAll();
			topMost.revalidate();
			topMost.repaint();
			
			
			mergePage=new JPanel();
			mergePage.setLayout(new GridLayout(10,1));
			
			JPanel header=new JPanel();
			JLabel mergeLabel=new JLabel("Account Merging Page");
			JButton goBack=new JButton("Go back");
			goBack.addActionListener(AccDirect);
			
			goBack.setPreferredSize(new Dimension(200,40));
			mergeLabel.setFont(new Font("Arial",Font.BOLD,32));
			mergeLabel.setBorder(BorderFactory.createEmptyBorder(0,275,0,350));
			
			
			header.add(goBack);
			header.add(mergeLabel);
			mergePage.add(header);
			
			
			JPanel currAcc=new JPanel();
			JPanel password1=new JPanel();
			JPanel username2=new JPanel();
			JPanel password2=new JPanel();
			JPanel firstName=new JPanel();
			JPanel lastName=new JPanel();
			JPanel newuser=new JPanel();
			JPanel newpassword=new JPanel();
			
			
			JLabel currUser=new JLabel("Current Account: "+User.getLoginAs());
			currUser.setFont(new Font("Arial",Font.PLAIN,18));

			currAcc.add(currUser);
			
			
			JLabel passwordText=new JLabel("Please enter this accounts password:");
			passwordText.setFont(new Font("Arial",Font.PLAIN,18));
			passwordInput=new JPasswordField();
			passwordInput.setPreferredSize(new Dimension(400,40));
			password1.add(passwordText);
			password1.add(passwordInput);
			
			JLabel username2Text=new JLabel("Please enter another accounts username:");
			username2Text.setFont(new Font("Arial",Font.PLAIN,18));
			username2Input=new JTextField();
			username2Input.setPreferredSize(new Dimension(400,40));

			username2.add(username2Text);
			username2.add(username2Input);
			
			JLabel password2Text=new JLabel("Please enter this accounts password:");
			password2Text.setFont(new Font("Arial",Font.PLAIN,18));
			password2Input=new JPasswordField();
			password2Input.setPreferredSize(new Dimension(400,40));
			password2.add(password2Text);
			password2.add(password2Input);
			
			JLabel newFirstNameLabel=new JLabel("What is the new first name: ");
			newFirstNameLabel.setFont(new Font("Arial",Font.PLAIN,18));
			newFirstName=new JTextField();
			newFirstName.setPreferredSize(new Dimension(400,40));
			firstName.add(newFirstNameLabel);
			firstName.add(newFirstName);
			
			
			JLabel newLastNameLabel=new JLabel("What is the new last name: ");
			newLastNameLabel.setFont(new Font("Arial",Font.PLAIN,18));
			newLastName=new JTextField();
			newLastName.setPreferredSize(new Dimension(400,40));
			lastName.add(newLastNameLabel);
			lastName.add(newLastName);

			
			JLabel newUser=new JLabel("What is the new username: ");
			newUser.setFont(new Font("Arial",Font.PLAIN,18));
			newUserInput=new JTextField();
			newUserInput.setPreferredSize(new Dimension(400,40));
			newuser.add(newUser);
			newuser.add(newUserInput);
			
			JLabel newPassword=new JLabel("What is the new password: "); 
			newPassword.setFont(new Font("Arial",Font.PLAIN,18));
			newPasswordInput=new JPasswordField();
			newPasswordInput.setPreferredSize(new Dimension(400,40));
			newpassword.add(newPassword);
			newpassword.add(newPasswordInput);
			
			JButton submit=new JButton("Submit Account Merge");
			submit.setPreferredSize(new Dimension(200,50));
			submit.addActionListener(mergeAccounts);
			JPanel submitButton=new JPanel();
			submitButton.add(submit);
			
			mergePage.add(currAcc);
			mergePage.add(password1);
			mergePage.add(username2);
			mergePage.add(password2);
			mergePage.add(firstName);
			mergePage.add(lastName);
			mergePage.add(newuser);
			mergePage.add(newpassword);
			mergePage.add(submitButton);
			
			topMost.add(mergePage);
		}
	};
	
	
	ActionListener mergeAccounts=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			boolean valid=true;
			if(!DBUtil.validateUser(User.getLoginAs(), passwordInput.getText()))
			{ 
				JOptionPane.showMessageDialog(settingsFrame, "This is the wrong password for the first user");
				valid=false;
			}
			if(!DBUtil.validateUser(username2Input.getText(), password2Input.getText()))
			{
				JOptionPane.showMessageDialog(settingsFrame, "This is the wrong password for the second user");
				valid=false;

			}
			if(!DBUtil.checkUser(newUserInput.getText()))
			{ 
				JOptionPane.showMessageDialog(settingsFrame, "This username already exists");
				valid=false;
			}
			if(valid)
			{
				DBUtil.joinUserAccs(User.getLoginAs(),username2Input.getText(),newFirstName.getText(), newLastName.getText(),newUserInput.getText(), newPasswordInput.getText(), "Personal");				
				User.setLoginAs(newUserInput.getText());
				settingsFrame.dispose();
				redirect=new NavigatorPage();	
			}
		}
	};
	
	
	//creates a page for password changing
	ActionListener passwordStateChange=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			topMost.removeAll();
			topMost.revalidate();
			topMost.repaint();
			
			JPanel deletePage=new JPanel(new GridLayout(6,1));
			
			JPanel header=new JPanel();
			
			JButton goBack=new JButton("Go back");
			goBack.addActionListener(AccDirect);
			goBack.setPreferredSize(new Dimension(200,40));
			
			JLabel changePassword=new JLabel("Change Password");
			changePassword.setFont(new Font("Arial",Font.BOLD,32));
			changePassword.setBorder(BorderFactory.createEmptyBorder(0,250,0,350));
			
			header.add(goBack);
			header.add(changePassword);
			
			JPanel currAcc=new JPanel();
			JLabel currUser=new JLabel("Current Account: "+User.getLoginAs());
			currUser.setFont(new Font("Arial",Font.PLAIN,18));
			currAcc.add(currUser);
			
			JPanel oldPasswordPanel=new JPanel();
			JLabel oldPassword=new JLabel("What is the current password: "); 
			oldPassword.setFont(new Font("Arial",Font.PLAIN,18));
			oldPasswordInput=new JTextField();
			oldPasswordInput.setPreferredSize(new Dimension(400,40));
			oldPasswordPanel.add(oldPassword);
			oldPasswordPanel.add(oldPasswordInput);
			
			JPanel newPasswordPanel=new JPanel();
			JLabel newPassword=new JLabel("What is the new password: "); 
			newPassword.setFont(new Font("Arial",Font.PLAIN,18));
			newPasswordInput=new JPasswordField();
			newPasswordInput.setPreferredSize(new Dimension(400,40));
			newPasswordPanel.add(newPassword);
			newPasswordPanel.add(newPasswordInput);
			
			JButton changePasswordSubmission=new JButton("Change Password");
			changePasswordSubmission.setPreferredSize(new Dimension(400,100));
			changePasswordSubmission.addActionListener(changePasswordDirect);
			JPanel changePasswordPanel=new JPanel();
			changePasswordPanel.add(changePasswordSubmission); 
			

			deletePage.add(header);
			deletePage.add(currAcc);
			deletePage.add(oldPasswordPanel);
			deletePage.add(newPasswordPanel);
			deletePage.add(changePasswordPanel);

			topMost.add(deletePage);
		}
	};
	
	//creates a page to delete an account 
	ActionListener changePasswordDirect=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			boolean checker;
			checker=DBUtil.changePW(User.getLoginAs(), oldPasswordInput.getText(), newPasswordInput.getText());
			if(checker)
			{
				JOptionPane.showMessageDialog(settingsFrame, "Password Successfully Changed");
			}
			else
			{
				JOptionPane.showMessageDialog(settingsFrame, "You've entered your old password wrong");
			}
		}
	};
	
	//creates a page to delete an account 
	ActionListener deleteAccountStateChange=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			topMost.removeAll();
			topMost.revalidate();
			topMost.repaint();
			deleteAccount=new JPanel();
			deleteAccount.setLayout(new GridLayout(4,1));
			JPanel header=new JPanel();
			
			JButton goBack=new JButton("Go back");
			goBack.addActionListener(AccDirect);
			goBack.setPreferredSize(new Dimension(200,40));
			
			JLabel deleteLabel=new JLabel("Delete Account");
			deleteLabel.setFont(new Font("Arial",Font.BOLD,32));
			deleteLabel.setBorder(BorderFactory.createEmptyBorder(0,325,0,500));
			
			header.add(goBack);
			header.add(deleteLabel);

			JPanel password1=new JPanel();
			JPanel currAcc=new JPanel();
			
			JLabel currUser=new JLabel("Current Account: "+User.getLoginAs());
			currUser.setFont(new Font("Arial",Font.PLAIN,18));
			currAcc.add(currUser);
			
			JLabel passwordText=new JLabel("Please enter this accounts password:");
			passwordText.setFont(new Font("Arial",Font.PLAIN,18));
			passwordInput=new JPasswordField();
			passwordInput.setPreferredSize(new Dimension(400,40));
			
			password1.add(passwordText);
			password1.add(passwordInput);
			
			JButton deleteAcc=new JButton("Delete your account");
			JPanel deleteButton=new JPanel();
			deleteAcc.setPreferredSize(new Dimension(400,100));
			deleteAcc.addActionListener(backToLogin);
			deleteButton.add(deleteAcc);
			
			
			deleteAccount.add(header);
			deleteAccount.add(currAcc);
			deleteAccount.add(password1);
			deleteAccount.add(deleteButton);

			
			topMost.add(deleteAccount);
		}
	};

	
	ActionListener backToLogin=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			boolean ex; 
			ex=DBUtil.deleteUser(User.getLoginAs(), passwordInput.getText());
			if(!ex)
			{ 
				JOptionPane.showMessageDialog(settingsFrame, "Password is wrong");
			}
			else
			{ 
				main=new LoginPage();
				settingsFrame.dispose();
				JOptionPane.showMessageDialog(main.mainFrame, "Account \'"+User.getLoginAs()+"\' Deleted");
				User.setLoginAs(null);
			}
		}
	};
	
	
	
	ActionListener NavDirect=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			settingsFrame.dispose();
			redirect=new NavigatorPage();
		}
	};
	
	
	public Settings()
	{ 
		settingsFrame=new JFrame(); 
		settingsFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		settingsFrame.setLocationRelativeTo(null);
		mainPage=new JPanel(); 
		titleMain=new JLabel("Settings");
		titleMain.setFont(new Font("Arial",Font.BOLD,32));
		mainPage.add(titleMain);
		
		button1=new JPanel();
		toAcc=new JButton("Account Settings");
		toNav=new JButton("Back to Nav Page");
		toNav.setPreferredSize(new Dimension(500,100));
		toAcc.setPreferredSize(new Dimension(500,100));
		
		
		toAcc.addActionListener(AccDirect);
		toNav.addActionListener(NavDirect);
		button1.add(toNav);
		button1.add(toAcc);

		button2 = new JPanel();
		themeLabel = new JLabel("Theme:");
		String[] themeList = {"Light", "Dark", "macOS Light", "macOS Dark", "IntelliJ Light", "Darcula"};
		themes = new JComboBox<>(themeList);
		themes.setSelectedIndex(curIndex);

		themes.addActionListener(themeListener);
		button2.add(themeLabel);
		button2.add(themes);
			
		topMost=new JPanel();
		topMost.setLayout(new GridLayout(5,3,1,1));
		topMost.add(mainPage);
		topMost.add(button1);
		topMost.add(button2);
		
		settingsFrame.add(topMost);
		settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settingsFrame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		Settings bruh=new Settings();
	}
}
