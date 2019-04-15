package com.example.unibiz.Model;


import java.util.Date;
import java.util.UUID;

public class Category {
    private UUID mId;
    private String mName;
    private byte[] mImage;
    private Double mPrice;
    private Date mDate;

    public Category() {
        this(UUID.randomUUID());
        mId = UUID.randomUUID();
    }

    public Category(UUID UUID) {
        mId = UUID;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
