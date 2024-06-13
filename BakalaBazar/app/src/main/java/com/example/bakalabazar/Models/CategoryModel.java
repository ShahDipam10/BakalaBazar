package com.example.bakalabazar.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {

    @SerializedName("category")
    List<Categories> categories;

    public List<Categories> getCategories() {
        return categories;
    }
}
