package entity;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Scarf")
public class Scarf extends Item{
	
	@Column(name = "LENGTH")
    private String length;
	
	@Column(name = "FABRICS")
    private String fabrics;
	
	@Column(name = "HEIGHT")
    private String height;
	
	@Column(name = "THICKNESS")
    private String thickness;
	
	public Scarf() {}

    public Scarf(String name, BigDecimal price, BigDecimal cost, String description, int quantity, String size,String category, String garmentCare, 
    		String imagePath, Supplier supplier, String length, String fabrics, String height, String thickness) {

        super(name, price, cost, description, quantity,size,category, garmentCare, imagePath, supplier);
        this.length = length;
        this.fabrics = fabrics;
        this.height = height;
        this.thickness = thickness;
    }
    
    //Getters & Setters
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getFabrics() {
        return fabrics;
    }

    public void setFabrics(String fabrics) {
        this.fabrics = fabrics;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }
}