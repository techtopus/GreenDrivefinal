package com.techtopus.greendrive;

public class user {

    String token;
    String name;
    String userid;
    String email;
    String gender;
    String phno;
    String aadharno;
    String imglink;

    public user(){}

    public user(String name, String userid, String email, String gender, String phno, String aadharno,String token,String imglink) {
        this.name = name;
        this.userid = userid;
        this.email = email;
        this.gender = gender;
        this.phno = phno;
        this.aadharno = aadharno;
        this.token=token;
        this.imglink=imglink;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhno() {
        return phno;
    }

    public String getAadharno() {
        return aadharno;
    }
}

