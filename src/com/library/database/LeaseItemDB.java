package com.library.database;

import com.library.model.LeaseItem;

import java.util.ArrayList;
import java.util.List;

public class LeaseItemDB {
    private List<LeaseItem> leaseItemList;

    public LeaseItemDB(){
        this.leaseItemList = new ArrayList<>();
    }
    public LeaseItemDB(List<LeaseItem> leaseItemList){
        this.leaseItemList = leaseItemList;
    }

    //for displaying the database
    public void show() {
        System.out.println("Displaying borrowing records database:");
        for (LeaseItem leaseItem : leaseItemList) {
            System.out.println(leaseItem);
        }
    }

    public boolean isEmpty() {
        return leaseItemList.isEmpty();
    }

    //for adding/removing borrowing records from the database
    public boolean add(LeaseItem leaseItem) {
        return leaseItemList.add(leaseItem);
    }

    public static boolean remove(LeaseItem leaseItem, LeaseItemDB leaseItemList) {
        return leaseItemList.leaseItemList.remove(leaseItem);
    }

    public static LeaseItemDB findLeaseListByUserId(int UserId, LeaseItemDB leaseItemList) {
        LeaseItemDB leaseList = new LeaseItemDB();
        for (LeaseItem leaseItem : leaseItemList.leaseItemList) {
            if (leaseItem.getUserId() == UserId) {
                leaseList.add(leaseItem);
            }
        }
        return leaseList;
    }

    public static LeaseItem findById(int itemId, LeaseItemDB leaseItemList) {
        for (LeaseItem leaseItem : leaseItemList.leaseItemList) {
            if (leaseItem.getItemId() == itemId) {
                return leaseItem;
            }
        }
        throw new IllegalArgumentException("No item with ID number " + itemId + " in the database.");
    }

    public static LeaseItem findByUserIdAndItemId(int itemNo, int userId,LeaseItemDB leaseItemList) {
        for (LeaseItem leaseItem : leaseItemList.leaseItemList) {
            if (leaseItem.getItemId() == itemNo&&leaseItem.getUserId() == userId) {
                return leaseItem;
            }
        }
        throw new IllegalArgumentException(userId + " did not lease the item id is "+ itemNo);
    }
}
