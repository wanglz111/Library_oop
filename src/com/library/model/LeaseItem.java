package com.library.model;

import java.time.LocalDate;

public class LeaseItem {
    private LocalDate shouldDate;
    private int itemId;
    private int userId;
    private String userName;
    private String itemTitle;

    public LeaseItem(int itemId, int userId, String userName, String itemTitle) {
        this.itemId = itemId;
        this.userId = userId;
        this.itemTitle = itemTitle;
        this.userName = userName;
        this.shouldDate = LocalDate.now().plusDays(28);
    }

    public void addLeaseDate(){
        this.shouldDate = LocalDate.now().plusDays(28);
    }

    public LocalDate getShouldDate() {
        return shouldDate;
    }

    public int getItemId() {
        return itemId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    @Override
    public String toString() {
        return "LeaseItem [" +
                ", userId=" + getUserId() +
                ", userName='" + getUserName() +
                ", itemId=" + getItemId() +
                ", itemTitle='" + getItemTitle()+
                "shouldDate=" + getShouldDate() +
                ']';
    }
}




