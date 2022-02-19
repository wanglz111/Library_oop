package com.library.item;

public class Book extends Items{
    public Book(String itemName, String authorName, Type type) {
        super(itemName, authorName, type);
    }

    public void validateAuthorName(String toCheck, String authorName) {
        if (toCheck.isEmpty()) {
            throw new IllegalArgumentException(authorName + " field can't be empty");
        }
        if (!Character.isUpperCase(toCheck.charAt(0))) {
            throw new IllegalArgumentException(authorName + "'s initial character should be an upper case letter");
        }
        for (int i = 1; i < toCheck.length(); i++) {
            if (!Character.isLowerCase(toCheck.charAt(i))) {
                throw new IllegalArgumentException(authorName + "'s characters other than the initial one should be lower case letters");
            }
        }
//        if (toCheck.length() > 10) {
//            throw new IllegalArgumentException(authorName + " field length more than 10");
//        }
    }
}


