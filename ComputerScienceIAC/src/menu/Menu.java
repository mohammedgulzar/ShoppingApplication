package menu;

import entity.Admin;
import entity.Item;
import entity.OrderHistory;
import entity.ProcessingOrder;
import entity.Supplier;
import gui.AddAdminPanel;
import gui.AddStockPanel;
import gui.AddSupplierPanel;
import gui.AdminListPanel;
import gui.CartPanel;
import gui.DiscountCategoryPanel;
import gui.DiscountItemPanel;
import gui.DiscountListPanel;
import gui.EditAccountPanel;
import gui.EditAdminPanel;
import gui.EditStockPanel;
import gui.EditSupplierPanel;
import gui.OrderHistoryPanel;
import gui.ProcessingOrderPanel;
import gui.RestockPanel;
import gui.ShoppingPagePanel;
import gui.SideBar;
import gui.StockListPanel;
import gui.SupplierListPanel;
import util.HibernateUtil;
import util.ObjectLoader;
import util.ShoppingDatabase;
import util.RowUpdater;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.hibernate.HibernateError;
import org.hibernate.Session;

public class Menu {
	
	protected int itemCount; //This is used for the ability to switch to the next or previous object
	protected int adminCount;
	protected int supplierCount;
		
	//GUI Components
	protected JFrame frame;
	protected JPanel contentPane;
	protected JLayeredPane layeredPane;
	protected JPanel stockListPanel;
	protected JPanel discountManagerPanel;
	protected JPanel processingOrderMenuPanel;
	protected JPanel restockMenuPanel;
	protected JPanel accountSettingsPanel;
	protected JPanel abayaInfoPanel_E;
	protected JPanel cardiganInfoPanel_E;
	protected JPanel scarfInfoPanel_E;
	
	protected StockListPanel viewStockListPanel;
	protected AddStockPanel addItemPanel;
	protected EditStockPanel editStockPanel;
	protected AdminListPanel adminListPanel;
	protected SupplierListPanel supplierListPanel;
	protected AddSupplierPanel addSupplierListPanel;
	protected AddAdminPanel addAdminPanel;
	protected EditAdminPanel editAdminPanel;
	protected EditSupplierPanel editSupplierListPanel;
	protected EditAccountPanel editAccountPanel;
	protected DiscountListPanel discountedListPanel;
	protected DiscountCategoryPanel categoryPanel;
	protected DiscountItemPanel specificItemPanel;
	protected RestockPanel restockPanel;
	protected ProcessingOrderPanel processingOrderPanel;
	protected OrderHistoryPanel orderHistoryPanel;
	protected CartPanel cartPanel;
	protected ShoppingPagePanel shoppingPagePanel;
	

	//Declaring Variables
	private LinkedList<Item> itemList;
	private LinkedList<Item> discountedItemList = new LinkedList<Item>();
	private LinkedList<Admin> adminList;
	private LinkedList<Supplier> supplierList;
    private Queue<ProcessingOrder> processingOrderList;
    private Stack<OrderHistory> orderHistory;
    private ShoppingDatabase shoppingDB = new ShoppingDatabase();
    
    
    public Menu() {
    	//Instantiate the lists by reading from the shopping Application database
    	this.itemList = readStockListDB(); //reading from stock table
    	this.adminList = readAdminDB();// reading from admin table
    	this.supplierList = readSupplierDB();// reading from supplier table
    	listAllDiscountedItems();// This is where the discountedItemList gets populated
    	this.orderHistory = readOrderHistoryDB();// reading from Order History table
    	this.processingOrderList = readProcessingOrderDB(); // reading from Processing Order table
    	
    }
    
	//Create, Update Deletion operations on Item
    public void addItem(Item item){ //This function adds the item to the database
    	item.setDisplayID(itemList.size());//This will set the display ID to the size of the List
    	itemList.add(item);//Adding the item to the menu itemList
    	Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(itemList.get(itemList.size()-1));//This will add item to the stock table 
			session.getTransaction().commit();//in the shopping database
			session.close();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void updateItem(Item item, int itemCount) {//This updates the attributes of an item in the database
    	item.setID(itemList.get(itemCount).getID());//This ensures that ID is referring to the same item in the database
		itemList.set(itemCount, item);//This replaces the item located at index 'itemCount' with the newly updated item
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.merge(itemList.get(itemCount));//The merge function will replace
			session.getTransaction().commit();//the existing item with the new modified item
			session.close();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
  
    
    public void removeItem(int itemCount) {//This remove item from the database
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.delete(itemList.get(itemCount));//This will remove item inside that index from the shopping database
			session.getTransaction().commit();
			session.close();
		}
		
		catch(Exception e) {
			session.getTransaction().rollback();
		}
		
    }
    
    public void resetAdminDisplayID() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			if(adminList.size() > 1) {
				session.beginTransaction();
				for (int i = 1; i < adminList.size();i++) {
					adminList.get(i).setDisplayID(i);
					session.merge(adminList.get(i));
				}
				session.getTransaction().commit();
				session.close();
			}
		}
		
