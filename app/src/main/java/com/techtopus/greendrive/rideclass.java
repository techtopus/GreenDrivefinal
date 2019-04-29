package com.techtopus.greendrive;

public class rideclass {
    String id;
    String custid;
    String start;
    String dest;
    String active;
    Double slatitude,slongitude,dlatitude,dlongitude;
    public rideclass(){}

    public rideclass(String id, String custid, String start, String dest, String active,

    Double slatitude,Double slongitude,Double dlatitude,Double dlongitude){
        this.id = id;
        this.custid = custid;
        this.start = start;
        this.dest = dest;

        this.active = active;

        this.slatitude=slatitude;
        this.slongitude=slongitude;
        this.dlatitude=dlatitude;
        this.dlongitude=dlongitude;
    }

    public Double getSlatitude() {
        return slatitude;
    }

    public void setSlatitude(Double slatitude) {
        this.slatitude = slatitude;
    }

    public Double getSlongitude() {
        return slongitude;
    }

    public void setSlongitude(Double slongitude) {
        this.slongitude = slongitude;
    }

    public Double getDlatitude() {
        return dlatitude;
    }

    public void setDlatitude(Double dlatitude) {
        this.dlatitude = dlatitude;
    }

    public Double getDlongitude() {
        return dlongitude;
    }

    public void setDlongitude(Double dlongitude) {
        this.dlongitude = dlongitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }





    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


}
