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

public class UserAuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authorization);

        // Верхнее меню
        ToolBar toolBar = new ToolBar(UserAuthorizationActivity.this, "User");
        // Обратный звонок
        CallBack callBack = new CallBack(UserAuthorizationActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(UserAuthorizationActivity.this);

        LinearLayout layout_registration = findViewById(R.id.layout_registration);
        layout_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizationActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });
        // Регистрация
        LinearLayout layout_push = findViewById(R.id.layout_push);
        layout_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthorizationActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}