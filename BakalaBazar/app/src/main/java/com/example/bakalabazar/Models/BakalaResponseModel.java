package com.example.bakalabazar.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BakalaResponseModel {

    @SerializedName("bakala")
    List<BakalaModel> bakalas;

    public List<BakalaModel> getBakalas() {
        return bakalas;
    }

    public void setBakalas(List<BakalaModel> bakalas) {
        this.bakalas = bakalas;
    }
}
