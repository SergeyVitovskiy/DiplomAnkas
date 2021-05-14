package com.sikdev.diplomankas.CommonComponents;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sikdev.diplomankas.Adapters.CategoryAdapterSearch;
import com.sikdev.diplomankas.Adapters.ProductAdapterSearch;
import com.sikdev.diplomankas.BasketActivity;
import com.sikdev.diplomankas.Info.NoInfoActivity;
import com.sikdev.diplomankas.MainActivity;
import com.sikdev.diplomankas.Objects.Category;
import com.sikdev.diplomankas.Objects.Product;
import com.sikdev.diplomankas.Objects.User;
import com.sikdev.diplomankas.R;
import com.sikdev.diplomankas.User.UserActivity;
import com.sikdev.diplomankas.User.UserAuthorizationActivity;

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

public class ToolBar {
    String mNameActivity;
    Activity mActivity;

    EditText eText_search;
    LinearLayout linearLayout_searchResult;

    TextView txt_searchMessage;

    // Категории
    ExpandedGridView grid_categorySearch;
    List<Category> mCategoryListSearch;
    CategoryAdapterSearch mCategoryAdapterSearch;
    TextView txt_categorySearch;
    // Товары
    ExpandedGridView grid_productSearch;
    List<Product> mProductListSearch;
    ProductAdapterSearch mProductAdapterSearch;
    TextView txt_productSearch;

    public ToolBar(Activity mActivity, String mNameActivity) {
        this.mActivity = mActivity;
        this.mNameActivity = mNameActivity;
        buttonToolBar();
    }

