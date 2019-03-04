package com.travelbuddy.travelguideapp.Models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class HistoryDetails {


    private DocumentReference guideDocRef;
    private DocumentReference planDocRef;

    @ServerTimestamp
    private Date dt;
    private String u_name;
    private String u_address;
    private Long u_contact;
    private String u_comments;
    private Long u_persons;

    public HistoryDetails()
    {}

    public HistoryDetails(String u_name, String u_address, Long u_contact, String u_comments, Long u_persons) {
        this.u_name = u_name;
        this.u_address = u_address;
        this.u_contact = u_contact;
        this.u_comments = u_comments;
        this.u_persons = u_persons;
    }


    public DocumentReference getGuideDocRef() {
        return guideDocRef;
    }

    public void setGuideDocRef(DocumentReference guideDocRef) {
        this.guideDocRef = guideDocRef;
    }

    public DocumentReference getPlanDocRef() {
        return planDocRef;
    }

    public void setPlanDocRef(DocumentReference planDocRef) {
        this.planDocRef = planDocRef;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public Long getU_contact() {
        return u_contact;
    }

    public void setU_contact(Long u_contact) {
        this.u_contact = u_contact;
    }

    public String getU_comments() {
        return u_comments;
    }

    public void setU_comments(String u_comments) {
        this.u_comments = u_comments;
    }

    public Long getU_persons() {
        return u_persons;
    }

    public void setU_persons(Long u_persons) {
        this.u_persons = u_persons;
    }
}
