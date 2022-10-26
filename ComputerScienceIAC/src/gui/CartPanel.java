package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import entity.Customer;
import entity.Item;
import entity.ProcessingOrder;
import menu.Menu;
import util.MailSender;
import util.ObjectChecker;
import util.ResetText;
import util.RowUpdater;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class CartPanel extends JPanel {
	
	private Menu menu;
	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable cartTable = table.createTable();
	private MailSender mailer = new MailSender();
	private RowUpdater rowUpdater;
	private ObjectChecker oChecker = new ObjectChecker();
	private ResetText rt = new ResetText();
	
	private JTextField fNameTF;
	private JTextField lNameTF;
	private JTextField homeAddressTF;
	private JTextField emailTF;
	private JTextField phoneNumTF;
	private JRadioButton rdbtnCard;
	private JRadioButton rdbtnCash;
	private ButtonGroup paymentTypeGroup;
	private JComboBox<String> cityComboBox;
	private LinkedList<Item> shoppingCart;
	private LinkedList<Integer> quantityList;
	
	private String fName; 
	private String lName;
	private String emailAddress;
	private String phoneNum;
	private String city; 
	private String homeAddress;
	private String paymentType;
	protected boolean validEmail;
	protected boolean validPhoneNum;
	protected boolean validFName;
	protected boolean validLName;
	protected boolean validPaymentType;
	
	public CartPanel(Menu menu)  {
		this.menu = menu;
		this.rowUpdater = new RowUpdater(menu);
		this.shoppingCart = new LinkedList<>();
		this.quantityList = new LinkedList<>();
	}
	
	
	public void initialize() {
		setBounds(0, 0, 1500, 1050);
		setLayout(null);
		
		JScrollPane cartScrollPane = new JScrollPane(cartTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cartScrollPane.setBounds(600, 72, 600, 522);
		cartScrollPane.setViewportView(cartTable);
		add(cartScrollPane);
		
		refreshTable();
		
		
		ResetText rt = new ResetText();//This object will reset the textfield to its empty form
		
		JLabel lblFName = new JLabel("First Name:");
		lblFName.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblFName.setBounds(90, 92, 131, 32);
		add(lblFName);
		
		fNameTF = new JTextField();
		fNameTF.setColumns(10);
		fNameTF.setBounds(205, 95, 305, 26);
		add(fNameTF);
		
		JLabel lblLName = new JLabel("Last Name:");
		lblLName.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblLName.setBounds(100, 141, 105, 32);
		add(lblLName);
		
		lNameTF = new JTextField();
		lNameTF.setColumns(10);
		lNameTF.setBounds(205, 144, 305, 26);
		add(lNameTF);
		
		JLabel lblEmail = new JLabel("Email Address:");
		lblEmail.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblEmail.setBounds(60, 267, 155, 32);
		add(lblEmail);
		
		emailTF = new JTextField();
		emailTF.setColumns(10);
		emailTF.setBounds(205, 270, 305, 26);
		add(emailTF);
		
		JLabel lblPhone = new JLabel("Phone Number:");
		lblPhone.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblPhone.setBounds(60, 317, 155, 32);
		add(lblPhone);
		
		phoneNumTF = new JTextField();
		phoneNumTF.setColumns(10);
		phoneNumTF.setBounds(205, 317, 305, 26);
		add(phoneNumTF);
		
		JLabel lblHomeAddress = new JLabel("Home Address:");
		lblHomeAddress.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblHomeAddress.setBounds(70, 367, 135, 32);
		add(lblHomeAddress);
		
		homeAddressTF = new JTextField();
		homeAddressTF.setColumns(10);
		homeAddressTF.setBounds(205, 367, 305, 26);
		add(homeAddressTF);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblCity.setBounds(140, 200, 65, 32);
		add(lblCity);
		
		cityComboBox = new JComboBox(new String[] {"Abu Dhabi","Ajman", "Dubai","Fujairah","Ra's al-Khaimah","Sharjah", "Umm al-Qaiwain"});
		cityComboBox.setFont(new Font("Consolas", Font.PLAIN, 18));
		cityComboBox.setBounds(205, 200, 205, 32);
		add(cityComboBox);
		
		JLabel lblPayment = new JLabel("Payment:");
		lblPayment.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblPayment.setBounds(120, 433, 83, 32);
		add(lblPayment);
		
		
		rdbtnCard = new JRadioButton("Card on delivery");
		rdbtnCard.setFont(new Font("Consolas", Font.PLAIN, 17));
		rdbtnCard.setBounds(221, 438, 189, 21);
		add(rdbtnCard);
		
		
		rdbtnCash = new JRadioButton("Cash");
		rdbtnCash.setFont(new Font("Consolas", Font.PLAIN, 17));
		rdbtnCash.setBounds(221, 477, 65, 21);
		add(rdbtnCash);
		
		paymentTypeGroup = new ButtonGroup();
		paymentTypeGroup.add(rdbtnCard);
		paymentTypeGroup.add(rdbtnCash);
		
		
		
		JButton btnDelete = new JButton("Remove Item");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cartTable.getSelectedRow() >= 0) {
					int count = cartTable.getSelectedRow();
					Item item = menu.getItemList().get(count);
					int result = JOptionPane.showConfirmDialog(btnDelete, "Would you like to remove the item entirely from the order list");
					
					if(result == JOptionPane.YES_OPTION) {
						shoppingCart.remove(count);
						quantityList.remove(count);
						JOptionPane.showMessageDialog(null, "Item has been removed from the shopping cart", "Removal of Item from Shopping cart",JOptionPane.PLAIN_MESSAGE);
						refreshTable();
					}
					else if(result == JOptionPane.NO_OPTION) {
						if(quantityList.get(count) == 1) {
							shoppingCart.remove(count);
							quantityList.remove(count);
						}
						else {
							quantityList.set(count, quantityList.get(count) - 1);
						}
						refreshTable();
					}
					
				}
				else if(cartTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "There is nothing to delete", "Empty Shopping Cart",JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(550, 661, 150, 60);
		add(btnDelete);
		
		JButton btnOrder = new JButton("Proceed to Order");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!shoppingCart.isEmpty()) {
					setVariables();
					if(!emptyFields() && validDatas()) {
						Customer customer = new Customer(fName, lName, phoneNum, emailAddress, city, homeAddress, paymentType);
						customer.setReceipt(makeReceipt());
						ProcessingOrder order = new ProcessingOrder(new Date(), customer, getTotal());
						menu.addPOrder(order);
						depleteQuantity();
						JOptionPane.showMessageDialog(null, "Thank you for using Moko Shopping Application, you will receive your receipt of your order. Please Wait.", "Order Sucess",JOptionPane.INFORMATION_MESSAGE);
						mailer.sendReceipt(customer);
						mailer.notifyAdmins(customer, menu.getAdminList());
						JOptionPane.showMessageDialog(null, "Order receipt has been sent to your email", "Receipt Sent",JOptionPane.INFORMATION_MESSAGE);
						menu.getShoppingPagePanel().refreshTable();
						reset();
						
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "There is nothing in the shopping cart", "Empty Shopping Cart",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnOrder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOrder.setBounds(750, 661, 150, 60);
		add(btnOrder);
	}
	
	public void reset() {
		rt.resetPersonalDetailsText(fNameTF, lNameTF, emailTF, phoneNumTF, cityComboBox, homeAddressTF, rdbtnCard, rdbtnCash);
		for (int i = 0; i < shoppingCart.size(); i++) {
			shoppingCart.remove(i);
			quantityList.remove(i);
		}
		refreshTable();
	}
	
	public boolean emptyFields(){
		if(fName.isEmpty()||lName.isEmpty()||emailAddress.isEmpty()||phoneNum.isEmpty()||homeAddress.isEmpty()||paymentType.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Make sure to fill in all of the fields", "Missing Data", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
	
	public void setVariables() {
		fName = fNameTF.getText().trim(); 
		lName = lNameTF.getText().trim();
		emailAddress = emailTF.getText().trim();
		phoneNum = phoneNumTF.getText().trim();
		city = cityComboBox.getSelectedItem().toString(); 
		homeAddress = homeAddressTF.getText().trim();
		if(rdbtnCard.isSelected()) {
			paymentType = rdbtnCard.getText();
		}
		else {
			paymentType = rdbtnCash.getText();
		}
		validEmail = oChecker.verifyEmail(emailAddress);
		validPhoneNum = oChecker.verifyPhoneNumber(phoneNum);
		validFName = oChecker.validName(fName);
		validLName = oChecker.validName(lName);
		validPaymentType = rdbtnCard.isSelected() || rdbtnCash.isSelected();
	}
	
	public boolean validDatas() {
		if(!(validFName)) { //This regular expression ensures that the first and last name does not contain any numerical values.
			JOptionPane.showMessageDialog(null, "First name must not contain any numbers!", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!(validLName)) {
			JOptionPane.showMessageDialog(null, "Last name must not contain any numbers!", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(validEmail)){
			JOptionPane.showMessageDialog(null,"Email address must contain @ and characters after as well","Invalid Email Address" , JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(validPhoneNum)) {
			JOptionPane.showMessageDialog(null,"Phone number must contain only 10 digits.","Invalid Phone Number" , JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(validPaymentType)) {
			JOptionPane.showMessageDialog(null, "You must select one payment type: Cash or Card on delivery","Payment Type Error" , JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		if(!shoppingCart.isEmpty()) {
			for (int i = 0; i < shoppingCart.size(); i++) {
				Item item = shoppingCart.get(i);
				total = total.add(shoppingCart.get(i).getPrice().multiply(new BigDecimal(quantityList.get(i))));
			}
			return total;
		}
		return total;
	} 
	
	public String makeReceipt() {
		String receipt = "";
		for (int i = 0; i < shoppingCart.size(); i++) {
			Item item = shoppingCart.get(i);
			receipt = receipt + item.getName() +"\n";
			receipt = receipt + "\t$" +item.getPrice() +"  *  "+ Integer.toString(quantityList.get(i)) + " Unit\n";
		}
		receipt = receipt + "-".repeat(80)+"\n";
		receipt = receipt + String.format("Total Payment %40s\n", "$"+getTotal());
		return receipt;
	}
	
	public void depleteQuantity() {
		for (int i = 0; i < shoppingCart.size(); i++) {
			Item chosenItem = shoppingCart.get(i);
			
			for (int j = 0; j < menu.getItemList().size(); j++) {
				Item shoppingItem = menu.getItemList().get(j);
				
				if(chosenItem.getDisplayID() == shoppingItem.getDisplayID() && shoppingItem.hasStock()) {
					int depletingQuantity = shoppingItem.getQuantity() - quantityList.get(i);
					shoppingItem.setQuantity(depletingQuantity);
					menu.updateItem(shoppingItem, j);
					if(menu.getItemList().get(j).getQuantity() <= 5) {
						mailer.mailLowStocks(shoppingItem, menu.getAdminList().get(0));//fix this
					}
				}		
			}
		}	
	}
	
	
	
	
	public void refreshTable() {
		updateTable.updateCartTable(cartTable, rowUpdater.updateCartRows(shoppingCart, quantityList), tableColumns.getCartColumn());
	}
	
	public LinkedList<Item> getShoppingCart() {
		return shoppingCart;
	}
	
	public void setShoppingCart(LinkedList<Item> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public LinkedList<Integer> getQuantityList() {
		return quantityList;
	}
	
	public void setQuantityList(LinkedList<Integer> quantityList) {
		this.quantityList = quantityList;
	}
	
}
