package entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Stock_List")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( //This is used to distiguish what type of item this object is (Abaya, Scarf and Cardigan)
		name= "ITEM_TYPE",
		discriminatorType = DiscriminatorType.STRING
)

public class Item {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private int productID;
	
	private int displayID;//This is used for JTable
	
	//Item is dependent on the Supplier as there is one Supplier for many items
	@ManyToOne(targetEntity = Supplier.class, fetch = FetchType.LAZY) 
	@JoinColumn(name = "SUPPLIER_ID", nullable= false)
    private Supplier supplier;
	
	@Column(name = "NAME")
    private String name;
	
	@Column(name = "UNIT_PRICE")
	private BigDecimal price;
	
	@Column(name = "PREVIOUS_PRICE")
    private BigDecimal prevPrice;
	
	@Column(name = "COST")
    private BigDecimal cost;
	
	@Column(name = "DESCRIPTION")
	@Lob
    private String description;
	
	@Column(name = "QUANTITY_AVAILABLE")
    private int quantity;
	
	@Column(name = "SIZE") 
    private String size;
	
	@Column(name = "CATEGORY")
    private String category;
	
	@Column(name = "GARMENT_CARE")
	@Lob
    private String garmentCare;
	
	@Column(name = "IS_DISCOUNTED")
    private boolean discount;
	
	@Column(name = "STARTING_DISCOUNT_DATE")
    private Date startDate;
	
	@Column(name = "ENDING_DISCOUNT_DATE")
    private Date endDate;
	
	@Column(name = "IMAGE_PATH")
	private String imagePath;
	
	private int hiddenID;//This is used to identify where the item is located inside the supplier's item List
    
    
    public Item() {}
    
	public Item(String name, BigDecimal price, BigDecimal cost, String description, int quantity, String size,
			String category, String garmentCare, String imagePath, Supplier supplier) 
	{
        displayID++;
        this.name = name;
        this.price = price;
        this.cost = cost;
        this.description = description;
        this.quantity = quantity;
        this.size = size;
        this.category = category;
        this.garmentCare = garmentCare;
        this.imagePath =imagePath;
        this.supplier = supplier;
    }
	
	//Getters & Setters
	public int getID() {
        return productID;
    }
    
    public void setID(int productID) {
		this.productID = productID;	
	}
	
    public int getDisplayID() {
        return displayID;
    }
    
    public void setDisplayID(int displayID) {
		this.displayID = displayID;	
	}

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrevPrice() {
        return prevPrice;
    }

    public void setPrevPrice(BigDecimal prevPrice) {
        this.prevPrice = prevPrice;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGarmentCare() {
        return garmentCare;
    }

    public void setGarmentCare(String garmentCare) {
        this.garmentCare = garmentCare;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
    	this.imagePath = imagePath;
    }
    
    public int getHiddenID() {
        return hiddenID;
    }
    
    public void setHiddenID(int hiddenID) {
		this.hiddenID = hiddenID;
		
	}
    
    public boolean hasStock() {
    	return this.quantity != 0;
    }

	
    
    
}