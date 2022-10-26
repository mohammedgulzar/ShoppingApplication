package entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Processing_orders")
public class ProcessingOrder extends Order {
	
	public ProcessingOrder() {}
	public ProcessingOrder(Date orderDate, Customer customer, BigDecimal totalPrice) {
		super(orderDate, customer, totalPrice);
	}
}
