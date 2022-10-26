package util;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import entity.Admin;
import entity.Item;
import entity.OrderHistory;
import entity.ProcessingOrder;
import entity.Supplier;
import menu.Menu;

public class RowUpdater {//This is used to create a two dimensional row filled with list of objects
	//Which will be used to load them onto the JTable
	private Menu menu;
	
	RowUpdater(){}
	
	public RowUpdater(Menu menu){
		this.menu = menu;
	}
	
	public static Object[][] updateItemRows(LinkedList<Item> list){ 
		Iterator it = list.iterator();
		int itemListSize = list.size();
		if(itemListSize>0) {
			Object[][] rows = new Object[itemListSize][11];
			int count = 0;
			while(it.hasNext()) {
				Item item = (Item) it.next();
				rows[count][0]= item.getDisplayID();
				rows[count][1]= item.getName();
				rows[count][2]= item.getPrice();
				rows[count][3]= item.getCost();
				rows[count][4]= item.getQuantity();
				rows[count][5]= item.getSize();
				rows[count][6]= item.getCategory();
				rows[count][7]= item.isDiscount();
				rows[count][8]= item.getDescription();
				rows[count][9]= item.getGarmentCare();
				rows[count][10]= item.getSupplier().getCompanyName();
				count++;
				
			}
			return rows;
		}
		else {//If there is nothing in the item list
			return null;
		}		
	}
	
