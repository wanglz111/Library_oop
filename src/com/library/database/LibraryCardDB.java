package com.library.database;

import com.library.model.LibraryCard;

import java.util.ArrayList;
import java.util.List;

public class LibraryCardDB {

    private List<LibraryCard> cardList;

    public LibraryCardDB(){
        this.cardList = new ArrayList<>();
    }

    public LibraryCardDB(List<LibraryCard> cardList){
        this.cardList = cardList;
    }

    //for displaying the database
    public void show() {
        System.out.println("Displaying Item database:");
        for (LibraryCard card : cardList) {
            System.out.println(card);
        }
    }

    public boolean isEmpty() {
        return cardList.isEmpty();
    }


    //for adding/removing cards from the database
    public boolean add(LibraryCard card) {
        return cardList.add(card);
    }

    private boolean remove(LibraryCard card) {
        return cardList.remove(card);
    }

    public void removeCard(LibraryCard card){
        if (card.getCardholdersId().size() == 0){
            remove(card);
        }else {throw new IllegalArgumentException("The card is not empty!");}
    }

    public  boolean isExist(int cardNo) {
        for (LibraryCard card : cardList){
            if (card.getCardNo() == cardNo){
                return true;
            }
        }
        return false;
    }

    public LibraryCard findById(int cardNo) {
        for (LibraryCard card : cardList) {
            if (card.getCardNo() == cardNo) {
                return card;
            }
        }
        throw new IllegalArgumentException("No Card with ID number " + cardNo + " in the database.");
    }
}
