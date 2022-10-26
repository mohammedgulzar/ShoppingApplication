package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import javax.swing.SwingConstants;

import entity.*;
import menu.AdminMenu;
import menu.ManagerMenu;
import menu.Menu;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class LogIn {
	//Declaring variables
	private JFrame frame;
	private Menu menu = new Menu();
	private LinkedHashMap<String, String> accountHashMap; //The purpose of this LinkedHashMap is used to check whether the user inputs 
	//exist in the admin list by creating a dynamic key-value pair for admin username and password.
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn();
					window.frame.setVisible(true); //this ensure that the Login GUI appears and visible for users 
				} catch (Exception e) { 
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public LogIn() {
		initialize();//This function will make GUI appear on the screen for which the user can simply interact.
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//Instantiate a frame object and adjust its settings
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("Moko Shopping Application");
		frame.setBounds(50, 0, 862, 550);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//This LinkedHashMap acts like a dictionary (key = username, value = password) 
		//This LinkedHashMap will be used to compare the user's inputs
		accountHashMap = new LinkedHashMap<String, String>();//Instantiating the HashMap 
		initialiseAccountTable();//this function will populate the accountHashMap with username and passwords from the admin list
		
		//JLabels
		JLabel lblLogin = new JLabel("Login Page");
		lblLogin.setBackground(Color.WHITE);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 35));
		lblLogin.setBounds(338, 36, 188, 80);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblLogo = new JLabel();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(LogIn.class.getResource("/img/Moko.png")).getImage().getScaledInstance(200, 76, Image.SCALE_SMOOTH));
		lblLogo.setIcon(imageIcon);
		lblLogo.setBounds(38, 36, 200, 88);
		frame.getContentPane().add(lblLogo);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Segoe UI Symbol", Font.BOLD, 25));//Adjustments
		lblUsername.setBounds(179, 189, 188, 71);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI Symbol", Font.BOLD, 25));//Adjustments
		lblPassword.setBounds(179, 248, 188, 80);
		frame.getContentPane().add(lblPassword);
		
		//JText
		JTextArea usernameTF = new JTextArea();
		usernameTF.setBackground(Color.LIGHT_GRAY);//Adjustments
		usernameTF.setForeground(new Color(0, 0, 0));
		usernameTF.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 17));
		usernameTF.setBounds(355, 209, 400, 33);
		frame.getContentPane().add(usernameTF);
		
		JPasswordField passwordTF = new JPasswordField(); //JPasswordField will hide the user's input on real time while the user is typing
		passwordTF.setBackground(Color.LIGHT_GRAY); //Adjustments
		passwordTF.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 17));
		passwordTF.setBounds(355, 270, 400, 33);
		frame.getContentPane().add(passwordTF);
		
		//JComboBox & Button
		JComboBox roleSelection = new JComboBox(new String[] {"Admin", "Manager"}); //User have the two choices. 
		roleSelection.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));//Either they login as an Admin or login a Manager
		roleSelection.setBounds(273, 146, 131, 33);
		frame.getContentPane().add(roleSelection);
		
		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == loginButton) { 
					String selectedValue = roleSelection.getSelectedItem().toString(); //Gets the role that the user wants to login as.
					String username = usernameTF.getText().strip();//Strip function is used to remove any unnecessary spaces
					String password =  new String(passwordTF.getPassword()).strip();
					boolean invalid = true;
					
					switch(selectedValue) { 
						case "Admin": //Logging in as an Admin
							int count = 1; //The count variable is set to one since the manager account is located at index 0 of the adminList
							for (String key: accountHashMap.keySet()) { //This line iterates through the LinkedHashMap
								if(username.equals(key)&& password.equals(accountHashMap.get(key))) { //Compare the user and password with the key-value pair
									Admin admin = menu.getAdminList().get(count); 
									String message = "Welcome " + admin.getFirstName() + " " + admin.getLastName();
									JOptionPane.showMessageDialog(null,message, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
									AdminMenu adminMenu = new AdminMenu(admin);	//once it instantiates, it will be automatically be prompted to the user's screen
									invalid = false;
									frame.dispose();//This will close the login frame
								}
								count++;
							}
							if(invalid) {//if the user input does not exist in the LinkedHashTable
								JOptionPane.showMessageDialog(null, "This account does not exist", "Login Failed", JOptionPane.ERROR_MESSAGE);
								usernameTF.setText("");
								passwordTF.setText("");
							}
							break;
							
						case "Manager":	
							Admin manager = menu.getAdminList().get(0);
							if(username.equals(manager.getUserName())&& password.equals(manager.getPassword())){
								String message = "Welcome " + manager.getFirstName() + " " + manager.getLastName();
								JOptionPane.showMessageDialog(null,message, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
								ManagerMenu managerMenu = new ManagerMenu(manager); //This display the manager version of the application.
								frame.dispose();
								invalid = false;
							}
							if(invalid) {
								JOptionPane.showMessageDialog(null, "This account does not exist", "Login Failed", JOptionPane.ERROR_MESSAGE);
								usernameTF.setText("");
								passwordTF.setText("");
							}
							break;	
					}
				}	
			}
		});
		
		loginButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		loginButton.setBounds(358, 369, 114, 45);
		frame.getContentPane().add(loginButton);
		
		JButton btnClose = new JButton("Close Application"); //When the button is pressed, the application closes
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  
				JOptionPane.showMessageDialog(null, "Closing the Application....", "Application closing", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		});
		btnClose.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		btnClose.setBounds(338, 460, 160, 27);
		frame.getContentPane().add(btnClose);
	}

	private void initialiseAccountTable() { //This populates the accountTable with the use of adminList (Reminder, this list does not include the manager object)
		if(menu.getAdminList().size() > 1) {//the size 
			for (int i = 1; i < menu.getAdminList().size(); i++) {//This for loop starts with the index of 1 because we can skip over the manager object. 
				Admin admin = menu.getAdminList().get(i);//This reduces interference between the admins and manager account.
				accountHashMap.put(admin.getUserName(), admin.getPassword());
			}
		}
		
	}

	//Getter
	public JFrame getFrame() {
		return frame;
	}
}
