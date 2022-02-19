package com.library.database;

import com.library.Person.Person;
import com.library.Person.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class PersonDB {
    List<Person> personList;

    public PersonDB(List<Person> personList) {
        this.personList = personList;
    }

    public PersonDB() {
        this.personList = new ArrayList<>();
    }

    public void show() {
        System.out.println("Displaying Person database:");
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    //for checking if the database is empty
    public boolean isEmpty() {
        return personList.isEmpty();
    }

    //for adding/removing Persons from the database
    public boolean add(Person person) {
        return personList.add(person);
    }

    public boolean remove(Person person, PersonDB personList) {
        return personList.personList.remove(person);
    }

    public void removeUser(UserInfo user, PersonDB personList){
        if (user.getBorrowList().size() == 0 ){
            remove(user, personList);
        }else {throw new IllegalArgumentException("The user you select still have items not return.");}
    }


    public static Person findById(int userId, PersonDB personList){
        for (Person person : personList.personList){
            if(new UserInfo(person).getID() == userId){
                return person;
            }
        }
        throw new IllegalArgumentException("No user id = "+ userId + "in the database.");
    }

    public static Person findByName(String name, PersonDB personList) {
        for (Person person : personList.personList) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        throw new IllegalArgumentException("No username = " + name + " in the database.");
    }

}

