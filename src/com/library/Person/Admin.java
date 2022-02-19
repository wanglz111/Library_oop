package com.library.Person;

import java.time.LocalDate;

public class Admin extends Person{
    /**
     * Number increases with constructor.
     */
    private static int number = 1;
    private int ID;


    public Admin(String name, String password, Gender gender, String telephone, String location, LocalDate dateOfBirth) {
        super(name, password, gender, telephone, location, dateOfBirth);
        this.ID = 100000+number;
        number++;
    }

    public Admin(Person person){
        super(person.getName(), person.getPassword(),person.getGender(),person.getTelephone(),person.getLocation(),person.getDateOfBirth());
        this.ID = 100000+number;
        number++;
    }

    @Override
    public String toString() {
        return "Admin [" +
                "ID=" + ID +", "+ super.toString() +
                ']';
    }
}
