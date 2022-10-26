package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import client.LogIn;
import entity.Abaya;
import entity.Cardigan;
import entity.Item;
import entity.Scarf;
import menu.Menu;
import util.BinarySearcher;
import util.ComponentDisplayer;
import util.ObjectLoader;
import util.RowUpdater;
import util.Sorter;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class ShoppingPagePanel extends JPanel {
	
	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable shoppingTable = table.createTable();
	private BinarySearcher searcher = new BinarySearcher();
	private Sorter sorter = new Sorter();
	private RowUpdater rowUpdater;
	private Menu menu;
	
	//GUI Components
	private JTextArea detailArea;
	private JTextField quantityOrderTF;
	private JTextField searchTF;
	private JButton btnAdd;
	private JButton btnPlus;
	private JButton btnMinus;
	private ObjectLoader loader;
	
	private JLabel lblImageDisplay;
	
	public ShoppingPagePanel(Menu menu) {
		this.menu = menu;
		this.rowUpdater = new RowUpdater(menu);
	}
	
	public void initialize() {
		setBounds(0, 0, 1500, 1050);
		setLayout(null);
		
		JLabel lblLogo = new JLabel();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(LogIn.class.getResource("/img/Moko.png")).getImage().getScaledInstance(200, 76, Image.SCALE_SMOOTH));
		lblLogo.setIcon(imageIcon);
		lblLogo.setBounds(730, 0, 205, 80);
		add(lblLogo);
		
		JScrollPane scrollPane = new JScrollPane(shoppingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane.setBounds(20, 132, 799, 560);
		scrollPane.setViewportView(shoppingTable);
		add(scrollPane);
		refreshTable();
		
		loader = new ObjectLoader(menu);
		
		lblImageDisplay = new JLabel();
		lblImageDisplay.setBounds(1035, 138, 189, 184);
		add(lblImageDisplay);
		
		detailArea= new JTextArea(); //This textarea is used to display the orders
		detailArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		detailArea.setBounds(955, 336, 400, 325);
		detailArea.setLayout(null);
		detailArea.setBackground(this.getBackground());
		detailArea.setLineWrap(true);
		detailArea.setEditable(false);
		
		add(detailArea);
		
		shoppingTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int itemCount = Integer.parseInt(shoppingTable.getValueAt(shoppingTable.getSelectedRow(), 0).toString());
				Item item = menu.getItemList().get(itemCount);
				infoItem(item);
			}
		});
		
		quantityOrderTF = new JTextField();
		quantityOrderTF.setFont(new Font("Consolas", Font.CENTER_BASELINE, 18));
		quantityOrderTF.setBounds(1085, 670, 60, 30);
		quantityOrderTF.setEditable(false);
		quantityOrderTF.setHorizontalAlignment(JTextField.CENTER);
		add(quantityOrderTF);
		
		searchTF = new JTextField();
		searchTF.setColumns(10);
		searchTF.setBounds(10, 87, 202, 38);
		searchTF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(searchTF);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() { // Add Button
			public void actionPerformed(ActionEvent e) {
				if(!searchTF.getText().trim().isEmpty()) {
					refreshTable();
					String criteria = searchTF.getText();
					if(menu.getItemList().size() > 1) {
						Item[] sortedArray = (Item[]) menu.getItemList().toArray(new Item[menu.getItemList().size()]);
						
						searcher.search(menu, sortedArray, sorter.sortAscendingItem(sortedArray, 0), 0, criteria);
					}
					else {
						if(menu.getItemList().get(0).getName().compareTo(criteria) != 0) {
							JOptionPane.showMessageDialog(null, criteria + " is not found in the table", "Failed to find search criteria",JOptionPane.ERROR_MESSAGE);
						}
					}
					searchTF.setText("");
				}
				else{
					JOptionPane.showMessageDialog(null, "The search criteria is empty", "Empty search criteria",JOptionPane.ERROR_MESSAGE);
					refreshTable();
				}
				
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(212,87, 91, 38);
		add(btnSearch);
		
		JComboBox sortCB = new JComboBox(new String[] {"A-Z", "Z-A", "Lowest to Highest Price", "Abaya", "Cardigan", "Scarf", "On Discount"});
		sortCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu.getItemList().size()>0) {
					LinkedList<Item> loadItems;
					int value = sortCB.getSelectedIndex();
					Item[] sortedArray = (Item[]) menu.getItemList().toArray(new Item[menu.getItemList().size()]);
					switch(value) {
						case 0:
							sortedArray = sorter.recursiveSortItem(sortedArray, "A",sortedArray.length);
							loadItems = new LinkedList<Item>(Arrays.asList(sortedArray));
							refreshTable(loadItems);
							break;
						case 1:
							sortedArray = sorter.recursiveSortItem(sortedArray, "D",sortedArray.length);
							loadItems = new LinkedList<Item>(Arrays.asList(sortedArray));
							refreshTable(loadItems);
							break;
						case 2:
							sortedArray = sorter.recursiveSortItem(sortedArray, "P",sortedArray.length);
							loadItems = new LinkedList<Item>(Arrays.asList(sortedArray));
							refreshTable(loadItems);
							break;
						case 3:
							loadItems = new LinkedList<Item>();
							for (int i = 0; i < menu.getItemList().size(); i++) {
								if(menu.getItemList().get(i) instanceof Abaya) {
									loadItems.add(menu.getItemList().get(i));
								}
							}
							refreshTable(loadItems);
							break;
						case 4:
							loadItems = new LinkedList<Item>();
							for (int i = 0; i < menu.getItemList().size(); i++) {
								if(menu.getItemList().get(i) instanceof Cardigan) {
									loadItems.add(menu.getItemList().get(i));
								}
							}
							refreshTable(loadItems);
							break;
						case 5:
							loadItems = new LinkedList<Item>();
							for (int i = 0; i < menu.getItemList().size(); i++) {
								if(menu.getItemList().get(i) instanceof Scarf) {
									loadItems.add(menu.getItemList().get(i));
								}
							}
							refreshTable(loadItems);
							break;
						case 6:
							loadItems = new LinkedList<Item>();
							for (int i = 0; i < menu.getItemList().size(); i++) {
								if(menu.getItemList().get(i).isDiscount()) {
									loadItems.add(menu.getItemList().get(i));
								}
							}
							refreshTable(loadItems);
							break;
							
					}
				}
				
			}
		});
		
		sortCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		sortCB.setBounds(540, 87, 285, 38);
		add(sortCB);

		
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() { // Add Button
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefresh.setBounds(301,87, 91, 38);
		add(btnRefresh);
		
		
		btnPlus = new JButton("+");
		btnPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int increment = Integer.parseInt(quantityOrderTF.getText()) + 1;
				quantityOrderTF.setText(Integer.toString(increment));
			}
		});
		btnPlus.setEnabled(false);
		btnPlus.setBounds(1145, 670, 50, 30);
		btnPlus.setFont(new Font("Calibri", Font.BOLD, 18));
		add(btnPlus);
		
		btnMinus = new JButton("-");
		btnMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(quantityOrderTF.getText().equals("1"))) {
					int decrease = Integer.parseInt(quantityOrderTF.getText()) - 1;
					quantityOrderTF.setText(Integer.toString(decrease));
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot order zero or lower quantity", "Invalid Quantity",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnMinus.setEnabled(false);
		btnMinus.setBounds(1035, 670, 50, 30);
		btnMinus.setFont(new Font("Calibri", Font.BOLD, 18));
		add(btnMinus);
		
		btnAdd = new JButton("Add to Shopping Cart");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(shoppingTable.getSelectedRow() >= 0) {
						int count = Integer.parseInt(shoppingTable.getValueAt(shoppingTable.getSelectedRow(), 0).toString());//This will get the ID number of the item
						int quantity = Integer.parseInt(quantityOrderTF.getText());
						boolean invalid = false;
						Item item = menu.getItemList().get(count);
						
						if(!menu.getCartPanel().getShoppingCart().isEmpty()) {
							boolean exist = false;
							for (int i = 0; i < menu.getCartPanel().getShoppingCart().size(); i++) {//Loop over the orderList to check whether the desired item is in the list
								if(menu.getCartPanel().getShoppingCart().get(i).getDisplayID() == item.getDisplayID()) { //This is possible by comparing the ID 
									exist = true;
									if(quantity + menu.getCartPanel().getQuantityList().get(i) <= item.getQuantity()) {
										menu.getCartPanel().getQuantityList().set(i, menu.getCartPanel().getQuantityList().get(i) + quantity);
										JOptionPane.showMessageDialog(null, "Adding more quantity to the shopping cart");
										
									}
									else {
										invalid = true;
									}
								}
							}
							if(exist == false) {
								if(quantity <= item.getQuantity()) {
									menu.getCartPanel().getShoppingCart().add(item);
									menu.getCartPanel().getQuantityList().add(quantity);
									JOptionPane.showMessageDialog(null, "Adding " + item.getName() +" to the shopping cart");
								}
								else {
									invalid = true;
								}
							}
						}
						else {
							if(quantity <= item.getQuantity()) {
								menu.getCartPanel().getShoppingCart().add(item);
								menu.getCartPanel().getQuantityList().add(quantity);
								JOptionPane.showMessageDialog(null, "Adding " + item.getName() +" to the shopping cart");
							}
							else {
								invalid = true;
							}
						}
						
						if(invalid) {
							JOptionPane.showMessageDialog(null, "The maximum amount that you can order for this item is "+ item.getQuantity(), "Invalid Quantity",JOptionPane.ERROR_MESSAGE);
						}
						quantityOrderTF.setText("1");
						menu.getCartPanel().refreshTable();
					}
					else if(shoppingTable.getRowCount() == 0) {
						JOptionPane.showMessageDialog(null, "There is no item available for ordering. Come back next time.", "Empty Table",JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please select an item from the table to order.","",JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "To add an item to shopping cart, please select a row from the table on the left", "",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAdd.setEnabled(false);
		btnAdd.setBounds(1035, 700, 160, 53);
		btnAdd.setFont(new Font("Calibri", Font.PLAIN, 13));
		add(btnAdd);

	}
	
	public void infoItem(Item item) {
		loader.loadImageToLabel(lblImageDisplay, item.getImagePath());
		
		detailArea.setText("");
		detailArea.setText(detailArea.getText() + "Name: " +  item.getName() + "\n"); 
		detailArea.setText(detailArea.getText() + "Price: $" +  item.getPrice() + "\n");
		detailArea.setText(detailArea.getText() + "Quantity: " +  Integer.toString(item.getQuantity()) + "\n");
		detailArea.setText(detailArea.getText() + "Size: " +  item.getSize() + "\n");
		detailArea.setText(detailArea.getText() + "Description: " +  item.getDescription() + "\n");
		detailArea.setText(detailArea.getText() + "Garment Care: " +  item.getGarmentCare() + "\n");
		detailArea.setText(detailArea.getText() + "Season Type: " +  item.getCategory() + "\n");
		
		if (item instanceof Abaya) {
			Abaya abaya = (Abaya) item;
			detailArea.setText(detailArea.getText() + "Fabrics: " +  abaya.getFabrics() + "\n"); 
			detailArea.setText(detailArea.getText() + "Length: " +  abaya.getLength() + "\n");
			detailArea.setText(detailArea.getText() + "Weight: " +  abaya.getWeight() + "\n");
		}
		else if(item instanceof Cardigan) {
			Cardigan cardigan = (Cardigan) item;
			detailArea.setText(detailArea.getText() + "Compositions: " +  cardigan.getCompositions() + "\n"); 
		}
		else if (item instanceof Scarf) {
			Scarf scarf = (Scarf) item;
			detailArea.setText(detailArea.getText() + "Fabrics: " +  scarf.getFabrics() + "\n");
			detailArea.setText(detailArea.getText() + "Length: " +  scarf.getLength() + "\n");
			detailArea.setText(detailArea.getText() + "Fabrics: " +  scarf.getHeight() + "\n"); 
			detailArea.setText(detailArea.getText() + "Weight: " +  scarf.getThickness() + "\n");
		}
		quantityOrderTF.setText("1");
		btnPlus.setEnabled(true);
		btnMinus.setEnabled(true);
		btnAdd.setEnabled(true);
	}
	
	public void resetID(LinkedList<Item> list) {
		for (int i = 0; i < list.size();i++) {
			list.get(i).setDisplayID(i);
		}
	}
	
	public void refreshTable() {
		updateTable.updateShoppingTable(shoppingTable, rowUpdater.updateShoppingRows(menu.getItemList()), tableColumns.getShoppingColumn());
		shoppingTable.getColumn("Image").setCellRenderer(new ComponentDisplayer());
	}
	
	public void refreshTable(LinkedList<Item> list) {
		resetID(menu.getItemList());
		updateTable.updateShoppingTable(shoppingTable, rowUpdater.updateShoppingRows(list), tableColumns.getShoppingColumn());
		shoppingTable.getColumn("Image").setCellRenderer(new ComponentDisplayer());
	}
	
	public JButton getBtnAdd() {
		return btnAdd;
	}
}
