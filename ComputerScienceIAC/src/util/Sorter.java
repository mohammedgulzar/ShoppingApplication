package util;

import entity.Admin;
import entity.Item;
import entity.Supplier;

public class Sorter {
	
	public void swap(Object[] arr, int i) { //Bubble sorting
		Object temp = arr[i];
		arr[i] = arr[i+1];
		arr[i+1] = temp;
	}
	
	public Supplier[] recursiveSortSupplier(Supplier[] arr, String type,int length) {// Recursive bubble sort
		if (length <= 1) {
			return arr;
		}
		switch(type) {
		case "A":
			for(int i = 0; i < length-1; i++) {
				if(sortAscendingSupplier(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
		
		case "D":
			for(int i = 0; i < length-1; i++) {
				if(sortDescendingSupplier(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
	}
		return recursiveSortSupplier(arr, type ,length-1);
	}
	
	public Item[] recursiveSortItem(Item[] arr, String type,int length) {// Recursive bubble sort
		if (length <= 1) {
			return arr;
		}
		switch(type) {
		case "A":
			for(int i = 0; i < length-1; i++) {
				if(sortAscendingItem(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
		
		case "D":
			for(int i = 0; i < length-1; i++) {
				if(sortDescendingItem(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
			
		case "P":
			for(int i = 0; i < length-1; i++) {
				if(sortAscendingPrice(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
	}
		return recursiveSortItem(arr, type ,length-1);
	}
	
	public Admin[] recursiveSortAdmin(Admin[] arr, String type,int length) {// Recursive bubble sort
		if (length <= 1) {
			return arr;
		}
		switch(type) {
		case "A":
			for(int i = 1; i < length-1; i++) {
				if(sortAscendingAdmin(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
		
		case "D":
			for(int i = 1; i < length-1; i++) {
				if(sortDescendingAdmin(arr, i)) {
					swap(arr, i);
				}	
			}
			break;
	}
		return recursiveSortAdmin(arr, type ,length-1);
	}
	
	
	public boolean sortAscendingItem(Item[] arr, int i) {
		return arr[i+1].getName().compareToIgnoreCase(arr[i].getName()) < 0;
	}
	
	public boolean sortDescendingItem(Item[] arr, int i) {
		return arr[i+1].getName().compareToIgnoreCase(arr[i].getName()) > 0;
	}
	
	public boolean sortAscendingPrice(Item[] arr, int i) {
		return arr[i+1].getPrice().compareTo(arr[i].getPrice()) <= 0;
	}
	
	public boolean sortAscendingAdmin(Admin[] arr, int i) {
		return arr[i+1].getFirstName().compareToIgnoreCase(arr[i].getFirstName()) < 0;
	}
	
	public boolean sortDescendingAdmin(Admin[] arr, int i) {
		return arr[i+1].getFirstName().compareToIgnoreCase(arr[i].getFirstName()) > 0;
	}
	
	public boolean sortAscendingSupplier(Supplier[] arr, int i) {
		return arr[i+1].getCompanyName().compareTo(arr[i].getCompanyName()) < 0;
	}
	
	public boolean sortDescendingSupplier(Supplier[] arr, int i) {
		return arr[i+1].getCompanyName().compareToIgnoreCase(arr[i].getCompanyName()) > 0;
	}
}
