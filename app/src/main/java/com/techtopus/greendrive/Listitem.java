package com.techtopus.greendrive;



public class Listitem {
    private String drivername;
    private String model;
    private Integer type;
    private String cid;

    public Listitem(String drivername, String model, Integer type,String cid) {
        this.drivername = drivername;
        this.model = model;
        this.type = type;
        this.cid=cid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
