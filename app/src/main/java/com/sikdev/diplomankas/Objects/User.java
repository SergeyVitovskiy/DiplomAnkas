package com.sikdev.diplomankas.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class User {

    public static User user;
    int id_;
    int id_role;
    String name;
    String telephone;
    String mail;
    String password;

    public User(int id_, int id_role, String name, String telephone, String mail, String password) {
        this.id_ = id_;
        this.id_role = id_role;
        this.name = name;
        this.telephone = telephone;
        this.mail = mail;
        this.password = password;
    }

    private static void saveSystemBasket(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SaveSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putInt("id_", user.getId_()).apply();
        sharedPreferencesEditor.putInt("id_role", user.getId_role()).apply();
        sharedPreferencesEditor.putString("name", user.getName()).apply();
        sharedPreferencesEditor.putString("telephone", user.getTelephone()).apply();
        sharedPreferencesEditor.putString("mail", user.getMail()).apply();
        sharedPreferencesEditor.putString("password", user.getPassword()).apply();
    }

    public static void loadSystemBasket(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SaveSettings", Context.MODE_PRIVATE);
        user = new User(
                sharedPreferences.getInt("id_", 0),
                sharedPreferences.getInt("id_role", 0),
                sharedPreferences.getString("name", "null"),
                sharedPreferences.getString("telephone", "null"),
                sharedPreferences.getString("mail", "null"),
                sharedPreferences.getString("password", "null"));
    }

    public static boolean checkUser(){
        if(user.getId_() == 0)
            return false;
        else
            return true;
    }

    public int getId_() {
        return id_;
    }

    public int getId_role() {
        return id_role;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
