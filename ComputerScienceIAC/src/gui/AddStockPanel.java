package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import entity.Abaya;
import entity.Cardigan;
import entity.Scarf;
import menu.Menu;

public class AddStockPanel extends ModifyStockPanel{ //fix this
	public AddStockPanel(Menu menu) {
		super(menu);
	}
	
	public void initialize() {
		
		super.initialize();
		//Buttons
		
		JButton btnSaveItem = new JButton("Save Item"); //Saving a new item 
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					setVariables();
					if(validFields() && validPrice() && validQuantity()) {
						switch(selectedValue) {
						case 0: // Select Abaya
							if(checkEmptyAddVariables(selectedValue)) {
								Abaya abaya = new Abaya(name, price, cost, description, quantity, size, category, garmentCare,imagePath, supplier, aFabric, aLength, weight);
								addItem(abaya);
							}
							break;
							
						case 1: // Select Cardigan
							if(checkEmptyAddVariables(selectedValue)) {
								Cardigan cardigan = new Cardigan(name, price, cost, description, quantity, size, category, garmentCare,imagePath, supplier, compositions);
								addItem(cardigan);
							}
							break;
							
						case 2: // Select Scarf
							if(checkEmptyAddVariables(selectedValue)) {
								Scarf scarf = new Scarf(name, price, cost, description, quantity, size, category, garmentCare,imagePath, supplier, sLength,sFabric,height,thickness);
								addItem(scarf);
							}
							break;
						}

					}
					
				}
				catch(NumberFormatException  e1) {
					JOptionPane.showMessageDialog(null, "Use Numbers (e.g. 2) inside the price, cost, quantity textfield", "Number Conversion Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnSaveItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveItem.setBounds(431, 625, 138, 53);
		add(btnSaveItem);
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
	}
}
