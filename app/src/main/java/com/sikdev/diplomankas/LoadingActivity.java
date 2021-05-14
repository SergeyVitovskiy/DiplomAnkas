package com.sikdev.diplomankas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sikdev.diplomankas.Objects.Basket;
import com.sikdev.diplomankas.Objects.User;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        // Загрузка товаров из корзины
        Basket.loadSystemBasket(LoadingActivity.this);
        // Загрузка данных пользователя
        User.loadSystemBasket(LoadingActivity.this);
        final int[] tick = {0};
        // Таймер задержки на загрузку
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tick[0] >=2){
                            timer.cancel();
                            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        tick[0]++;
                    }
                });
            }
        },0,1000);
    }

    @Override
    public void onBackPressed() {
    }
}