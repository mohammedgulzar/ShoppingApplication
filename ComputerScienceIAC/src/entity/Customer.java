package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable //This annotations means that customer does not have its own identity
public class Customer {
	
	@Column(name = "FIRST_NAME")
    private String firstName;
	
	@Column(name = "LAST_NAME")
    private String lastName;
	
	@Column(name = "PHONE_NUMBER")
    private String phoneNumber;
	
	@Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "HOME_ADDRESS")
	private String homeAddress;
	
	@Column(name = "RECEIPT")
	private String receipt;
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	public Customer() { 
	}
	
	public Customer(String firstName, String lastName, String phoneNumber, String emailAddress, String city, String homeAddress, String paymentType){
		this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
		this.city = city;
		this.homeAddress = homeAddress;
		this.paymentType = paymentType;
	}
	
	 //Getters & Setters
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

//	public ArrayList<Item> getShoppingCart() {
//		return shoppingCart;
//	}
//
//	public void setShoppingCart(ArrayList<Item> shoppingCart) {
//		this.shoppingCart = shoppingCart;
//	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
}
