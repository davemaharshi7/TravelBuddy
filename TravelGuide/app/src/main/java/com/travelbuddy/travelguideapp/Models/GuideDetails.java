package com.travelbuddy.travelguideapp.Models;

public class GuideDetails {

private String Guide_name;
private String Current_city;
private Long Gender;
private String Language;
private Long Ratings;
private boolean Available;

public GuideDetails()
{}

    public GuideDetails(String guide_name, String current_city, Long gender, String language, Long ratings,boolean Available) {
        Guide_name = guide_name;
        Current_city = current_city;
        Gender = gender;
        Language = language;
        Ratings = ratings;
        this.Available=Available;
    }

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        Available = available;
    }

    public String getGuide_name() {
        return Guide_name;
    }

    public void setGuide_name(String guide_name) {
        Guide_name = guide_name;
    }

    public String getCurrent_city() {
        return Current_city;
    }

    public void setCurrent_city(String current_city) {
        Current_city = current_city;
    }

    public String getGender() {
        String gender;
    if(Gender.intValue()==0) {
            gender="Male";
        }
        else if(Gender.intValue()==1)
        {
            gender="Female";
        }
        else {
        gender="Other";
    }
        return gender;
    }

    public void setGender(Long gender) {
        Gender = gender;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public Long getRatings() {
        return Ratings;
    }

    public void setRatings(Long ratings) {
        Ratings = ratings;
    }
}
