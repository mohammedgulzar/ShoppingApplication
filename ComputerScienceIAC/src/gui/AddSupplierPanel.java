package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import entity.Supplier;
import menu.Menu;

public class AddSupplierPanel extends ModifySupplierPanel {

	public AddSupplierPanel(Menu menu) {
		super(menu);
	}
	
	@Override
	public void initialize() {
		
		super.initialize();
		lblTitle.setText("Add New Supplier");

		//Buttons to save and reset supplier textfields
		JButton btnSaveSupplier = new JButton("Save Supplier");
		btnSaveSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					setVariables();
					
					if(notEmptyFields() && validCompanyName && validEmail && !(existingSupplier())) {
						Supplier supplier =  new Supplier(companyName,emailAddress,type);
						menu.addSupplier(supplier);//This ensures that the supplier is added to the database
						menu.getAddItemPanel().updateSupplierCB();
						menu.getSupplierListPanel().refreshTable();
						JOptionPane.showMessageDialog(null, companyName +" has been successfully added to the Supplier List");
						reset();
					}
					else { //error message for existing email
						errorDisplay(!(validCompanyName), !(validEmail));
					}
				}
				catch(NullPointerException e2) {
					JOptionPane.showMessageDialog(null, "There is null values", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		btnSaveSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveSupplier.setBounds(449, 400, 138, 53);
		add(btnSaveSupplier);
		
		btnReset.setBounds(640, 400, 138, 53);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rt.resetSupplierText(companyNameTF, supplierEmailAddressTF, businessTypeCB);
			}
		});
		
		}
}
