package com.library.item;

public class Magazine extends Items{


    public Magazine(String itemName, String authorName, Type type) {
        super(itemName, authorName, type);
    }

    public void validateauthorname(String toCheck, String AuthorName) {
        if (toCheck.isEmpty()) {
            throw new IllegalArgumentException(AuthorName + " field can't be empty");
        }
        if (!Character.isUpperCase(toCheck.charAt(0))) {
            throw new IllegalArgumentException(AuthorName + "'s initial character should be an upper case letter");
        }
        for (int i = 1; i < toCheck.length(); i++) {
            if (!Character.isLowerCase(toCheck.charAt(i))) {
                throw new IllegalArgumentException(AuthorName + "'s characters other than the initial one should be lower case letters");
            }
        }
        if (toCheck.length() > 10) {
            throw new IllegalArgumentException(AuthorName + " field length more than 10");
        }
    }

}
