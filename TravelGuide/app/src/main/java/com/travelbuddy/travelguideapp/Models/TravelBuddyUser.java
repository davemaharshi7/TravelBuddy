package com.travelbuddy.travelguideapp.Models;

public class TravelBuddyUser
{
    private String user_name, user_email, password;

    public TravelBuddyUser(){

    }

    public TravelBuddyUser(String user_name, String user_email, String password) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
