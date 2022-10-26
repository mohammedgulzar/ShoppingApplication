package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import menu.Menu;
import util.ObjectChecker;
import util.ResetText;

public class ModifySupplierPanel extends JPanel {
	
	//GUI Components
	protected JLabel lblTitle;
	protected JTextField supplierEmailAddressTF;
	protected JTextField companyNameTF;
	protected JComboBox businessTypeCB;
	protected JButton btnReset;
	
	//
	protected ResetText rt = new ResetText();
	protected ObjectChecker oChecker = new ObjectChecker();
	protected Menu menu;
	
	//These variables are used for adding and modifying Supplier
	//They will collect the data from a given textfield and instantiate a Supplier Object for saving or merging
	protected String companyName; 
	protected String emailAddress;
	protected String type;
	protected boolean validEmail;
	protected boolean validCompanyName;
	
	public ModifySupplierPanel(Menu menu) {
		this.menu = menu;
	}
	
	public void initialize() {

		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		//Labels
		
		lblTitle = new JLabel();
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(494, 0, 390, 80);
		add(lblTitle);
		
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblCompanyName.setBounds(398, 180, 201, 32);
		add(lblCompanyName);
		
		JLabel lblSupplierEmailAddress = new JLabel("Email Address:");
		lblSupplierEmailAddress.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblSupplierEmailAddress.setBounds(382, 306, 198, 32);
		add(lblSupplierEmailAddress);
		
		JLabel lblBusinessType = new JLabel("Business Type:");
		lblBusinessType.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblBusinessType.setBounds(382, 247, 201, 32);
		add(lblBusinessType);
		
		//Textfields
		
		supplierEmailAddressTF = new JTextField();
		supplierEmailAddressTF.setColumns(10);
		supplierEmailAddressTF.setBounds(595, 305, 333, 35);
		add(supplierEmailAddressTF);
		
		companyNameTF = new JTextField();
		companyNameTF.setColumns(10);
		companyNameTF.setBounds(596, 180, 333, 35);
		add(companyNameTF);
		
		//Combobox and Button
		
		businessTypeCB = new JComboBox(new String[] {"Local", "International"});
		businessTypeCB.setFont(new Font("Tahoma", Font.PLAIN, 15));
		businessTypeCB.setBounds(597, 245, 119, 32);
		add(businessTypeCB);
		
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReset.setBounds(640, 400, 138, 53);
		add(btnReset);
	}
	
	//Setters
	public void setSupplierEmailAddress(String supplierEmailAddress) {
		this.supplierEmailAddressTF.setText(supplierEmailAddress);
	}

	public void setCompanyName(String companyName) {
		this.companyNameTF.setText(companyName);
	}

	public void setBusinessType(String businessType) {
		switch(businessType) {
			case "Local":
				this.businessTypeCB.setSelectedIndex(0);
				break;
			case "International":
				this.businessTypeCB.setSelectedIndex(1);
				break;		
		}
	}
	
	
	public boolean notEmptyFields() {//This method checks whether the inputted fields are not empty
		if(companyName.isEmpty()||emailAddress.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fill out all the fields", "Empty Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;		
	}
	//This function check whether username and emailAddress of the new Supplier already exist
	public boolean existingSupplier() {
		return oChecker.existingCompanyName(companyName) || oChecker.existingSupplierEmail(emailAddress);
	}
	
	public void errorDisplay(boolean invalidCompanyName, boolean invalidEmail) {
		if(invalidCompanyName) {
			JOptionPane.showMessageDialog(null, "The company name must contain letters only!", "Invalid Name", JOptionPane.ERROR_MESSAGE);
		}
		else if(invalidEmail){
			JOptionPane.showMessageDialog(null,"Your email address must contain @ and characters after as well","Invalid Email Address" , JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void reset() {
		rt.resetSupplierText(companyNameTF, supplierEmailAddressTF, businessTypeCB);
	}
	
	public void setVariables() {
		companyName = companyNameTF.getText().trim(); 
		emailAddress = supplierEmailAddressTF.getText().trim();
		type = businessTypeCB.getSelectedItem().toString();
		validEmail = oChecker.verifyEmail(emailAddress);
		validCompanyName = oChecker.validName(companyName);
		
	}
}
