package com.library;

import com.library.Person.Admin;
import com.library.Person.Person;
import com.library.Person.UserInfo;
import com.library.database.*;
import com.library.model.Activity;
import com.library.item.Items;
import com.library.model.LeaseItem;
import com.library.model.LibraryCard;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LibraryInterface {

    private static final Scanner keyboard = new Scanner(System.in);

    public static ItemDB itemList = new ItemDB();
    public static ActivityDB activityList = new ActivityDB();
    public static LibraryCardDB libraryCardList = new LibraryCardDB();
    public static LeaseItemDB leaseItemList = new LeaseItemDB();
    public static PersonDB personDatabase = new PersonDB();
    /**
     * entrance
     * @param personDatabase
     */

    public static void run(PersonDB personDatabase) {
        System.out.println("Welcome to the Library management system");
        String[] options = { //initial options
                "0. Exit.",
                "1. User login.",
                "2. Register."
        };
        String choice;
        do {
            System.out.println("====================================");
            choice = getChoice(options);
            switch (choice) {
                case "1" -> {
                    try {
                        login(personDatabase);
                    } catch (IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> addPersonMenu(personDatabase);
            }
        }
        while (!"0".equals(choice));
        keyboard.close();
    }
    public static void login(PersonDB personDatabase){
        String username = getUsername();
        String password = getPasswordInput();
        Person person = personDatabase.findByName(username, personDatabase);
        if (password.equals(person.getPassword())){
            roleJudgment(person);
        }else {throw new IllegalArgumentException("The username or password is wrong! Please try again.");}
    }

    private static void roleJudgment(Person person){
        //Admin login
        if ("Admin".equals(person.getClass().getSimpleName().toString())){
            adminInterface();
        }else if ("UserInfo".equals(person.getClass().getSimpleName().toString())){
            userInterface(person);
        }else {throw new IllegalArgumentException("Something wrong with system, please login again.");
        }
    }

    private static void adminInterface(){
        String[] options = {
                "1. Enter the item management interface.",
                "2. Enter the activity management interface.",
                "3. Enter the userinfo management interface."
        };
        String choice = getChoice(options);
        switch (choice) {
            case "1" -> itemManageInterface(itemList);
            case "2" -> activityManageInterface(activityList);
            case "3" -> userinfoManageInterface(personDatabase);
        }
    }

    private static void userInterface(Person person){
        String[] options = {
                "1. Enter the item management interface.",
                "2. Enter the activity management interface.",
                "3. Return of renew items"
        };
        String choice = getChoice(options);
        switch (choice) {
            case "1" -> userItemManageInterface(itemList , new UserInfo(person));
            case "2" -> userActivityManageInterface(activityList, new UserInfo(person));
            case "3" -> leaseItemInterface(new UserInfo(person));
        }
    }

    private static void leaseItemInterface(UserInfo userInfo) {
        String[] options = {
                "1. Return items.",
                "2. Renew items.",
                "3. Show Lease list."
        };
        String choice = getChoice(options);
        switch (choice) {
            case "1" -> userReturnItemInterface(userInfo);
            case "2" -> userRenewItemInterface(userInfo);
            case "3" -> getLeaseItemList(userInfo);
        }
    }

    private static void userReturnItemInterface(UserInfo userInfo){
        getLeaseItemList(userInfo);
        System.out.println("Choose which item you want to return ,please enter the item id");
        String input = keyboard.nextLine().strip();
        try {
            int itemId = Integer.parseInt(input);
            Items item = ItemDB.findById(itemId, itemList);
            Items.returnItem(item);
            UserInfo.returnLeaseItemNo(itemId, userInfo.getBorrowNoList());
            LeaseItem leaseItemRecord = LeaseItemDB.findByUserIdAndItemId(itemId, userInfo.getID(), leaseItemList);
            LeaseItemDB.remove(leaseItemRecord, leaseItemList);

        }catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Something wrong, please try again. ");
            System.out.println(ex.getMessage());
            userReturnItemInterface(userInfo);
        }

    }

    private static void userRenewItemInterface(UserInfo userInfo){
        getLeaseItemList(userInfo);
        System.out.println("Choose which item you want to renew ,please enter the item id");
        String input = keyboard.nextLine().strip();
        try {
            int itemId = Integer.parseInt(input);
            LeaseItem leaseItemRecord = LeaseItemDB.findByUserIdAndItemId(itemId, userInfo.getID(), leaseItemList);
            leaseItemRecord.addLeaseDate();

        }catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Something wrong, please try again. ");
            System.out.println(ex.getMessage());
            userRenewItemInterface(userInfo);
        }

    }

    private static void getLeaseItemList(UserInfo userInfo) {
        LeaseItemDB userLeaseList = LeaseItemDB.findLeaseListByUserId(userInfo.getID(), leaseItemList);
        userLeaseList.show();
    }


    private static void userActivityManageInterface(ActivityDB activityList, UserInfo userInfo) {
        activityList.show();
        System.out.println("Choose which activity you want to attend ,please enter the activity id");
        String input = keyboard.nextLine().strip();
        try {
            int activityId = Integer.parseInt(input);
            Activity activity = ActivityDB.findById(activityId, activityList);
            UserInfo.addActivityNo(activityId, userInfo.getActivityNoList());
        }catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Please enter the right activity id. ");
            System.out.println(ex.getMessage());
            userActivityManageInterface(activityList, userInfo);
        }

    }

    private static void userItemManageInterface(ItemDB itemList, UserInfo userInfo) {
        itemList.show();
        System.out.println("Choose which item do you want to lease ,please enter the item id");
        String input = keyboard.nextLine().strip();
        try {
            int itemId = Integer.parseInt(input);
            Items item = ItemDB.findById(itemId, itemList);
            Items.borrowItem(item);
            LeaseItem leaseItemRecord = new LeaseItem(item.getItemId(), userInfo.getID(), userInfo.getName(), item.getItemName());
            leaseItemList.add(leaseItemRecord);
            UserInfo.addLeaseItemNo(itemId, userInfo.getBorrowNoList());
            userItemManageInterface(itemList, userInfo);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Please enter the right item id. ");
            System.out.println(ex.getMessage());
            userItemManageInterface(itemList, userInfo);
        }

    }

    private static void userinfoManageInterface(PersonDB personDatabase) {
        personDatabase.show();
        String[] options = {
                "1. Add userInfo to database.",
                "2. delete userInfo from database.",
                "3. show userInfo of database."
        };
        String choice = getChoice(options);
        switch (choice) {
            case "1" -> addUser(personDatabase);
            case "2" -> delUserList(personDatabase);
            case "3" -> showUser(personDatabase);
        }
    }

    private static void showUser(PersonDB personDatabase) {
        personDatabase.show();
    }

    public static void delUserList(PersonDB personDatabase) {
        personDatabase.show();
        System.out.println("Please input user name you want to delete");
        String input = keyboard.nextLine().strip();
        try {
            delUser(personDatabase, input);

        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            personDatabase.show();
            delUserList(personDatabase);
        }
    }

    private static void delUser(PersonDB personDatabase, String userName) {
        try {personDatabase.remove(personDatabase.findByName(userName, personDatabase), personDatabase);}
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            delUser(personDatabase, userName);
        }
    }

    public static void addUser(PersonDB personDatabase) {
        String name = getProperNounInput("name");
        String password = getPasswordInput();
        Person.Gender gender = getGenderInput();
        LocalDate dateOfBirth = getDateOfBirthInput();
        String location = getProperNounInput("location");
        String telephone = getTelephoneInput();
        int studentNo = getStudentNoInput();

        UserInfo toAdd = new UserInfo(name, password, gender, telephone, location, dateOfBirth,studentNo);
        personDatabase.add(toAdd);
        userinfoManageInterface(personDatabase);

    }

    public static void activityManageInterface(ActivityDB activityList){
        activityList.show();
        String[] options = {
                "1. Add items to database.",
                "2. delete items from database.",
                "3. show items of database."
        };
        String choice = getChoice(options);
        switch (choice) {
            case "1" -> addActivity(activityList);
            case "2" -> delActivityList(activityList);
            case "3" -> showActivity(activityList);
        }
    }

    public static void showActivity(ActivityDB activityList) {
        activityList.show();
    }

    public static void delActivityList(ActivityDB activityList) {
        activityList.show();
        System.out.println("Please input activity id you want to delete. ");
        String input = keyboard.nextLine().strip();
        try {
            int activityId = Integer.parseInt(input);
            delActivity(activityList, activityId);

        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            activityList.show();
            delActivityList(activityList);
        }
    }

    public static void delActivity(ActivityDB activityList, int activityId) {
        try {activityList.remove(ActivityDB.findById(activityId, activityList));}
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            delActivity(activityList, activityId);
        }
    }

    public static void addActivity(ActivityDB activityList){
        String name = getProperNounInput("name");
        String type = getActivityType();
        String place = getActivityPlace();
        LocalDate startDate = getStartDate();
        int lastTime = getLastTime();
        int capacity = getCapacity();
        Activity activity = new Activity(name,type,place,startDate,lastTime,capacity);
        activityList.add(activity);
        activityList.show();
    }

    public static int getCapacity() {
        System.out.println("Please enter the maximum capacity");
        String input = keyboard.nextLine().strip();
        try {
            int capacity = Integer.parseInt(input);
            Activity.validateCapacity(capacity);
            return capacity;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Please enter again. ");
            return getCapacity();
        }
    }

    public static int getLastTime() {
        System.out.println("Please input how long activity will take");
        String input = keyboard.nextLine().strip();
        try {
            int lastTime = Integer.parseInt(input);
            return lastTime;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: The duration of activity must positive. ");
            return getLastTime();
        }
    }

    public static LocalDate getStartDate() {
        System.out.println("Please input the date of activity. Format should be YYYY-MM-DD.");
        String input = keyboard.nextLine().strip();
        try {
            LocalDate startDate = LocalDate.parse(input);
            Activity.validateActivityTime(startDate);
            return startDate;
        }
        catch (DateTimeParseException ex) {
            System.out.println("Invalid format. Please try again. " + ex.getMessage());
            return getStartDate();
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
            return getStartDate();
        }
        
    }

    public static String getActivityPlace() {
        System.out.println("Please input activity place. ");
        String input = keyboard.nextLine().strip();
        try {
            Person.validateProperNoun(input, "Activity place ");
            return input;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Error in get activity place, please try again.");
            return getActivityPlace();
        }
    }

    public static String getActivityType(){
        System.out.println("Please input activity type. ");
        String input = keyboard.nextLine().strip();
        try {
            Person.validateProperNoun(input, "Activity type ");
            return input;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Error in get activity type, please try again.");
            return getActivityType();
        }
    }


    public static void itemManageInterface(ItemDB itemList){
        itemList.show();
        String[] options = {
                "1. Add items to database.",
                "2. delete items from database.",
                "3. show items of database."
        };
        String choice = getChoice(options);
        switch (choice) {
            case "1" -> addItem(itemList);
            case "2" -> delItemList(itemList);
            case "3" -> showItem(itemList);
        }

    }

    public static void addItem(ItemDB itemList){
        String itemName = getProperNounInput("Item Name");
        String authorName = getProperNounInput("Author Name");
        Items.Type type = getTypeInput();
        Items item = new Items(itemName, authorName, type);
        itemList.add(item);
        itemList.show();
        itemManageInterface(itemList);
    }

    public static void delItemList(ItemDB itemList){
        itemList.show();
        System.out.println("Please input ItemId you want to delete");
        String input = keyboard.nextLine().strip();
        try {
            int itemId = Integer.parseInt(input);
            delItem(itemList, itemId);

        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            itemList.show();
            delItemList(itemList);
        }
    }

    public static void delItem(ItemDB itemList, int itemId){
        try {itemList.remove(ItemDB.findById(itemId, itemList));}
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            delItem(itemList, itemId);
        }
    }

    public static void showItem(ItemDB itemList){
        itemList.show();
    }

    public static String getUsername() {
        System.out.println("Please input your username. (This is the name you register.)");
        String input = keyboard.nextLine().strip();
        try {
            Person.validateProperNoun(input, "Username ");
            return input;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: The username is wrong, please try again.");
            return getPasswordInput();
        }
    }

    public static Items.Type getTypeInput() {
        System.out.println("Please input the type of Item. (DVD / Magazine /Book)");
        String input = keyboard.nextLine().strip();
        try {
            return Items.Type.valueOf(input);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: Type should be one of the following "
                    + Arrays.toString(Person.Gender.values()));
            return getTypeInput();
        }
    }


    //add a person to the database
    public static void addPersonMenu(PersonDB personDatabase) {
        String[] options = {
                "1. Register as an administrator.",
                "2. Register as a student."
        };
        String choice = getChoice(options);
        userRegister(personDatabase, choice); //use a single method to add a Person, pass the choice of academic/student as argument
    }

    public static String getChoice(String[] options) {
        //all Strings in options should start "x. []" with a number x.
        System.out.println("Please choose one of the following " + options.length + " options.");
        for (String opt : options) {
            System.out.println(opt);
        }
        List<String> choices = new ArrayList<>();
        for (String opt : options) {
            choices.add(opt.substring(0, opt.indexOf(".")));
        }
        String choice = keyboard.nextLine().strip();
        if (choices.contains(choice)) {
            return choice;
        } else {
            System.out.println("Invalid input. Please try again.");
            return getChoice(options);
        }
    }

    public static void userRegister(PersonDB db, String choice) {
        //get all the Person input first
        String name = getProperNounInput("name");
        String password = getPasswordInput();
        Person.Gender gender = getGenderInput();
        LocalDate dateOfBirth = getDateOfBirthInput();
        String location = getProperNounInput("location");
        String telephone = getTelephoneInput();
        Person toAdd = null;//value will never be used since 'choice' will always equal "1" or "2"
        switch (choice) {
            case "1" -> {//add an admin

                toAdd = new Admin(name, password, gender, telephone, location, dateOfBirth);
            }
            case "2" -> {//add a student
                int studentNo = getStudentNoInput();
                toAdd = new UserInfo(name, password, gender, telephone, location, dateOfBirth,studentNo);
            }
        }
        db.add(toAdd);
    }

    private static int getStudentNoInput() {
        System.out.println("Please input your Student Card number");
        String input = keyboard.nextLine().strip();
        try {
            int studentNo = Integer.parseInt(input);
            return studentNo;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: The length of student card number must be 7. ");
            return getStudentNoInput();
        }
    }

    private static String getTelephoneInput() {
        System.out.println("Please input your telephone");
        String input = keyboard.nextLine();
        try {
            Person.validateTelephone(input);
            return input;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: telephone is must beginning with 1 and the length of telephone is 11. ");
            return getPasswordInput();
        }
    }

    private static String getPasswordInput() {
        System.out.println("Please input your password");
        String input = keyboard.nextLine();
        try {
            Person.validatePassword(input);
            return input;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: length of password is between 6-12.");
            return getPasswordInput();
        }

    }

    public static String getProperNounInput(String fieldName) {
        System.out.println("Please input the Person's " + fieldName + ".");
        String input = keyboard.nextLine().strip();
        try {
            Person.validateProperNoun(input, fieldName);
            return input;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
            return getProperNounInput(fieldName);
        }
    }

    public static Person.Gender getGenderInput() {
        System.out.println("Please input the Person's gender. (Male / Female)");
        String input = keyboard.nextLine().strip().toUpperCase().replace(" ", "_");
        try {
            return Person.Gender.valueOf(input);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: gender should be one of the following "
                    + Arrays.toString(Person.Gender.values()));
            return getGenderInput();
        }
    }

    public static LocalDate getDateOfBirthInput() {
        System.out.println("Please input the Person's date of birth. Format should be YYYY-MM-DD.");
        String input = keyboard.nextLine().strip();
        try {
            LocalDate dateOfBirth = LocalDate.parse(input);
            Person.validateDateOfBirth(dateOfBirth);
            return dateOfBirth;
        }
        catch (DateTimeParseException ex) {
            System.out.println("Invalid format. Please try again. " + ex.getMessage());
            return getDateOfBirthInput();
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
            return getDateOfBirthInput();
        }
    }
}