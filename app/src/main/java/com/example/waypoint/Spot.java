package com.example.waypoint;

public class Spot {
    private String spotName;
    private String city;
    private String note;
    private String spotType;
    private String mood;
    private String dateAdded;

    public Spot(String spotName, String city, String note, String spotType, String mood, String dateAdded) {
        this.spotName = spotName;
        this.city = city;
        this.note = note;
        this.spotType = spotType;
        this.mood = mood;
        this.dateAdded = dateAdded;
    }

    public String getSpotName() {
        return spotName;
    }

    public String getCity() {
        return city;
    }

    public String getNote() {
        return note;
    }

    public String getSpotType() {
        return spotType;
    }

    public String getMood() {
        return mood;
    }

    public String getDateAdded() {
        return dateAdded;
    }
}