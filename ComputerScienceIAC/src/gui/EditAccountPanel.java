package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import entity.Admin;
import menu.Menu;

public class EditAccountPanel extends ModifyAdminPanel {
	private boolean isManager; 
	private Admin admin;
	
	public EditAccountPanel(Menu menu, Admin admin) {
		super(menu);
		this.admin = admin;
		this.isManager = false;
	}
	
	public EditAccountPanel(Menu menu, boolean isManager) {//This constructor can only be accessed by manager 
		super(menu);
		this.isManager = isManager;
	}
	
	@Override
	public void initialize() {
		
		super.initialize();

		//JLabel
		lblTitle.setText("Account Settings");
		
		//JButtons
		JButton btnChange = new JButton("Apply");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					setVariables();
					if (notEmptyFields() && validDatas()) { //Checking if user's input is valid
						if(!(phoneNum.isEmpty()) && validPhoneNum()) {
							Admin updatedAccount = new Admin(fName,lName,emailAddress, phoneNum,userName,password);
							if(isManager) {
								menu.updateAdmin(updatedAccount,0);//This function will update the manager in the database
							}
							else {
								menu.updateAdmin(updatedAccount, admin.getDisplayID());//This function will update the existing admin in the database
							}
						}
						else if(phoneNum.isEmpty()) {
							Admin updatedAccount =  new Admin(fName,lName,emailAddress,userName,password);
							if(isManager) {
								menu.updateAdmin(updatedAccount,0);
							}
							else {
								menu.updateAdmin(updatedAccount, admin.getDisplayID());
							}
						}
						String name = fName + " " + lName;
						JOptionPane.showMessageDialog(null, name +" has been successfully updated");
					}
						
				}
				catch(NullPointerException e2) {
					JOptionPane.showMessageDialog(null, "Some data are missing. Please double check", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChange.setBounds(458, 625, 138, 53);
		add(btnChange);
		
	}
	
	//Setters
	public void setAccountEmailAddressTF(String emailTF) {
		this.emailTF.setText(emailTF);
	}

	public void setAccountFNameTF(String firstNameTF) {
		this.firstNameTF.setText(firstNameTF);;
	}

	public void setAccountLNameTF(String lastNameTF) {
		this.lastNameTF.setText(lastNameTF);
	}

	public void setAccountPasswordTF(String passwordTF) {
		this.passwordTF.setText(passwordTF); 
	}

	public void setAccountPhoneNumberTF(String phoneNumTF) {
		this.phoneNumTF.setText(phoneNumTF);
	}

	public void setAccountUsernameTF(String usernameTF) {
		this.usernameTF.setText(usernameTF);
	}



}