	public static Object[][] updateItemRows(Set<Item> list){ //look at this later
		Iterator it = list.iterator();
		int itemListSize = list.size();
		if(itemListSize>0) {
			Object[][] rows = new Object[itemListSize][11];
			int count = 0;
			while(it.hasNext()) {
				Item item = (Item) it.next();
				rows[count][0]= item.getDisplayID();//new change 16 january
				rows[count][1]= item.getName();
				rows[count][2]= item.getPrice();
				rows[count][3]= item.getCost();
				rows[count][4]= item.getQuantity();
				rows[count][5]= item.getSize();
				rows[count][6]= item.getCategory();
				rows[count][7]= item.isDiscount();
				rows[count][8]= item.getDescription();
				rows[count][9]= item.getGarmentCare();
				rows[count][10]= item.getSupplier().getCompanyName();
				count++;	
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public static Object[][] updateDiscountRows(LinkedList<Item> list){
		int size = list.size();
		if(size > 0) {
			Iterator it = list.iterator();
			Object[][] rows = new Object[size][6];
			int count = 0;
			while(it.hasNext()) {
				
				Item item = (Item) it.next();
				rows[count][0]= item.getDisplayID();
				rows[count][1]= item.getName();
				rows[count][2]= item.getPrevPrice();
				rows[count][3]= item.getPrice();
				rows[count][4]= item.getStartDate();
				rows[count][5]= item.getEndDate();
				count++;
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	
	
	public static Object[][] updateItemPriceRows(LinkedList<Item> list){//fix this 
		int size = list.size();
		int count = 0;
		LinkedList<Item> nonDiscountList = new LinkedList<Item>();
		if(size>0) {
			
			for(int i = 0; i < size; i++) {
				Item item = list.get(i);
				if(!(item.isDiscount())) {
					count++;
					nonDiscountList.add(item);
				}
			}
			
			Object[][] rows = new Object[count][3];
			
			for(int i = 0; i < nonDiscountList.size(); i++) {
				Item item = nonDiscountList.get(i);
				if(!(item.isDiscount())) {
					rows[i][0]= item.getDisplayID();
					rows[i][1]= item.getName();
					rows[i][2]= item.getPrice();
				}
			}
			
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public static Object[][] updateAdminRows(LinkedList<Admin> list){ //look at this later
		int adminListSize = list.size();
		if(adminListSize>1) {//The admin list to be at most greater than one
			//we want to display only the admin and not the manager which is located in index 0 of the admin list
			Object[][] rows = new Object[adminListSize-1][7];
			int count = 0;
			for (int i = 1; i < adminListSize; i++) {
				Admin admin = (Admin) list.get(i);
				rows[count][0]= admin.getDisplayID();
				rows[count][1]= admin.getFirstName();
				rows[count][2]= admin.getLastName();
				rows[count][3]= admin.getEmailAddress();
				rows[count][4]= admin.getPhoneNumber();
				rows[count][5]= admin.getUserName();
				rows[count][6]= admin.getPassword();
				count++;
			}
			
			return rows;
		}
		else { //If there is nothing in the admin list besides the manager
			return null;
		}		
	}
	
	public static Object[][] updateSupplierRows(LinkedList<Supplier> list){ //look at this later
		Iterator it = list.iterator();
		int size = list.size();
		if(size > 0) {
			Object[][] rows = new Object[size][5];
			int count = 0;
			while(it.hasNext()) {
				Supplier supplier = (Supplier) it.next();
				supplier.updatePayment();
				rows[count][0]= supplier.getDisplayID();
				rows[count][1]= supplier.getCompanyName();
				rows[count][2]= supplier.getType();
				rows[count][3]= supplier.getEmailAddress();
				rows[count][4]= supplier.getTotalPayment();
				count++;
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public static Object[][] updateRestockRows(LinkedList<Item> list){ //look at this later
		Iterator it = list.iterator();
		int itemListSize = list.size();
		if(itemListSize>0) {
			Object[][] rows = new Object[itemListSize][5];
			int count = 0;
			while(it.hasNext()) {
				Item item = (Item) it.next();
				
				JLabel label = new JLabel();
				ImageIcon imageIcon = new ImageIcon(item.getImagePath());
				ImageIcon image = new ImageIcon(imageIcon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH));
				label.setIcon(image);
				JButton button = new JButton("Add");
				
				rows[count][0]= item.getDisplayID();
				rows[count][1]= label;//fix this one
				rows[count][2]= item.getName();
				rows[count][3]= item.getCost();
				rows[count][4]= item.getSupplier().getCompanyName();
				
				count++;
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public Object[][] updateShoppingRows(LinkedList<Item> list){ //look at this later
		LinkedList<Item> availableStocks = new LinkedList<Item>();
		for (Item item : list) {
			if(item.hasStock()) {
				availableStocks.add(item);
			}
		}
		
		Iterator it = availableStocks.iterator();
		int size = availableStocks.size();
		
		if(size>0) {
			Object[][] rows = new Object[size][5];
			int count = 0;
			while(it.hasNext()) {
				Item item = (Item) it.next();
				
				if(item.hasStock()) {
					JLabel label = new JLabel();
					ImageIcon imageIcon = new ImageIcon(item.getImagePath());
					ImageIcon image = new ImageIcon(imageIcon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH));
					label.setIcon(image);

					rows[count][0]= item.getDisplayID();
					rows[count][1]= label;
					rows[count][2]= item.getName();
					rows[count][3]= item.getPrice();
					rows[count][4]= item.getQuantity();
					count++;	
				}
				
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public Object[][] updateCartRows(LinkedList<Item> list, LinkedList<Integer> quantities){ //look at this later
		int size = list.size();
		if(size>0) {
			Object[][] rows = new Object[size][4];
			int count = 0;
			for(int i = 0; i < size; i++) {
				Item item = list.get(i);
				int quantity = quantities.get(i);
				
				rows[count][0]= item.getDisplayID();
				rows[count][1]= item.getName();
				rows[count][2]= item.getPrice();
				rows[count][3]= quantity;
				count++;
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public static Object[][] updateProcessingOrderRows(Queue<ProcessingOrder> list){ //look at this later
		Iterator it = list.iterator();
		int size = list.size();
		if(size >0) {
			Object[][] rows = new Object[size][6];
			int count = 0;
			while(it.hasNext()) {
				ProcessingOrder order = (ProcessingOrder) it.next();
				String fullname = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(); 
				
				rows[count][0]= order.getID();
				rows[count][1]= order.getOrderDate();
				rows[count][2]= fullname;
				rows[count][3]= order.getCustomer().getCity();
				rows[count][4]= order.getCustomer().getHomeAddress();
				rows[count][5]= order.getTotalPrice();
				count++;
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
	public static Object[][] updateOrderHistoryRows(Stack<OrderHistory> list){ //look at this later
		Iterator it = list.iterator();
		int size = list.size();
		if(size>0) {
			Object[][] rows = new Object[size][4];
			int count = 0;
			while(it.hasNext()) {
				OrderHistory order = (OrderHistory) it.next();
				String fullname = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(); 
				
				rows[count][0]= order.getID();
				rows[count][1]= order.getDateProcessed();
				rows[count][2]= fullname;
				rows[count][3]= order.getTotalPrice();
				count++;
			}
			return rows;
		}
		else {
			return null;
		}		
	}
	
}
