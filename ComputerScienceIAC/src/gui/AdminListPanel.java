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

import entity.Admin;
import menu.Menu;
import util.MailSender;
import util.ObjectLoader;
import util.RowUpdater;
import util.Sorter;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class AdminListPanel extends JPanel{
	
	//Creation of JTable components
	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable adminTable = table.createTable();
	
	//Utilities for GUI
	private Sorter sorter = new Sorter();
	private MailSender mailer = new MailSender();
	
	//GUI components
	private JTabbedPane adminTabbedPane;
	private Menu menu;
	
	int adminCount;
	
	
	public AdminListPanel(Menu menu,JTabbedPane adminTabbedPane){
		this.menu = menu;
		this.adminTabbedPane = adminTabbedPane;
	}

	public void initialize() 
	{
		//Adjustments of the window frame
		setBounds(0, 0, 1414, 795); 
		setLayout(null);
		
		ObjectLoader loader = new ObjectLoader(menu, menu.getEditAdminListPanel());
		adminCount = menu.getAdminCount();
		 
		JScrollPane adminScrollPane = new JScrollPane(adminTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		adminScrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		adminScrollPane.setBounds(84, 110, 1132, 522);
		adminScrollPane.setViewportView(adminTable);
		add(adminScrollPane);
		
		refreshTable();
		
		
		//Labels
		JLabel lblTitle = new JLabel("Admin List");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 35));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(494, 0, 390, 80);
		add(lblTitle);

		
		//JButtons
		JButton btnAdd = new JButton("Add New Admin");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminTabbedPane.setEnabledAt(2,true);
				adminTabbedPane.setSelectedIndex(2);//this switch the tabs and display its contents.
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(450, 638, 159, 53);
		add(btnAdd);
		
		JButton btnEdit = new JButton("Edit Admin");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(adminTable.getSelectedRow() >= 0) {//This insist that an admin row must be selected before pressing the edit button
					adminTabbedPane.setEnabledAt(4,true);
					adminTabbedPane.setSelectedIndex(4);
					
					adminCount = Integer.parseInt(adminTable.getValueAt(adminTable.getSelectedRow(), 0).toString());//this line attempts to get the displayID of an Admin
					menu.getEditAdminListPanel().setAdminCount(adminCount);//By getting the value first column of the JTable which is the "ID" column
					menu.getEditAdminListPanel().reset();//it will first empty textfields in this panel 
					
					loader.loadAdmin(adminCount,menu.getAdminList()); // Then populate the fields with the loaded admin
				}
				else if(adminTable.getRowCount() == 0) {//if there is no admin available in the adminList
					adminTabbedPane.setEnabledAt(4,false);//This will therefore disable the tab to edit Admin
					JOptionPane.showMessageDialog(null, "There is nothing to edit", "Empty Table",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.setBounds(646, 638, 138, 53);
		add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(adminTable.getSelectedRow() >= 0) {
					adminCount = Integer.parseInt(adminTable.getValueAt(adminTable.getSelectedRow(), 0).toString());
					Admin admin = menu.getAdminList().get(adminCount);
					menu.removeAdmin(adminCount);//This will ensure to remove the admin from the database 
					
					JOptionPane.showMessageDialog(null, "Deleting the Admin. Please wait","Deleting Admin",JOptionPane.INFORMATION_MESSAGE);
					mailer.revokeAdmin(admin);//An email will be sent to the deleted admin about the deactivation of their account
					
					JOptionPane.showMessageDialog(null, admin.getFirstName() + " will receive a email about account deletion","Deletion Success",JOptionPane.INFORMATION_MESSAGE);
					refreshTable();
				}
				else {
					JOptionPane.showMessageDialog(null, "You must select one row of item to delete", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(824, 638, 159, 53);
		add(btnDelete);
		
		JComboBox sortCB = new JComboBox(new String[] {"A-Z", "Z-A"});
		sortCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedValue = sortCB.getSelectedIndex();
				switch(selectedValue) { 
					case 0://Ascending Order
						sort(menu.getAdminList(), "A");
						break;
						
					case 1://Decending Order
						sort(menu.getAdminList(), "D");
						break;
				}
					
					
			}
		});
		sortCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		sortCB.setBounds(1100, 67, 91, 38);
		add(sortCB);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();//Refresh the JTable
					
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefresh.setBounds(1005, 67, 91, 38);
		add(btnRefresh);
		
		
		
	}
	
	public void resetID(LinkedList<Admin> list) {
		for (int i = 1; i < list.size();i++) {//This will ensure that modifying the list of admins won't interfer with the manager object
			list.get(i).setDisplayID(i);	  //Hence the for loop starts at the value of 1.
		}
	}
	
	public void refreshTable() {
		//Update the display ID for admins.
		resetID(menu.getAdminList());
		updateTable.updateAdminTable(adminTable, RowUpdater.updateAdminRows(menu.getAdminList()), tableColumns.getAdminColumns());
//		First parameter = JTable object
//		Second parameter = Rows ---> This is populated by using the RowUpdater class which returns a two dimensional object array
//		Third parameter = Columns ---> TableColumns provides the names of each column within a specified JTable
	}
	
	public void refreshTable(LinkedList<Admin> list) {
		updateTable.updateAdminTable(adminTable, RowUpdater.updateAdminRows(list), tableColumns.getAdminColumns());
	}
	
	//Sorting algorithms (Recursive Bubble Sort)
	public void sort(LinkedList<Admin> list, String sortType) {
		
		Admin[] sortedArray = (Admin[]) list.toArray(new Admin[list.size()]);
		switch(sortType) {
			case "A":
				sortedArray = sorter.recursiveSortAdmin(sortedArray, "A",sortedArray.length);
				break;
			
			case "D":
				sortedArray = sorter.recursiveSortAdmin(sortedArray, "D",sortedArray.length);
				break;
		}
		list = new LinkedList<Admin>(Arrays.asList(sortedArray));
		
		refreshTable(list);
	}
	

	//Getters
	
	public JTabbedPane getAdminTabbedPane() {
		return adminTabbedPane;
	}
}
