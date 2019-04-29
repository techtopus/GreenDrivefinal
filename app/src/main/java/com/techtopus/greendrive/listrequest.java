package com.techtopus.greendrive;

public class listrequest {
    String requestid;
    private String tripId;
    private String riderid;

    String imglink;
    String status,email;
    public listrequest(){}

    public listrequest(String requestid,String tripId, String riderid,String imglink,String status,String email) {
        this.requestid=requestid;
        this.tripId = tripId;
        this.riderid = riderid;
        this.imglink=imglink;
        this.status=status;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImglink() {
        return imglink;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getRiderid() {
        return riderid;
    }

    public void setRiderid(String riderid) {
        this.riderid = riderid;
    }
}