		catch(Exception e) {
			session.getTransaction().rollback();
		}
		
		
	}
    
    //Create, Update Deletion operations on Admins
    public void addAdmin(Admin admin) {//This adds the admin to the database
    	admin.setDisplayID(adminList.size()+1);
    	adminList.add(admin);
    	Session session = HibernateUtil.getSessionFactory().openSession();
		
		
		try {
			session.beginTransaction();
			session.save(adminList.get(adminList.size()-1));
			session.getTransaction().commit();
			session.close();
		}
		
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void updateAdmin(Admin admin, int adminCount) { //This updates the attributes of an admin in the database
    	admin.setID(adminList.get(adminCount).getID());
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			adminList.set(adminCount, admin);
			session.merge(adminList.get(adminCount));
			session.getTransaction().commit();
			session.close();
		}
		
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void removeAdmin(int count) {// This removes the admin from the database
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			session.beginTransaction();
			session.delete(adminList.get(count));
			session.getTransaction().commit();
			session.close();
			adminList.remove(count);
		}
		
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    //Create, Update Deletion operations on Suppliers 
    public void addSupplier(Supplier supplier) {// This adds the supplier to the database
    	supplier.setDisplayID(supplierList.size());
    	supplierList.add(supplier);
    	Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.persist(supplierList.get(supplierList.size()-1));
			session.getTransaction().commit();
			session.close();
		}
		
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void updateSupplier(Supplier supplier, int existingSupplier) {//This updates the attributes of a supplier in the database
    	Supplier replaceSupplier = supplierList.get(supplierCount);
    	replaceSupplier.setCompanyName(supplier.getCompanyName());
    	replaceSupplier.setType(supplier.getType());
    	replaceSupplier.setEmailAddress(supplier.getEmailAddress());
    	supplierList.set(supplierCount, replaceSupplier);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.merge(supplierList.get(supplierCount));
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void updateSupplier(Supplier supplier, int supplierCount, Item item) {//This updates the attributes of a supplier in the database
    	supplier.setID(supplierList.get(supplierCount).getID());
    	supplierList.set(supplierCount, supplier);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			supplier.updateItem(item);
			session.merge(supplierList.get(supplierCount));
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void updateSupplierR(Supplier supplier, int supplierCount, Item item) {//This updates the attributes of a supplier in the database
    	supplier.setID(supplierList.get(supplierCount).getID());
    	supplierList.set(supplierCount, supplier);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			supplier.removeItem(item);
			session.merge(supplierList.get(supplierCount));
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void removeSupplier(int supplierCount) {// This removes the supplier from the database
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.delete(supplierList.get(supplierCount));
			session.getTransaction().commit();
			session.close();
			supplierList.remove(supplierCount);
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    //Create and Deletion operations on Or 
    public void addPOrder(ProcessingOrder order){ //This adds the item to the database
    	//Enqueing the order to the processing order queue
    	order.setID(processingOrderList.size());
    	Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(order);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }
    
    public void removePOrder() {
    	//Instantiate a processed orders
    	//The peek function retrieves the head of the queue but does not remove the order from the queue
    	OrderHistory order = new OrderHistory(processingOrderList.peek().getCustomer(), processingOrderList.peek().getTotalPrice(), new Date());
    	Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			if(!processingOrderList.isEmpty()) {//Checks whether the queue is not empty
				session.beginTransaction();
				session.delete(processingOrderList.peek());
				processingOrderList.poll();//The poll will retrieve and as well remove the head of the queue
				session.save(orderHistory.push(order));//The processed order is then pushed into the Order History stack
				session.getTransaction().commit();
				session.close();
			}	
		}
		catch(Exception e) {
			session.getTransaction().rollback();
		}
    }

    //Discount manager
    
 // Loading all of the items which are discounted to a linked list
 	public void listAllDiscountedItems() {
 		Iterator<Item> it = itemList.iterator();
 		while(it.hasNext()) {
 			Item item = it.next();
 			if(item.isDiscount()) {
 				this.discountedItemList.add(item);
 			}
 		}
 	}
    
    //Read from a Database
    public LinkedList<Supplier> readSupplierDB() { 
		return shoppingDB.readSupplierTableFromDatabase("Supplier");
	}
    
    public LinkedList<Item> readStockListDB() { 
		return shoppingDB.readStockTableFromDatabase("Item");
	}
    
    public  LinkedList<Admin> readAdminDB() {
		return shoppingDB.readAdminTableFromDatabase("Admin");
	}
    
    public  Queue<ProcessingOrder> readProcessingOrderDB() {
		return shoppingDB.readPOrdersTableFromDatabase("ProcessingOrder");
	}
    
    public Stack<OrderHistory> readOrderHistoryDB() { 
    	return shoppingDB.readOrderHistoryTableFromDatabase("OrderHistory");
	}
    

    
  //This method helps to switch panels
  	public void switchPanels(JLayeredPane layeredPane,JPanel panel){
  		layeredPane.removeAll();
  		layeredPane.add(panel);
  		layeredPane.repaint();
  		layeredPane.revalidate();
  	}
  	
  //Getter & Setter  methods
    public LinkedList<Supplier> getSupplierList() {
		return supplierList;
	}
    
	public LinkedList<Item> getItemList() {
		return itemList;
	}

	public Queue<ProcessingOrder> getProcessingOrderList() {
		return processingOrderList;
	}

	public LinkedList<Item> getDiscountedItemList() {
		return discountedItemList;
	}

	public LinkedList<Admin> getAdminList() {
		return adminList;
	}

	public Stack<OrderHistory> getOrderHistory() {
		return orderHistory;
	}
	
	public int getAdminCount() {
		return adminCount;
	}
  	
  	public void setAdminCount(int adminCount) {
		this.adminCount = adminCount;
	}
  	
  	public int getItemCount() {
		return itemCount;
	}
  	
  	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
  	
  	public int getSupplierCount() {
		return supplierCount;
	}
  	
  	public void setSupplierCount(int supplierCount) {
		this.supplierCount = supplierCount;
	}
  	
  	//Getters for Panels
  	public StockListPanel getViewStockListPanel() {
		return viewStockListPanel;
	}

  	public void setViewStockListPanel(StockListPanel viewStockListPanel) {
		this.viewStockListPanel = viewStockListPanel;
	}

	public AddStockPanel getAddItemPanel() {
		return addItemPanel;
	}
	
	public void setAddItemPanel(AddStockPanel addItemPanel) {
		this.addItemPanel = addItemPanel;
	}

	public EditStockPanel getEditStockPanel() {
		return editStockPanel;
	}
	
	public void setEditStockPanel(EditStockPanel editStockPanel) {
		this.editStockPanel = editStockPanel;
	}

	public AdminListPanel getAdminListPanel() {
		return adminListPanel;
	}
	
	public void setAdminListPanel(AdminListPanel adminListPanel) {
		this.adminListPanel = adminListPanel;
	}

	public SupplierListPanel getSupplierListPanel() {
		return supplierListPanel;
	}

	public void setSupplierListPanel(SupplierListPanel supplierListPanel) {
		this.supplierListPanel = supplierListPanel;
	}

	public AddSupplierPanel getAddSupplierListPanel() {
		return addSupplierListPanel;
	}

	public void setAddSupplierListPanel(AddSupplierPanel addSupplierListPanel) {
		this.addSupplierListPanel = addSupplierListPanel;
	}

	public AddAdminPanel getAddAdminPanel() {
		return addAdminPanel;
	}

	public void setAddAdminPanel(AddAdminPanel addAdminPanel) {
		this.addAdminPanel = addAdminPanel;
	}

	public EditAdminPanel getEditAdminListPanel() {
		return editAdminPanel;
	}

	public void setEditAdminListPanel(EditAdminPanel editAdminListPanel) {
		this.editAdminPanel = editAdminListPanel;
	}

	public EditSupplierPanel getEditSupplierListPanel() {
		return editSupplierListPanel;
	}
	
	public void setEditSupplierListPanel(EditSupplierPanel editSupplierListPanel) {
		this.editSupplierListPanel = editSupplierListPanel;
	}

	public EditAccountPanel getEditAccountPanel() {
		return editAccountPanel;
	}

	public void setEditAccountPanel(EditAccountPanel editAccountPanel) {
		this.editAccountPanel = editAccountPanel;
	}

	public DiscountListPanel getDiscountedItemListPanel() {
		return discountedListPanel;
	}

	public void setDiscountedItemListPanel(DiscountListPanel discountedItemListPanel) {
		this.discountedListPanel = discountedItemListPanel;
	}

	public DiscountCategoryPanel getCategoryPanel() {
		return categoryPanel;
	}

	public void setCategoryPanel(DiscountCategoryPanel categoryPanel) {
		this.categoryPanel = categoryPanel;
	}

	public DiscountItemPanel getSpecificItemPanel() {
		return specificItemPanel;
	}

	public void setSpecificItemPanel(DiscountItemPanel specificItemPanel) {
		this.specificItemPanel = specificItemPanel;
	}
	
	public RestockPanel getRestockPanel() {
		return restockPanel;
	}

	public void setRestockPanel(RestockPanel restockPanel) {
		this.restockPanel = restockPanel;
	}
	
	public ProcessingOrderPanel getProcessingOrderPanel() {
		return processingOrderPanel;
	}

	public void setProcessingOrderPanel(ProcessingOrderPanel processingOrderPanel) {
		this.processingOrderPanel = processingOrderPanel;
	}
	
	public OrderHistoryPanel getOrderHistoryPanel() {
		return orderHistoryPanel;
	}

	public void setOrderHistoryPanel(OrderHistoryPanel orderHistoryPanel) {
		this.orderHistoryPanel = orderHistoryPanel;
	}
	
	public CartPanel getCartPanel() {
		return cartPanel;
	}

	public void setCartPanel(CartPanel cartPanel) {
		this.cartPanel = cartPanel;
	}
	
	public ShoppingPagePanel getShoppingPagePanel() {
		return shoppingPagePanel;
	}

	public void setShoppingPagePanel(ShoppingPagePanel shoppingPagePanel) {
		this.shoppingPagePanel = shoppingPagePanel;
	}
}