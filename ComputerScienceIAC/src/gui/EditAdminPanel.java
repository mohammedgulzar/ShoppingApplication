package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import entity.Admin;
import menu.Menu;
import util.ObjectLoader;


public class EditAdminPanel extends ModifyAdminPanel {
	
	private int adminCount;
	
	//GUI components
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnChange;
	private JLabel lblID;
	private JLabel lblIDNum;
	

	public EditAdminPanel(Menu menu) {
		super(menu);
	}
	
	@Override
	public void initialize() {
		
		super.initialize();

		//LinkedList<Admin> adminList = menu.getAdminList();
		ObjectLoader loader = new ObjectLoader(menu, this);
		
		//JLabels
		
		lblTitle.setText("Edit Admin");
		
		lblID = new JLabel("ID:");
		lblID.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblID.setBounds(21, 126, 58, 32);
		add(lblID);
		
		lblIDNum = new JLabel(); 
		lblIDNum.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblIDNum.setBounds(109, 126, 58, 32);
		add(lblIDNum);
		
		//Buttons
		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				if(adminCount > 1) {//if the adminCount is greater than 1, you can load the data of the previous admin by subtracting adminCount by 1
					adminCount--;
					loader.loadAdmin(adminCount, menu.getAdminList());
					if(adminCount < menu.getAdminList().size()) {
						btnNext.setEnabled(true);
					}
				}else { //Otherwise, it will cycle around the list by change the adminCount to the last index of the adminList
					adminCount = menu.getAdminList().size()-1;
					loader.loadAdmin(adminCount, menu.getAdminList());
				}
			}
		});
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPrevious.setBounds(361, 20, 130, 59);
		add(btnPrevious);
		
		btnNext = new JButton("Next");//if the adminCount is greater than 1, you can load the data of the next admin by adding adminCount by 1
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				
				if(adminCount == menu.getAdminList().size()-1 ) {
					adminCount = 1;
					loader.loadAdmin(adminCount, menu.getAdminList());
				}
				else { //Otherwise, it will cycle around the list by change the adminCount to index 1 of the adminList
					adminCount++;
					loader.loadAdmin(adminCount, menu.getAdminList());
				}}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNext.setBounds(896, 20, 130, 59);
		add(btnNext);
		
		btnChange = new JButton("Apply"); 
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(adminCount == 0) {
						adminCount++;
					}
					setVariables();
					
					if (notEmptyFields() && validDatas()) {// checking if user input is valid
						int count = Integer.parseInt(lblIDNum.getText());
						if(!(phoneNum.isEmpty()) && validPhoneNum()) {
							Admin admin =  new Admin(fName,lName,emailAddress, phoneNum,userName,password);
							menu.updateAdmin(admin,count);//This function will update the existing admin in the database
							menu.getAdminListPanel().refreshTable();
						}
						else if(phoneNum.isEmpty()) {
							Admin admin =  new Admin(fName,lName,emailAddress,userName,password);
							menu.updateAdmin(admin,count); 
							menu.getAdminListPanel().refreshTable();
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
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(btnReset, "Would you like to delete this admin from the list instead?");
				switch(result) {
					case JOptionPane.YES_OPTION:
						Admin admin = menu.getAdminList().get(adminCount);
						menu.removeAdmin(adminCount);//This function will remove the existing admin from the database
						mailer.revokeAdmin(admin);//An email will be sent to the deleted admin about the deactivation of their account
						JOptionPane.showMessageDialog(null, admin.getFirstName() + " will receive a email about account deletion","Deletion Success",JOptionPane.INFORMATION_MESSAGE);
						
						if(adminCount == 1 &&  menu.getAdminList().size() == 1) {//This means that after deleting the admin, there is nothing left
							reset();
							menu.getAdminListPanel().getAdminTabbedPane().setSelectedIndex(0);
							menu.getAdminListPanel().getAdminTabbedPane().setEnabledAt(4,false);
							
						}
						if(adminCount > 1 && adminCount < menu.getAdminList().size()+1) { // This will load the previous admin 
							adminCount--;
							loader.loadAdmin(adminCount, menu.getAdminList());
							
						}
						menu.getAdminListPanel().refreshTable();
						break;
						
					case JOptionPane.NO_OPTION:
						reset();
						break;		
				}
			}
		});
	}
	
	//Setters & Getters
	public void setLblIDNum(String lblIDNum) {
		this.lblIDNum.setText(lblIDNum);
	}
	
	public void setAdminCount(int count) {
		this.adminCount = count;
	}
	
	public int getAdminCount() {
		return adminCount;
	}

}
