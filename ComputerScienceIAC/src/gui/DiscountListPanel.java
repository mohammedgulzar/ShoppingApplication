package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import entity.Item;
import menu.Menu;
import util.MailSender;
import util.RowUpdater;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class DiscountListPanel extends JPanel {
	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable discountTable = table.createTable();
	private Menu menu;
	private MailSender mailer = new MailSender();
	private Date minimumDate =  new Date();
	private JTabbedPane discountManagerTabs;
	


	public DiscountListPanel(Menu menu, JTabbedPane discountManagerTabs) {
		
		this.menu = menu;
		this.discountManagerTabs = discountManagerTabs;
	}
	
	public void initialize() {
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(discountTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane.setBounds(174, 92, 1100, 522);
		scrollPane.setViewportView(discountTable);
		add(scrollPane);
		
		if(menu.getDiscountedItemList() != null) {
			refreshTable();
			expiredDiscount();
		}
		
		JButton btnDiscountItem = new JButton("Discount Item");  //This will switch to the Discount Item Panel
		btnDiscountItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				discountManagerTabs.setEnabledAt(2,true);
				discountManagerTabs.setSelectedIndex(2);
			}
		});
		btnDiscountItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDiscountItem.setBounds(434, 684, 175, 53);
		add(btnDiscountItem);
		
		JButton btnDiscountCategories = new JButton("Discount Categories");  //This will switch to the Discount Categories Panel
		btnDiscountCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				discountManagerTabs.setEnabledAt(1,true);
				discountManagerTabs.setSelectedIndex(1);
			}
		});
		btnDiscountCategories.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDiscountCategories.setBounds(637, 684, 175, 53);
		add(btnDiscountCategories);
		
		JButton btnCancelDiscount = new JButton("Cancel Discount"); //This will cancel the discount of an item 
		btnCancelDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(discountTable.getSelectedRow() >= 0) {
					int itemCount = discountTable.getSelectedRow();
					cancel(itemCount);
				}
				else if(discountTable.getSelectedRowCount() != 0) {
					JOptionPane.showMessageDialog(null, "You have not selected an item to cancel", "Cancel Failure",JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "There is nothing to canel", "Empty Table",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCancelDiscount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelDiscount.setBounds(833, 684, 190, 53);
		add(btnCancelDiscount);
		
		JLabel lblDiscountedItems = new JLabel("Discounted Items");
		lblDiscountedItems.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiscountedItems.setFont(new Font("Consolas", Font.BOLD, 30));
		lblDiscountedItems.setBackground(Color.WHITE);
		lblDiscountedItems.setBounds(561, 2, 390, 80);
		add(lblDiscountedItems);
		
		
	}
	
	public void cancel(int itemCount) {
		Item item = menu.getDiscountedItemList().get(itemCount);
		item.setPrice(item.getPrevPrice());
		item.setStartDate(null);
		item.setEndDate(null);
		item.setDiscount(false);
		this.menu.updateItem(item, item.getDisplayID());
		this.menu.getDiscountedItemList().remove(itemCount);
		refreshTable();
		JOptionPane.showMessageDialog(null, item.getName() +" has been successfully cancelled");
	}
	
	public void refreshTable() {
		updateTable.updateDiscountTable(discountTable, RowUpdater.updateDiscountRows(menu.getDiscountedItemList()), tableColumns.getDiscountColumns());
		this.menu.getSpecificItemPanel().refreshTable();
		this.menu.getViewStockListPanel().refreshTable();
	}
	
	public JTable getDiscountTable() {
		return discountTable;
	}
	
	public Date getMinimumDate() {
		return minimumDate;
	}
	
	public void expiredDiscount() {
		ArrayList<Item> finishedItemList = new ArrayList<>();
		int count = 0;
		
		while (count < this.menu.getDiscountedItemList().size()){
			
			Item discountedItem = this.menu.getDiscountedItemList().get(count);
			Date expiryDate = discountedItem.getEndDate();
			
			if(minimumDate.after(expiryDate)) {
				cancel(count);
				finishedItemList.add(discountedItem);
			}
			count++;
		}
		
		if(!(finishedItemList.isEmpty())) {
			JOptionPane.showMessageDialog(null, "The discount of some items have expired. Please wait an email will be sent");
			mailer.mailExpiredDiscountedItem(finishedItemList, menu.getAdminList());
		}
	}
	
	
	
	
}