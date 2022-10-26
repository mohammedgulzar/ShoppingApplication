package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import entity.Item;
import entity.Supplier;
import menu.Menu;
import util.MailSender;
import util.ObjectLoader;
import util.RowUpdater;
import util.Sorter;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class SupplierListPanel extends JPanel {
	
	//Creation of JTable components
	private Table table = new Table();
	private JTable supplierTable = table.createTable();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	
	//Utilities for GUI
	private MailSender mailer = new MailSender();
	private Sorter sorter = new Sorter();
	
	//GUI components
	private JTabbedPane adminTabbedPane;
	private Menu menu;
	private JTextArea detailArea;
	
	int supplierCount;
	
	

	public SupplierListPanel(Menu menu, JTabbedPane adminTabbedPane){
		this.adminTabbedPane = adminTabbedPane;
		this.menu = menu;
	}
	
	public void initialize() {
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		
		ObjectLoader loader = new ObjectLoader(menu, menu.getEditSupplierListPanel());
		
		JScrollPane supplierScrollPane = new JScrollPane(supplierTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		supplierScrollPane.setBounds(100, 125, 805, 522);//212
		supplierScrollPane.setViewportView(supplierTable);
		add(supplierScrollPane);
		
		detailArea= new JTextArea(); //This shows the details of each supplier and the list of items 
		detailArea.setBounds(955, 72, 350, 400);
		detailArea.setLayout(null);
		detailArea.setEditable(false);
		add(detailArea);
		
		refreshTable();

		supplierTable.addMouseListener(new MouseAdapter() {//When you select a row from the JTable, it will automatically loads the details
			//onto the detailArea
			public void mouseClicked(MouseEvent e) {
				supplierCount = Integer.parseInt(supplierTable.getValueAt(supplierTable.getSelectedRow(), 0).toString());
				Supplier supplier = menu.getSupplierList().get(supplierCount);
				loadPayment(supplier);
			}
		});
		
		//JLabels
		JLabel lblTitle = new JLabel("Supplier List");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(494, 0, 390, 80);
		add(lblTitle);
		
		// JButtons
		JButton btnAdd = new JButton("Add New Supplier");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminTabbedPane.setEnabledAt(3,true);
				adminTabbedPane.setSelectedIndex(3);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(455, 661, 159, 53);
		add(btnAdd);
		
		JButton btnEdit = new JButton("Edit Supplier");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(supplierTable.getSelectedRow() >= 0) {
					adminTabbedPane.setEnabledAt(5,true);
					adminTabbedPane.setSelectedIndex(5);
					supplierCount = Integer.parseInt(supplierTable.getValueAt(supplierTable.getSelectedRow(), 0).toString());
					menu.getEditSupplierListPanel().setSupplierCount(supplierCount);
					loader.loadSupplier(supplierCount, menu.getSupplierList());
				}
				else if(supplierTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "There is nothing to edit", "Empty Table",JOptionPane.ERROR_MESSAGE);
				}
				else {//If no rows were selected and there is suppliers in the list, then it will load the first supplier in the supplier list
					adminTabbedPane.setEnabledAt(5,true);
					adminTabbedPane.setSelectedIndex(5);
					supplierCount = 0;
					menu.getEditSupplierListPanel().setSupplierCount(supplierCount);
					loader.loadSupplier(supplierCount, menu.getSupplierList());
				}

			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.setBounds(651, 661, 138, 53);
		add(btnEdit);
		
		JButton btnDelete = new JButton("Delete\r\n"); 
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(btnDelete, "Are you sure that you want to delete this supplier? "
						+ "If yes, then items that linked to this supplier will be removed as well");
				
				if(result == JOptionPane.YES_OPTION) {
					LinkedList<Item> deletedItems = new LinkedList<>();
					if(supplierTable.getSelectedRow() >= 0) {
						supplierCount = Integer.parseInt(supplierTable.getValueAt(supplierTable.getSelectedRow(), 0).toString());
						Supplier supplier = menu.getSupplierList().get(supplierCount);
						
						for(Item item: supplier.getItemList()) { //This will add the supplier itemList to deletedItems list which will be sent to the manager.
							deletedItems.add(item);
						}
						
						for(int i = 0; i < menu.getItemList().size(); i++) {
							Item item = menu.getItemList().get(i);
							if(item.getSupplier().getID() == supplier.getID()) {//Comparing Supplier ID
								
								supplier.removeItem(item);//This will remove the item from the supplier's itemList
								menu.removeItem(item.getDisplayID());//This will ensure that the supplier is removed from the database 
								menu.getItemList().remove(item.getDisplayID());
								menu.getViewStockListPanel().refreshTable();
							}
						}
						
						menu.removeSupplier(supplierCount);
						menu.getAddItemPanel().updateSupplierCB();//This will update the comboBox in the AddItemPanel
				
						refreshTable();
						mailer.sendDeletedItems(deletedItems, menu.getAdminList().get(0)); //emails the manager the list of all deleted items
					}
					else {
						JOptionPane.showMessageDialog(null, "You must select one row of supplier to delete", "", JOptionPane.ERROR_MESSAGE);
					}
				}}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(830, 661, 138, 53);
		add(btnDelete);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detailArea.setText("");
				refreshTable();
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefresh.setBounds(725, 83, 91, 38);
		add(btnRefresh);
		
		//JComboBox
		JComboBox sortCB = new JComboBox(new String[] {"A-Z", "Z-A"});
		sortCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedValue = sortCB.getSelectedIndex();
				switch(selectedValue) {
					case 0:
						sort(menu.getSupplierList(), "A");//Ascending
						break;
						
					case 1:
						sort(menu.getSupplierList(), "D");//Descending
						break;
				}	
			}
		});
		sortCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		sortCB.setBounds(820, 83, 91, 38);
		add(sortCB);

	}
	
	public void resetID(LinkedList<Supplier> list) {
		for (int i = 0; i < list.size();i++) {
			list.get(i).setDisplayID(i);
		}
	}
	
	
	//Sorting algorithms 
	public void sort(LinkedList<Supplier> list, String sortType) {
		Supplier[] sortedArray = (Supplier[]) list.toArray(new Supplier[list.size()]);//The list is first converted into an array
		switch(sortType) {
			case "A"://Ascending Order 
				sortedArray =  sorter.recursiveSortSupplier(sortedArray, "A",sortedArray.length);
				break;
			
			case "D"://Descending Order 
				sortedArray = (Supplier[]) sorter.recursiveSortSupplier(sortedArray, "D",sortedArray.length);
				break;
		}
		
		list = new LinkedList<Supplier>(Arrays.asList(sortedArray)); //the list copies the data of the sorted array
		refreshTable(list);
	}
	
	public void loadPayment(Supplier supplier) {//This loads the list of items that is provided by one supplier and displays how their total payments were calculated.
		detailArea.setText("*".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"Company Name: " + supplier.getCompanyName() +"\n");
		detailArea.setText(detailArea.getText() +"Email Address: " + supplier.getEmailAddress() +"\n");
		detailArea.setText(detailArea.getText() +"BusinessType: " + supplier.getType() +"\n");
		detailArea.setText(detailArea.getText() +"*".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"Payments\n");
		detailArea.setText(detailArea.getText() +"*".repeat(12)+"\n");
		detailArea.setText(detailArea.getText() + "Items \n");
		try {
			if(supplier.getItemList().size() >= 1) {
				for (int i = 0; i < supplier.getItemList().size(); i++) {
					Item item = supplier.getItemList().get(i);
					int quantity = item.getQuantity();
					detailArea.setText(detailArea.getText() + item.getName() +"\n"); 
					detailArea.setText(detailArea.getText() + "\t$"+item.getCost() +"  *  " + Integer.toString(quantity) + " Unit\n");
					
				}
			}
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
		detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"Total Payment" +" ".repeat(60)+ "$"+supplier.getTotalPayment());
	}
	
	public JTable getSupplierTable() {
		return supplierTable;
	}
	
	public void refreshTable() {
		//Resets the Display ID starting from 0
		resetID(menu.getSupplierList());
		updateTable.updateSupplierTable(supplierTable, RowUpdater.updateSupplierRows(menu.getSupplierList()), tableColumns.getSupplierColumns());
		
	}
	
	public void refreshTable(LinkedList<Supplier> list) {
		updateTable.updateSupplierTable(supplierTable, RowUpdater.updateSupplierRows(list), tableColumns.getSupplierColumns());
		
	}
	
	public JTabbedPane getAdminTabbedPane() {
		return adminTabbedPane;
	}
	

}
