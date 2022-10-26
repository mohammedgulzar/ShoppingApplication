package util;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class ResetText { //This class simply resets all of the textfields and GUI components by emptying the text or setting the default/null values
	
	public void resetAdminText(JTextField adminFNameTF, JTextField adminLNameTF, JTextField adminEmailAddressTF, 
			JTextField adminPhoneNumTF, JTextField adminUsernameTF, JTextField adminPasswordTF) {
		adminFNameTF.setText("");
		adminLNameTF.setText("");
		adminEmailAddressTF.setText("");
		adminPhoneNumTF.setText("");
		adminUsernameTF.setText("");
		adminPasswordTF.setText("");
	}
	
	
	
	public void resetAdditionalItemText(JTextField aFabricTF, JTextField aLengthTF, JTextField compositionTF, JTextField heightTF,JTextField sFabricTF,
			JTextField sLengthTF,JTextField thicknessTF, JTextField weightTF ) {
		
		aFabricTF.setText("");
		aLengthTF.setText("");
		compositionTF.setText("");
		heightTF.setText("");
		sFabricTF.setText("");
		sLengthTF.setText("");
		thicknessTF.setText("");
		weightTF.setText("");
	}
	
	public void resetDiscountText(JTextField percentageTF, JDateChooser startingDateChooser, JDateChooser endingDateChooser) {
		percentageTF.setText("");
		startingDateChooser.setDate(null);
		endingDateChooser.setDate(null);
	}
	
	//Resets all Textfields for Items
	public void resetItemText(JTextField aFabricTF, JTextField aLengthTF, JTextField compositionTF, JTextField costTF, JTextArea descriptionTA, JTextArea garmentCareTA,
			JTextField heightTF, JTextField itemNameTF, JComboBox itemTypeCB, JLabel lblImageDisplay, JTextField priceTF, JTextField quantityTF, JTextField sFabricTF, JTextField sLengthTF,
			JComboBox seasonTypeCB,JComboBox sizeCB,JComboBox supplierNameCB, JTextField thicknessTF, JTextField weightTF) {
		
		costTF.setText("");
		descriptionTA.setText("");
		garmentCareTA.setText("");
		itemNameTF.setText("");
		itemTypeCB.setSelectedIndex(0);
		priceTF.setText("");
		quantityTF.setText("");
		seasonTypeCB.setSelectedIndex(0);
		sizeCB.setSelectedIndex(0);
		supplierNameCB.setSelectedIndex(0);
		lblImageDisplay.setIcon(null);
		
		resetAdditionalItemText(aFabricTF, aLengthTF, compositionTF, heightTF, sFabricTF, sLengthTF, thicknessTF, weightTF);
	}
	
	public void resetPersonalDetailsText(JTextField fNameTF, JTextField lNameTF, JTextField emailTF, 
			JTextField phoneNumTF, JComboBox<String> cityComboBox, JTextField homeAddressTF, JRadioButton rdbtnCard, JRadioButton rdbtnCash) {
		fNameTF.setText("");
		lNameTF.setText("");
		emailTF.setText("");
		phoneNumTF.setText("");
		cityComboBox.setSelectedIndex(0);
		homeAddressTF.setText("");
		rdbtnCard.setSelected(false);
		rdbtnCash.setSelected(false);
	}
	
	
	
	//Resets all Textfields for Suppliers
	public void resetSupplierText(JTextField companyNameTF, JTextField emailAddress, JComboBox type) {
		companyNameTF.setText("");
		emailAddress.setText("");
		type.setSelectedIndex(0);
	}
}
