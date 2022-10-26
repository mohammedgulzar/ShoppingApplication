package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Abaya;
import entity.Cardigan;
import entity.Item;
import entity.Scarf;
import menu.Menu;

public class DiscountCategoryPanel extends DiscountFieldPanels {
	
	protected JCheckBox[]  checkBoxes;
	protected LinkedList<String> selectedCategories;
	
	public DiscountCategoryPanel(Menu menu, DiscountListPanel discountListPanel) {
		super(menu,discountListPanel);
	}
		//Category Panel
	@Override
	public void initialize()
	{
		super.initialize();
		//Adjusting Some GUI components
		lblTitle.setText("Discount Categories");
		lblPercentage.setBounds(205, 341, 593, 32);
		lblStartingDate.setBounds(201, 419, 151, 32);
		lblEndingDate.setBounds(606, 419, 125, 32);
		lblPercent.setBounds(894, 351, 54, 22);
		percentageTF.setBounds(804, 342, 80, 29);
		startingDateChooser.setBounds(375, 419, 125, 32);
		endingDateChooser.setBounds(770, 419, 125, 32);
		btnDiscount.setBounds(574, 611, 183, 53);
		
		
		JLabel lblCategories = new JLabel("Select Categories to discount:");
		lblCategories.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblCategories.setBounds(164, 166, 336, 32);
		add(lblCategories);
		
		JPanel radioButtonGroupPanel = new JPanel();
		radioButtonGroupPanel.setBounds(255, 196, 543, 87);
		add(radioButtonGroupPanel);
		radioButtonGroupPanel.setLayout(null);
	
		JCheckBox chckbxAbaya = new JCheckBox("Abaya");
		chckbxAbaya.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxAbaya.setBounds(46, 45, 93, 21);
		radioButtonGroupPanel.add(chckbxAbaya);
		
		JCheckBox chckbxSpring = new JCheckBox("Spring");
		chckbxSpring.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxSpring.setBounds(157, 22, 93, 21);
		radioButtonGroupPanel.add(chckbxSpring);
		
		JCheckBox chckbxSummer = new JCheckBox("Summer");
		chckbxSummer.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxSummer.setBounds(269, 22, 93, 21);
		radioButtonGroupPanel.add(chckbxSummer);
		
		JCheckBox chckbxAutumn = new JCheckBox("Autumn");
		chckbxAutumn.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxAutumn.setBounds(46, 22, 93, 21);
		radioButtonGroupPanel.add(chckbxAutumn);
		
		JCheckBox chckbxCardigan = new JCheckBox("Cardigan");
		chckbxCardigan.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxCardigan.setBounds(157, 45, 109, 21);
		radioButtonGroupPanel.add(chckbxCardigan);
		
		JCheckBox chckbxScarf = new JCheckBox("Scarf");
		chckbxScarf.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxScarf.setBounds(269, 45, 93, 21);
		radioButtonGroupPanel.add(chckbxScarf);
		
		JCheckBox chckbxWinter = new JCheckBox("Winter");
		chckbxWinter.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		chckbxWinter.setBounds(364, 22, 93, 21);
		radioButtonGroupPanel.add(chckbxWinter);
		
		checkBoxes = new JCheckBox[]{chckbxAbaya,chckbxSpring,chckbxSummer,chckbxAutumn, chckbxCardigan, chckbxScarf, chckbxWinter};
		
		//setEnabled(false);
		
		
		//Discounting Method
		btnDiscount.addActionListener(new ActionListener() { //Do validations later
			public void actionPerformed(ActionEvent e) {
				selectedCategories = createDiscountCategory();
				try {
					if(menu.getItemList().size() > 0) {
						if (!selectedCategories.isEmpty() && validDiscount() && validDuration()) {
							discountItems();
							refreshTable();
							menu.getViewStockListPanel().refreshTable();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "There is nothing to discount", "Empty Item List", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Fill in the start and end date for this discount!", "Empty Date Field", JOptionPane.ERROR_MESSAGE);
				}
				catch(NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "The percentage textfield must only contain numerical value from 0 to 100 %", "Data type mismatch", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
	}
	
	public void discountItems() {
		boolean invalidPrice = false;
		boolean discounted = false;
		for (int i = 0; i < menu.getItemList().size(); i++) {
			Item item = menu.getItemList().get(i);
			int count = 0;
			if(!item.isDiscount()) {
				while (count != selectedCategories.size()) {
					if(item.getCategory().equalsIgnoreCase(selectedCategories.get(count)) ||//Compares the seasons type: Winter, Autumn, Summer and Spring
							item instanceof Abaya || item instanceof Cardigan || item instanceof Scarf ){ //This compares what type the item is: 													 								   //It can be either Abaya or Cardigan or Scarf						
						discounted = true;
						calculateDiscountedPrice(item);
						
						if(discountedPrice.compareTo(item.getCost()) > 0) { //If the newly discounted price is greater than the item's cost
							discountItem(item);
						}
						else {
							invalidPrice = true;
						}
					}
					count = count + 1;
				}
			}
		}
		if(!discounted) {
			JOptionPane.showMessageDialog(null, "There were no item which contain these categories", "Discount Failure", JOptionPane.ERROR_MESSAGE);
		}
		else if(invalidPrice) {
			JOptionPane.showMessageDialog(null, "Some items were not discounted due to discounted price being lower than the cost. Use the Discount Specific Item Tab instead", "Discount Failure", JOptionPane.ERROR_MESSAGE);
		}
		else {
			reset();
			JOptionPane.showMessageDialog(null, "The discount process was done successfully.", "Discount Success", JOptionPane.PLAIN_MESSAGE);
		}

	}

	public LinkedList<String> createDiscountCategory(){
		LinkedList<String> list = new LinkedList<>();
		
		if(!(emptyCheckBox())) {
			for (JCheckBox box : checkBoxes) {
				if(box.isSelected()) {
					list.add(box.getText());
				}
			}
			return list;
		}
		return null;
	}
	
	public boolean emptyCheckBox() {
		boolean empty = true;
		
		for (JCheckBox box : checkBoxes) {
			if(box.isSelected()) {
				empty = false;
			}
		}
		
		if(empty) {
			JOptionPane.showMessageDialog(null, "You must select at least one CheckBox", "Unselected Check Box", JOptionPane.ERROR_MESSAGE);
			return empty;
		}
		return empty; //returns false
	}
	
	public void reset() {
		super.reset();
		for (JCheckBox box : checkBoxes) {
			box.setSelected(false);
		}
	}
}
