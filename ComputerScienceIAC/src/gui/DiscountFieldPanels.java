package gui;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import entity.Item;
import menu.Menu;
import util.ResetText;
import util.RowUpdater;
import util.Table;
import util.TableColumns;
import util.UpdateTable;



public class DiscountFieldPanels extends JPanel{ //This class is used to hold the common fields between DiscountItemPanel and DiscountCategoryPanel, to reduce redundacies
	
	protected Table table = new Table();
	protected UpdateTable updateTable = new UpdateTable();
	protected TableColumns tableColumns = new TableColumns();
	protected ResetText rt = new ResetText();
	
	protected JLabel lblPercentage;
	protected JLabel lblPercent;
	protected JLabel lblStartingDate;
	protected JLabel lblEndingDate;
	protected JTextField percentageTF;
	protected JDateChooser startingDateChooser;
	protected JDateChooser endingDateChooser;
	protected JButton btnDiscount;
	protected JLabel lblTitle;
	
	protected DiscountListPanel discountListPanel;
	protected Menu menu;
	
	protected BigDecimal percentage;
	protected BigDecimal discountedValue;
	protected BigDecimal discountedPrice;
	
	public DiscountFieldPanels(Menu menu, DiscountListPanel discountListPanel) {
		this.menu = menu;
		this.discountListPanel = discountListPanel;
		
	}
	
		
	public void initialize() {
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		//JLabels
		lblTitle = new JLabel("Discount Specific Item");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(456, 10, 390, 80);
		add(lblTitle);
		
		lblPercentage = new JLabel("Enter the percentage that you would like to discount for: (0-100)");
		lblPercentage.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblPercentage.setBounds(355, 504, 593, 32);
		add(lblPercentage);
		
		lblPercent = new JLabel("%");
		lblPercent.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblPercent.setBounds(1080, 513, 54, 22);
		add(lblPercent);
		
		lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblStartingDate.setBounds(365, 582, 152, 32);
		add(lblStartingDate);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblEndingDate.setBounds(760, 582, 125, 32);
		add(lblEndingDate);
		
		//JTextField
		percentageTF = new JTextField();
		percentageTF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		percentageTF.setColumns(10);
		percentageTF.setBounds(974, 504, 96, 29);
		add(percentageTF);
		
		//Date Chooser 
		startingDateChooser = new JDateChooser();
		startingDateChooser.setBounds(507, 582, 125, 32);
		add(startingDateChooser);
		startingDateChooser.setMinSelectableDate(discountListPanel.getMinimumDate());
		JTextFieldDateEditor startEditor = (JTextFieldDateEditor) startingDateChooser.getDateEditor();
		startEditor.setEditable(false);
		
		
		endingDateChooser = new JDateChooser();
		endingDateChooser.setBounds(893, 582, 125, 32);
		add(endingDateChooser);
		endingDateChooser.setMinSelectableDate(discountListPanel.getMinimumDate());
		JTextFieldDateEditor endEditor = (JTextFieldDateEditor) endingDateChooser.getDateEditor();
		endEditor.setEditable(false);
		
		//Button
		btnDiscount = new JButton("Discount");
		btnDiscount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDiscount.setBounds(663, 686, 159, 53);
		add(btnDiscount);
		
		
	}
	
	public void calculateDiscountedPrice(Item item) { //This method calculated the discounted price.
		percentage = new BigDecimal(percentageTF.getText());
		percentage = percentage.divide(new BigDecimal(100));// percentage / 100
		discountedValue = item.getPrice().multiply(percentage);// item current price * percentage
		discountedPrice = item.getPrice().subtract(discountedValue);// Item current price - discounted value
		discountedPrice = discountedPrice.setScale(2,  RoundingMode.HALF_UP);//This functions rounds up to two decimal place
	}
	
	public void discountItem(Item item) { //This adjust the values of the item to the new discounted price and enabling the boolean expression saying it is discounted.
		if(!item.isDiscount()) {
			item.setPrevPrice(item.getPrice());
			item.setPrice(discountedPrice);
			item.setStartDate(startingDateChooser.getDate());
			item.setEndDate(endingDateChooser.getDate());
			item.setDiscount(true);
			menu.getDiscountedItemList().add(item); //add the discount 
			menu.updateItem(item, item.getDisplayID());
			refreshTable();	
		}
	}
	
	public boolean validDiscount() {
		Double percentage = Double.parseDouble(percentageTF.getText());
		
		if(percentage <= 0  || percentage >= 100) {
			JOptionPane.showMessageDialog(null, "The discount percentage cannot go beyond 100% and also below 0%", "Invalid discount percentage", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean validDuration() {
		if(startingDateChooser.getDate().after(endingDateChooser.getDate())) {
			JOptionPane.showMessageDialog(null, "The starting date cannot be after the ending date!", "Invalid Time Duration", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean emptyDateFields() {
		return startingDateChooser.getDate() == null || endingDateChooser.getDate() == null;
	}
	
	
	public void refreshTable() {
		updateTable.updateDiscountTable(discountListPanel.getDiscountTable(),RowUpdater.updateDiscountRows(menu.getDiscountedItemList()), tableColumns.getDiscountColumns());
	}
	
	public void reset()
	{
		rt.resetDiscountText(percentageTF, startingDateChooser, endingDateChooser);
	}
	
	
	
}
