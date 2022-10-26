package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import entity.OrderHistory;
import menu.Menu;
import util.RowUpdater;
import util.Table;
import util.TableColumns;
import util.UpdateTable;

public class OrderHistoryPanel extends JPanel {

	private Table table = new Table();
	private UpdateTable updateTable = new UpdateTable();
	private TableColumns tableColumns = new TableColumns();
	private JTable orderHistoryTable = table.createTable();
	private JTextArea detailArea;
	private Menu menu;
	
	public OrderHistoryPanel(Menu menu) {
		this.menu = menu;
		
	}
	
	public void initialise() {
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Order History");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 30));
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBounds(490, 0, 390, 67);
		add(lblTitle);
		
		detailArea= new JTextArea(); //This textarea is used to display the orders
		detailArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		detailArea.setBounds(955, 72, 325, 400);
		detailArea.setLayout(null);
		detailArea.setEditable(false);
		add(detailArea);
		header();
		
		JScrollPane scrollPane = new JScrollPane(orderHistoryTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(100, 72, 805, 522);
		scrollPane.setViewportView(orderHistoryTable);
		add(scrollPane);
		
		orderHistoryTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int orderCount = orderHistoryTable.getSelectedRow();
				OrderHistory order = menu.getOrderHistory().get(orderCount);
				header(order);
			}
		});
		
		refreshTable();
	}
	
	public void refreshTable() {
		updateTable.updateOrderHistoryTable(orderHistoryTable, 
				RowUpdater.updateOrderHistoryRows(menu.getOrderHistory()),
				tableColumns.getOrderHistoryColumns()
				);
	}
	
	public void header() {
		detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"\t\tReceipt\n");
		detailArea.setText(detailArea.getText() +"-".repeat(400)+"\n");
		detailArea.setText(detailArea.getText() +"Items: \n");
	}
	
	public void header(OrderHistory order) {
		detailArea.setText("");
		detailArea.setText(detailArea.getText() +"Order No.: "+ order.getID()+"\n");
		header();
		detailArea.setText(detailArea.getText() + order.getCustomer().getReceipt());
	}
}
