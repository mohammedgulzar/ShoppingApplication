package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gui.CartPanel;
import gui.ShoppingPagePanel;
import menu.Menu;

public class ShoppingApp {
	
	//GUI Components
	private JFrame frame;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	protected JPanel shoppingMenuPanel;
	protected CartPanel cartPanel;
	protected ShoppingPagePanel shoppingPagePanel;
	protected Menu menu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShoppingApp window = new ShoppingApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShoppingApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//Instantiate a frame object and adjust its settings
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("Moko Shopping Application");
		frame.setBounds(0, 0, 1500, 1050);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		contentPane = new JPanel();
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1500, 1050);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		menu = new Menu();
		
		/*
		  Shopping Menu DESIGN -----------------
		*/
		
		shoppingMenuPanel = new JPanel(); 
		layeredPane.add(shoppingMenuPanel, "name_26126057024300"); shoppingMenuPanel.setLayout(null);
		
		JTabbedPane shoppingTabs = new JTabbedPane(JTabbedPane.TOP);
		shoppingTabs.setFont(new Font("Century Gothic", Font.BOLD, 17));
		shoppingTabs.setBounds(0, 0, 1500, 1050); 
		shoppingMenuPanel.add(shoppingTabs);

		//This panel views the Stocks available from the application database 
		shoppingPagePanel = new ShoppingPagePanel(menu);
		menu.setShoppingPagePanel(shoppingPagePanel);
		shoppingTabs.addTab("Front Page", shoppingPagePanel);
		
		//Adding New Item Panel
		cartPanel = new CartPanel(menu);
		menu.setCartPanel(cartPanel);
		shoppingTabs.addTab("Shopping Cart", cartPanel);
		
		//Initialise
		shoppingPagePanel.initialize();
		cartPanel.initialize();
		
	}
}
