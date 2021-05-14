package com.sikdev.diplomankas.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    public static List<Basket> mListBasket = new ArrayList<>();

    int id_;
    String name;
    int price;
    String name_image;
    int quantity;

    public Basket(int id_, String name, int price, String name_image, int quantity) {
        this.id_ = id_;
        this.name = name;
        this.price = price;
        this.name_image = name_image;
        this.quantity = quantity;
    }

    public static void clearBasket(Context context){
        mListBasket.clear();
        saveSystemBasket(context);
    }

    public static void addProduct(Context context, int id_, String name, int price, String name_image) {
        mListBasket.add(new Basket(id_, name, price, name_image, 1));
        saveSystemBasket(context);
    }

    public static void deleteProduct(Context context, int position) {
        mListBasket.remove(position);
        saveSystemBasket(context);
    }

    private static void saveSystemBasket(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SaveSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        int quantityBasket = mListBasket.size();
        sharedPreferencesEditor.putInt("quantityBasket", quantityBasket).apply();
        for (int i = 0; i < quantityBasket; i++) {
            Basket basket = mListBasket.get(i);
            sharedPreferencesEditor.putInt("id_", basket.getId_()).apply();
            sharedPreferencesEditor.putString("name", basket.getName()).apply();
            sharedPreferencesEditor.putInt("price", basket.getPrice()).apply();
            sharedPreferencesEditor.putString("name_image", basket.getName_image()).apply();
            sharedPreferencesEditor.putInt("quantity", basket.getQuantity()).apply();
        }
    }

    public static void loadSystemBasket(Context context) {
        mListBasket = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("SaveSettings", Context.MODE_PRIVATE);
        int quantityBasket = sharedPreferences.getInt("quantityBasket", 0);
        for (int i = 0; i < quantityBasket; i++) {
            mListBasket.add(new Basket(
                    sharedPreferences.getInt("id_", 0),
                    sharedPreferences.getString("name", ""),
                    sharedPreferences.getInt("price", 0),
                    sharedPreferences.getString("name_image", ""),
                    sharedPreferences.getInt("quantity", 0)
            ));
        }
    }

    public static boolean checkProductBasket(int id_) {
        for (int i = 0; i < mListBasket.size(); i++) {
            if (mListBasket.get(i).getId_() == id_)
                return true;
        }
        return false;
    }

    public static int getSumPrice(){
        int sum = 0;
        for (int i=0;i<mListBasket.size();i++){
            sum+=mListBasket.get(i).getPrice()*mListBasket.get(i).getQuantity();
        }
        return sum;
    }

    public int getId_() {
        return id_;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getName_image() {
        return name_image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity, Context context) {
        this.quantity = quantity;
        saveSystemBasket(context);
    }
}
