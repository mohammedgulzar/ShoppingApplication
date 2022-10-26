package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import menu.Menu;
import util.MailSender;
import util.ObjectChecker;
import util.ResetText;

public class ModifyAdminPanel extends JPanel {
	
	protected ResetText rt = new ResetText();
	protected Menu menu;
	
	//GUI components
	protected JLabel lblTitle;
	protected JTextField firstNameTF;
	protected JTextField phoneNumTF;
	protected JTextField lastNameTF;
	protected JTextField passwordTF;
	protected JTextField emailTF;
	protected JTextField usernameTF;
	protected JLabel lblEmailAddress;
	protected JLabel lblFName;
	protected JLabel lblLName;
	protected JLabel lblPassword;
	protected JLabel lblPhoneNum;
	protected JLabel lblUsername;
	protected JButton btnReset;
	protected ObjectChecker oChecker = new ObjectChecker();
	protected MailSender mailer = new MailSender();
	
	//These variables are used for adding and modifying Admin/ Account
	//They will collect the data from a given textfield and instantiate a Admin Object for saving or merging
	protected String fName; 
	protected String lName;
	protected String emailAddress;
	protected String phoneNum;
	protected String userName;
	protected String password;
	protected boolean validEmail;
	protected boolean validPassword;
	protected boolean validPhoneNum;
	protected boolean validFName;
	protected boolean validLName;
	protected boolean accountExist;
	
		
		
	public ModifyAdminPanel(Menu menu) {
		this.menu = menu;
		mailer.initialise();
	}
		
	public void initialize() {
		
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		lblTitle = new JLabel();
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(496, 20, 390, 80);
		add(lblTitle);
		
		//JTextFields
		firstNameTF = new JTextField();
		firstNameTF.setColumns(10);
		firstNameTF.setBounds(553, 139, 333, 35);
		add(firstNameTF);
		
		phoneNumTF = new JTextField();
		phoneNumTF.setColumns(10);
		phoneNumTF.setBounds(553, 276, 333, 35);
		add(phoneNumTF);
		
		lastNameTF = new JTextField();
		lastNameTF.setColumns(10);
		lastNameTF.setBounds(553, 188, 333, 35);
		add(lastNameTF);
		
		passwordTF = new JTextField();
		passwordTF.setColumns(10);
		passwordTF.setBounds(553, 373, 333, 35);
		add(passwordTF);
		
		emailTF = new JTextField();
		emailTF.setColumns(10);
		emailTF.setBounds(553, 233, 333, 35);
		add(emailTF);
		
		usernameTF = new JTextField();
		usernameTF.setColumns(10);
		usernameTF.setBounds(553, 323, 333, 35);
		add(usernameTF);
		
		//JLabels
		
		lblEmailAddress = new JLabel("Email Address:");
		lblEmailAddress.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblEmailAddress.setBounds(345, 234, 198, 32);
		add(lblEmailAddress);
		
		lblFName = new JLabel("First Name:");
		lblFName.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblFName.setBounds(393, 139, 163, 32);
		add(lblFName);
		
		lblLName = new JLabel("Last Name:");
		lblLName.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblLName.setBounds(403, 188, 140, 32);
		add(lblLName);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblPassword.setBounds(413, 374, 130, 32);
		add(lblPassword);
		
		lblPhoneNum = new JLabel("Phone Number:");
		lblPhoneNum.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblPhoneNum.setBounds(355, 276, 188, 32);
		add(lblPhoneNum);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblUsername.setBounds(413, 324, 130, 32);
		add(lblUsername);
		
		
		//JButtons 
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReset.setBounds(622, 625, 138, 53);
		add(btnReset);
		
	}
	
	//Setters
		public void setPasswordTF(String passwordTF) {
			this.passwordTF.setText(passwordTF);
		}

		public void setEmailTF(String emailTF) {
			this.emailTF.setText(emailTF);
		}

		public void setFirstNameTF(String firstNameTF) {
			this.firstNameTF.setText(firstNameTF);
		}

		public void setPhoneNumTF(String phoneNumTF) {
			this.phoneNumTF.setText(phoneNumTF);
		}

		public void setLastNameTF(String lastNameTF) {
			this.lastNameTF.setText(lastNameTF);
		}

		public void setUsernameTF(String usernameTF) {
			this.usernameTF.setText(usernameTF);
		}
	
	public boolean notEmptyFields() {//This method checks whether the inputted fields are not empty
		if(fName.isEmpty()||lName.isEmpty()||emailAddress.isEmpty()||userName.isEmpty()|| password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fill out all the fields or fill out everything besides the phone number", "Empty Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;		
	}
	
	//This function check whether username and emailAddress of the new Admin already exist
	public boolean existingAccount() {
		return oChecker.existingUsername(userName) || oChecker.existingAdminEmail(emailAddress) ;
	}
	
	public boolean validDatas() {
		if(!(validFName)) { //This regular expression ensures that the first and last name does not contain any numerical values.
			JOptionPane.showMessageDialog(null, "Admin first name must not contain any numbers!", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(validLName)) {
			JOptionPane.showMessageDialog(null, "Admin last name must not contain any numbers!", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(validEmail)){
			JOptionPane.showMessageDialog(null,"Admin email address must contain @ and characters after as well","Invalid Email Address" , JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(validPassword)) {
			JOptionPane.showMessageDialog(null,"Admin password must 8 characters long","Invalid Password" , JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean validPhoneNum() {
		if(!(validPhoneNum)) {
			JOptionPane.showMessageDialog(null,"Phone number must contain only 10 digits.","Invalid Phone Number" , JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	
	public void reset() {//This method resets all of the textfields by emptying the text inside.
		rt.resetAdminText(firstNameTF, lastNameTF, emailTF, phoneNumTF , usernameTF, passwordTF);
	}
	
	
	
	//Initialize all admin variables 
	public void setVariables() {
		fName = firstNameTF.getText().trim(); 
		lName = lastNameTF.getText().trim();
		emailAddress = emailTF.getText().trim();
		phoneNum = phoneNumTF.getText().trim();
		userName = usernameTF.getText().trim();
		password = passwordTF.getText().trim();
		validEmail = oChecker.verifyEmail(emailAddress);
		validPassword = oChecker.verifyPassword(password);
		validPhoneNum = oChecker.verifyPhoneNumber(phoneNum);
		validFName = oChecker.validName(fName);
		validLName = oChecker.validName(lName);
	}
	
	
	
}
		
