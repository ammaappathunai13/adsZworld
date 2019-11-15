package com.vjsm.sports.adszworld;

public class FirebaseDatabase_Upload_Objectclass {

    String Name;
    String UserId;
    String PhoneNumber;
    String Password;

    public FirebaseDatabase_Upload_Objectclass() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public FirebaseDatabase_Upload_Objectclass(String name, String userId, String phoneNumber, String password) {
        Name = name;
        UserId = userId;
        PhoneNumber = phoneNumber;
        Password = password;
    }
}
