package com.example.bakalabazar.network;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.bakalabazar.Models.BakalaResponseModel;
import com.example.bakalabazar.Models.CategoryModel;
import com.example.bakalabazar.Models.LoginModel;
import com.example.bakalabazar.Models.OrderModel;
import com.example.bakalabazar.Models.RegisterModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {



    @GET("category.php")
    Call<CategoryModel> getCategory();


    @FormUrlEncoded
    @POST("Bakala.php")
    Call<BakalaResponseModel> getBakala(@Field("catid") String catid);

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterModel> registerUser(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("Login.php")
    Call<LoginModel> loginUser(@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("placeorder.php")
    Call<LoginModel> placeOrder(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getorders.php")
    Call<OrderModel> getOrders(@Field("email") String email);



}
