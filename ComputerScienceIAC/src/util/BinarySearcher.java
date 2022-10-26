package util;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Admin;
import entity.Item;
import entity.Supplier;
import menu.Menu;

public class BinarySearcher { //criteria
	
	Sorter sorter = new Sorter();//Instantiate Sorter
	
	 public void search(Menu menu, Item[] arr, boolean sortType, int index, String search){
		 int start = index;
		 int end = arr.length-1;
		 arr = sorter.recursiveSortItem(arr, "A", arr.length);//Before performing the binary search it is essential to sort the array
		 binarySearch(menu, arr, start, end, search); 
	}
	 
	public void binarySearch(Menu menu, Object[] list, int start, int end, String search) {
		if(start > end) { //This is displayed whenever the search criteria is not found in the item list
			JOptionPane.showMessageDialog(null, search + " is not found in the table", "Failed to find search criteria",JOptionPane.ERROR_MESSAGE);
		}
		else {
			int mid = start + (end-start)/2; //calculating the middle index 
			searchShopping(menu, list, start, end, search, mid);
		}
	}
	
	public void searchShopping(Menu menu, Object[] list, int start, int end, String search, int mid) {
		Item[] itemList = (Item[]) list;
		LinkedList<Item> foundItem = new LinkedList<Item>();
		
		if(itemList[mid].getName().compareTo(search) == 0) {//Item has been found
			//Item has been found
			foundItem.add(itemList[mid]);
			menu.getShoppingPagePanel().refreshTable(foundItem); //refresh the Shopping JTable displaying the found Item
		}
		else if(itemList[mid].getName().compareTo(search) > 0) {//If the search criteria is lexicographically smaller than the mid index
			end = mid-1;
			binarySearch(menu,list, start, end, search);
		}
		else {//If the search criteria is lexicographically greater than the mid index
			start = mid+1;
			binarySearch(menu,list, start, end, search);
		}
	}
	

}
