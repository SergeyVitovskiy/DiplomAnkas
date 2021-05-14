package com.sikdev.diplomankas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sikdev.diplomankas.Adapters.CharacteristicsAdapter;
import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ExpandedGridView;
import com.sikdev.diplomankas.CommonComponents.GeneralMethods;
import com.sikdev.diplomankas.CommonComponents.MySliderImage;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.Objects.Basket;
import com.sikdev.diplomankas.Objects.Characteristics;
import com.sikdev.diplomankas.Objects.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        // Верхнее меню
        ToolBar toolBar = new ToolBar(ProductActivity.this, "CategoryAndProductActivity");
        // Обратный звонок
        CallBack callBack = new CallBack(ProductActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(ProductActivity.this);
        // Заказ в один клик
        orderOneClick();
        // Нашли дешевле
        foundCheaper();
        // Выбор описания, характеристик, файлов
        tabs();
        // Сообщение об ошибке
        errorMessage();
        // Получение информации с предыдущей формы
        final int id_ = getIntent().getIntExtra("id_", 0);
        // Получение информации о товаре
        new getInfoProduct().execute("http://anndroidankas.h1n.ru/mobile-api/Product/" + id_);
        // Присвоение элементов
        final TextView txt_by = findViewById(R.id.txt_by);
        final LinearLayout layout_txt_by = findViewById(R.id.layout_txt_by);
        // Есть ли товар в корзине
        if (Basket.checkProductBasket(id_)) {
            txt_by.setText("В корзине");
            txt_by.setTextColor(getResources().getColor(R.color.colorGreen));
            layout_txt_by.setBackgroundResource(R.drawable.border_green);
        } else {
            txt_by.setText("Купить");
            txt_by.setTextColor(getResources().getColor(R.color.colorWhite));
            layout_txt_by.setBackgroundResource(R.color.colorGreen);
        }
        // Купить товар
        txt_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Basket.checkProductBasket(id_)) {
                    Intent intent = new Intent(ProductActivity.this, BasketActivity.class);
                    startActivity(intent);
                } else {
                    new GeneralMethods().addProductToBasket(ProductActivity.this, mProduct.getId_(),
                            mProduct.getName(), mProduct.getPrice(), mProduct.getName_image());
                    txt_by.setText("В корзине");
                    txt_by.setTextColor(getResources().getColor(R.color.colorGreen));
                    layout_txt_by.setBackgroundResource(R.drawable.border_green);
                }

            }
        });
    }

    // Сообщение об ошибке
    private void errorMessage(){
        TextView txt_errorMessage = findViewById(R.id.txt_errorMessage);
        txt_errorMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(ProductActivity.this);
                View viewDialog = View.inflate(ProductActivity.this, R.layout.dialog_error_message, null);
                builderDialog.setView(viewDialog);
                // Обьявление компонентов диалога
                final LinearLayout layout_mail = viewDialog.findViewById(R.id.layout_mail);
                final LinearLayout layout_message = viewDialog.findViewById(R.id.layout_message);
                final EditText txt_mail = viewDialog.findViewById(R.id.txt_mail);
                final EditText txt_message = viewDialog.findViewById(R.id.txt_message);
                LinearLayout layout_cancel = viewDialog.findViewById(R.id.layout_cancel);
                LinearLayout layout_push = viewDialog.findViewById(R.id.layout_push);
                final Dialog dialogCallBack = builderDialog.create();
                // Закрытие диалога
                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCallBack.cancel();
                    }
                });
                // Отправить запрос
                layout_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mail = txt_mail.getText().toString();
                        String message = txt_message.getText().toString();
                        // Проверка полей
                        int check = 0;
                        if (mail.length() >= 5) {
                            check++;
                            layout_mail.setBackgroundResource(R.drawable.border_gray);
                        } else {
                            layout_mail.setBackgroundResource(R.drawable.border_red);
                        }
                        if (message.length() >= 5) {
                            check++;
                            layout_message.setBackgroundResource(R.drawable.border_gray);
                        } else {
                            layout_message.setBackgroundResource(R.drawable.border_red);
                        }
                        if (check >= 2) {
                            Toast.makeText(ProductActivity.this, "Спасибо что сообщили нам об ошибке.", Toast.LENGTH_LONG).show();
                            dialogCallBack.cancel();
                        } else {
                            Toast.makeText(ProductActivity.this, "Некорректное заполнение полей.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialogCallBack.show();
            }
        });
    }

    private void setInfoProduct(){
        TextView txt_name = findViewById(R.id.txt_name);
        MySliderImage slider_image = findViewById(R.id.slider_image);
        TextView txt_code = findViewById(R.id.txt_code);
        TextView txt_brand = findViewById(R.id.txt_brand);
        TextView txt_price = findViewById(R.id.txt_price);
        TextView txt_descriptionProduct = findViewById(R.id.txt_descriptionProduct);
        ExpandedGridView grid_characteristics = findViewById(R.id.grid_characteristics);

        txt_name.setText(mProduct.getName());
        slider_image.setListImage(mProduct.getListImage());
        txt_code.setText("код: " + mProduct.getId_());
        txt_brand.setText(mProduct.getBrand_name() + ", " + mProduct.getBrand_country());
        txt_price.setText(new GeneralMethods().priceChange(String.valueOf(mProduct.getPrice())) + " ₽");
        if (mProduct.getDescription().equals("null") || mProduct.getDescription().equals(""))
        {

            txt_descriptionProduct.setText("Описание отсутствует");
        } else {
            txt_descriptionProduct.setText(mProduct.getDescription());
        }

        CharacteristicsAdapter characteristicsAdapter = new CharacteristicsAdapter(mProduct.getListCharacteristics(), ProductActivity.this);
        grid_characteristics.setAdapter(characteristicsAdapter);
        grid_characteristics.setExpanded(true);
    }

    private class getInfoProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                // Получение данных
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "null";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Проверка ответа
            if (new GeneralMethods().checkResult(result)) {
                // Парсинг ответа от сервера
                try {
                    JSONObject jsonObjectResult = new JSONObject(result);
                    // Информация о товаре
                    JSONObject jsonObjectProduct = jsonObjectResult.getJSONObject("Product");
                    int id_ = jsonObjectProduct.getInt("id_");
                    String name = jsonObjectProduct.getString("name");
                    int price = jsonObjectProduct.getInt("price");
                    String brand_country = jsonObjectProduct.getString("brand_country");
                    String brand_name = jsonObjectProduct.getString("brand_name");
                    String name_image = jsonObjectProduct.getString("name_image");
                    int quantity = jsonObjectProduct.getInt("quantity");
                    String description = jsonObjectProduct.getString("description");
                    // Изображения
                    List<String> listImage = new ArrayList<>();
                    JSONArray jsonArrayImage = jsonObjectResult.getJSONArray("Image");
                    for (int i = 0;i<jsonArrayImage.length();i++){
                        JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(i);
                        listImage.add(jsonObjectImage.getString("name_image"));
                    }
                    // Характеристики
                    List<Characteristics> listCharacteristics = new ArrayList<>();
                    JSONArray jsonArrayCharacteristics = jsonObjectResult.getJSONArray("Characteristics");
                    for (int i = 0;i<jsonArrayCharacteristics.length();i++){
                        JSONObject jsonObjectCharacteristics = jsonArrayCharacteristics.getJSONObject(i);
                        String nameCharacteristic = jsonObjectCharacteristics.getString("name");
                        String characteristic = jsonObjectCharacteristics.getString("characteristic");
                        listCharacteristics.add(
                                new Characteristics(nameCharacteristic, characteristic));
                    }
                    mProduct = new Product(id_, name, price, brand_country, brand_name, name_image, quantity, description, listImage, listCharacteristics);
                    setInfoProduct();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Выбор элемента
    private void tabs() {
        final TextView txt_description = findViewById(R.id.txt_description);
        final TextView txt_characteristics = findViewById(R.id.txt_characteristics);
        final TextView txt_file = findViewById(R.id.txt_file);
        final LinearLayout layout_tab_description = findViewById(R.id.layout_tab_description);
        final LinearLayout layout_tab_characteristics = findViewById(R.id.layout_tab_characteristics);
        final LinearLayout layout_tab_file = findViewById(R.id.layout_tab_file);
        txt_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_tab_description.setVisibility(View.VISIBLE);
                layout_tab_characteristics.setVisibility(View.GONE);
                layout_tab_file.setVisibility(View.GONE);
                txt_description.setTextColor(getResources().getColor(R.color.colorBlack));
                txt_characteristics.setTextColor(getResources().getColor(R.color.colorBlue));
                txt_file.setTextColor(getResources().getColor(R.color.colorBlue));
            }
        });
        txt_characteristics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_tab_description.setVisibility(View.GONE);
                layout_tab_characteristics.setVisibility(View.VISIBLE);
                layout_tab_file.setVisibility(View.GONE);
                txt_description.setTextColor(getResources().getColor(R.color.colorBlue));
                txt_characteristics.setTextColor(getResources().getColor(R.color.colorBlack));
                txt_file.setTextColor(getResources().getColor(R.color.colorBlue));
            }
        });
        txt_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_tab_description.setVisibility(View.GONE);
                layout_tab_characteristics.setVisibility(View.GONE);
                layout_tab_file.setVisibility(View.VISIBLE);
                txt_description.setTextColor(getResources().getColor(R.color.colorBlue));
                txt_characteristics.setTextColor(getResources().getColor(R.color.colorBlue));
                txt_file.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
    }

    // Нашли дешевле
    private void foundCheaper() {
        TextView text_foundCheaper = findViewById(R.id.text_foundCheaper);
        text_foundCheaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(ProductActivity.this);
                View viewFoundCheaper = View.inflate(ProductActivity.this, R.layout.dialog_found_cheaper, null);
                builderDialog.setView(viewFoundCheaper);
                final Dialog dialogFoundCheaper = builderDialog.create();
                final LinearLayout layout_name = viewFoundCheaper.findViewById(R.id.layout_name);
                final LinearLayout layout_tell = viewFoundCheaper.findViewById(R.id.layout_tell);
                final EditText txt_name = viewFoundCheaper.findViewById(R.id.txt_name);
                final EditText txt_tell = viewFoundCheaper.findViewById(R.id.txt_tell);
                final EditText txt_mail = viewFoundCheaper.findViewById(R.id.txt_mail);
                final EditText txt_link = viewFoundCheaper.findViewById(R.id.txt_link);
                ImageView img_cancel = viewFoundCheaper.findViewById(R.id.img_cancel);
                img_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogFoundCheaper.cancel();
                    }
                });
                LinearLayout layout_push = viewFoundCheaper.findViewById(R.id.layout_push);
                layout_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = 0;
                        String name = txt_name.getText().toString();
                        String tell = txt_tell.getText().toString();
                        String mail = txt_mail.getText().toString();
                        String link = txt_link.getText().toString();
                        if (name.length() > 5) {
                            layout_name.setBackgroundResource(R.drawable.border_gray);
                            check++;
                        } else {
                            layout_name.setBackgroundResource(R.drawable.border_red);
                        }
                        if (tell.length() > 5) {
                            layout_tell.setBackgroundResource(R.drawable.border_gray);
                            check++;
                        } else {
                            layout_tell.setBackgroundResource(R.drawable.border_red);
                        }
                        if (check >= 2) {
                            Toast.makeText(ProductActivity.this, "Мы обработаем ваш запрос.", Toast.LENGTH_SHORT).show();
                            dialogFoundCheaper.cancel();
                        } else {
                            Toast.makeText(ProductActivity.this, "Некорректное заполнение полей.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialogFoundCheaper.show();
            }
        });
    }

    // Заказ в один клик
    private void orderOneClick() {
        TextView text_orderOneClick = findViewById(R.id.text_orderOneClick);
        text_orderOneClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(ProductActivity.this);
                View viewOrderOneClick = View.inflate(ProductActivity.this, R.layout.dialog_order_one_click, null);
                builderDialog.setView(viewOrderOneClick);
                final Dialog dialogOrderOneClick = builderDialog.create();
                final LinearLayout layout_name = viewOrderOneClick.findViewById(R.id.layout_name);
                final LinearLayout layout_tell = viewOrderOneClick.findViewById(R.id.layout_tell);
                final EditText txt_name = viewOrderOneClick.findViewById(R.id.txt_name);
                final EditText txt_tell = viewOrderOneClick.findViewById(R.id.txt_tell);
                final EditText txt_mail = viewOrderOneClick.findViewById(R.id.txt_mail);
                ImageView img_cancel = viewOrderOneClick.findViewById(R.id.img_cancel);
                img_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogOrderOneClick.cancel();
                    }
                });
                LinearLayout layout_push = viewOrderOneClick.findViewById(R.id.layout_push);
                layout_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = 0;
                        String name = txt_name.getText().toString();
                        String tell = txt_tell.getText().toString();
                        String mail = txt_mail.getText().toString();
                        if (name.length() > 5) {
                            layout_name.setBackgroundResource(R.drawable.border_gray);
                            check++;
                        } else {
                            layout_name.setBackgroundResource(R.drawable.border_red);
                        }
                        if (tell.length() > 5) {
                            layout_tell.setBackgroundResource(R.drawable.border_gray);
                            check++;
                        } else {
                            layout_tell.setBackgroundResource(R.drawable.border_red);
                        }
                        if (check >= 2) {
                            Toast.makeText(ProductActivity.this, "Заказ оформлен, ожидайте звонка оператора", Toast.LENGTH_SHORT).show();
                            dialogOrderOneClick.cancel();
                        } else {
                            Toast.makeText(ProductActivity.this, "Некорректное заполнение полей.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialogOrderOneClick.show();
            }
        });
    }
}