package entity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity//This ensures the supplier class can be mapped onto the shopping database.
@Table(name="supplier")//The name of the table in the shopping database
public class Supplier extends Person{
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	@Column(name = "BUSINESS_TYPE")
	private String type;
	
	//In hibernate you can create relationship between objects
	//In this case, Supplier has a OneToMany relationship with Item whereas 
	//Item has a ManyToOne relationship. 
	//FetchType.Eager---> this will load everything in itemList which was stored in the database 
	//Orphan Removal ---> Whenever the user decides to remove the supplier from the database
	//the items, which is dependent on the supplier, is removed.
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Item> itemList = new LinkedList<Item>();
	
	@Column(name = "TOTAL_PAYMENT")
	private BigDecimal totalPayment;
	
	public Supplier() {
	}
	
	public Supplier(String companyName, String emailAddress, String type){
		super(emailAddress);
		this.companyName = companyName;
		this.type = type;
		this.totalPayment = BigDecimal.ZERO;
		this.itemList = new LinkedList<Item>();
	}
	
	/*
	 * Actions on the supplier's Item List
	*/
	public void addItem(Item item) {
		item.setHiddenID(this.itemList.size());
		this.itemList.add(item);
		this.itemList.set(item.getHiddenID(), item);
		
		BigDecimal cost = item.getCost().multiply(BigDecimal.valueOf(item.getQuantity()));
		totalPayment.add(cost);
	}
	
	public void updateItem(Item updatedItem) {
		for (int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getHiddenID() == updatedItem.getHiddenID()) {
				updatedItem.setID(this.itemList.get(updatedItem.getHiddenID()).getID());
				this.itemList.set(i, updatedItem);
				updatePayment();
			}
		}
	}
	
	

	public void updateItemStock(Item item, int quantity) { 
		for (int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getHiddenID() == item.getHiddenID()) {
				item.setID(this.itemList.get(item.getHiddenID()).getID());
				this.itemList.get(i).setQuantity(this.itemList.get(i).getQuantity() + quantity);
			}
		}
	}
	
	public void removeItem(Item item) {
		for (int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getHiddenID() == item.getHiddenID()) {
				this.itemList.remove(i);
				updatePayment();
			}
		}
	}
	
	public void updatePayment() {
		totalPayment = BigDecimal.ZERO;
		if(this.itemList.size() > 0) {
			for (int i = 0; i < this.itemList.size(); i++) {
				Item item = this.itemList.get(i);
				BigDecimal cost = item.getCost().multiply(BigDecimal.valueOf(item.getQuantity()));// Payment = Quantity multiply cost 
				totalPayment = totalPayment.add(cost);
			}
			setTotalPayment(totalPayment);
		}
		setTotalPayment(totalPayment);//Return Zero;
		
		
	}
	
	//Getters and Setters
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(LinkedList<Item> itemList) {
		this.itemList = itemList;
	}
	

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}
	
	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}
	
}

