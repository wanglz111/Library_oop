package com.library.database;

import com.library.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityDB {
    private List<Activity> activityList;

    public ActivityDB(){
        this.activityList = new ArrayList<>();
    }

    public ActivityDB(List<Activity> activityList){
        this.activityList = activityList;
    }

    //for displaying the database
    public void show() {
        System.out.println("Displaying Item database:");
        for (Activity activity : activityList) {
            System.out.println(activity);
        }
    }


    public boolean isEmpty() {
        return activityList.isEmpty();
    }

    //for adding/removing Items from the database
    public boolean add(Activity activity) {
        return activityList.add(activity);
    }

    public boolean remove(Activity activity) {
        return activityList.remove(activity);
    }

    public static Activity findById(int id, ActivityDB activityList) {
        for (Activity activity : activityList.activityList) {
            if (activity.getActivityId() == id) {
                return activity;
            }
        }
        throw new IllegalArgumentException("No activity with ID number " + id + " in the database.");
    }


    public static Activity findByName(String name, ActivityDB activityList) {
        for (Activity activity : activityList.activityList) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        throw new IllegalArgumentException("No activity with name " + name + " in the database.");
    }
}
