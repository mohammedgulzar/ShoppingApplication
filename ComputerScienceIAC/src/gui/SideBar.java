package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import menu.AdminMenu;
import menu.ManagerMenu;

public class SideBar extends JPanel {
	
	//This constructor is used for only AdminMenu
	public SideBar(AdminMenu menu, JLayeredPane layeredPane, JPanel stockListPanel, JPanel discountManagerPanel, JPanel restockMenuPanel,
			JPanel processingOrderPanel, JPanel accountSettingsPanel) {
		setBackground(Color.PINK);
		setBounds(0, 0, 126, 763);
		setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBackground(Color.PINK);
		lblLogo.setBounds(16, 10, 110, 80);
		add(lblLogo);
		
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(ManagerMenu.class.getResource("/img/Moko.png")).getImage().
				getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH));
		lblLogo.setIcon(imageIcon);
		
		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard.setBounds(0, 166, 126, 47);
		add(lblDashboard);
		
		JLabel lblStockList = new JLabel("Stock List"); 
		lblStockList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,stockListPanel);
			}
		});
		
		lblStockList.setHorizontalAlignment(SwingConstants.CENTER);
		lblStockList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblStockList.setBounds(0, 201, 126, 47);
		add(lblStockList);
		
		JLabel lblDiscountManager = new JLabel(" Discount Manager");
		lblDiscountManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,discountManagerPanel);
			}
		});
		
		lblDiscountManager.setHorizontalAlignment(SwingConstants.LEFT);
		lblDiscountManager.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDiscountManager.setBounds(0, 240, 126, 39);
		add(lblDiscountManager);
		
		JLabel lblRestockMenu = new JLabel("Restock Menu");
		lblRestockMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,restockMenuPanel);
			}
		});
		
		lblRestockMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestockMenu.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblRestockMenu.setBounds(0, 274, 126, 39);
		add(lblRestockMenu);
		
		JLabel lblProcessingOrders = new JLabel(" Processing Orders");
		lblProcessingOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,processingOrderPanel);
			}
		});
		
		lblProcessingOrders.setHorizontalAlignment(SwingConstants.LEFT);
		lblProcessingOrders.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblProcessingOrders.setBounds(0, 308, 126, 39);
		add(lblProcessingOrders);
		
		JLabel lblAccountSettings = new JLabel("Account Settings");
		lblAccountSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,accountSettingsPanel);
			}
		});
		
		lblAccountSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccountSettings.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblAccountSettings.setBounds(0, 345, 126, 47);
		add(lblAccountSettings);
		
		
		JPanel black1 = new JPanel();
		black1.setBackground(Color.BLACK);
		black1.setBounds(0, 201, 126, 5);
		add(black1);
		
		JPanel black2 = new JPanel();
		black2.setBackground(Color.BLACK);
		black2.setBounds(0, 240, 126, 5);
		add(black2);
		
		JPanel black3 = new JPanel();
		black3.setBackground(Color.BLACK);
		black3.setBounds(0, 274, 126, 5);
		add(black3);
		
		JPanel black4 = new JPanel();
		black4.setBackground(Color.BLACK);
		black4.setBounds(0, 308, 126, 5);
		add(black4);
		
		JPanel black5 = new JPanel();
		black5.setBackground(Color.BLACK);
		black5.setBounds(0, 345, 126, 5);
		add(black5);
		
		JPanel black6 = new JPanel();
		black6.setBackground(Color.BLACK);
		black6.setBounds(0, 385, 126, 5);
		add(black6);
	}
	
	//This constructor is used for only ManagerMenu
	public SideBar(ManagerMenu menu,JLayeredPane layeredPane, JPanel stockListPanel, JPanel discountManagerPanel, JPanel restockMenuPanel,
			JPanel processingOrderPanel, JPanel orderHistoryPanel, JPanel accountSettingsPanel) {
		setBackground(Color.PINK);
		setBounds(0, 0, 126, 763);
		setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBackground(Color.PINK);
		lblLogo.setBounds(16, 10, 110, 80);
		add(lblLogo);
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(ManagerMenu.class.getResource("/img/Moko.png")).getImage().
				getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH));
		lblLogo.setIcon(imageIcon);
		
		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard.setBounds(0, 166, 126, 47);
		add(lblDashboard);
		
		JLabel lblStockList = new JLabel("Stock List"); 
		lblStockList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,stockListPanel);
			}
		});
		
		lblStockList.setHorizontalAlignment(SwingConstants.CENTER);
		lblStockList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblStockList.setBounds(0, 201, 126, 47);
		add(lblStockList);
		
		JLabel lblDiscountManager = new JLabel(" Discount Manager");
		lblDiscountManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,discountManagerPanel);
			}
		});
		
		lblDiscountManager.setHorizontalAlignment(SwingConstants.LEFT);
		lblDiscountManager.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDiscountManager.setBounds(0, 240, 126, 39);
		add(lblDiscountManager);
		
		JLabel lblRestockMenu = new JLabel("Restock Menu");
		lblRestockMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,restockMenuPanel);
			}
		});
		
		lblRestockMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestockMenu.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblRestockMenu.setBounds(0, 274, 126, 39);
		add(lblRestockMenu);
		
		JLabel lblProcessingOrders = new JLabel(" Processing Orders");
		lblProcessingOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,processingOrderPanel);
			}
		});
		
		lblProcessingOrders.setHorizontalAlignment(SwingConstants.LEFT);
		lblProcessingOrders.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblProcessingOrders.setBounds(0, 308, 126, 39);
		add(lblProcessingOrders);
		
		JLabel lblOrderHistory = new JLabel("\tOrder History");
		lblOrderHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,orderHistoryPanel);
			}
		});
		
		lblOrderHistory.setHorizontalAlignment(SwingConstants.LEFT);
		lblOrderHistory.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblOrderHistory.setBounds(20, 345, 126, 39);
		add(lblOrderHistory);
		
		JLabel lblAccountSettings = new JLabel("Account Settings");
		lblAccountSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.switchPanels(layeredPane,accountSettingsPanel);
			}
		});
		
		lblAccountSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccountSettings.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblAccountSettings.setBounds(0, 385, 126, 47);
		add(lblAccountSettings);
		
		//Black Lines
		JPanel black = new JPanel();
		black.setBackground(Color.BLACK);
		black.setBounds(0, 425, 126, 5);
		add(black);
		
		JPanel black1 = new JPanel();
		black1.setBackground(Color.BLACK);
		black1.setBounds(0, 201, 126, 5);
		add(black1);
		
		JPanel black2 = new JPanel();
		black2.setBackground(Color.BLACK);
		black2.setBounds(0, 240, 126, 5);
		add(black2);
		
		JPanel black3 = new JPanel();
		black3.setBackground(Color.BLACK);
		black3.setBounds(0, 274, 126, 5);
		add(black3);
		
		JPanel black4 = new JPanel();
		black4.setBackground(Color.BLACK);
		black4.setBounds(0, 308, 126, 5);
		add(black4);
		
		JPanel black5 = new JPanel();
		black5.setBackground(Color.BLACK);
		black5.setBounds(0, 345, 126, 5);
		add(black5);
		
		JPanel black6 = new JPanel();
		black6.setBackground(Color.BLACK);
		black6.setBounds(0, 385, 126, 5);
		add(black6);
		
		
				
	}
	
	 
  	
  	
}
