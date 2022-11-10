package com.sa22.trippy.venue_management;

public class Venue {

    private int venueId;
    private String venueName;
    private String venueType;
    private String venueCity;
    private String venueAddress;
    private String venueEmail;
    private String venuePhone;


    public Venue(int venueId, String venueName, String venueType, String venueCity,
                 String venueAddress, String venueEmail, String venuePhone) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueType = venueType;
        this.venueCity = venueCity;
        this.venueAddress = venueAddress;
        this.venueEmail = venueEmail;
        this.venuePhone = venuePhone;
    }

    public Venue(String venueName, String venueType, String venueCity,
                 String venueAddress, String venueEmail, String venuePhone) {
        this.venueName = venueName;
        this.venueType = venueType;
        this.venueCity = venueCity;
        this.venueAddress = venueAddress;
        this.venueEmail = venueEmail;
        this.venuePhone = venuePhone;
    }

    public Venue(int venue_rating) {
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueEmail() {
        return venueEmail;
    }

    public void setVenueEmail(String venueEmail) {
        this.venueEmail = venueEmail;
    }

    public String getVenuePhone() {
        return venuePhone;
    }

    public void setVenuePhone(String venuePhone) {
        this.venuePhone = venuePhone;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueId=" + venueId +
                ", venueName='" + venueName + '\'' +
                ", venueType='" + venueType + '\'' +
                ", venueCity='" + venueCity + '\'' +
                ", venueAddress='" + venueAddress + '\'' +
                ", venueEmail='" + venueEmail + '\'' +
                ", venuePhone=" + venuePhone +
                '}';
    }


}

