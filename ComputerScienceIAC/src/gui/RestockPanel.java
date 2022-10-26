package gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import entity.Item;
import entity.Supplier;
import menu.Menu;
import util.ComponentDisplayer;
import util.RowUpdater;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class RestockPanel extends JPanel {

	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable restockTable = table.createTable();
	private Menu menu;
	private JTabbedPane restockTabbedPane;
	private JTextArea detailArea;
	private LinkedList<Item> orderList;
	private LinkedList<Integer> quantityList;
	
	

	public RestockPanel(Menu menu, JTabbedPane restockTabbedPane) {	
		this.restockTabbedPane = restockTabbedPane;
		this.menu = menu;
		this.orderList = new LinkedList<Item>();
		this.quantityList = new LinkedList<Integer>();
	}
	
	public void initialize(){
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		JScrollPane restockScrollPane = new JScrollPane(restockTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		restockScrollPane.setBounds(100, 72, 805, 522);
		restockScrollPane.setViewportView(restockTable);
		add(restockScrollPane);
		
		refreshTable();
		
		detailArea= new JTextArea(); //This textarea is used to display the orders
		detailArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		detailArea.setBounds(955, 72, 350, 400);
		detailArea.setLayout(null);
		detailArea.setEditable(false);
		add(detailArea);
		orderInitials();
		
		
		
		// JButtons
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(restockTable.getSelectedRow() >= 0) {
					int count = (Integer) restockTable.getModel().getValueAt(restockTable.getSelectedRow(), 0);
					Item item = menu.getItemList().get(count);
					
					if(!orderList.isEmpty()){//Check if the orderList is not empty
						boolean exist = false;
						for (int i = 0; i < orderList.size(); i++) {//Loop over the orderList to check whether the desired item is in the list
							if(orderList.get(i).getDisplayID() == item.getDisplayID()) { //This is possible by comparing the ID 
								exist = true;
								quantityList.set(i, quantityList.get(i) + 1);
							}
						}
						if(exist == false) {
							orderList.add(item);
							quantityList.add(1);
						}
						loadOrder();
					}else {
						orderList.add(item);
						quantityList.add(1);
						loadOrder();
					}
				}
				else if(restockTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "There is nothing to delete", "Empty Table",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(455, 661, 159, 53);
		add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(restockTable.getSelectedRow() >= 0) {
					int count = (Integer) restockTable.getModel().getValueAt(restockTable.getSelectedRow(), 0);
					Item item = menu.getItemList().get(count);
					int result = JOptionPane.showConfirmDialog(btnDelete, "Would you like to remove the item entirely from the order list instead?");
					
					if(result == JOptionPane.YES_OPTION) {
						if(!orderList.isEmpty()){
							boolean exist = false;
							for (int i = 0; i < orderList.size(); i++) {
								if(orderList.get(i).getDisplayID() == item.getDisplayID()) {
									exist = true;
									orderList.remove(i);
									quantityList.remove(i);
								}	
							}
							if(!exist) {
								JOptionPane.showMessageDialog(null, "This item is not in the order list", "Empty Table",JOptionPane.ERROR_MESSAGE);
							}
							loadOrder();
						}
						else {
							JOptionPane.showMessageDialog(null, "There is no item to delete as there is nothing in the order list.", "Empty OrderList",JOptionPane.ERROR_MESSAGE);
						}
						
					}
					else if(result == JOptionPane.NO_OPTION) {
						if(!orderList.isEmpty()){
							boolean exist = false;
							for (int i = 0; i < orderList.size(); i++) {
								if(orderList.get(i).getDisplayID() == item.getDisplayID()) {
									exist = true;
									quantityList.set(i, quantityList.get(i) - 1);
									if(quantityList.get(i) == 0) {
										orderList.remove(i);
										quantityList.remove(i);
									}
								}	
							}
							
							if(exist == false) {
								JOptionPane.showMessageDialog(null, "This item is not in the order list", "Empty Table",JOptionPane.ERROR_MESSAGE);
							}
							loadOrder();
						}else {
							JOptionPane.showMessageDialog(null, "There is no item to delete as there is nothing in the order list.", "Empty OrderList",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else if(restockTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "There is nothing to delete", "Empty Table",JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(651, 661, 138, 53);
		add(btnDelete);
		
		JButton btnOrder = new JButton("Order"); 
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //fill in the function
				if(!orderList.isEmpty()) {
					for (int i = 0; i < orderList.size(); i++) {
						for(Item item :menu.getItemList()) {
							if(item.getDisplayID() == orderList.get(i).getDisplayID()) {
								
								item.setQuantity(item.getQuantity() + quantityList.get(i));
								menu.updateItem(item, i);
								
								//Remove Item from the Order list
								orderList.remove(i);
								quantityList.remove(i);
							}
						}
					}
					JOptionPane.showMessageDialog(null, "Restocking Items");
				}
				else {
					JOptionPane.showMessageDialog(null, "There is nothing in the order list", "Empty Order List",JOptionPane.ERROR_MESSAGE);
				}
				loadOrder();
				menu.getViewStockListPanel().refreshTables();
				refreshTable();
			}
		});
		btnOrder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOrder.setBounds(830, 661, 138, 53);
		add(btnOrder);
	}
	
	public void orderInitials() {
		detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"\t\tOrders\n");
		detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"Items\n");
	}
	
	public void loadOrder() {
		detailArea.setText("");
		orderInitials();
		for (int i = 0; i < orderList.size(); i++) {
			Item item = orderList.get(i);
			detailArea.setText(detailArea.getText() + item.getName() +"\n");
			detailArea.setText(detailArea.getText() +"\t$" + item.getCost() + "  *  " + Integer.toString(quantityList.get(i))+ "\n");
		}
		detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"Total Cost" +" ".repeat(50)+ "$"+getTotal());
	}
	
	public BigDecimal getTotal() {
		if(!orderList.isEmpty()) {
			BigDecimal total = BigDecimal.ZERO;
			for (int i = 0; i < orderList.size(); i++) {
				total = total.add(orderList.get(i).getCost().multiply(new BigDecimal(quantityList.get(i))));
				
			}
			return total; 
		}
		
		return BigDecimal.ZERO;
	}
	
	public void refreshTable(LinkedList<Item> list) {
		updateTable.updateRestockTable(restockTable, RowUpdater.updateItemRows(list), tableColumns.getRestockColumn());
		restockTable.getColumn("Image").setCellRenderer(new ComponentDisplayer());
	}
	public void refreshTable() {
		updateTable.updateRestockTable(restockTable, RowUpdater.updateRestockRows(menu.getItemList()), tableColumns.getRestockColumn());
		restockTable.getColumn("Image").setCellRenderer(new ComponentDisplayer());
	}
}	
