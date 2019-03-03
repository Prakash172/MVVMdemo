package com.prakash.androidcodeshop.mvvmdemo.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.prakash.androidcodeshop.mvvmdemo.activities.MainActivity;
import com.prakash.androidcodeshop.mvvmdemo.databases.MyDatabase;
import com.prakash.androidcodeshop.mvvmdemo.model.User;
import com.prakash.androidcodeshop.mvvmdemo.rest.APIClient;
import com.prakash.androidcodeshop.mvvmdemo.rest.UserEndPoints;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UserRepository {

    private static UserRepository userRepoInstance;
    private MutableLiveData<User> userMutableLiveData;
    private static Context context;
    private MyDatabase database;
    private User user;

    public static UserRepository getInstance(Context context) {
        UserRepository.context = context;
        if (userRepoInstance == null) userRepoInstance = new UserRepository();
        return userRepoInstance;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        userMutableLiveData = new MutableLiveData<>();
        if (isNetworkAvailable()) {
            loadCompanyUsers();
        } else loadCompanyUserFormCache();
        return userMutableLiveData;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadCompanyUsers() {
        Toast.makeText(context, "Data is loading from internet", Toast.LENGTH_SHORT).show();
        UserEndPoints apiService = APIClient.getClient().create(UserEndPoints.class);
        Call<User> call = apiService.getCompanyUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                user = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: Failed to load data");
            }
        });

        if (user != null) {
            userMutableLiveData.setValue(user);
            insertUserToCache();
        } else Toast.makeText(context, "Data is not fetching...", Toast.LENGTH_LONG).show();
    }

    private void loadCompanyUserFormCache() {
        Toast.makeText(context, "data is loading from cache", Toast.LENGTH_SHORT).show();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (database == null) database = MyDatabase.getInstance(context);
                if (database.userDao().count().length > 0) {
                    user = database.userDao().getUser();
                }
                userMutableLiveData.postValue(user);
            }
        });

    }

    private void insertUserToCache() {
        if (database == null) database = MyDatabase.getInstance(context);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                database.userDao().insertUser(userMutableLiveData.getValue());
                Toast.makeText(context, "Data inserted to cached successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
