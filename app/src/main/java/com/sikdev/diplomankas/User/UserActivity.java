package com.sikdev.diplomankas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.MainActivity;
import com.sikdev.diplomankas.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Верхнее меню
        ToolBar toolBar = new ToolBar(UserActivity.this, "User");
        // Обратный звонок
        CallBack callBack = new CallBack(UserActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(UserActivity.this);
    }
}