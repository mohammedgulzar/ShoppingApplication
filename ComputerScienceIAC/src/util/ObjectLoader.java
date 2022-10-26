package util;

import java.awt.Image;
import java.awt.Panel;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.Abaya;
import entity.Admin;
import entity.Cardigan;
import entity.Item;
import entity.Scarf;
import entity.Supplier;
import gui.EditAccountPanel;
import gui.EditAdminPanel;
import gui.EditStockPanel;
import gui.EditSupplierPanel;
import menu.Menu;

public class ObjectLoader { //This class loads attributes of an object onto their given textfields or textareas and other GUI components
	 
	private Menu menu;
	private EditAdminPanel editAdminPanel;
	private EditAccountPanel editAccountPanel;
	private EditSupplierPanel editSupplierPanel;
	private EditStockPanel editStockPanel;
	
	public ObjectLoader(Menu menu){
		this.menu = menu;
	}
	
	public ObjectLoader(Menu menu, EditAdminPanel editAdmimPanel){
		this.menu = menu;
		this.editAdminPanel = editAdmimPanel;
	}
	
	public ObjectLoader(Menu menu, EditAccountPanel editAccountPanel){
		this.menu = menu;
		this.editAccountPanel = editAccountPanel;
	}
	
	public ObjectLoader(Menu menu, EditSupplierPanel editSupplierPanel){
		this.menu = menu;
		this.editSupplierPanel = editSupplierPanel;
	}
	
	public ObjectLoader(Menu menu, EditStockPanel editStockPanel){
		this.menu = menu;
		this.editStockPanel = editStockPanel;
	}
	
	public void loadAdmin(int i) {
		
		Admin admin = menu.getAdminList().get(i);
		editAccountPanel.setFirstNameTF(admin.getFirstName());
		editAccountPanel.setLastNameTF(admin.getLastName());
		editAccountPanel.setEmailTF(admin.getEmailAddress());
		if(admin.getPhoneNumber() != null) {
			editAccountPanel.setPhoneNumTF(admin.getPhoneNumber());
		}
		editAccountPanel.setUsernameTF(admin.getUserName());
		editAccountPanel.setPasswordTF(admin.getPassword());
	}
	
	public void loadAdmin(int i,  LinkedList<Admin> adminList) {
		if(i == 0) {
			i++;
		}
		
		if( i < adminList.size()) { //it must be greater than zero as the first element is the manager object
			
			Admin admin = adminList.get(i);
			editAdminPanel.setFirstNameTF(admin.getFirstName());
			editAdminPanel.setLastNameTF(admin.getLastName());
			editAdminPanel.setEmailTF(admin.getEmailAddress());
			if(admin.getPhoneNumber() != null) {
				editAdminPanel.setPhoneNumTF(admin.getPhoneNumber());
			}
			editAdminPanel.setUsernameTF(admin.getUserName());
			editAdminPanel.setPasswordTF(admin.getPassword());
			editAdminPanel.setLblIDNum(Integer.toString(i));
		}
	}
	
	public void loadManager(boolean isManager) { //this function loads the data of manager instance (Fix this)
		if(isManager) {
			Admin manager = menu.getAdminList().get(0);
			editAccountPanel.setAccountFNameTF(manager.getFirstName());
			editAccountPanel.setAccountLNameTF(manager.getLastName());
			editAccountPanel.setAccountEmailAddressTF(manager.getEmailAddress());
			if(manager.getPhoneNumber() != null) {
				editAccountPanel.setAccountPhoneNumberTF(manager.getPhoneNumber());
			}
			editAccountPanel.setAccountUsernameTF(manager.getUserName());
			editAccountPanel.setAccountPasswordTF(manager.getPassword());
		}
	}
	
	public void loadSupplier(int i, LinkedList<Supplier> supplierList) {
		if(i >= 0 && i < supplierList.size()) {
			Supplier supplier = supplierList.get(i);
			editSupplierPanel.setCompanyName(supplier.getCompanyName());
			editSupplierPanel.setSupplierEmailAddress(supplier.getEmailAddress());
			editSupplierPanel.setBusinessType(supplier.getType());
			editSupplierPanel.setLblIDNum(Integer.toString(i));
		}
	}
	
	
	//This helps to load the item's data into the comboBox with no issues
	public void loadSelectedValueInComboBox(String name, JComboBox combo) {
		for (int i = 0; i< combo.getItemCount();i++) {
			if(name.equals((String)combo.getItemAt(i))) {
				combo.setSelectedIndex(i);
				break;
			}
			
		}
	}
	
