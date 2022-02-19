package com.library.model;

import java.time.LocalDate;

public class Activity{
    private static int i = 1;
    private int activityId;
    private String name;
    private String type;
    private String place;
    private LocalDate startDate;
    private int capacity;
    private int lastTime;
    private LocalDate endDate;
    private static final int maxCapacity = 200;

    public Activity(String name,String type, String place, LocalDate startDate,
                    LocalDate endDate, int capacity)
    {
        validateActivityName(name);
        this.name = name;
        this.activityId = 800000+i;
        this.type = type;
        this.place = place;
        validateActivityTime(startDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastTime = endDate.compareTo(startDate);
        this.capacity = capacity;
        i++;
    }

    public Activity(String name,String type, String place, LocalDate startDate,
                     int lastTime, int capacity)
    {
        validateActivityName(name);
        this.name = name;
        this.activityId = 800000+i;
        this.type = type;
        this.place = place;
        validateActivityTime(startDate);
        this.startDate = startDate;
        this.endDate = startDate.plusDays(lastTime);
        this.lastTime = lastTime;
        validateCapacity(capacity);
        this.capacity = capacity;
        i++;
    }
    public void stringChangeTo(String toChange, String fieldName){
        //satisfies the conditions.
        if ("type".equals(fieldName)){this.type = toChange;}
        else if ("place".equals(fieldName)){this.place = toChange;}
        else if ("name".equals(fieldName)){this.name = toChange;}
        else {throw new IllegalArgumentException("Cannot find the field "+ fieldName);}
    }

    public static void validateCapacity(int capacity) {//Limit activity capacity (number of people) is 0-200
        if (capacity < 0) {
            throw new IllegalArgumentException(" capacity of activity must be more than 0.");
        }
        if (capacity > maxCapacity) {
            throw new IllegalArgumentException(" capacity of activity should be less than 200.");
        }
    }

    public static void validateActivityTime(LocalDate activityTime) {
        if (activityTime.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("activityTime field can't be past time.");
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

    public static void validateActivityName(String activityName) {//Restrict the activityName cannot be empty, separate words with spaces
        String[] parts = activityName.split(" ");
        //String location is empty
        if (activityName.isEmpty()) {
            throw new IllegalArgumentException("activityName field can't be empty");
        }
        for (String part : parts) {
            validateProperNoun(part, "activityName part");
        }
    }

    public static boolean isAlpha(String activityType) {//限制活动类型只能填写字母
        return activityType.matches("[a-zA-Z]+");
    }

    public void modifyCapacityTo(int toChange){
        this.capacity = toChange;
    }

    public int getActivityId() {
        return activityId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLastTime() {
        return lastTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
