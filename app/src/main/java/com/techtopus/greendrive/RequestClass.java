package com.techtopus.greendrive;

public class RequestClass {
    String requestid;
    String name;
    String journey;
    String imglink;
    private String tripId;
    private String riderid;
    String status;
public RequestClass(){}

    public RequestClass(String requestid, String name, String journey, String imglink,
                        String tripId, String riderid, String status) {
        this.requestid = requestid;
        this.name = name;
        this.journey = journey;
        this.imglink = imglink;
        this.tripId = tripId;
        this.riderid = riderid;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJourney() {
        return journey;
    }

    public void setJourney(String journey) {
        this.journey = journey;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
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