	public void loadItem(int i) {  //Fix this one
		if(i >= 0 && i < menu.getItemList().size()) {
			Item item =  menu.getItemList().get(i);
			editStockPanel.setItemNameTF(item.getName());
			editStockPanel.setPriceTF(String.valueOf(item.getPrice()));
			editStockPanel.setCostTF(String.valueOf(item.getCost()));
			editStockPanel.setQuantityTF(String.valueOf(item.getQuantity()));
			editStockPanel.setDescriptionTA(item.getDescription());
			editStockPanel.setGarmentCareTA(item.getGarmentCare());
			loadItemType(item, editStockPanel.getItemTypeCB());
			loadSelectedValueInComboBox(item.getSupplier().getCompanyName(), editStockPanel.getSupplierNameCB());
			loadSelectedValueInComboBox(item.getCategory(), editStockPanel.getSeasonTypeCB());
			loadSelectedValueInComboBox(item.getSize(), editStockPanel.getSizeCB());
			String imagePath = item.getImagePath();
			loadImageToLabel(editStockPanel.getLblImageDisplay(), imagePath);
		}
	}
	
	//This checks whether the item is an Abaya, cardigan or scarf, in order for the item to shares its additional features
	public void loadItemType(Item item, JComboBox combo) {
		if(item instanceof Abaya) {
			combo.setSelectedIndex(0);
			Abaya abaya = (Abaya) item;
			editStockPanel.setAbayaFabricTF(abaya.getFabrics());
			editStockPanel.getAbayaFabricTF().setEnabled(true);
			editStockPanel.setAbayaLengthTF(abaya.getLength());
			editStockPanel.getAbayaLengthTF().setEnabled(true);
			editStockPanel.setWeightTF(abaya.getWeight());
			editStockPanel.getWeightTF().setEnabled(true);
			//switchPanels(AdditionalDetailLayeredPane, abayaInfoPanel);
		}
		else if(item instanceof Cardigan) {
			combo.setSelectedIndex(1);
			Cardigan cardigan = (Cardigan) item;
			editStockPanel.setCompositionTF(cardigan.getCompositions());
			editStockPanel.getCompositionTF().setEnabled(true);
			//switchPanels(AdditionalDetailLayeredPane, cardiganInfoPanel);
			
		}
		else if (item instanceof Scarf) {
			combo.setSelectedIndex(2);
			Scarf scarf = (Scarf) item;
			editStockPanel.setHeightTF(scarf.getHeight());
			editStockPanel.getHeightTF().setEnabled(true);
			editStockPanel.setScarfFabricsTF(scarf.getFabrics());
			editStockPanel.getScarfFabricsTF().setEnabled(true);
			editStockPanel.setScarfLengthTF(scarf.getLength());
			editStockPanel.getScarfLengthTF().setEnabled(true);
			editStockPanel.setThicknessTF(scarf.getThickness());
			editStockPanel.getThicknessTF().setEnabled(true);
			//switchPanels(AdditionalDetailLayeredPane, scarfInfoPanel);
		}
		
	}
	
	//This enables the user to go to their file explorer to select any images 
	public String browseItem(JLabel label, String imagePath) {
		JFileChooser fileChooser =  new JFileChooser("C:\\Users\\moham\\OneDrive\\Pictures"); //This creates a file folder in order to browse for images
		FileNameExtensionFilter fnaf = new FileNameExtensionFilter("IMAGES","jpg","png"); // only select jpg, jpeg and png
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(fnaf);
		int showOpenDialouge = fileChooser.showOpenDialog(null);
		if(showOpenDialouge == JFileChooser.APPROVE_OPTION) {
			File choosenImage = fileChooser.getSelectedFile();
			imagePath = choosenImage.getAbsolutePath();
			loadImageToLabel(label, imagePath);
			return imagePath;
		}
		return null;
	}
	
	//This method loads an image into a JLabel
	public void loadImageToLabel(JLabel label, String imagePath) {
		ImageIcon imageIcon = new ImageIcon(imagePath);
		Image image = imageIcon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));
	}
}
