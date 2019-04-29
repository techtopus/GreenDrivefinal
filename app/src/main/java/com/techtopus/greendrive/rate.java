package com.techtopus.greendrive;

public class rate {
    String suggession;
    String uid;
    float rating;
public rate(){}
    public rate(String suggession, String uid, float rating) {
        this.suggession = suggession;
        this.uid = uid;
        this.rating = rating;
    }

    public String getSuggession() {
        return suggession;
    }

    public void setSuggession(String suggession) {
        this.suggession = suggession;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
