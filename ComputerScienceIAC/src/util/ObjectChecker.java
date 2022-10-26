package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Admin;

public class ObjectChecker {//This class is used to verify the attributes of an object and check whether the object with the same attributes exist in the database.
	
	public boolean existingUsername(String username) {
		boolean exist = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query adminQuery = session.createQuery("select a.userName from Admin a");
		List<String> adminlist = (List<String>) adminQuery.list();
		LinkedList<String>usernames = new LinkedList<String>(adminlist);
		session.getTransaction().commit();
		session.close();
		
		if(usernames.isEmpty()) {
			return false;
		}
		else {
			for (int i = 0; i < usernames.size(); i++) {
				if(usernames.get(i).equals(username)){
					JOptionPane.showMessageDialog(null, "This username already exist", "Existing Username", JOptionPane.ERROR_MESSAGE);
					exist = true;
				}
			}
		}
		
		return exist;
	};
	
	public boolean existingAdminEmail(String email) {
		boolean exist = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query adminQuery = session.createQuery("select a.emailAddress from Admin a");
		List<String> adminList = (List<String>) adminQuery.list();
		LinkedList<String>adminEmails = new LinkedList<String>(adminList);
		session.getTransaction().commit();
		session.close();
		
		
		if(adminEmails.isEmpty()) {
			return false;
		}
		else {
			for (int i = 0; i < adminEmails.size(); i++) {
				if(adminEmails.get(i).equals(email)){
					JOptionPane.showMessageDialog(null, "This email already exist", "Existing Email", JOptionPane.ERROR_MESSAGE);
					exist = true;
				}
			}
			
		}
		
		return exist;
		
	};
	
	public boolean existingCompanyName(String companyName) {
		boolean exist = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query supplierQuery = session.createQuery("select s.companyName from Supplier s");
		List<String> supplierList = (List<String>) supplierQuery.list();
		LinkedList<String>supplierNames = new LinkedList<String>(supplierList);
		session.getTransaction().commit();
		session.close();
		
		
		if(supplierNames.isEmpty()) {
			return false;
		}
		else {
			for (int i = 0; i < supplierNames.size(); i++) {
				if(supplierNames.get(i).equals(companyName)){
					JOptionPane.showMessageDialog(null, "This name already exist", "Existing Company Name", JOptionPane.ERROR_MESSAGE);
					exist = true;
				}
			}
			
		}
		
		return exist;
		
	};
	
	public boolean existingSupplierEmail(String email) {
		boolean exist = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query supplierQuery = session.createQuery("select s.emailAddress from Supplier s");
		List<String> supplierList = (List<String>) supplierQuery.list();
		LinkedList<String>supplierEmails = new LinkedList<String>(supplierList);
		session.getTransaction().commit();
		session.close();
		
		if(supplierEmails.isEmpty()) {
			return false;
		}
		else {
			for (int i = 0; i < supplierEmails.size(); i++) {
				if(supplierEmails.get(i).equals(email)){
					JOptionPane.showMessageDialog(null, "This email already exist", "Existing Email", JOptionPane.ERROR_MESSAGE);
					exist = true;
				}
			}
		}
		
		return exist;
	};
	
	public boolean existingPhoneNum(String phoneNum) {
		boolean exist = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query adminQuery = session.createQuery("select a.phoneNumber from Admin a");
		List<String> adminlist = (List<String>) adminQuery.list();
		LinkedList<String>phoneNums = new LinkedList<String>(adminlist);
		session.getTransaction().commit();
		session.close();
		
		
		if(phoneNums.isEmpty()) {
			return false;
		}
		else {
			for (int i = 0; i < phoneNums.size(); i++) {
				if(phoneNums.get(i).equals(phoneNum)){
					JOptionPane.showMessageDialog(null, "This phone number already exist", "Existing Phone Number", JOptionPane.ERROR_MESSAGE);
					exist = true;
				}
			}
		}
		
		return exist;
	};
	
	public boolean verifyPassword(String password) {
		return password.length() >= 8;
	}
	
	public boolean verifyPhoneNumber(String phoneNum) {
		Pattern phonePattern = Pattern.compile("^\\d{10}$");//This regular expression checks whether the inputted phone number has the length of 10
		Matcher match = phonePattern.matcher(phoneNum);
		return match.matches();
	}
	
	public boolean verifyEmail(String email) {
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");//This regular expression checks whether the inputted email address contains string of characters or numerical value before and after the "@"
		Matcher match = pattern.matcher(email);//This ensures the email address is valid and make sense.
		return match.matches();
	}
	
	public boolean validName(String name) {
		if(name.matches(".*\\d.*")) { //This regular expression ensures that the first and last name does not contain any numerical values.
			return false;
		}
		return true;
	}
	
	
}
