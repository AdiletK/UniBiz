package com.example.unibiz.Model;


import java.util.UUID;

public class Product {
    private UUID mId;
    private UUID mId_empl;
    private UUID mId_supl;
    private UUID mId_items;
    private String mName;
    private String mImei_code;
    private String mCount;
    private String mPrice;
    private String mDate;

    public Product(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public UUID getId_empl() {
        return mId_empl;
    }

    public void setId_empl(UUID id_empl) {
        mId_empl = id_empl;
    }

    public UUID getId_supl() {
        return mId_supl;
    }

    public void setId_supl(UUID id_supl) {
        mId_supl = id_supl;
    }

    public UUID getId_items() {
        return mId_items;
    }

    public void setId_items(UUID id_items) {
        mId_items = id_items;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImei_code() {
        return mImei_code;
    }

    public void setImei_code(String imei_code) {
        mImei_code = imei_code;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
