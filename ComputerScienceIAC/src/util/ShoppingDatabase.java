package util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionException;

import entity.Admin;
import entity.Item;
import entity.OrderHistory;
import entity.ProcessingOrder;
import entity.Supplier;

public class ShoppingDatabase {
	
	public ShoppingDatabase(){}
	
	// Reading from Stock table 
		public LinkedList<Item> readStockTableFromDatabase(String tableName){
			LinkedList<Item> items;
			try {
				//Hibernate Session factory is responsible for creating the Session objects
				// Sessions are essential to communicate with the database.
				// Sessions can perform CRUD = create, read, update, delete 
				//In this case, we are using Sessions to read the data from the shopping database
				Session session = HibernateUtil.getSessionFactory().openSession();
				Query query; //Query object will be able to store the data from hibernate query language (HQL)
				session.beginTransaction();//This starts the lifecyle of the session object.
				query = session.createQuery("from " +tableName); //from keyword in HQL will load every object in the stock table onto the query 
				List<Item> itemlist = (List<Item>) query.list();//The query can be converted into java created List using the list function
				items = new LinkedList<Item>(itemlist);//The list is then converted into linkedList
				session.getTransaction().commit();//This line confirms the action of the session object  
				session.close();//Once the transanctions are committed, the lifecycle of the session object terminates
				//This ensures that there is no memory leakages when the session object is not needed anymore
				return items;
			}
			catch (SessionException e) {//If there is nothing inside the stock table, it will instantiate a new linked list
				items = new LinkedList<Item>();
				return items;
			}
			
		}
		
	//Similar reading process for other tables as well
		
	// Reading from Admin table 
	public LinkedList<Admin> readAdminTableFromDatabase(String tableName){
		LinkedList<Admin> admins;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query;
			session.beginTransaction();
			query = session.createQuery("from " +tableName);
			List<Admin> adminlist = (List<Admin>) query.list();
			admins = new LinkedList<Admin>(adminlist);
			session.getTransaction().commit();
			session.close();
			return admins;
		}
		catch (SessionException e) {
			admins = new LinkedList<Admin>();
			return admins;
		}
		
	}
	
	// Reading from Processing Orders table 
		public Stack<OrderHistory> readOrderHistoryTableFromDatabase(String tableName){
			Stack<OrderHistory> orders;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Query query;
				session.beginTransaction();
				query = session.createQuery("from " +tableName);
				List<OrderHistory> orderHistoryList = (List<OrderHistory>) query.list();
				orders = new Stack<OrderHistory>();
				orders.addAll(orderHistoryList);
				session.getTransaction().commit();
				session.close();
				return orders;
			}
			catch (SessionException e) {
				orders = new Stack<OrderHistory>();
				return orders;
			}
		}
	
	// Reading from Processing Orders table 
	public Queue<ProcessingOrder> readPOrdersTableFromDatabase(String tableName){
		Queue<ProcessingOrder> orders;
		
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query;
			session.beginTransaction();
			query = session.createQuery("from " +tableName);
			List<ProcessingOrder> orderList = (List<ProcessingOrder>) query.list();
			orders = new LinkedList<ProcessingOrder>(orderList);
			session.getTransaction().commit();
			session.close();
			return orders;
		}
		catch (SessionException e) {
			orders = new LinkedList<ProcessingOrder>();
			return orders;
		}
	}
	
	// Reading from Suppliers table 
	public LinkedList<Supplier> readSupplierTableFromDatabase(String tableName){
		LinkedList<Supplier> suppliers;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query;
			session.beginTransaction();
			query = session.createQuery("from " +tableName);
			List<Supplier> supplierList = (List<Supplier>) query.list();
			suppliers = new LinkedList<Supplier>(supplierList);
			session.getTransaction().commit();
			session.close();
			return suppliers;
		}
		catch (SessionException e) {
			suppliers = new LinkedList<Supplier>();
			return suppliers;
		}
			
	}
}
