package com.sikdev.diplomankas.Info;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.R;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_info);

        // Верхнее меню
        ToolBar toolBar = new ToolBar(ContactsActivity.this, "MainActivity");
        // Обратный звонок
        CallBack callBack = new CallBack(ContactsActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(ContactsActivity.this);
    }
}