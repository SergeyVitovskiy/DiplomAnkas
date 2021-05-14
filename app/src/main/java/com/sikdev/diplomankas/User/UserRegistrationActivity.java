package com.sikdev.diplomankas.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.R;

public class UserRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Верхнее меню
        ToolBar toolBar = new ToolBar(UserRegistrationActivity.this, "User");
        // Обратный звонок
        CallBack callBack = new CallBack(UserRegistrationActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(UserRegistrationActivity.this);
        // Авторизация
        LinearLayout layout_authorization = findViewById(R.id.layout_authorization);
        layout_authorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistrationActivity.this, UserAuthorizationActivity.class);
                startActivity(intent);
            }
        });
        // Регистрация
        LinearLayout layout_push = findViewById(R.id.layout_push);
        layout_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistrationActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}