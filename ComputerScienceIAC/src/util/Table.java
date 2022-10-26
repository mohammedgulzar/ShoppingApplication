package util;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table {//This class just creates the tables for each entity required for this shopping application

	private JTable table;
	
	public JTable createTable() {
		table = new JTable();
		table.setFont(new Font("Consolas", Font.PLAIN, 14));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultEditor(Object.class, null);//This makes sure that no cells within the table can be edited
		
		return table;
		
		
	}
	

}
