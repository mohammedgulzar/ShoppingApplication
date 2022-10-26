package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import entity.Item;
import entity.Supplier;
import menu.Menu;
import util.ObjectLoader;
import util.ResetText;

public class ModifyStockPanel extends JPanel {
	
	protected ResetText rt = new ResetText();
	protected Menu menu;
	
	//GUI Components
	protected JTextField abayaFabricTF;
	protected JTextField abayaLengthTF;
	protected JTextField compositionTF;
	protected JTextField costTF;
	protected JTextArea descriptionTA;
	protected JTextArea garmentCareTA;
	protected JTextField heightTF;
	protected JTextField itemNameTF;
	protected JTextField priceTF;
	protected JTextField quantityTF;
	protected JTextField scarfLengthTF;
	protected JTextField scarfFabricsTF;
	protected JTextField thicknessTF;
	protected JTextField weightTF;
	protected JLabel lblImageDisplay;
	protected JLabel lblCost;
	protected JLabel lblQuantity;
	protected JLabel lblItemType;
	protected JComboBox itemTypeCB;
	protected JComboBox sizeCB;
	protected JComboBox seasonTypeCB;
	protected JComboBox supplierNameCB;
	protected JButton btnReset;
	protected JButton btnBrowse;
	protected StockListPanel stockListPanel;
	
	protected JLabel lblSize;
	protected JLabel lblSupplier;
	protected JLabel lblSeasonType_1;
	protected JLabel lblDescription;
	protected JLabel lblGarmentCare;

	
	//Item Variables
	protected int selectedValue;
	protected String name;
	protected BigDecimal price;
	protected BigDecimal cost;
	protected String description;
	protected int quantity;
	protected Supplier supplier;
	protected String size;
	protected String category;
	protected String garmentCare;
	protected String imagePath;
	protected int supplierCount;
	
	
	//Abaya variables
	protected String aFabric;
	protected String aLength;
	protected String weight;
	
	//Cardigan Variable
	protected String compositions;
	
	//Scarf Variables
	protected String sFabric;
	protected String sLength;
	protected String height;
	protected String thickness;
	
	
	
	
	
	public ModifyStockPanel(Menu menu) {
		this.menu = menu;
	}
	
