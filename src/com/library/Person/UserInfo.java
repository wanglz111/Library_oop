package com.library.Person;

import com.library.database.ActivityDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserInfo extends Person{

    private int ID;
    private int studentNo;
    private static final int userMaxLease = 10;
    private static int idNumber = 1;

    /**
     * the list shows that what does the person borrow;
     */
    private ArrayList borrowNoList;

    /**
     * Activity which is ordered by this person.
     */
    private ArrayList activityNoList;

    public UserInfo( String name, String password, Gender gender, String telephone,
                    String location, LocalDate dateOfBirth, int studentNo) {
        super(name, password, gender, telephone, location, dateOfBirth);

        this.ID = 90000 + idNumber;
        this.studentNo = studentNo;
        this.borrowNoList = new ArrayList();
        this.activityNoList = new ArrayList();
        idNumber++;
    }

    public UserInfo(Person person) {
        super(person.getName(),person.getPassword(),person.getGender(),person.getTelephone(),person.getLocation(),person.getDateOfBirth());
        this.ID = 90000 + idNumber;
        this.studentNo = studentNo;
        this.borrowNoList = new ArrayList();
        this.activityNoList = new ArrayList();
        idNumber++;
    }


    public static ArrayList addLeaseItemNo(int borrowItemNo, ArrayList borrowNoList){
        //everyone can borrow less than 10 items;
        if (borrowNoList.size() >= userMaxLease){
            throw new IllegalArgumentException("You can lease less than 10 items.");
        }else {
            borrowNoList.add(borrowItemNo);
            return borrowNoList;
        }
    }

    public static ArrayList addActivityNo(int activityNo, ArrayList activityNoList){
        //everyone can borrow less than 10 items;
        activityNoList.add(activityNo);
        return activityNoList;
    }

    public static ArrayList returnLeaseItemNo(int borrowItemNo, ArrayList borrowNoList) {
        for (int i = 0; i < borrowNoList.size(); i++){
            if (borrowNoList.get(i).equals(borrowItemNo)) {
                borrowNoList.remove(i);
                return borrowNoList;
            }
        }
        throw new IllegalArgumentException("You did not lease this item.");
    }

    public static ArrayList viewLeaseNo(int userId){
        List<UserInfo> UserInfoList = new ArrayList<>();
        for (UserInfo userInfo : UserInfoList){
            if (userInfo.getID() == userId){
                return userInfo.getBorrowList();
            }
        }
        throw new IllegalArgumentException("Cannot find this Person in the database, whose Id = " + userId);
    }

    public static String[] viewLease(ArrayList borrowNoList){
        String[] itemList = new String[borrowNoList.size()];
        return itemList;
    }


    @Override
    public String toString() {
        return "Student [" +
                "ID=" + getID() +
                ", studentNo=" + getStudentNo() +", "+
                    super.toString() +
                ", borrowNoList=" + getBorrowList() +
                ", activityNoList=" + getActivityNoList() +
                ']';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        UserInfo other = (UserInfo) obj;
        return studentNo==other.studentNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo, borrowNoList, activityNoList);
    }

    public ArrayList getBorrowList() {
        return borrowNoList;
    }

    public int getID() {
        return ID;
    }

    public int getStudentNo() {
        return studentNo;
    }


    public ArrayList getBorrowNoList() {
        return borrowNoList;
    }

    public ArrayList getActivityNoList() {
        return activityNoList;
    }
}
