package entity;

import java.math.BigDecimal;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="order_history")
public class OrderHistory extends Order{
	
	@Temporal(TemporalType.DATE)
    @Column(name = "DATE_PROCESSED")
    private Date dateProcessed;
	
	public OrderHistory() {}
	
	
	public OrderHistory(Customer customer, BigDecimal totalPrice, Date dateProcessed) {
		super(customer, totalPrice);
		this.dateProcessed = dateProcessed;
	}
	
	//Getters & Setters
	public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
    
}
