package entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="admin_list")

public class Admin extends Person{

    @Column(name = "USERNAME")
    private String userName;
    
    @Column(name = "PASSWORD")
    private String password;
    
    public Admin() {}

    public Admin(String firstName, String lastName, String emailAddress, String phoneNumber, String userName, String password) {
      
    	super(firstName, lastName, emailAddress, phoneNumber);
        this.userName = userName;
        this.password = password;
    }
    public Admin(String firstName, String lastName, String emailAddress, String userName, String password) {
        super(firstName, lastName, emailAddress);
        this.userName = userName;
        this.password = password;
    }

    //Getters & Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
