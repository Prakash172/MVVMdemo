package com.prakash.androidcodeshop.mvvmdemo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.prakash.androidcodeshop.mvvmdemo.model.User;
import com.prakash.androidcodeshop.mvvmdemo.repository.TextDisplayRepository;
import com.prakash.androidcodeshop.mvvmdemo.repository.UserRepository;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<String> mTextToDisplay;// mutable data is subclass of live data, it can be changed
    private TextDisplayRepository mRepo;
    private MutableLiveData<Boolean> isChanging = new MutableLiveData<>();
    private Context context;

    public LiveData<String> getStringToDisplay(){ // live data is immutable
        return mTextToDisplay;
    }

    private MutableLiveData<User> mUserData;
    private UserRepository userRepository;
    public LiveData<User> getUserDetails(){
        return mUserData;
    }

    public void init(){
        if(mTextToDisplay != null){
            return;
        }
        mRepo = TextDisplayRepository.getInstance();
        mTextToDisplay = mRepo.getStringToDisplay();
    }

    /**
     * initialize the user repository and get the initial data value from repository
     * update the data
     */
    public void initUser(Context context){
        this.context = context;
        if(mUserData != null) return;
        userRepository = UserRepository.getInstance(context);
        mUserData = userRepository.getUserMutableLiveData();
        mUserData.setValue(userRepository.getUserMutableLiveData().getValue());
    }

    public void addNewText(){
        mTextToDisplay = mRepo.getText();
    }

}
