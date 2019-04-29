package com.techtopus.greendrive;

class HistoryList {
    String type;
    String  from;
    String to;
    String status;
public HistoryList(){}
    public HistoryList(String type, String from, String to, String status) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
