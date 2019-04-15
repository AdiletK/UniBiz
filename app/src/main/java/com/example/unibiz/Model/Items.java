package com.example.unibiz.Model;

import java.util.UUID;

public class Items {
    private UUID mId_UUID;
    private String mItem;



    public UUID getId_UUID() {
        return mId_UUID;
    }

    public void setId_UUID(UUID id_UUID) {
        mId_UUID = id_UUID;
    }

    public String getItem() {
        return mItem;
    }

    public void setItem(String item) {
        mItem = item;
    }
}
