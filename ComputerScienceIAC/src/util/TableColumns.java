package util;

import javax.swing.JTable;

public class TableColumns {//This contains the names of columns for each specific table
	
	public String[] getAdminColumns() {
		return new String[] {"ID", "First Name", "Last Name", "Email Address", "Phone Number", "Username", "Password"};
	}
	
	public String[] getStockColumns() {
		return new String[] {"ID", "Name", "Unit Price", "Cost", "Quantity", "Sizes", "Season Type", "On Discount", "Description", "Garment Care", "Supplier Name"};
	}
	
	public String[] getSupplierColumns() {
		return new String[] {"ID", "Company Name", "Business Type", "Email Address", "Total Payment"};
	}
	
	public String[] getDiscountColumns() {
		return new String[] {"ID", "Item Name", "Normal Price", "Discount Price", "Starting Date", "Ending Date"};
	}
	
	public String[] getItemNamePriceColumns() {
		return new String[] {"ID","Item Name", "Current Price"};
	}
	
	public String[] getRestockColumn() {
		return new String[] {"ID","Image","Item Name", "Cost  ($)", "Supplier Name"};
	}
	
	public String[] getShoppingColumn() {//Fix Later
		return new String[] {"ID","Image","Item Name", "Price ($)", "Quantity Available"};
	}
	
	public String[] getCartColumn() {//Fix Later
		return new String[] {"ID","Item Name", "Price ($)", "Quantity Ordered"};
	}
	
	public String[] getProcessingOrderColumns() {
		return new String[] {"Order Number","Order Date", "Customer Name","City","Home Address", "Total Price ($)"}; //6
	}
	
	public String[] getOrderHistoryColumns() {
		return new String[] {"Order Number","Processed Date","Customer Name", "Total Price ($)"}; //5
	}
	
	
	
	
}
