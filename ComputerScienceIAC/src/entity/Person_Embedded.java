package entity;

import javax.persistence.Column;

public class Person_Embedded { //This class only exist so it can be inherited by the customer class
	//Which is an embedded entity.
	
	@Column(name = "FIRST_NAME")
    private String firstName;
	
	@Column(name = "LAST_NAME")
    private String lastName;
	
	@Column(name = "PHONE_NUMBER")
    private String phoneNumber;
	
	@Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

	public Person_Embedded() {}
	
    public Person_Embedded(String firstName, String lastName,  String emailAddress,String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
    
    
    public Person_Embedded(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Person_Embedded(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    //Getters and Setters

    
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
