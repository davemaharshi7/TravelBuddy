package com.travelbuddy.travelguideapp.Models;

public class Plan {
    private String Plan_name;
    private String Plan_places;
    private String Duration;
    private Long Price;
    public Plan()
    {

    }

    public Plan(String plan_name, String plan_places, String duration, Long price) {
        Plan_name = plan_name;
        Plan_places = plan_places;
        Duration = duration;
        Price = price;
    }


    public String getPlan_name() {
        return Plan_name;
    }

    public void setPlan_name(String plan_name) {
        Plan_name = plan_name;
    }

    public String getPlan_places() {
        return Plan_places;
    }

    public void setPlan_places(String plan_places) {
        Plan_places = plan_places;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }
}
