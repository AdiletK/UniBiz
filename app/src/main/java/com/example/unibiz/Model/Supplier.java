package com.example.unibiz.Model;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;

public class Supplier {
    private UUID mUUID;
    private String mName;
    private String mOrganization;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private Date mDateCome;
    private Date mDateOut;
    private Date mDateVisit;
    private UUID mId_items;

    @NonNull
    @Override
    public String toString() {
        return ""+mName;
    }



    public Supplier() {
        this(UUID.randomUUID());
    }

    public Supplier(UUID UUID) {
        mUUID = UUID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Date getDateCome() {
        return mDateCome;
    }

    public void setDateCome(Date dateCome) {
        mDateCome = dateCome;
    }

    public Date getDateOut() {
        return mDateOut;
    }

    public void setDateOut(Date dateOut) {
        mDateOut = dateOut;
    }

    public Date getDateVisit() {
        return mDateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        mDateVisit = dateVisit;
    }

    public UUID getId_items() {
        return mId_items;
    }

    public void setId_items(UUID id_items) {
        mId_items = id_items;
    }
}
