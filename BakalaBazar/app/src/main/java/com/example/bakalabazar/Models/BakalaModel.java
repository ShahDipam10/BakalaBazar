package com.example.bakalabazar.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BakalaModel {


    @SerializedName("id")
    String id;

    @SerializedName("cat_id")
    String catID;

    @SerializedName("name")
    String name;

    @SerializedName("price")
    String price;

    @SerializedName("description")
    String description;

    @SerializedName("photo")
    String photo;

    public BakalaModel(){

    }

    public BakalaModel(String id, String name, String price,String photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
