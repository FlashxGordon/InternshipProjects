package com.sa22.trippy.user_management;

import java.sql.Date;

public class User {

    private int userId;
    private String userName;
    private String userCity;
    private String userEmail;
    private Date dateJoined;

    public User() {
    }

    public User(int userId, String userName, String userCity, String userEmail, Date dateJoined) {
        this.userId = userId;
        this.userName = userName;
        this.userCity = userCity;
        this.userEmail = userEmail;
        this.dateJoined = dateJoined;

    }

    public User(String userName, String userCity,
                String userEmail, Date dateJoined) {
        this.userName = userName;
        this.userCity = userCity;
        this.userEmail = userEmail;
        this.dateJoined = dateJoined;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;


    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", dateJoined=" + dateJoined +
                '}';
    }
}
