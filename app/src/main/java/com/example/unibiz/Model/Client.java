package com.example.unibiz.Model;


import java.util.Date;
import java.util.UUID;

public class Client {
    private UUID mId;
    private UUID mId_category;
    private UUID mId_empl;
    private UUID mId_item;
    private String mName;
    private String mModel;
    private String mNomer;
    private String mImei;
    private Date mDate;
    private String mVisitDate;
    private Double mPrice;

    public Client() {
        this(UUID.randomUUID());
    }

    public Client(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public UUID getId_category() {
        return mId_category;
    }

    public void setId_category(UUID id_category) {
        mId_category = id_category;
    }

    public UUID getId_empl() {
        return mId_empl;
    }

    public void setId_empl(UUID id_empl) {
        mId_empl = id_empl;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public String getNomer() {
        return mNomer;
    }

    public void setNomer(String nomer) {
        mNomer = nomer;
    }

    public String getImei() {
        return mImei;
    }

    public void setImei(String imei) {
        mImei = imei;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getVisitDate() {
        return mVisitDate;
    }

    public void setVisitDate(String visitDate) {
        mVisitDate = visitDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public UUID getId_item() {
        return mId_item;
    }

    public void setId_item(UUID id_item) {
        mId_item = id_item;
    }
}
