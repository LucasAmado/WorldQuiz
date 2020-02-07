package com.gonzaloandcompany.woldquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gonzaloandcompany.woldquiz.service.UserService;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        UserService.getUser();
    }
}
