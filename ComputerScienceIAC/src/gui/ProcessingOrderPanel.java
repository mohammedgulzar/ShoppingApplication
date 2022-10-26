package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import entity.Customer;
import entity.ProcessingOrder;
import menu.Menu;
import util.RowUpdater;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class ProcessingOrderPanel extends JPanel {

	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable pOrderTable = table.createTable();
	private JTextArea detailArea;
	private Menu menu;
	
	public ProcessingOrderPanel(Menu menu) {
		this.menu = menu;
	}
	
	public void initialise() {
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Processing Orders");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(490, 0, 390, 67);
		add(lblTitle);
		
		detailArea= new JTextArea(); //This textarea is used to display the orders
		detailArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		detailArea.setBounds(955, 72, 350, 450);
		detailArea.setLayout(null);
		detailArea.setEditable(false);
		add(detailArea);
		header();
		
		JScrollPane pOrderScrollPane = new JScrollPane(pOrderTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pOrderScrollPane.setBounds(100, 72, 805, 522);
		pOrderScrollPane.setViewportView(pOrderTable);
		add(pOrderScrollPane);
		
		refreshTable();
		
		JButton btnOrderProcessed = new JButton("Proccess Order");
		btnOrderProcessed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pOrderTable.getRowCount() > 0) {
					menu.removePOrder();
					refreshTable();
					menu.getOrderHistoryPanel().refreshTable();
					header();
					JOptionPane.showMessageDialog(null, "This order has been processed.", "Order Added to Order History",JOptionPane.INFORMATION_MESSAGE);
					//Mail Customer.
				}
				else if(pOrderTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "There is no orders available for processing", "No Orders Available",JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnOrderProcessed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOrderProcessed.setBounds(575, 630, 159, 53);
		add(btnOrderProcessed);
		
		//mouse action
		//int count = Integer.parseInt(pOrderTable.getValueAt(pOrderTable.getSelectedRow(), 0).toString());
		
		
	}
	
	public void refreshTable() {
		updateTable.updateProcessingOrderTable(pOrderTable, 
				RowUpdater.updateProcessingOrderRows(menu.getProcessingOrderList()),
				tableColumns.getProcessingOrderColumns()
				);
	}
	
	
	public void header() {
		if(!menu.getProcessingOrderList().isEmpty()){
			ProcessingOrder order = menu.getProcessingOrderList().peek();
			Customer customer = order.getCustomer();
			detailArea.setText("");
			detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
			detailArea.setText(detailArea.getText() +"\t\tCustomer\n");
			detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
			
			detailArea.setText(detailArea.getText() +"Order No.: "+ order.getID() +"\n");
			detailArea.setText(detailArea.getText() +"Customer Name: "+ customer.getFirstName() + " "+ customer.getLastName() +"\n");
			detailArea.setText(detailArea.getText() +"Email: " + customer.getEmailAddress() +"\n");
			detailArea.setText(detailArea.getText() +"Phone Number: " + customer.getPhoneNumber() +"\n");
			detailArea.setText(detailArea.getText() +"City: " + customer.getCity() +"\n");
			detailArea.setText(detailArea.getText() +"Home Address: " + customer.getHomeAddress() +" \n");
			detailArea.setText(detailArea.getText() +"Payment Type: " + customer.getPaymentType() +" \n");
			detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
			
			detailArea.setText(detailArea.getText() +"\t\tReceipt\n");
			detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
			detailArea.setText(detailArea.getText() + "Items \n");
			detailArea.setText(detailArea.getText() + customer.getReceipt());
		}
		else {
			detailArea.setText("");
		}
		
	}
	
	

}
