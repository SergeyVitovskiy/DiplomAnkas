package com.sikdev.diplomankas.CommonComponents;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sikdev.diplomankas.BasketActivity;
import com.sikdev.diplomankas.Objects.Basket;
import com.sikdev.diplomankas.R;
import com.squareup.picasso.Picasso;

public class GeneralMethods {

    public GeneralMethods() {
    }

    // Проверка ответа от сервера
    public boolean checkResult(String result) {
        if (result != "null" && result != "") {
            return true;
        } else {
            return false;
        }
    }

    // Изменение формата цены
    public String priceChange(String price) {
        StringBuilder priceResult = new StringBuilder(price);
        int position = 3;
        while (priceResult.length() > position) {
            priceResult = priceResult.insert((priceResult.length() - position), " ");
            position += 4;
        }
        return priceResult.toString();
    }

    // Добавление товара в корзину
    public void addProductToBasket(final Context context,int id_, String name, int price, String image) {
        // Добавление товара в коризну
        Basket.addProduct(context, id_, name, price, image);
        // Настройка диалога покупки
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(context);
        View viewDialog = View.inflate(context, R.layout.dialog_add_basket, null);
        // Присвоение элементов
        TextView txt_name = viewDialog.findViewById(R.id.txt_name);
        TextView txt_price = viewDialog.findViewById(R.id.txt_price);
        ImageView image_product = viewDialog.findViewById(R.id.image_product);
        LinearLayout layout_basket = viewDialog.findViewById(R.id.layout_basket);
        LinearLayout layout_cancel = viewDialog.findViewById(R.id.layout_cancel);
        builderDialog.setView(viewDialog);
        // Вывод информации
        txt_name.setText(name);
        txt_price.setText(new GeneralMethods().priceChange(String.valueOf(price)) + " ₽");
        Picasso.get().load("http://anndroidankas.h1n.ru/image/" + image)
                .into(image_product);
        final Dialog dialog = builderDialog.create();
        // Обработка нажатий
        layout_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Intent intent = new Intent(context, BasketActivity.class);
                context.startActivity(intent);
            }
        });
        layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
