package com.techtopus.greendrive;

public class car {
    public String type;
    public String regno;
    public String uid;
    public String model;
    String carid;
public car(){}
    public car(String type, String regno,String uid,String model,String carid) {
        this.type = type;
        this.regno = regno;
        this.uid=uid;
        this.model=model;
        this.carid=carid;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public  String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public  String getType() {
        return type;
    }

    public  String getRegno() {
        return regno;
    }

    public String getUid() {
        return uid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
