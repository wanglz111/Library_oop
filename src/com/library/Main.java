package com.library;

import com.library.database.PersonDB;

public class Main {

    public static void main(String[] args) {
        PersonDB db = new PersonDB();
        LibraryInterface.run(db);
    }

}


//	try {
//        UserInfo.returnLeaseItemNo(3,a);
//    }catch (ArrayIndexOutOfBoundsException e){
//        System.out.println(a);
//    }

