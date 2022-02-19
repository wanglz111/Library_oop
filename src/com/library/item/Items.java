package com.library.item;

import java.time.LocalDate;
import java.time.Period;

public class Items{

    private static int i = 1;
    public enum Type{Book, Magazine, DVD}
    private int itemId;
    private String itemName;
    private String authorName;
    private Type type;
    private static final int MAX_AGE = 5000;
    //true for not rent, false for rent.
    private boolean status;


    public Items(String itemName, String authorName, Type type){
        validateProperNoun(authorName, "Author Name");
        this.authorName = authorName;
        this.itemId = 900000+i;
        this.itemName = itemName;
        this.type = type;
        this.status = true;
        i++;
    }

    public static void borrowItem(Items item){
        //rent an item and reverse the flag to false
        if (item.status){
        item.status = false;
        }else {
            throw new IllegalArgumentException("The item has been rented, please choose another one.");
        }
    }

    public static void returnItem(Items item){
        //rent an item and reverse the flag to false
        if (!item.status){
            item.status = true;
        }else {
            throw new IllegalArgumentException("The item has been returned, please check again.");
        }
    }

    public static void validateProperNoun(String toCheck, String fieldName) {//The limited name cannot be empty, the first letter is capitalized, and the other is lowercase
        if (toCheck == null || toCheck.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " field can't be empty");
        }
        if (!Character.isUpperCase(toCheck.charAt(0))) {
            throw new IllegalArgumentException(fieldName + "'s initial character should be an upper case letter");
        }
        for (int i = 1; i < toCheck.length(); i++) {
            if (!Character.isLowerCase(toCheck.charAt(i))) {
                throw new IllegalArgumentException(fieldName + "'s characters other than the initial one should be lower case letters");
            }
        }
    }



    public static void validateId(String toCheck, String itemId){
        if (toCheck == null || toCheck.isEmpty()) {
            throw new IllegalArgumentException(itemId + " field can't be empty");
        }
//        if (toCheck.length() > 10) {
//            throw new IllegalArgumentException(itemId + " field length more than 10");
//        }

    }

    public static void validateDateOfPublic(LocalDate dateOfPublic) {
        if (dateOfPublic.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Years of publication field can't have negative value");
        }
        if (calculateAge(dateOfPublic) > MAX_AGE) {
            throw new IllegalArgumentException("Years of publication field can't have value more than " + MAX_AGE);
        }
    }

    private static int calculateAge(LocalDate dateOfPublic) {
        //today
        LocalDate now = LocalDate.now();
        //age in years, months and days
        Period totalAge = Period.between(dateOfPublic, now);
        return totalAge.getYears();
    }


    public static void validateItemPrice(double price) {
        int maxPrice = 10000;
        if (price <= 0) {
            throw new IllegalArgumentException("Price of item must be more than 0.");
        }
        if(price>= maxPrice) {
            throw new IllegalArgumentException("Price of item must be less than 10000.");


        }
    }



    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Items.Type getType() {
        return type;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return type + "[" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", status=" + status +
                ']';
    }
}
