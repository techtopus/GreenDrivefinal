package com.techtopus.greendrive;

import java.util.Date;

public class trip {
   public String id;
    public String custid;
    public String start;
    public String dest;
    public String vno,model,type,seat,stop1,stop2,stop3,active;
    public String y,m,d,h,o;
    public String cid;
    public  Double slatitude,slongitude,dlatitude,dlongitude;
    public Double s1latitude,s1longitude,s2latitude,s2longitude,s3latitude,s3longitude;
    String name,imglink;
    public trip()
    {}
    public trip(String id,String custid,String start, String dest, String vno,
                String model, String type, String seat, String stop1, String stop2, String stop3,String active
            ,String y,String m,String d,String h,String o,String cid
    ,Double slatitude,Double slongitude,Double dlatitude,Double dlongitude,
                Double s1latitude,Double s1longitude,Double s2latitude,Double s2longitude,
                Double s3latitude,Double s3longitude,String name,String imglink) {
        this.id=id;
this.cid=cid;
        this.custid=custid;
        this.start = start;
        this.dest = dest;
        this.vno = vno;
        this.model = model;
        this.type = type;
        this.seat = seat;
        this.stop1 = stop1;
        this.stop2 = stop2;
        this.stop3 = stop3;
        this.active=active;
        this.y=y;
        this.m=m;
        this.d=d;
        this.h=h;
        this.o=o;
        this.slatitude=slatitude;
        this.slongitude=slongitude;
        this.dlatitude=dlatitude;
        this.dlongitude=dlongitude;
        this.s1latitude=s1latitude;
        this.s1longitude=s1longitude;
        this.s2latitude=s2latitude;
        this.s2longitude=s2longitude;
        this.s3latitude=s3latitude;
        this.s3longitude=s3longitude;
        this.name=name;
        this.imglink=imglink;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public Double getS1latitude() {
        return s1latitude;
    }

    public void setS1latitude(Double s1latitude) {
        this.s1latitude = s1latitude;
    }

    public Double getS1longitude() {
        return s1longitude;
    }

    public void setS1longitude(Double s1longitude) {
        this.s1longitude = s1longitude;
    }

    public Double getS2latitude() {
        return s2latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setS2latitude(Double s2latitude) {
        this.s2latitude = s2latitude;
    }

    public Double getS2longitude() {
        return s2longitude;
    }

    public void setS2longitude(Double s2longitude) {
        this.s2longitude = s2longitude;
    }

    public Double getS3latitude() {
        return s3latitude;
    }

    public void setS3latitude(Double s3latitude) {
        this.s3latitude = s3latitude;
    }

    public Double getS3longitude() {
        return s3longitude;
    }

    public void setS3longitude(Double s3longitude) {
        this.s3longitude = s3longitude;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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


    public String getVno() {
        return vno;
    }

    public void setVno(String vno) {
        this.vno = vno;
    }

    public String getModel() {
        return model;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStop1() {
        return stop1;
    }

    public void setStop1(String stop1) {
        this.stop1 = stop1;
    }

    public String getStop2() {
        return stop2;
    }

    public void setStop2(String stop2) {
        this.stop2 = stop2;
    }

    public String getStop3() {
        return stop3;
    }

    public void setStop3(String stop3) {
        this.stop3 = stop3;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
