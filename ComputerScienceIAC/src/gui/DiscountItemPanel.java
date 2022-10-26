package gui;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import entity.Item;
import menu.Menu;
import util.RowUpdater;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DiscountItemPanel extends DiscountFieldPanels {
	
	private JTable itemNamePriceTable = table.createTable();
	
	public DiscountItemPanel(Menu menu, DiscountListPanel discountListPanel) {
		
		super(menu,discountListPanel);
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
		JScrollPane scrollPane = new JScrollPane(itemNamePriceTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane.setBounds(466, 74, 460, 380);
		scrollPane.setViewportView(itemNamePriceTable);
		add(scrollPane);
		
		menu.getViewStockListPanel().resetID(menu.getItemList());
		
		refreshTable();
		
		
		//Discounting Method
		btnDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(itemNamePriceTable.getSelectedRow() >= 0) {
						int index = Integer.parseInt(itemNamePriceTable.getValueAt(itemNamePriceTable.getSelectedRow(), 0).toString());
						Item item = menu.getItemList().get(index);
						
						if(validDiscount() && validDuration()) {
							calculateDiscountedPrice(item);
							
							
							if(discountedPrice.compareTo(item.getCost()) > 0) { //If the newly discounted price is greater than the item's cost
								discountItem(item);
								reset();
								JOptionPane.showMessageDialog(null, item.getName() + " has been discounted. Please check out the discounted table", "Discount Success", JOptionPane.INFORMATION_MESSAGE);
								menu.getViewStockListPanel().refreshTable();
								refreshTable();
							}
							else {
								JOptionPane.showMessageDialog(null, "This discounted price should not be equal or less than the cost of the item. Lower the discount percentage", "Discount Failure", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "You must select a row to discount", "Discount Failure", JOptionPane.ERROR_MESSAGE);
					}
				}catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Fill in the start and end date for this discount!", "Empty Date Field", JOptionPane.ERROR_MESSAGE);
				}
				catch(NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "The percentage textfield must only contain numerical value from 0 to 100 %", "Data type mismatch", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
	
	@Override
	public void refreshTable() {
		updateTable.updateDiscountTable(discountListPanel.getDiscountTable(),RowUpdater.updateDiscountRows(menu.getDiscountedItemList()), tableColumns.getDiscountColumns());
		updateTable.updateItemNamePriceTable(itemNamePriceTable,RowUpdater.updateItemPriceRows(menu.getItemList()), tableColumns.getItemNamePriceColumns());
	}
	
	public JTable getItemNamePriceTable() {
		return itemNamePriceTable;
	}
}
