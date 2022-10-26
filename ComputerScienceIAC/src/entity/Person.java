package entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass //This annotation is used to enable the child class 
//to inherit the data fields within the parent class
abstract class Person {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) //This helps to distinguish an object within the database
	@Column(name = "ID")
	private int ID;

	@Column(name = "FIRST_NAME")
    private String firstName;
	
	@Column(name = "LAST_NAME")
    private String lastName;
	
	@Column(name = "PHONE_NUMBER")
    private String phoneNumber;
	
	@Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
	
	private int displayID;//Instead of interfering with the ID of the entity, 
	//displayID is used instead which index number the object located within the list.

	public Person() {}
	
    public Person(String firstName, String lastName,  String emailAddress,String phoneNumber) {
        this.displayID++;
    	this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
    
    
    public Person(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Person(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    //Getters and Setters
    
    public int getID() {
    	return ID;
    }
    
    public void setID(int ID) {
    	this.ID = ID;
    }
    
    public int getDisplayID() {
    	return displayID;
    }
    
    public void setDisplayID(int displayID) {
    	this.displayID = displayID;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}