    private void buttonToolBar() {
        // Логитип
        LinearLayout layout_logo = mActivity.findViewById(R.id.layout_logo);
        layout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNameActivity.equals("MainActivity")) {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    mActivity.startActivity(intent);
                }
            }
        });
        // Корзина
        ImageView item_toolBar_basket = mActivity.findViewById(R.id.item_toolBar_basket);
        item_toolBar_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNameActivity.equals("BasketActivity")) {
                    Intent intent = new Intent(mActivity, BasketActivity.class);
                    mActivity.startActivity(intent);
                }
            }
        });
        // Пользователь
        ImageView item_toolBar_user = mActivity.findViewById(R.id.item_toolBar_user);
        item_toolBar_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mNameActivity.equals("User")) {
                    if (!User.checkUser()) {
                        Intent intent = new Intent(mActivity, UserActivity.class);
                        mActivity.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mActivity, UserAuthorizationActivity.class);
                        mActivity.startActivity(intent);
                    }
                }
            }
        });
        // Поиск
        ImageView item_toolBar_search = mActivity.findViewById(R.id.item_toolBar_search);
        item_toolBar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        // Меню
        ImageView item_toolBar_menu = mActivity.findViewById(R.id.item_toolBar_menu);
        item_toolBar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibleMenu();
            }
        });
        menu();
    }

    private void search() {
        final LinearLayout linearLayout_search = mActivity.findViewById(R.id.linearLayout_search);
        final RelativeLayout relativeLayout_toolbar = mActivity.findViewById(R.id.relativeLayout_toolbar);
        if (linearLayout_search.getVisibility() == View.VISIBLE) {
            relativeLayout_toolbar.setVisibility(View.VISIBLE);
            linearLayout_search.setVisibility(View.GONE);
        } else {
            relativeLayout_toolbar.setVisibility(View.GONE);
            linearLayout_search.setVisibility(View.VISIBLE);
        }
        // Функции поиска
        // Закрыть поиск
        final TextView search_cancel = mActivity.findViewById(R.id.search_cancel);
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout_toolbar.setVisibility(View.VISIBLE);
                linearLayout_search.setVisibility(View.GONE);
                eText_search.setText("");
            }
        });
        // Поиск
        txt_categorySearch = mActivity.findViewById(R.id.txt_categorySearch);
        txt_productSearch = mActivity.findViewById(R.id.txt_productSearch);
        mCategoryListSearch = new ArrayList<>();
        mProductListSearch = new ArrayList<>();
        grid_categorySearch = mActivity.findViewById(R.id.grid_categorySearch);
        grid_productSearch = mActivity.findViewById(R.id.grid_productSearch);
        mCategoryAdapterSearch = new CategoryAdapterSearch(mCategoryListSearch, mActivity);
        mProductAdapterSearch = new ProductAdapterSearch(mProductListSearch, mActivity);
        grid_categorySearch.setAdapter(mCategoryAdapterSearch);
        grid_productSearch.setAdapter(mProductAdapterSearch);
        grid_categorySearch.setExpanded(true);
        grid_productSearch.setExpanded(true);
        txt_searchMessage = mActivity.findViewById(R.id.txt_searchMessage);
        linearLayout_searchResult = mActivity.findViewById(R.id.linearLayout_searchResult);
        eText_search = mActivity.findViewById(R.id.eText_search);
        txt_searchMessage.setText("Введите название товара или категории");
        eText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = eText_search.getText().toString();
                if (searchText.equals("")) {
                    txt_searchMessage.setText("Введите название товара или категории");
                    txt_searchMessage.setVisibility(View.VISIBLE);
                    linearLayout_searchResult.setVisibility(View.GONE);
                    txt_categorySearch.setVisibility(View.GONE);
                    txt_productSearch.setVisibility(View.GONE);
                } else {
                    txt_searchMessage.setVisibility(View.GONE);
                    linearLayout_searchResult.setVisibility(View.VISIBLE);
                    // Поиск
                    new getSearch().execute("http://anndroidankas.h1n.ru/mobile-api/search?param=" + searchText);
                }
                Toast.makeText(mActivity, "Ввод", Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void menu() {
        TextView receiptAndPayment = mActivity.findViewById(R.id.receiptAndPayment);
        receiptAndPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoInfoActivity.class);
                mActivity.startActivity(intent);
            }
        });
        TextView contacts = mActivity.findViewById(R.id.contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoInfoActivity.class);
                mActivity.startActivity(intent);
            }
        });
        TextView serviceAndSupport = mActivity.findViewById(R.id.serviceAndSupport);
        serviceAndSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoInfoActivity.class);
                mActivity.startActivity(intent);
            }
        });
        TextView makingOrders = mActivity.findViewById(R.id.makingOrders);
        makingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoInfoActivity.class);
                mActivity.startActivity(intent);
            }
        });
        TextView information = mActivity.findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoInfoActivity.class);
                mActivity.startActivity(intent);
            }
        });
        TextView company = mActivity.findViewById(R.id.company);
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoInfoActivity.class);
                mActivity.startActivity(intent);
            }
        });

    }

    private void visibleMenu() {
        // Показ меню
        final LinearLayout layout_menu = mActivity.findViewById(R.id.layout_menu);
        if (layout_menu.getVisibility() == View.VISIBLE) {
            layout_menu.setVisibility(View.GONE);
        } else {
            layout_menu.setVisibility(View.VISIBLE);
        }
        // Функции меню
    }

    private class getSearch extends AsyncTask<String, Void, String> {

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
                    mCategoryListSearch.clear();
                    try {
                        JSONArray jsonArrayCategory = jsonObjectResult.getJSONArray("Category");
                        for (int i = 0; i < jsonArrayCategory.length(); i++) {
                            JSONObject jsonObjectCategory = jsonArrayCategory.getJSONObject(i);
                            Category category = new Category(
                                    jsonObjectCategory.getInt("id_"),
                                    jsonObjectCategory.getString("name"),
                                    jsonObjectCategory.getString("description"),
                                    jsonObjectCategory.getString("image")
                            );
                            mCategoryListSearch.add(category);
                        }
                        txt_categorySearch.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        txt_categorySearch.setVisibility(View.GONE);
                    }
                    mCategoryAdapterSearch.notifyDataSetChanged();
                    // Вывести окно товаров
                    mProductListSearch.clear();
                    try {
                        JSONArray jsonArrayProduct = jsonObjectResult.getJSONArray("Product");
                        for (int i = 0; i < jsonArrayProduct.length(); i++) {
                            JSONObject jsonObjectProduct = jsonArrayProduct.getJSONObject(i);
                            Product product = new Product(
                                    jsonObjectProduct.getInt("id_"),
                                    jsonObjectProduct.getString("name"),
                                    jsonObjectProduct.getInt("price"),
                                    jsonObjectProduct.getString("brand_country"),
                                    jsonObjectProduct.getString("brand_name"),
                                    jsonObjectProduct.getString("name_image")
                            );
                            mProductListSearch.add(product);
                        }
                        txt_productSearch.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        txt_productSearch.setVisibility(View.GONE);
                    }
                    mProductAdapterSearch.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mProductListSearch.size() == 0 && mCategoryListSearch.size() == 0) {
                    txt_searchMessage.setVisibility(View.VISIBLE);
                    txt_searchMessage.setText("Не удалось ничего найти");
                }
            }
        }
    }
}
