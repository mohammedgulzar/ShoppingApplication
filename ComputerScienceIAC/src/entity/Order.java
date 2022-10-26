package entity;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
abstract class Order {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_NUMBER")
	private int ID;
	
	@Temporal(TemporalType.DATE)//This creates a tempory date
	@Column(name = "DATE_OF_ORDER")
	private Date orderDate;
	
    @Embedded//Customer is embedded within the Order database 
    //as it does not need to have it own standalone table in the shopping database
    private Customer customer;
    
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;
    
    @Column(name = "IS_ORDER_PROCESSED")
    private boolean orderProcessed;
    
    public Order() {}
    
    public Order(Customer customer, BigDecimal totalPrice) {
    	
    	this.customer = customer;
    	this.totalPrice = totalPrice;
    	this.orderProcessed = true;
    }
    
    public Order(Date orderDate, Customer customer, BigDecimal totalPrice) {
        
    	this.orderDate = orderDate;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.orderProcessed = false;
 
    }
    
  //Getters & Setters
    public void setID(int ID) {
    	 this.ID = ID;
    }
    
    public int getID() {
    	return ID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isOrderProcessed() {
        return orderProcessed;
    }

    public void setOrderProcessed(boolean orderProcessed) {
        this.orderProcessed = orderProcessed;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }
}