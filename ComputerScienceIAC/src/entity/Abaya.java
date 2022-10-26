package entity;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Abaya")//When reading from the SQL database
//Hibernate will ensure that the right object type is casted onto the loaded data
public class Abaya extends Item {
	
	@Column(name = "FABRICS")
    private String fabrics;
	
	@Column(name = "LENGTH")
    private String length;
	
	@Column(name = "WEIGHT")
    private String weight;
	
	public Abaya() {}

    public Abaya(String name, BigDecimal price, BigDecimal cost, String description, int quantity, String size,String category, String garmentCare, 
    		String imagePath, Supplier supplier, String fabrics, String length, String weight) {
    	
    	super(name, price, cost, description, quantity,size,category, garmentCare, imagePath, supplier);
        this.fabrics = fabrics;
        this.length = length;
        this.weight = weight;
    }

  //Getters & Setters
    public String getFabrics() {
        return fabrics;
    }

    public void setFabrics(String fabrics) {
        this.fabrics = fabrics;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}