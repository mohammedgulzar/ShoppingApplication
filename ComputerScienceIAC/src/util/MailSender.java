package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.Admin;
import entity.Customer;
import entity.Item;


public class MailSender {
	private String email;
	private String password;
	private Session session;
	
	public MailSender() {
		initialise();
	}
	
	public void initialise() { //This initialises the Mail Sender object
		Properties properties = new Properties();
		//Properties is a required function in order to make the Java Mail API to work
		properties.put("mail.smtp.auth", "true");//The API tries to authenticate the user
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");//Connecting the gmail server
		properties.put("mail.smtp.port", "587");//Connecting the server port
		//For gmail it is essential to use port number 587
		email = "mokoshoppingbot@gmail.com";
		password = "computerScience";
		session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password); //This function will check if the shopping bot account is within the gmail server
			}
		});
	}
	
	//This sends the deleted items to the manager's email
	public void sendDeletedItems(LinkedList<Item> deletedItems, Admin manager) {
		String text = displayDeletedList(deletedItems);
		try {
			Message msg = new MimeMessage(session);//Instantiate the Message object
			msg.setFrom(new InternetAddress(email));//This creates a internet address representing the shopping email account
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(manager.getEmailAddress()));//Setting the recipient's Emaill address
			msg.setSubject("Deleted Items");//Header
			msg.setText(text);
			Transport.send(msg);//
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	public String  displayDeletedList(LinkedList<Item> deletedItems) { //Loops over the deleted Item List to create a message
		String text = "Deleted Items: \n\n";
		for (Item item : deletedItems) {
			text += item.getName() + "\n";
		}
		return text;
	}
	
	public void mailExpiredDiscountedItem(ArrayList<Item> list, LinkedList<Admin> adminList) {
		String text = displayExpiredList(list);
		try {
			Message msg = new MimeMessage(session);//Instantiate the Message object
			msg.setFrom(new InternetAddress(email));
			Address[] addresses = new Address[adminList.size()];
			for (int i = 0; i < addresses.length; i++) {
				addresses[i] = new InternetAddress(adminList.get(i).getEmailAddress());
			}
			msg.setSubject("Expired Discounted Items");//Header
			msg.setText(text);
			Transport.send(msg, addresses);;//This will send the email to the receipient's email address 
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	public String  displayExpiredList(ArrayList<Item> list) { //Loops over the expired Item List to create a message
		String text = "Items whose discount period has expired: \n\n";
		for (Item item : list) {
			text += item.getName() + "\n";
		}
		return text;
	}
	
	public void welcomeNewAdmin(Admin admin) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email));
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(admin.getEmailAddress()));
			msg.setSubject("Account created");
			msg.setText("An Admin account has been created for you, here are some details about your account \n"+
					"Name: " + admin.getFirstName() +" "+ admin.getLastName() +
					"\nUsername: " + admin.getUserName() +
					"\nPassword: " + admin.getPassword());
			Transport.send(msg);
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	public  void revokeAdmin(Admin admin) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email));
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(admin.getEmailAddress()));
			msg.setSubject("Account Deletion");
			msg.setText("Dear " + admin.getFirstName() + ", your account in Moko Shopping Application has been completely revoked."
					+ "\n If there are issues with this account deletion, please pay a visit to the manager.");
			Transport.send(msg);
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	public void mailLowStocks(Item item, Admin manager) {
		try {
			Message msg = new MimeMessage(session);//Instantiate the Message object
			msg.setFrom(new InternetAddress(email));
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(manager.getEmailAddress()));//Setting the recipient's Emaill address
			msg.setSubject("One of your item is running out of stocks");
			msg.setText(item.getName() + " has a quantity of 5 or below, please reorder more quantity "
					+ "from the supplier of this item by visiting the Restock page");
			Transport.send(msg);//This will send the email to the receipient's email address 
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	
	public void notifyAdmins(Customer customer, LinkedList<Admin> adminList) {
		try {
			Message msg = new MimeMessage(session);//Instantiate the Message object
			msg.setFrom(new InternetAddress(email));
			Address[] addresses = new Address[adminList.size()];
			for (int i = 0; i < addresses.length; i++) {
				addresses[i] = new InternetAddress(adminList.get(i).getEmailAddress());
			}
			msg.setSubject("New Order created");//Header
			msg.setText("Order has been created and requires processing. Below is the receipt of the order\n" 
					+ displayReceipt(customer));
			Transport.send(msg, addresses);;//This will send the email to the receipient's email address 
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	
	public void sendReceipt(Customer customer) {
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email));
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(customer.getEmailAddress()));
			msg.setSubject("Order Receipt from Moko Shopping Application");
			msg.setText("Your orders will arrive to your home address in a couple of days\n" 
						+ displayReceipt(customer));
			Transport.send(msg);
		}
		catch(Exception e){
			Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null,e);
		}
	}
	
	public String displayReceipt(Customer customer) {//Displaying a receipt for the customer.
		String text = "";
		text = text +"-".repeat(55)+"\n";
		text = text +String.format("%35s","Receipt\n");;
		text = text +"-".repeat(55)+"\n";
		text = text + "Items \n";
		text = text + customer.getReceipt();
		return text;
	}

	//Getters
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
