package menu;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.LogIn;
import entity.Admin;
import gui.AddStockPanel;
import gui.DiscountCategoryPanel;
import gui.DiscountItemPanel;
import gui.DiscountListPanel;
import gui.EditAccountPanel;
import gui.EditStockPanel;
import gui.ProcessingOrderPanel;
import gui.RestockPanel;
import gui.SideBar;
import gui.StockListPanel;
import util.ObjectLoader;

public class AdminMenu extends Menu {
	private Admin admin;
	
	public AdminMenu(Admin admin) {
		super();
		this.admin = admin;
		initialize();
	}
	
	public void initialize() {
    	// Initialise the frame to pop a window for the user
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1550, 875);
		frame.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(126, 50, 1414, 795);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		/*
		  STOCK LIST DESIGN -----------------
		*/
		  
		stockListPanel = new JPanel(); 
		layeredPane.add(stockListPanel, "name_26126057024300"); stockListPanel.setLayout(null);
		
		JTabbedPane stockListTabs = new JTabbedPane(JTabbedPane.TOP);
		stockListTabs.setFont(new Font("Century Gothic", Font.BOLD, 17));
		stockListTabs.setBounds(0, 0, 1414, 795); 
		stockListPanel.add(stockListTabs);

		//This panel views the Stocks available from the application database 
		viewStockListPanel = new StockListPanel(this, stockListTabs);
		setViewStockListPanel(viewStockListPanel);
		stockListTabs.addTab("View Stock List", viewStockListPanel);

		//Adding New Item Panel
		addItemPanel = new AddStockPanel(this);
		setAddItemPanel(addItemPanel);
		stockListTabs.addTab("Add New Item", addItemPanel);
		
		//Edit Item Panel
		editStockPanel =  new EditStockPanel(this);
		setEditStockPanel(editStockPanel);
		stockListTabs.addTab("Edit Item", editStockPanel);
		
		stockListTabs.setEnabledAt(1,false);
		stockListTabs.setEnabledAt(2,false);
		
		/*
		 * Account Settings DESIGN
		 */
		
		accountSettingsPanel = new JPanel();
		layeredPane.add(accountSettingsPanel, "name_26121644724800");
		accountSettingsPanel.setLayout(null);
		
		// All of the tabs for the admin panels
		JTabbedPane adminTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		adminTabbedPane.setFont(new Font("Century Gothic", Font.BOLD, 17));
		adminTabbedPane.setBounds(0, 0, 1414, 795);
		accountSettingsPanel.add(adminTabbedPane);
		
		// Edit Account
		editAccountPanel = new EditAccountPanel(this,admin); // replace
		ObjectLoader loader = new ObjectLoader(this, editAccountPanel);
		setEditAccountPanel(editAccountPanel);
		adminTabbedPane.addTab("Edit Account", editAccountPanel);
		adminTabbedPane.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                JTabbedPane tabs = (JTabbedPane)e.getSource();
                if(tabs.getSelectedIndex() == 0) {
                	resetAdminDisplayID();
                	editAccountPanel.reset();
                	if(admin.getDisplayID()!= 0) {
                		loader.loadAdmin(admin.getDisplayID());
                	}
            		
                }
            }
		});
		editAccountPanel.setLayout(null);
		
		/*
		 * DISCOUNT MANAGER DESIGN
		 */
		

		discountManagerPanel = new JPanel();
		layeredPane.add(discountManagerPanel, "name_26127957635400");
		discountManagerPanel.setLayout(null);

		JTabbedPane discountMangerTabs = new JTabbedPane(JTabbedPane.TOP);
		discountMangerTabs.setFont(new Font("Century Gothic", Font.BOLD, 17));
		discountMangerTabs.setBounds(0, 0, 1414, 785);
		discountManagerPanel.add(discountMangerTabs);
		
		
		discountedListPanel = new DiscountListPanel(this, discountMangerTabs);
		setDiscountedItemListPanel(discountedListPanel);
		discountMangerTabs.addTab("View Discounted Items", discountedListPanel);
		
		categoryPanel = new DiscountCategoryPanel(this, discountedListPanel);
		setCategoryPanel(categoryPanel);
		discountMangerTabs.addTab("Discount Categories", categoryPanel);
		
		//Specific Items 
		
		specificItemPanel = new DiscountItemPanel(this, discountedListPanel);
		setSpecificItemPanel(specificItemPanel);
		discountMangerTabs.addTab("Discount Specific Item ", specificItemPanel); 
		
		/*
		 * Restock Menu Tab
		 */
		
		restockMenuPanel = new JPanel();
		layeredPane.add(restockMenuPanel, "name_29099126858300");
		restockMenuPanel.setLayout(null);
		
		JTabbedPane restockMenuTabs = new JTabbedPane(JTabbedPane.TOP);
		restockMenuTabs.setFont(new Font("Century Gothic", Font.BOLD, 17));
		restockMenuTabs.setBounds(0, 0, 1414, 795);
		restockMenuPanel.add(restockMenuTabs);
		
		restockPanel = new RestockPanel(this, restockMenuTabs);
		restockMenuTabs.addTab("Restock Menu", null, restockPanel, null);
		restockPanel.setLayout(null);
		
		/*
		 * Processing Order
		 */
		
		processingOrderMenuPanel = new JPanel();
		layeredPane.add(processingOrderMenuPanel, "name_29099126858300");
		processingOrderMenuPanel.setLayout(null);
		
		JTabbedPane processingOrderTabs = new JTabbedPane(JTabbedPane.TOP);
		processingOrderTabs.setFont(new Font("Century Gothic", Font.BOLD, 17));
		processingOrderTabs.setBounds(0, 0, 1414, 795);
		processingOrderMenuPanel.add(processingOrderTabs);
		
		processingOrderPanel = new ProcessingOrderPanel(this);
		processingOrderTabs.addTab("Processing Orders", null, processingOrderPanel, null);
		processingOrderPanel.setLayout(null);
		
		//Initialise
		viewStockListPanel.initialize();
		addItemPanel.initialize();
		editStockPanel.initialize();
		editAccountPanel.initialize();
		discountedListPanel.initialize();
		categoryPanel.initialize();
		specificItemPanel.initialize();
		restockPanel.initialize();
		processingOrderPanel.initialise();
		createSideBar();

		// Logout from the application

		JLabel lblLogout = new JLabel("Logout");
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //
				int result = JOptionPane.showConfirmDialog(lblLogout, "Would you like to log out?");
				switch(result) {
					case JOptionPane.YES_OPTION:
						JOptionPane.showMessageDialog(null,"You are logging out...", "Logout Successful", JOptionPane.INFORMATION_MESSAGE);
						LogIn login = new LogIn();
						login.getFrame().setVisible(true);
						frame.dispose();
						break;
				}
				
			}
		});
		lblLogout.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogout.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblLogout.setBounds(1414, 0, 126, 47);
		contentPane.add(lblLogout);
		
    }
	
	 // This method creates the side bar of the application
 	protected void createSideBar() {
 		SideBar sideBarPanel = new SideBar(this, layeredPane, stockListPanel, discountManagerPanel, restockMenuPanel,
 				processingOrderMenuPanel, accountSettingsPanel);
 		contentPane.add(sideBarPanel);

 	}
	
	public Admin getAdmin() {
		return admin;
	}
}
