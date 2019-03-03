package com.prakash.androidcodeshop.mvvmdemo.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


//For room usage... first step is to create Entity
// For MVVM it the model for our data to be fetched from repository
@Entity(tableName = "User") // this is the table name which is creatd by Rooom
public class User { // User is schema(id,user,username);
    @SerializedName("username") // this is the attribute
    private String username ;

    @SerializedName("user")
    private String name ;

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) // to auto generate the id to remove conflict
    private int id;

    public User(String username, String name, int id) {
        this.username = username;
        this.name = name;
        this.id = id;
    }

    @Ignore // because Room support only the single Constructor entity so we have to use IGNORE for this
    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @NonNull
    @Override
    public String toString() {
        return "User{ " +
                "Name: "+getName()+
                "\nUsername: "+getUsername()+
                "\nID : "+getId()+
                " }";
    }
}
