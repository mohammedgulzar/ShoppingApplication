package util;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UpdateTable {// This is used when something occurs in a table that requires refreshing the table. 
	//For instance, adding a new item to the database will require a refresh from stock table so that the user can see the new item.
	
	public void updateAdminTable(JTable adminTable, Object[][] rows, String[] columns) {
		adminTable.setModel(new DefaultTableModel(rows, columns));
		adminTable.getColumnModel().getColumn(0).setPreferredWidth(48); //These are adjustments for the table
		adminTable.getColumnModel().getColumn(1).setPreferredWidth(130);
		adminTable.getColumnModel().getColumn(2).setPreferredWidth(130);
		adminTable.getColumnModel().getColumn(3).setPreferredWidth(265);
		adminTable.getColumnModel().getColumn(4).setPreferredWidth(215);
		adminTable.getColumnModel().getColumn(5).setPreferredWidth(157);
		adminTable.getColumnModel().getColumn(6).setPreferredWidth(183);
	}
	
	
	public void updateItemTable(JTable stockTable, Object[][] rows, String[] columns) {
		stockTable.setModel(new DefaultTableModel(rows, columns));
		stockTable.getColumnModel().getColumn(0).setPreferredWidth(48); //These are adjustments for the table
		stockTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		stockTable.getColumnModel().getColumn(5).setPreferredWidth(55);
		stockTable.getColumnModel().getColumn(6).setPreferredWidth(99);
		stockTable.getColumnModel().getColumn(8).setPreferredWidth(265);
		stockTable.getColumnModel().getColumn(9).setPreferredWidth(196);
		stockTable.getColumnModel().getColumn(10).setPreferredWidth(183);
	}
	
	
	public void updateSupplierTable(JTable supplierTable, Object[][] rows, String[] columns) {
		supplierTable.setModel(new DefaultTableModel(rows, columns));
		supplierTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		supplierTable.getColumnModel().getColumn(1).setPreferredWidth(193); //These are adjustments for the table
		supplierTable.getColumnModel().getColumn(2).setPreferredWidth(193);
		supplierTable.getColumnModel().getColumn(3).setPreferredWidth(193);
		supplierTable.getColumnModel().getColumn(4).setPreferredWidth(193);
		
	}
	
	public void updateDiscountTable(JTable discountTable, Object[][] rows, String[] columns) {
		discountTable.setModel(new DefaultTableModel(rows, columns));
		discountTable.getColumnModel().getColumn(0).setPreferredWidth(63);
		discountTable.getColumnModel().getColumn(1).setPreferredWidth(183);
		discountTable.getColumnModel().getColumn(2).setPreferredWidth(183);
		discountTable.getColumnModel().getColumn(3).setPreferredWidth(183);
		discountTable.getColumnModel().getColumn(4).setPreferredWidth(243);
		discountTable.getColumnModel().getColumn(5).setPreferredWidth(242);
		
	}
	
	public void updateItemNamePriceTable(JTable itemNamePriceTable, Object[][] rows, String[] columns) {
		itemNamePriceTable.setModel(new DefaultTableModel(rows, columns));
		itemNamePriceTable.getColumnModel().getColumn(0).setPreferredWidth(153);//These are adjustments for the table
		itemNamePriceTable.getColumnModel().getColumn(1).setPreferredWidth(154);
		itemNamePriceTable.getColumnModel().getColumn(2).setPreferredWidth(153);
	}
	
	public void updateRestockTable(JTable restockTable, Object[][] rows, String[] columns) {
		restockTable.setModel(new DefaultTableModel(rows, columns));
		restockTable.getColumnModel().getColumn(0).setPreferredWidth(150);//These are adjustments for the table
		restockTable.getColumnModel().getColumn(1).setPreferredWidth(201);
		restockTable.getColumnModel().getColumn(2).setPreferredWidth(170);//These are adjustments for the table
		restockTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		restockTable.getColumnModel().getColumn(4).setPreferredWidth(170);
	}
	
	public void updateShoppingTable(JTable shoppingTable, Object[][] rows, String[] columns) {
		shoppingTable.setModel(new DefaultTableModel(rows, columns));
		shoppingTable.getColumnModel().getColumn(0).setPreferredWidth(145);
		shoppingTable.getColumnModel().getColumn(1).setPreferredWidth(201);
		shoppingTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		shoppingTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		shoppingTable.getColumnModel().getColumn(4).setPreferredWidth(150);
	}
	
	public void updateCartTable(JTable cartTable, Object[][] rows, String[] columns) {
		cartTable.setModel(new DefaultTableModel(rows, columns));
		cartTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		cartTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		cartTable.getColumnModel().getColumn(2).setPreferredWidth(198);
		cartTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		
	}
	
	public void updateProcessingOrderTable(JTable processingOrderTable, Object[][] rows, String[] columns) {
		processingOrderTable.setModel(new DefaultTableModel(rows, columns));
		processingOrderTable.getColumnModel().getColumn(0).setPreferredWidth(100);//These are adjustments for the table
		processingOrderTable.getColumnModel().getColumn(1).setPreferredWidth(141);
		processingOrderTable.getColumnModel().getColumn(2).setPreferredWidth(141);
		processingOrderTable.getColumnModel().getColumn(3).setPreferredWidth(141);
		processingOrderTable.getColumnModel().getColumn(4).setPreferredWidth(141);
		processingOrderTable.getColumnModel().getColumn(5).setPreferredWidth(139);
	}
	
	public void updateOrderHistoryTable(JTable orderHistoryTable, Object[][] rows, String[] columns) {
		orderHistoryTable.setModel(new DefaultTableModel(rows, columns));
		orderHistoryTable.getColumnModel().getColumn(0).setPreferredWidth(201);//These are adjustments for the table
		orderHistoryTable.getColumnModel().getColumn(1).setPreferredWidth(201);
		orderHistoryTable.getColumnModel().getColumn(2).setPreferredWidth(202);
		orderHistoryTable.getColumnModel().getColumn(3).setPreferredWidth(200);
	}
	
	
	
}
