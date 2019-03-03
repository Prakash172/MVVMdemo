package com.prakash.androidcodeshop.mvvmdemo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prakash.androidcodeshop.mvvmdemo.R;
import com.prakash.androidcodeshop.mvvmdemo.model.User;
import com.prakash.androidcodeshop.mvvmdemo.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    // main activity is View and having the direct connection with viewmodel only
    private MainActivityViewModel mainActivityViewModel;
    private TextView display;
    private Button changeBtn;
    private TextView updatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.textDisplay);
        changeBtn = findViewById(R.id.changeBtn);
        updatedText = findViewById(R.id.updateText);
        // step 1: define the view model
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //step 2: initUser initialize the data from repository wihtin viewmodel
        mainActivityViewModel.initUser(this);
        mainActivityViewModel.init();
        //step 3:  Observe for any data change in repository and update the view accordingly
        mainActivityViewModel.getUserDetails().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user!= null) {
                    display.setText(user.toString());
                }else Toast.makeText(getApplicationContext(), "Data is empty", Toast.LENGTH_SHORT).show();
            }
        });

        mainActivityViewModel.getStringToDisplay().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                updatedText.setText(s);
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.addNewText();

            }
        });
    }
}
