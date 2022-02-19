package com.library.database;

import com.library.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ItemDB {

    private List<Items> itemsList;

    public ItemDB(){
        this.itemsList = new ArrayList<>();
    }
    public ItemDB(List<Items> itemsList){
        this.itemsList = itemsList;
    }

    //for displaying the database
    public void show() {
        System.out.println("Displaying Item database:");
        for (Items item : itemsList) {
            System.out.println(item);
        }
    }

    public boolean isEmpty() {
        return itemsList.isEmpty();
    }

    //for adding/removing Items from the database
    public boolean add(Items item) {
        return itemsList.add(item);
    }

    public boolean remove(Items item) {
        return itemsList.remove(item);
    }

    public void removeItem(Items item) {
        if (item.isStatus()){
            remove(item);
        }else {
            throw new IllegalArgumentException("This item is rented, please check.");
        }
    }

    public static Items findById(int id, ItemDB itemsList) {
        if (!itemsList.isEmpty()){
        for (Items item : itemsList.itemsList) {
            if (item.getItemId() == id) {
                return item;
            }
        }
        }
        throw new IllegalArgumentException("No Item with ID number " + id + " in the database.");
    }

    public static Items findByName(String name, ItemDB itemsList) {
        for (Items item : itemsList.itemsList) {
            if (item.getItemName().equals(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException("No Item with name " + name + " in the database.");
    }


}
