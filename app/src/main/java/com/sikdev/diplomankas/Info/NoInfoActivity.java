package com.sikdev.diplomankas.Info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.MainActivity;
import com.sikdev.diplomankas.R;

public class NoInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_info);

        // Верхнее меню
        ToolBar toolBar = new ToolBar(NoInfoActivity.this, "MainActivity");
        // Обратный звонок
        CallBack callBack = new CallBack(NoInfoActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(NoInfoActivity.this);
    }
}