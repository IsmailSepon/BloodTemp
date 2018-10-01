package com.example.hp.bloodtemp;

public class Userinfo {

   String uName;
   String uNumber;
   String uGroup;

    public Userinfo(String uName, String uNumber, String uGroup) {
        this.uName = uName;
        this.uNumber = uNumber;
        this.uGroup = uGroup;
    }

    public Userinfo() {

    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getuGroup() {
        return uGroup;
    }

    public void setuGroup(String uGroup) {
        this.uGroup = uGroup;
    }
}