	public void initialize() {
		setBounds(0, 0, 1414, 795);
		setLayout(null);
		
		ObjectLoader loader = new ObjectLoader(menu);
		stockListPanel = menu.getViewStockListPanel();
		
		//JLayeredPane & JPanels
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(910, 85, 504, 193);
		add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel abayaInfoPanel = new JPanel();
		abayaInfoPanel.setLayout(null);
		layeredPane.add(abayaInfoPanel, "name_43614072048600");

		JPanel cardiganInfoPanel = new JPanel();
		cardiganInfoPanel.setLayout(null);
		layeredPane.add(cardiganInfoPanel, "name_43624095459000");
		
		JPanel scarfInfoPanel = new JPanel();
		scarfInfoPanel.setLayout(null);
		layeredPane.add(scarfInfoPanel, "name_43635233345900");
		
		//Set is used to remove redundacies of having the same name repeated
		Vector<String> supplierNameSet = returnSupplierNameSet();
		
				
		//Labels
		
		JLabel lblAbayaFabrics = new JLabel("Fabrics:");
		lblAbayaFabrics.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblAbayaFabrics.setBounds(10, 10, 83, 32);
		abayaInfoPanel.add(lblAbayaFabrics);
				
		JLabel lblAbayaLength = new JLabel("Length:");
		lblAbayaLength.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblAbayaLength.setBounds(20, 43, 77, 32);
		abayaInfoPanel.add(lblAbayaLength);
		
		lblCost = new JLabel("Cost:");
		lblCost.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblCost.setBounds(396, 156, 59, 32);
		add(lblCost);
		
		lblDescription = new JLabel("Description\r\n:");
		lblDescription.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblDescription.setBounds(321, 321, 134, 32);
		add(lblDescription);
		
		lblGarmentCare = new JLabel("Garment Care:");
		lblGarmentCare.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblGarmentCare.setBounds(310, 408, 145, 32);
		add(lblGarmentCare);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblHeight.setBounds(30, 82, 79, 32);
		scarfInfoPanel.add(lblHeight);
		
		lblImageDisplay = new JLabel();
		lblImageDisplay.setBounds(78, 54, 179, 174);
		add(lblImageDisplay);
		
		JLabel lblItemName = new JLabel("Name:");
		lblItemName.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblItemName.setBounds(397, 54, 58, 32);
		add(lblItemName);
		
		lblItemType = new JLabel("Type of Item:");
		lblItemType.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblItemType.setBounds(1039, 54, 145, 32);
		add(lblItemType);
		
		JLabel lblPrice = new JLabel("Unit Price:");
		lblPrice.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblPrice.setBounds(340, 102, 119, 32);
		add(lblPrice);
		
		lblQuantity = new JLabel("Quantity:");
		lblQuantity.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblQuantity.setBounds(356, 202, 99, 32);
		add(lblQuantity);
		
		JLabel lblScarfFabrics = new JLabel("Fabrics:");
		lblScarfFabrics.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblScarfFabrics.setBounds(10, 7, 83, 32);
		scarfInfoPanel.add(lblScarfFabrics);
		
		JLabel lblScarfLength = new JLabel("Length:");
		lblScarfLength.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblScarfLength.setBounds(30, 43, 77, 32);
		scarfInfoPanel.add(lblScarfLength);
		
		lblSeasonType_1 = new JLabel("Season Type\r\n:");
		lblSeasonType_1.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblSeasonType_1.setBounds(329, 535, 128, 32);
		add(lblSeasonType_1);
		
		lblSize = new JLabel("Size\r\n:");
		lblSize.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblSize.setBounds(680, 254, 59, 32);
		add(lblSize);
		
		lblSupplier = new JLabel("Supplier\r\n:");
		lblSupplier.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblSupplier.setBounds(356, 254, 99, 32);
		add(lblSupplier);
		
		JLabel lblThickness = new JLabel("Thickness:");
		lblThickness.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblThickness.setBounds(0, 124, 106, 32);
		scarfInfoPanel.add(lblThickness);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblWeight.setBounds(21, 83, 78, 32);
		abayaInfoPanel.add(lblWeight);
		
		JLabel lblComposition = new JLabel("Compositions:");
		lblComposition.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblComposition.setBounds(10, 24, 135, 32);
		cardiganInfoPanel.add(lblComposition);
		
		//TextField & TextArea
		
		abayaFabricTF = new JTextField(); 
		abayaFabricTF.setEnabled(true);
		abayaFabricTF.setColumns(10);
		abayaFabricTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		abayaFabricTF.setBounds(103, 10, 305, 26);
		abayaInfoPanel.add(abayaFabricTF);
		
		abayaLengthTF = new JTextField();
		abayaLengthTF.setEnabled(true);
		abayaLengthTF.setColumns(10);
		abayaLengthTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		abayaLengthTF.setBounds(103, 46, 305, 26);
		abayaInfoPanel.add(abayaLengthTF);
		
		compositionTF = new JTextField();
		compositionTF.setEnabled(true);
		compositionTF.setColumns(10);
		compositionTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		compositionTF.setBounds(142, 27, 289, 26);
		cardiganInfoPanel.add(compositionTF);
		
		costTF = new JTextField();
		costTF.setColumns(10);
		costTF.setBounds(455, 159, 370, 26);
		add(costTF);
		
		descriptionTA = new JTextArea();
		descriptionTA.setBorder(new LineBorder(new Color(0, 0, 0)));
		descriptionTA.setBounds(455, 315, 370, 70);
		descriptionTA.setLineWrap(true);
		add(descriptionTA);	
		
		garmentCareTA = new JTextArea();
		garmentCareTA.setBorder(new LineBorder(new Color(0, 0, 0)));
		garmentCareTA.setBounds(455, 408, 370, 79);
		garmentCareTA.setLineWrap(true);
		add(garmentCareTA);
		
		heightTF = new JTextField();
		heightTF.setEnabled(true);
		heightTF.setColumns(10);
		heightTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		heightTF.setBounds(103, 85, 305, 26);
		scarfInfoPanel.add(heightTF);
		
		itemNameTF = new JTextField();
		itemNameTF.setColumns(10);
		itemNameTF.setBounds(455, 57, 370, 26);
		add(itemNameTF);
		
		priceTF = new JTextField();
		priceTF.setColumns(10);
		priceTF.setBounds(455, 105, 370, 26);
		add(priceTF);
		
		quantityTF = new JTextField();
		quantityTF.setColumns(10);
		quantityTF.setBounds(455, 205, 370, 26);
		add(quantityTF);
		
		scarfFabricsTF = new JTextField();
		scarfFabricsTF.setEnabled(true);
		scarfFabricsTF.setColumns(10);
		scarfFabricsTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		scarfFabricsTF.setBounds(103, 10, 305, 26);
		scarfInfoPanel.add(scarfFabricsTF);
				
		scarfLengthTF = new JTextField();
		scarfLengthTF.setEnabled(true);
		scarfLengthTF.setColumns(10);
		scarfLengthTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		scarfLengthTF.setBounds(103, 46, 305, 26);
		scarfInfoPanel.add(scarfLengthTF);
		
		thicknessTF = new JTextField();
		thicknessTF.setEnabled(true);
		thicknessTF.setColumns(10);
		thicknessTF.setBounds(103, 127, 303, 26);
		scarfInfoPanel.add(thicknessTF);
		
		weightTF = new JTextField();
		weightTF.setEnabled(true);
		weightTF.setColumns(10);
		weightTF.setBorder(new LineBorder(new Color(171, 173, 179)));
		weightTF.setBounds(103, 82, 305, 26);
		abayaInfoPanel.add(weightTF);
		
		//ComboBox
		
		itemTypeCB = new JComboBox(new String[] {"Abaya", "Cardigan","Scarf"});
		itemTypeCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedValue = itemTypeCB.getSelectedIndex();
				rt.resetAdditionalItemText(abayaFabricTF, abayaLengthTF, compositionTF, heightTF, scarfFabricsTF,scarfLengthTF, thicknessTF, weightTF);
				switch(selectedValue) {
					case 0:
						menu.switchPanels(layeredPane, abayaInfoPanel);
						break;
						
					case 1:
						menu.switchPanels(layeredPane, cardiganInfoPanel);
						break;
						
					case 2:
						menu.switchPanels(layeredPane, scarfInfoPanel);
						break;
				}
			}
		});
		itemTypeCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		itemTypeCB.setBounds(1195, 54, 140, 32);
		add(itemTypeCB);
		
		sizeCB = new JComboBox(new String[] {"S", "M","L", "XL","XXL"});
		sizeCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		sizeCB.setBounds(739, 254, 86, 32);
		add(sizeCB);
				
		seasonTypeCB = new JComboBox(new String[] {"Spring", "Summer","Autumn", "Winter"});
		seasonTypeCB.setFont(new Font("Consolas", Font.PLAIN, 18));
		seasonTypeCB.setBounds(459, 538, 140, 32);
		add(seasonTypeCB);
		
		supplierNameCB = new JComboBox(supplierNameSet);
		supplierNameCB.setFont(new Font("Consolas", Font.PLAIN, 14));
		supplierNameCB.setBounds(454, 254, 200, 32);
		add(supplierNameCB);
		
		
		//Buttons
		btnBrowse = new JButton("Browse File for Image");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePath = loader.browseItem(lblImageDisplay, imagePath);
			}
		});
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBrowse.setBounds(78, 238, 189, 53);
		add(btnBrowse);
		
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReset.setBounds(622, 625, 138, 53);
		add(btnReset);
		
}
	public void setVariables(){
		this.selectedValue = itemTypeCB.getSelectedIndex();
		this.name = itemNameTF.getText(); 
		this.price = new BigDecimal(priceTF.getText()); 
		this.cost = new BigDecimal(costTF.getText());
		this.description = descriptionTA.getText();
		this.quantity = Integer.parseInt(quantityTF.getText());
		this.supplier = getSelectedSupplier(supplierNameCB.getSelectedItem().toString());
		this.size = sizeCB.getSelectedItem().toString();
		this.category = seasonTypeCB.getSelectedItem().toString();
		this.garmentCare = garmentCareTA.getText();
		this.aFabric = abayaFabricTF.getText();
		this.aLength = abayaLengthTF.getText();
		this.weight = weightTF.getText();
		this.compositions = compositionTF.getText();
		this.sFabric = scarfFabricsTF.getText();
		this.sLength = scarfLengthTF.getText();
		this.height = heightTF.getText();
		this.thickness = thicknessTF.getText();
		this.supplierCount = supplierNameCB.getSelectedIndex();
	}
	
	public void setVariables(Item item){
		setVariables();
		this.imagePath = item.getImagePath();
		this.cost = item.getCost();
		this.quantity = item.getQuantity();
	}
	
	public boolean validPrice() {
		if(price.compareTo(cost) < 0) {
			JOptionPane.showMessageDialog(null, "The price of the item must be greater than the cost", "Invalid Price", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean validFields() {
		if(name.trim().isEmpty()||description.trim().isEmpty()||size.trim().isEmpty()||garmentCare.trim().isEmpty()|| imagePath.isEmpty()||
				priceTF.getText().trim().isEmpty()||costTF.getText().trim().isEmpty() || quantityTF.getText().trim().isEmpty()) {
		
			JOptionPane.showMessageDialog(null, "Fill out all the fields", "Empty Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	

	
	public boolean validQuantity() {
		if(Integer.parseInt(quantityTF.getText().trim()) <= 5) {
			JOptionPane.showMessageDialog(null, "Quantity available must be atleast greater than 5", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean checkEmptyAddVariables(int selectedValue) {
		switch(selectedValue) {
			case 0: // Select Abaya
				if (aFabric.trim().isEmpty()||aLength.trim().isEmpty()||weight.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "One or more fields are empty in the Abaya Section", "Abaya Section Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				return true; 
				
			case 1: // Select Cardigan
				if (compositions.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "The composition textfield is empty", "Missing Composition TextField", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				return true; 
			case 2: // Select Scarf
				if (sFabric.trim().isEmpty()||sLength.trim().isEmpty()||height.trim().isEmpty()||thickness.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "One or more fields are empty in the Scarf Section", "Scarf Section Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				return true; 
		}
		return false;
	}
	
	// Gets the supplier name and puts into a set in order to reduce the redundacy
	// of the supplier's name
	protected Supplier getSelectedSupplier(String supplier_name) {
		int count = 0;
		Set<Supplier> supplierSet = new LinkedHashSet<Supplier>(menu.getSupplierList());
		for (Supplier supplier : supplierSet) {
			if (supplier.getCompanyName().equals(supplier_name)) {
				menu.setSupplierCount(count);
				return supplier;
			}
			count++;
		}
		return null;
	}
	
	public void reset() { //This will remove everything within the textfields and textareas and restore back to default
		rt.resetItemText(abayaFabricTF, abayaLengthTF, compositionTF, costTF, descriptionTA, 
				garmentCareTA, heightTF, itemNameTF, itemTypeCB, lblImageDisplay, priceTF, 
				quantityTF, abayaFabricTF, scarfLengthTF, seasonTypeCB, sizeCB, supplierNameCB, thicknessTF, weightTF);
	}
	
	public void addItem(Item item) {
		//This line will process the newly added item and add the cost of this item to this supplier's total payment
		menu.addItem(item);
		Supplier supplier = item.getSupplier();
		menu.getSupplierList().get(supplier.getDisplayID()).addItem(item);
		
		menu.updateSupplier(menu.getSupplierList().get(supplierCount), supplierCount);//Updates the supplier within the database.
		menu.getViewStockListPanel().refreshTable();
		menu.getSpecificItemPanel().refreshTable();
		menu.getRestockPanel().refreshTable();
		
		JOptionPane.showMessageDialog(null, name +" has been successfully added to the Stock List");
		reset();
	}
	
	public void updateItem(Item item, int itemCount) {
		
		item.setDisplayID(menu.getItemList().get(itemCount).getDisplayID());
    	menu.getItemList().set(itemCount, item);
    	Supplier supplier = item.getSupplier();
		menu.getSupplierList().get(supplier.getDisplayID()).updateItem(item);
    	
		menu.updateSupplier(menu.getSupplierList().get(supplier.getDisplayID()), supplier.getDisplayID(), item);//Updates the supplier within the database.
		menu.getViewStockListPanel().refreshTable();
		menu.getSpecificItemPanel().refreshTable();
		//menu.getSupplierListPanel().refreshTable();//Refreshes the supplier table to shows the supplier's updated payment
		menu.getRestockPanel().refreshTable();	
	}
	
	
	public void deleteItem(Item item, int itemCount) {
		menu.removeItem(itemCount);
		Supplier supplier = item.getSupplier();
		menu.getSupplierList().get(supplier.getDisplayID()).removeItem(item);
		menu.getItemList().remove(itemCount);
		menu.getViewStockListPanel().refreshTable();
		menu.getSpecificItemPanel().refreshTable();
		//menu.getSupplierListPanel().refreshTable();
		JOptionPane.showMessageDialog(null, item.getName() +" has been successfully removed from the Stock List");
	}
	

	
	//Getters and Setters
	public void setAbayaFabricTF(String abayaFabricTF) {
		this.abayaFabricTF.setText(abayaFabricTF);
	}
	
	public JTextField getAbayaFabricTF() {
		return abayaFabricTF;
	}

	public void setAbayaLengthTF(String abayaLengthTF) {
		this.abayaLengthTF.setText(abayaLengthTF);;
	}
	
	public JTextField getAbayaLengthTF() {
		return abayaLengthTF;
	}

	public void setCompositionTF(String compositionTF) {
		this.compositionTF.setText(compositionTF);
	}
	
	public JTextField getCompositionTF() {
		return compositionTF;
	}

	public void setCostTF(String costTF) {
		this.costTF.setText(costTF);;
	}

	public void setDescriptionTA(String descriptionTA) {
		this.descriptionTA.setText(descriptionTA);
	}

	public void setGarmentCareTA(String garmentCareTA) {
		this.garmentCareTA.setText(garmentCareTA);;
	}

	public void setHeightTF(String heightTF) {
		this.heightTF.setText(heightTF);
	}
	
	public JTextField getHeightTF() {
		return heightTF;
	}

	public void setItemNameTF(String itemNameTF) {
		this.itemNameTF.setText(itemNameTF);
	}

	public void setPriceTF(String priceTF) {
		this.priceTF.setText(priceTF);
	}

	public void setQuantityTF(String quantityTF) {
		this.quantityTF.setText(quantityTF);
	}

	public void setScarfLengthTF(String scarfLengthTF) {
		this.scarfLengthTF.setText(scarfLengthTF);
	}
	
	public JTextField getScarfLengthTF() {
		return scarfLengthTF;
	}

	public void setScarfFabricsTF(String scarfFabricsTF) {
		this.scarfFabricsTF.setText(scarfFabricsTF);
	}
	
	public JTextField getScarfFabricsTF() {
		return scarfFabricsTF;
	}

	public void setThicknessTF(String thicknessTF) {
		this.thicknessTF.setText(thicknessTF);
	}
	
	public JTextField getThicknessTF() {
		return thicknessTF;
	}
	
	public void setWeightTF(String weightTF) {
		this.weightTF.setText(weightTF);
	}
	
	public JTextField getWeightTF() {
		return weightTF;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public JComboBox getItemTypeCB() {
		return itemTypeCB;
	}

	public void setItemTypeCB(JComboBox itemTypeCB) {
		this.itemTypeCB = itemTypeCB;
	}
	
	public JComboBox getSizeCB() {
		return sizeCB;
	}

	public JComboBox getSeasonTypeCB() {
		return seasonTypeCB;
	}


	public JComboBox getSupplierNameCB() {
		return supplierNameCB;
	}
	
	public JLabel getLblImageDisplay() {
		return lblImageDisplay;
	}
	
	public Vector<String> returnSupplierNameSet() {
		Vector<String> supplierNameSet = new Vector<String>();
		for(Supplier supplier: menu.getSupplierList()) {
			supplierNameSet.add(supplier.getCompanyName());
		}
		return supplierNameSet;
	}
	
	public void updateSupplierCB()
	{
		Vector<String> supplierNameSet = returnSupplierNameSet();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(supplierNameSet);
		supplierNameCB.setModel(model);
		
	}
}
