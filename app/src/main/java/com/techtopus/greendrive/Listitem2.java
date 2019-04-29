package com.techtopus.greendrive;

public class Listitem2 {
    public  String drivername;
    public Integer vtype;
    public String fromto;
    public String time;
    public String date;
    public String imglink,id;
    public Double km;
    String custid;

    public Listitem2(String drivername, Integer vtype, String fromto, String time, String date,
                     String imglink,Double km,String id,String custid ) {
        this.drivername = drivername;
        this.vtype = vtype;
        this.fromto = fromto;
        this.time = time;
        this.date = date;
        this.imglink = imglink;
        this.km=km;
        this.id=id;
        this.custid=custid;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public Integer getVtype() {
        return vtype;
    }

    public void setVtype(Integer vtype) {
        this.vtype = vtype;
    }

    public String getFromto() {
        return fromto;
    }

    public void setFromto(String fromto) {
        this.fromto = fromto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }
}
