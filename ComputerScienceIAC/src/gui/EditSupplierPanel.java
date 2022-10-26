package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import entity.Supplier;
import menu.Menu;
import util.ObjectLoader;

public class EditSupplierPanel extends ModifySupplierPanel {
	
	private int supplierCount;
	
	private JButton btnPrevious;
	private JButton btnNext;
	private JLabel lblID;
	private JLabel lblIDNum;
	
	public EditSupplierPanel(Menu menu) { // not finished
		super(menu);
	}
	
	@Override
	public void initialize() {
		
		super.initialize();
		
		LinkedList<Supplier> supplierList = menu.getSupplierList();
		ObjectLoader loader = new ObjectLoader(menu, this);
		
		
		lblTitle.setText("Edit Supplier");
		lblTitle.setBounds(496, 20, 390, 80);
		
		lblID = new JLabel("ID:");
		lblID.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblID.setBounds(21, 126, 58, 32);
		add(lblID);
		
		lblIDNum = new JLabel();
		lblIDNum.setFont(new Font("Consolas", Font.PLAIN, 25));
		lblIDNum.setBounds(109, 126, 58, 32);
		add(lblIDNum);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(supplierCount > 0) {
					supplierCount--;
					loader.loadSupplier(supplierCount, supplierList);
					if(supplierCount < supplierList.size()) {
						btnNext.setEnabled(true);
					}
				}else {
					supplierCount = supplierList.size()-1;
					loader.loadSupplier(supplierCount, supplierList);
				}
			
			}
		});
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPrevious.setBounds(361, 20, 130, 59);
		add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(supplierCount == supplierList.size()-1 ) {
					supplierCount = 0;
					loader.loadSupplier(supplierCount, supplierList);
				}
				else {
					supplierCount++;
					loader.loadSupplier(supplierCount, supplierList);
				}}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNext.setBounds(896, 20, 130, 59);
		add(btnNext);
		
		JButton btnChange = new JButton("Apply Changes"); //fix
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					setVariables();
					
					if(notEmptyFields() && validCompanyName && validEmail) {
						int count = Integer.parseInt(lblIDNum.getText());
						Supplier supplier =  new Supplier(companyName,emailAddress,type);
						menu.updateSupplier(supplier, count);
						menu.getSupplierListPanel().refreshTable();
						JOptionPane.showMessageDialog(null, companyName + " has been successfully updated");
						menu.getAddItemPanel().updateSupplierCB();
						menu.getEditStockPanel().updateSupplierCB();
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
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChange.setBounds(449, 400, 138, 53); 
		add(btnChange);
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(btnReset, "Would you like to delete this admin from the list instead?");
				if(result == JOptionPane.YES_OPTION) {
					menu.removeSupplier(supplierCount);
					JOptionPane.showMessageDialog(null, "This supplier has been deleted");
					menu.getAddItemPanel().updateSupplierCB();
					
					if(supplierCount > 0 && supplierCount < supplierList.size()) {
						supplierCount--;
						loader.loadSupplier(supplierCount, supplierList);
					}
					else if(supplierCount == 0 && supplierList.size() == 0) {
						reset();
						menu.getSupplierListPanel().getAdminTabbedPane().setSelectedIndex(1);
						menu.getSupplierListPanel().getAdminTabbedPane().setEnabledAt(5,false);
					}
					menu.getSupplierListPanel().refreshTable();
				}
				else if(result == JOptionPane.NO_OPTION) {
					reset();
				}
			}
		});
		
		
	}
	
	public void setLblIDNum(String lblIDNum) {
		this.lblIDNum.setText(lblIDNum);
	}
	
	public void setSupplierCount(int count) {
		this.supplierCount = count;
	}
	
	public int getSupplierCount() {
		return supplierCount;
	}

}
