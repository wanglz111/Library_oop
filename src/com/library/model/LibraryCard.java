package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class LibraryCard {
    private int cardNo;
    private ArrayList cardholdersId;
    public static final int maxCardCapacity = 4;


    public LibraryCard(int cardNo) {
        this.cardNo = cardNo;
        this.cardholdersId = new ArrayList();
    }

    public LibraryCard(int cardNo, ArrayList cardholdersId){
        this.cardNo = cardNo;
        this.cardholdersId = cardholdersId;
    }

    public static ArrayList addCardholdersId(int UserId, ArrayList cardholdersId) {
        //every card can be given to less than 4 people.
        if (cardholdersId.size() >= maxCardCapacity) {
            throw new IllegalArgumentException("The card capacity is full, please choose another card.");
        } else {
            cardholdersId.add(UserId);
            return cardholdersId;
        }
    }

    public static ArrayList delCardholdersId(int UserId, ArrayList cardholdersId) {
        for (int i = 0; i < cardholdersId.size(); i++) {
            if (cardholdersId.get(i).equals(UserId)) {
                cardholdersId.remove(i);
                return cardholdersId;
            }
        }
        throw new IllegalArgumentException("Cannot find the person whose userid is" + UserId +" in this cardNo");
    }

    public static ArrayList updateCardholdersId(int UserId, int toUpdate, ArrayList cardholdersId) {
        for (int i = 0; i < cardholdersId.size(); i++) {
            if (cardholdersId.get(i).equals(UserId)) {
                cardholdersId.remove(i);
                cardholdersId.add(toUpdate);
                return cardholdersId;
            }
        }
        throw new IllegalArgumentException("Cannot find the person whose userid is" + UserId +" in this cardNo");
    }

    public static String[] viewCardholdersId(ArrayList cardholdersId){
        String[] idList = new String[4];
        for (int i = 0; i < cardholdersId.size(); i++){
            idList[i] = cardholdersId.get(i).toString();
        }
        return idList;
    }

    /**
     * The entered card number must be greater than 0
     */
    public static void validateCardNo( int cardNo) {
        if (cardNo<0) {
            throw new IllegalArgumentException(" Please enter cardNo correctly.");
        }
    }

    public ArrayList getCardholdersId() {
        return cardholdersId;
    }

    public int getCardNo() {
        return cardNo;
    }

    @Override
    public String toString() {
        return "LibraryCard[" +
                "cardNo=" + cardNo +
                ", cardholdersId=" + cardholdersId +
                ']';
    }
}










