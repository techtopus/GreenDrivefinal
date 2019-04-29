package com.techtopus.greendrive;

public class AactiveList {
    String name;
    String pickup,imglink;
    Double km;
    String rid;

    public AactiveList(String name, String pickup, Double km,String imglink,String rid) {
        this.name = name;
        this.pickup = pickup;
        this.km = km;
        this.imglink=imglink;
        this.rid=rid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }
}
