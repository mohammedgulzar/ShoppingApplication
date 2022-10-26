package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import entity.Admin;
import menu.Menu;


public class AddAdminPanel extends ModifyAdminPanel {
	
	//GUI components
	public AddAdminPanel(Menu menu)  {
		super(menu);
	}
	
	public void initialize() {
		super.initialize();
		
		
		lblTitle.setText("Add Admin");
		
		//JButtons
		
		JButton btnSaveAdmin = new JButton("Save Admin"); //Saving New Admin 
		btnSaveAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVariables();
				
				if (notEmptyFields() && validDatas() && !existingAccount()) {//validation checking on the user input on creating a new Admin
					if(!(phoneNum.isEmpty()) && validPhoneNum()) {//User have the choice to include phone number or not
						Admin admin =  new Admin(fName,lName,emailAddress,phoneNum,userName,password);
						menu.addAdmin(admin);//This ensures that the new admin is added to the database.
						String name = fName + " " + lName;
						JOptionPane.showMessageDialog(null, name +" has been successfully added to the Admin List");
						reset();//Once finished saving the new admin to the shopping database, the field are forced to be emptied
						mailer.welcomeNewAdmin(admin); //emails the newly created admin
						
					}
					
					else if(phoneNum.isEmpty()){
						Admin admin =  new Admin(fName,lName,emailAddress,userName,password);
						menu.addAdmin(admin);//This ensures that the new admin is added to the database.
						String name = fName + " " + lName;
						JOptionPane.showMessageDialog(null, name +" has been successfully added to the Admin List");
						reset();//Once finished saving the new admin to the shopping database, the field are forced to be emptied
						mailer.welcomeNewAdmin(admin); //emails the newly created admin
					}
					menu.getAdminListPanel().resetID(menu.getAdminList());
					menu.getAdminListPanel().refreshTable();
					
					JOptionPane.showMessageDialog(null, "An email has been sent to the new admin");	
				}
				
			}
			
		});
		btnSaveAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveAdmin.setBounds(458, 625, 138, 53);
		add(btnSaveAdmin);
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		
	}
}
