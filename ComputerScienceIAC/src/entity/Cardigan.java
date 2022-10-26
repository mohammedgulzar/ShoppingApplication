package entity;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Cardigan")
public class Cardigan extends Item{
	
	@Column(name = "COMPOSITIONS")
    private String compositions;
	
	public Cardigan() {}

    public Cardigan(String name, BigDecimal price, BigDecimal cost, String description, int quantity, String size,String category, String garmentCare, 
    		String imagePath, Supplier supplier, String compositions) {
    	super(name, price, cost, description, quantity,size,category, garmentCare, imagePath, supplier);
        this.compositions = compositions;
    }
    
  //Getters & Setters
    public String getCompositions() {
        return compositions;
    }

    public void setCompositions(String compositions) {
        this.compositions = compositions;
    }
}