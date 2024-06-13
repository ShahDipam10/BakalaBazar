package com.example.bakalabazar.Models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {


    @SerializedName("success")
    String success;

    @SerializedName("msg")
    String massage;

    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }

    public String getSuccess() {
        return success;
    }

    public String getMassage() {
        return massage;
    }

    public class Data{

        @SerializedName("firstname")
        String first;

        @SerializedName("lastname")
        String last;

        @SerializedName("mobile")
        String mobile;

        @SerializedName("email")
        String email;


        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public String getMobile() {
            return mobile;
        }

        public String getEmail() {
            return email;
        }
    }
}
