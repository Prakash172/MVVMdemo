package com.prakash.androidcodeshop.mvvmdemo.rest;

import com.prakash.androidcodeshop.mvvmdemo.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserEndPoints {
    @GET("/user")
    Call<User> getCompanyUser();
}
