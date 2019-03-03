package com.prakash.androidcodeshop.mvvmdemo.repository;

import android.arch.lifecycle.MutableLiveData;

import com.prakash.androidcodeshop.mvvmdemo.model.TextDisplay;

import org.w3c.dom.Text;

public class TextDisplayRepository {

    private static TextDisplayRepository instance;
    private MutableLiveData<String> text ;

    /**
     *Singleton pattern class
     */
    public static TextDisplayRepository getInstance(){
        if(instance == null){
            instance = new TextDisplayRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getStringToDisplay(){
        text = new MutableLiveData<>();
        text.setValue("Changed text from setStringToDisplay");
        return text;
    }

    public MutableLiveData<String> getText() {
        text.setValue("New Value");
        return text;
    }
}
