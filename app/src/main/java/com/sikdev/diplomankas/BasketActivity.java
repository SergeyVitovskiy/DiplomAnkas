package com.sikdev.diplomankas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sikdev.diplomankas.Adapters.BasketAdapter;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ExpandedGridView;
import com.sikdev.diplomankas.CommonComponents.GeneralMethods;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.Objects.Basket;

import java.util.List;

public class BasketActivity extends AppCompatActivity {
    static BasketAdapter mBasketAdapter;

    static TextView txt_nullBasket;
    static ExpandedGridView grid_product;
    static LinearLayout layout_order;

    LinearLayout layout_deliveryItem;

    static TextView txt_sumPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        // Верхнее меню
        ToolBar toolBar =
                new ToolBar(BasketActivity.this, "BasketActivity");
        // Обратный звонок
        CallBack callBack = new CallBack(BasketActivity.this);

        layout_order = findViewById(R.id.layout_order);
        txt_nullBasket = findViewById(R.id.txt_nullBasket);
        grid_product = findViewById(R.id.grid_product);
        mBasketAdapter = new BasketAdapter(BasketActivity.this);
        grid_product.setAdapter(mBasketAdapter);
        grid_product.setExpanded(true);
        // Присвоение элементов
        layout_deliveryItem = findViewById(R.id.layout_deliveryItem);
        checkProductBasket();
        // Сумма товаров
        txt_sumPrice = findViewById(R.id.txt_sumPrice);
        updateSumPrice();
        // Способ доставки
        delivery();
        // Способ оплаты
        paymentMethod();
        // Заказать
        push();
    }

    private void push() {
        // Обьявление компонентов диалога
        final LinearLayout layout_name = findViewById(R.id.layout_name_order);
        final LinearLayout layout_tell = findViewById(R.id.layout_tell_order);
        final LinearLayout layout_address = findViewById(R.id.layout_address_order);
        final EditText txt_name = findViewById(R.id.txt_name_order);
        final EditText txt_tell = findViewById(R.id.txt_tell_order);
        final EditText txt_message = findViewById(R.id.txt_message_order);
        final EditText txt_mail = findViewById(R.id.txt_mail_order);
        final EditText txt_address = findViewById(R.id.txt_address_order);
        LinearLayout layout_push = findViewById(R.id.layout_push_order);
        // Отправить запрос
        layout_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txt_name.getText().toString();
                String tell = txt_tell.getText().toString();
                String message = txt_message.getText().toString();
                String mail = txt_mail.getText().toString();
                String address = txt_address.getText().toString();
                // Проверка полей
                int check = 0;
                if (name.length() >= 5) {
                    check++;
                    layout_name.setBackgroundResource(R.drawable.border_gray);
                } else {
                    layout_name.setBackgroundResource(R.drawable.border_red);
                }
                if (tell.length() >= 5) {
                    check++;
                    layout_tell.setBackgroundResource(R.drawable.border_gray);
                } else {
                    layout_tell.setBackgroundResource(R.drawable.border_red);
                }
                if (layout_deliveryItem.getVisibility() == View.VISIBLE) {
                    if (address.length() >= 5) {
                        check++;
                        layout_address.setBackgroundResource(R.drawable.border_gray);
                    } else {
                        layout_address.setBackgroundResource(R.drawable.border_red);
                    }
                }
                if ((check >= 2 && layout_deliveryItem.getVisibility() == View.GONE) ||
                        (check >= 3 && layout_deliveryItem.getVisibility() == View.VISIBLE)) {
                    Toast.makeText(BasketActivity.this,
                            "Заказа оформлен. Ожидайте звонка оператора.",
                            Toast.LENGTH_LONG).show();
                    // Отчистить корзину
                    Basket.clearBasket(BasketActivity.this);
                    basketAdapterNotifyDataSetChanged();
                } else {
                    Toast.makeText(BasketActivity.this,
                            "Некорректное заполнение полей.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void paymentMethod() {
        final LinearLayout layout_online = findViewById(R.id.layout_online);
        final LinearLayout layout_cash = findViewById(R.id.layout_cash);
        layout_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_online.setBackgroundResource(R.drawable.border_green);
                layout_cash.setBackgroundResource(R.drawable.border_gray);
            }
        });
        layout_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_online.setBackgroundResource(R.drawable.border_gray);
                layout_cash.setBackgroundResource(R.drawable.border_green);
            }
        });
    }

    private void delivery() {
        final LinearLayout layout_pickUp = findViewById(R.id.layout_pickUp);
        final LinearLayout layout_delivery = findViewById(R.id.layout_delivery);
        layout_pickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pickUp.setBackgroundResource(R.drawable.border_green);
                layout_delivery.setBackgroundResource(R.drawable.border_gray);
                layout_deliveryItem.setVisibility(View.GONE);
            }
        });
        layout_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pickUp.setBackgroundResource(R.drawable.border_gray);
                layout_delivery.setBackgroundResource(R.drawable.border_green);
                layout_deliveryItem.setVisibility(View.VISIBLE);
            }
        });
    }

    private static void checkProductBasket() {

        // Есть ли товары в корзине
        if (Basket.mListBasket.size() <= 0) {
            txt_nullBasket.setVisibility(View.VISIBLE);
            grid_product.setVisibility(View.GONE);
            layout_order.setVisibility(View.GONE);
        } else {
            txt_nullBasket.setVisibility(View.GONE);
            grid_product.setVisibility(View.VISIBLE);
            layout_order.setVisibility(View.VISIBLE);
        }
    }

    public static void basketAdapterNotifyDataSetChanged() {
        mBasketAdapter.notifyDataSetChanged();
        checkProductBasket();
    }

    public static void updateSumPrice() {
        txt_sumPrice.setText(new GeneralMethods().priceChange(String.valueOf(Basket.getSumPrice())) + " ₽");
    }
}