package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingConstants;

import entity.Item;
import entity.Supplier;
import menu.Menu;
import util.ObjectLoader;
import util.RowUpdater;
import util.Sorter;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class StockListPanel extends JPanel{
	
	
	private Sorter sorter = new Sorter();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private Table table = new Table();
	private String[] stockColumns = tableColumns.getStockColumns();
	private int itemCount;
	private Menu menu;
	
	//GUI Components
	private EditStockPanel editStockPanel;
	private JTable stockTable = table.createTable();
	private JTabbedPane stockListTabs;
	private DiscountItemPanel discountItemPanel;
	private SupplierListPanel supplierListPanel;
	
	public StockListPanel(Menu menu, JTabbedPane stockListTabs) {
		this.stockListTabs = stockListTabs;
		this.menu = menu;
		
		
		//initialize();
		
	}
	
	public void initialize()
	{
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		//Edit Item Panel
		//These panels are used to edit current items by changing the name and etc.
		
		
		discountItemPanel = menu.getSpecificItemPanel();
		supplierListPanel = menu.getSupplierListPanel();
		this.editStockPanel   = menu.getEditStockPanel();
		ObjectLoader loader = new ObjectLoader(menu,editStockPanel);
		LinkedList<Supplier> supplierList = menu.getSupplierList();
		LinkedList<Item> itemList = menu.getItemList();
		
		resetID(itemList);
			
		//View Stock List Table
		JScrollPane scrollPane = new JScrollPane(stockTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane.setBounds(10, 107, 1350, 522);
		scrollPane.setViewportView(stockTable);
		add(scrollPane);
		refreshTable();	
		
		JLabel lblTitle = new JLabel("Stock List");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(490, 0, 390, 67);
		add(lblTitle);
		
		JButton btnAdd = new JButton("Add New Item");
		btnAdd.addActionListener(new ActionListener() { // Add Button
			public void actionPerformed(ActionEvent e) {
				if(!menu.getSupplierList().isEmpty()) {
					stockListTabs.setEnabledAt(1,true);
					stockListTabs.setSelectedIndex(1);
				}
				else {
					JOptionPane.showMessageDialog(null, "You must add a supplier first", "Empty Supplier List",JOptionPane.ERROR_MESSAGE);
					stockListTabs.setEnabledAt(1,false);
				}
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(431, 657, 138, 53);
		add(btnAdd);		
		
		JButton btnEdit = new JButton("Edit Item");
		btnEdit.addActionListener(new ActionListener() { // Edit Button
			public void actionPerformed(ActionEvent e) {
				if(stockTable.getSelectedRow() >= 0) {
					stockListTabs.setEnabledAt(2,true);
					stockListTabs.setSelectedIndex(2);
					itemCount = Integer.parseInt(stockTable.getValueAt(stockTable.getSelectedRow(), 0).toString());
					editStockPanel.setItemCount(itemCount);
					loader.loadItem(itemCount);
				}
				else if(stockTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "There is nothing to edit", "Empty Table",JOptionPane.ERROR_MESSAGE);
				}
				else {
					stockListTabs.setEnabledAt(2,true);
					stockListTabs.setSelectedIndex(2);
					itemCount = 0;
					loader.loadItem(itemCount);
				}
				
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.setBounds(601, 657, 138, 53);
		add(btnEdit);	
		
		JButton btnDelete = new JButton("Delete\r\n"); //Delete button
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(stockTable.getSelectedRow() >= 0) {
					itemCount = Integer.parseInt(stockTable.getValueAt(stockTable.getSelectedRow(), 0).toString());
					resetID(itemList);
					Item item = menu.getItemList().get(itemCount);
					editStockPanel.deleteItem(item, itemCount);
					
					menu.getRestockPanel().refreshTable();
				}
				
				else if(stockTable.getRowCount() != 0) {
					JOptionPane.showMessageDialog(null, "You must select one row of item to delete", "Deletion Failure",JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Theere is nothing to delete", "Empty Table", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(774, 657, 138, 53);
		add(btnDelete);
		
		
				
		
		
		JComboBox sortCB = new JComboBox(new String[] {"A-Z", "Z-A"});
		sortCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedValue = sortCB.getSelectedIndex();
				switch(selectedValue) {
					case 0:
						sort(menu.getItemList(), "A");
						break;
						
					case 1:
						sort(menu.getItemList(), "D");
						break;
				}
					
					
			}
		});
		sortCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		sortCB.setBounds(1250, 67, 91, 38);
		add(sortCB);
		
		JButton btnRefresh = new JButton("Refresh"); //fix
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
				
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefresh.setBounds(1155, 67, 91, 38);
		add(btnRefresh);
		
		
	}
	
	public void resetID(LinkedList<Item> list) {
		for (int i = 0; i < list.size();i++) {
			list.get(i).setDisplayID(i);
		}
	}
	
	//Sorting algorithms 
	public void sort(LinkedList<Item> list, String sortType) {
		Item[] sortedArray = (Item[]) list.toArray(new Item[list.size()]);
		switch(sortType) {
			case "A":
				sortedArray = sorter.recursiveSortItem(sortedArray, "A",sortedArray.length);
				break;
			
			case "D":
				sortedArray = sorter.recursiveSortItem(sortedArray, "A",sortedArray.length);
				break;
				
		}
		list = new LinkedList<Item>(Arrays.asList(sortedArray));
		refreshTable(list);
	}

	//Refreshing JTable
	public void refreshTable() {
		resetID(menu.getItemList());
		updateTable.updateItemTable(stockTable, RowUpdater.updateItemRows(menu.getItemList()), stockColumns);
	}
	
	public void refreshTable(LinkedList<Item> list) {
		
		updateTable.updateItemTable(stockTable, RowUpdater.updateItemRows(list), stockColumns);
	}
	
	public void refreshTable(Menu menu) {
		resetID(menu.getItemList());
		updateTable.updateItemTable(stockTable, RowUpdater.updateItemRows(menu.getItemList()), stockColumns);
	}
	
	public void refreshTables() {
		resetID(menu.getItemList());
		updateTable.updateItemTable(stockTable, RowUpdater.updateItemRows(menu.getItemList()), stockColumns);
		updateTable.updateItemNamePriceTable(discountItemPanel.getItemNamePriceTable(),RowUpdater.updateItemPriceRows(menu.getItemList()), tableColumns.getItemNamePriceColumns());
		updateTable.updateSupplierTable(supplierListPanel.getSupplierTable(), RowUpdater.updateSupplierRows(menu.getSupplierList()), tableColumns.getSupplierColumns()); 
		updateTable.updateDiscountTable(menu.getDiscountedItemListPanel().getDiscountTable(), RowUpdater.updateDiscountRows(menu.getDiscountedItemList()), tableColumns.getDiscountColumns());
	}
	

	public JTabbedPane getStockListTabs() {
		return stockListTabs;
	}
	
	public JTable getStockTable() {
		return stockTable;
	}

}
