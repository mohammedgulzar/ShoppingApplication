package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import entity.Abaya;
import entity.Cardigan;
import entity.Item;
import entity.Scarf;
import entity.Supplier;
import menu.Menu;
import util.ObjectLoader;
import util.ResetText;

public class EditStockPanel extends ModifyStockPanel{
	//GUI Components
	private JButton btnPrevious;
	private JButton btnNext;
	int itemCount;
	
	public EditStockPanel(Menu menu) {
		super(menu);
	}
	
	
	
	@Override
	public void initialize(){
		super.initialize();
		ObjectLoader loader = new ObjectLoader(menu, this);
		
		//This ensures that the client does not modify amount of stocks available and cost as well as the supplier name
		costTF.setVisible(false);
		quantityTF.setVisible(false);
		lblCost.setVisible(false);
		lblQuantity.setVisible(false);
		lblSupplier.setVisible(false);
		supplierNameCB.setVisible(false);
		itemTypeCB.setVisible(false);
		lblItemType.setVisible(false);
		
		//Readjustments
		lblSize.setBounds(518, 150, 59, 32);
		sizeCB.setBounds(596, 150, 145, 32);
		lblDescription.setBounds(321, 221, 134, 32);
		lblGarmentCare.setBounds(310, 308, 145, 32);
		lblSeasonType_1.setBounds(329, 435, 128, 32);
		descriptionTA.setBounds(455, 215, 370, 70);
		garmentCareTA.setBounds(455, 308, 370, 79);
		seasonTypeCB.setBounds(459, 438, 140, 32);
		btnReset.setBounds(622, 525, 138, 53);
		
		
		

		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				if(itemCount > 0) {
					itemCount--;
					loader.loadItem(itemCount);
					if(itemCount < menu.getItemList().size()) {
						btnNext.setEnabled(true);
					}
				}else { 
					itemCount = menu.getItemList().size()-1;
					loader.loadItem(itemCount);
				}
			}
		});
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPrevious.setBounds(0, 10, 92, 38);
		add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				
				if(itemCount == menu.getItemList().size()-1 ) {
					itemCount = 0;
					loader.loadItem(itemCount);
				}
				else { 
					itemCount++;
					loader.loadItem(itemCount);
				}
				
			
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNext.setBounds(234, 10, 92, 38);
		add(btnNext);
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(btnReset, "Would you like to delete the item from the list instead?");
				if(result == JOptionPane.YES_OPTION) {
					itemCount = menu.getItemCount();
					menu.removeItem(itemCount);
					JOptionPane.showMessageDialog(null, "This item has been deleted");					
					if(itemCount > 0  && itemCount < menu.getItemList().size()+1) {
						itemCount--;
						loader.loadItem(itemCount);
					}
					
					else if(itemCount == 0 && menu.getItemList().size() == 0) {
						stockListPanel.getStockListTabs().setSelectedIndex(0);
						stockListPanel.getStockListTabs().setEnabledAt(2,false);
					}
					stockListPanel.refreshTable();
				}
				else if(result == JOptionPane.NO_OPTION) {
					reset();
				}
				
			}
		});
		
		
		JButton btnChangeItem = new JButton("Apply Changes");
		btnChangeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Item item = menu.getItemList().get(getItemCount());
					setVariables(item);
					
					if(validFields() && validPrice()) {
						
						switch(selectedValue) {
						case 0: // Select Abaya
							if(checkEmptyAddVariables(selectedValue)) {
								item = new Abaya(name, price, cost, description, quantity, size, category, garmentCare,imagePath, supplier, aFabric, aLength, weight);
								updateItem(item,itemCount);
								JOptionPane.showMessageDialog(null, name +" has been successfully modified");
							}
							break;
						case 1: // Select Cardigan
						
							if(checkEmptyAddVariables(selectedValue)) {
								item = new Cardigan(name, price, cost, description, quantity, size, category, garmentCare,imagePath, supplier, compositions);
								updateItem(item, itemCount);
								JOptionPane.showMessageDialog(null, name +" has been successfully modified");
							}
							break;
							
						case 2: // Select Scarf
							if(checkEmptyAddVariables(selectedValue)) {
								item = new Scarf(name, price, cost, description, quantity, size, category, garmentCare,imagePath, supplier, sLength,sFabric,height,thickness);
								updateItem(item, itemCount);
								JOptionPane.showMessageDialog(null, name +" has been successfully modified");
							}
							break;
							
						}
					}
				}
				catch(NumberFormatException  e1) {
					JOptionPane.showMessageDialog(null, "Use Numbers (e.g. 2) inside the price, cost, quantity textfield", "Number Conversion Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException e2) {
					JOptionPane.showMessageDialog(null, "Something is Missing! Make sure to fill in all of the fields", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnChangeItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChangeItem.setBounds(458, 525, 138, 53);
		add(btnChangeItem);
	}
	
	@Override
	public boolean validFields() {
		if(name.trim().isEmpty()||description.trim().isEmpty()||size.trim().isEmpty()||garmentCare.trim().isEmpty()|| imagePath.isEmpty()||
				priceTF.getText().trim().isEmpty()) {
		
			JOptionPane.showMessageDialog(null, "Fill out all the fields", "Empty Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	
	public int getItemCount() {
		return itemCount;
	}
  	
  	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}


}
