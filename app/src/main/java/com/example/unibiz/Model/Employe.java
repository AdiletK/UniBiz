package com.example.unibiz.Model;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;

public class Employe {
    private UUID mUUID;
    private String mName;
    private String mJob;
    private String mSex;
    private byte[] mImage;
    private String mNomer;
    private String mEmail;
    private Date mDateBorn;
    private Date mDateCome;
    private Date mDateOut;
    private String mAdress;
    private UUID mId_items;

    @NonNull
    @Override
    public String toString() {
        return ""+mName;
    }

    public Employe() {
        this(UUID.randomUUID());
        mUUID = UUID.randomUUID();
    }

    public Employe(UUID UUID) {
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

    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public String getNomer() {
        return mNomer;
    }

    public void setNomer(String nomer) {
        mNomer = nomer;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Date getDateBorn() {
        return mDateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        mDateBorn = dateBorn;
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

    public String getAdress() {
        return mAdress;
    }

    public void setAdress(String adress) {
        mAdress = adress;
    }

    public UUID getId_items() {
        return mId_items;
    }

    public void setId_items(UUID id_items) {
        mId_items = id_items;
    }
}
