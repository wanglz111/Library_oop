package com.library.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Person {

    public enum Gender{MALE, FEMALE}
    //Up to 150 years old
    private static final int MAX_AGE = 150;

    private String name;

    private String password;

    private Gender gender;

    private String telephone;

    private String location;

    private LocalDate dateOfBirth;

    public Person(String name, String password, Gender gender, String telephone,
                  String location, LocalDate dateOfBirth){
        this.name = name;
        validatePassword(password);
        this.password = password;
        this.gender = gender;
        validateTelephone(telephone);
        this.telephone = telephone;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
    }

    public void relocateTo(String newLocation) {
//        validateLocation(newLocation); //check new location is valid
        this.location = newLocation;
    }

    public static void validateProperNoun(String toCheck, String fieldName) {
        if (toCheck.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " field can't be empty");
        }
        if (!Character.isUpperCase(toCheck.charAt(0))) {
            throw new IllegalArgumentException(fieldName + "'s initial character should be an upper case letter");
        }
        for (int i=1; i<toCheck.length(); i++) {
            if (!Character.isLowerCase(toCheck.charAt(i))) {
                throw new IllegalArgumentException(fieldName + "'s characters other than the initial one should be lower case letters");
            }
        }
    }

    public static void validatePassword(String password) {
        //password cannot be empty and can  6-12 digits
        if (password.isEmpty()) {
            throw new IllegalArgumentException(" Password field can't be empty");
        }
        int length = password.length();
        if (length <6 ) {
            throw new IllegalArgumentException(" password must be 6-12 digits.");
        }
        else if (length >12){
            throw new IllegalArgumentException(" password must be 6-12 digits.");
        }
    }

    public void attributeChangeTo(String toChange, String fieldName){
        //satisfies the conditions.
        if ("password".equals(fieldName)){this.password = toChange;}
        else if ("location".equals(fieldName)){this.location = toChange;}
        else if ("telephone".equals(fieldName)){this.telephone = toChange;}
    }

    private static int calculateAge(LocalDate dateOfBirth) {
        //today
        LocalDate now = LocalDate.now();
        //age in years, months and days
        Period totalAge = Period.between(dateOfBirth, now);
        return totalAge.getYears();
    }

    public static void validateDateOfBirth(LocalDate dateOfBirth) {//Restrict birthdays to be non-negative and not more than 150 years old
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Age field can't have negative value");
        }
        if (calculateAge(dateOfBirth) > MAX_AGE) {
            throw new IllegalArgumentException("Age field can't have value more than " + MAX_AGE);
        }
    }

    public static void validateTelephone(String telephone) {//The phone number is 11 digits and starts with 1
        int length = telephone.length();

        if ("1".equals(String.valueOf(telephone.charAt(0)))){}else{
            throw new IllegalArgumentException((" Telephone must begin with 1."));
        }
        if (length != 11){
            throw new IllegalArgumentException(" Telephone must be 11 digits.");
        }
    }


    public void register(){}

    public void login(){}

    public void logout(){}

    @Override
    public String toString() {
        return "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", telephone='" + getTelephone() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof Person)) {return true;}
        Person person = (Person) o;
        return name.equals(person.name) && password.equals(person.password) && gender == person.gender && telephone.equals(person.telephone) && location.equals(person.location) && dateOfBirth.equals(person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, gender, telephone, location, dateOfBirth);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
