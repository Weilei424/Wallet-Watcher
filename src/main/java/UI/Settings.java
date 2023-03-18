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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Settings { 
	JFrame settingsFrame; 
	navigatorPage redirect;
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
	JPanel changeUserPassowrd; 
	JPanel deleteAccount;
	
	
	
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
			JLabel mergeLabel=new JLabel("Account Merging Page");
			mergeLabel.setFont(new Font("Arial",Font.BOLD,32));
			
			mergePage.add(mergeLabel);
			topMost.add(mergePage);
			
		}
	};
	
	//creates a page for password changing
	ActionListener passwordStateChange=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//creates a page to delete an account 
	ActionListener deleteAccountStateChange=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	
	
	ActionListener NavDirect=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			settingsFrame.dispose();
			redirect=new navigatorPage();
		}
	};
	
	
	public Settings()
	{ 
		settingsFrame=new JFrame(); 
		settingsFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
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
			
		topMost=new JPanel();
		topMost.setLayout(new GridLayout(5,3,1,1));
		topMost.add(mainPage);
		topMost.add(button1);
		
		settingsFrame.add(topMost);
		settingsFrame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		Settings bruh=new Settings();
	}
}
