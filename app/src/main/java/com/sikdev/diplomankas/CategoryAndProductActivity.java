package com.sikdev.diplomankas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sikdev.diplomankas.Adapters.CategoryAdapter;
import com.sikdev.diplomankas.Adapters.ProductAdapter;
import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ExpandedGridView;
import com.sikdev.diplomankas.CommonComponents.GeneralMethods;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.Objects.Category;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryAndProductActivity extends AppCompatActivity {
    // Категории
    ExpandedGridView grid_category;
    List<Category> mCategoryList;
    CategoryAdapter mCategoryAdapter;
    // Товары
    ExpandedGridView grid_product;
    List<Product> mProductList;
    ProductAdapter mProductAdapter;
    TextView txt_productQuantity;
    // Для вывода
    LinearLayout linearLayout_category;
    LinearLayout linearLayout_product;
    TextView txt_title_category;

    int id_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_and_product);
        // Верхнее меню
        ToolBar toolBar = new ToolBar(CategoryAndProductActivity.this, "CategoryAndProductActivity");
        // Обратный звонок
        CallBack callBack = new CallBack(CategoryAndProductActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(CategoryAndProductActivity.this);

        // Для категории
        mCategoryList = new ArrayList<>();
        grid_category = findViewById(R.id.grid_category);
        mCategoryAdapter = new CategoryAdapter(mCategoryList, CategoryAndProductActivity.this);
        grid_category.setAdapter(mCategoryAdapter);
        grid_category.setExpanded(true);

        // Для товаров
        mProductList = new ArrayList<>();
        grid_product = findViewById(R.id.grid_product);
        mProductAdapter = new ProductAdapter(mProductList, CategoryAndProductActivity.this);
        grid_product.setAdapter(mProductAdapter);
        grid_product.setExpanded(true);
        txt_productQuantity = findViewById(R.id.txt_productQuantity);

        // Получение данных с предыдущей формы
        id_ = getIntent().getIntExtra("id_", 0);
        String title = getIntent().getStringExtra("title");
        // Заголовок категории
        txt_title_category = findViewById(R.id.txt_title_category);
        txt_title_category.setText(title);

        // Присвоение элементов
        linearLayout_product = findViewById(R.id.linearLayout_product);
        linearLayout_category = findViewById(R.id.linearLayout_category);

        // Запрос на получение товаров или категорий
        new getCategoryAndProduct().execute("http://anndroidankas.h1n.ru/mobile-api/ProductOrCategory/" + id_, "ef");

        // Фильтр
        filter();
        // Сортировка
        sort();
    }

    private class getCategoryAndProduct extends AsyncTask<String, Void, String> {

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
                    String params = jsonObjectResult.getString("Param");
                    if (params.equals("Category")) {
                        // Вывести окно категории
                        linearLayout_category.setVisibility(View.VISIBLE);
                        mCategoryList.clear();
                        JSONArray jsonArrayCategory = jsonObjectResult.getJSONArray("Category");
                        for (int i = 0; i < jsonArrayCategory.length(); i++) {
                            JSONObject jsonObjectCategory = jsonArrayCategory.getJSONObject(i);
                            Category category = new Category(
                                    jsonObjectCategory.getInt("id_"),
                                    jsonObjectCategory.getString("name"),
                                    jsonObjectCategory.getString("description"),
                                    jsonObjectCategory.getString("image")
                            );
                            mCategoryList.add(category);
                        }
                        mCategoryAdapter.notifyDataSetChanged();
                    } else if (params.equals("Product")) {
                        // Вывести окно товаров
                        linearLayout_product.setVisibility(View.VISIBLE);
                        mProductList.clear();
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
                            mProductList.add(product);
                        }
                        txt_productQuantity.setText("Товары (" + mProductList.size() + " шт.)");
                        mProductAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }

    // Сортировка
    private void sort() {
        LinearLayout layout_sort = findViewById(R.id.layout_sort);
        layout_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenuSort = new PopupMenu(CategoryAndProductActivity.this, view);
                popupMenuSort.inflate(R.menu.sort_menu);
                popupMenuSort.show();
                popupMenuSort.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_popularity:
                                new getCategoryAndProduct().execute("http://anndroidankas.h1n.ru/mobile-api/sortproduct/" + id_ + "?param=popular", "sort");
                                return true;
                            case R.id.item_top_cheap:
                                int f = 1;
                                new getCategoryAndProduct().execute("http://anndroidankas.h1n.ru/mobile-api/sortproduct/" + id_ + "?param=priceUp");
                                return true;
                            case R.id.item_top_expensive:
                                new getCategoryAndProduct().execute("http://anndroidankas.h1n.ru/mobile-api/sortproduct/" + id_ + "?param=priceDown");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    //Вильтр
    private void filter() {
        LinearLayout layout_filter = findViewById(R.id.layout_filter);
        layout_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderFilter = new AlertDialog.Builder(CategoryAndProductActivity.this);
                builderFilter.setView(R.layout.dialog_filter);
                Dialog dialogFilter = builderFilter.create();
                dialogFilter.show();
            }
        });
    }
}