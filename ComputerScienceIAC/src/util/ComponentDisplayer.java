package util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ComponentDisplayer extends DefaultTableCellRenderer{
	
	//This class is used to load the image of an item onto the JTable
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		table.setRowHeight(100);
		return (Component) value;
	};
}
