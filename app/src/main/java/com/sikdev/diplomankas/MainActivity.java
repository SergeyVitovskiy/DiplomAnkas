package com.sikdev.diplomankas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.sikdev.diplomankas.Adapters.CategoryAdapter;
import com.sikdev.diplomankas.CommonComponents.BottomMenu;
import com.sikdev.diplomankas.CommonComponents.CallBack;
import com.sikdev.diplomankas.CommonComponents.ExpandedGridView;
import com.sikdev.diplomankas.CommonComponents.GeneralMethods;
import com.sikdev.diplomankas.CommonComponents.MySliderImage;
import com.sikdev.diplomankas.CommonComponents.ToolBar;
import com.sikdev.diplomankas.Objects.Category;

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


public class MainActivity extends AppCompatActivity {
    // Категории
    ExpandedGridView grid_category;
    List<Category> mCategoryList;
    CategoryAdapter mCategoryAdapter;
    // Баннер
    List<String> mImageBannerList;
    MySliderImage slider_image_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Верхнее меню
        ToolBar toolBar = new ToolBar(MainActivity.this, "MainActivity");
        // Обратный звонок
        CallBack callBack = new CallBack(MainActivity.this);
        // Нижнее меню
        BottomMenu bottomMenu = new BottomMenu(MainActivity.this);
        // Баннер
        mImageBannerList = new ArrayList<>();
        slider_image_banner = findViewById(R.id.slider_image_banner);
        // Категории
        mCategoryList = new ArrayList<>();
        grid_category = findViewById(R.id.grid_category);
        mCategoryAdapter = new CategoryAdapter(mCategoryList, MainActivity.this);
        grid_category.setAdapter(mCategoryAdapter);
        grid_category.setExpanded(true);
        // Получение данных
        new getMainScreen().execute("http://anndroidankas.h1n.ru/mobile-api/MainScreen");
    }

    // Получение категорий
    private class getMainScreen extends AsyncTask<String, Void, String> {

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
                    // Получение банера
                    mImageBannerList.clear();
                    JSONArray jsonArrayBanner = jsonObjectResult.getJSONArray("Banner");
                    for (int i = 0;i<jsonArrayBanner.length();i++){
                        JSONObject jsonObjectBanner = jsonArrayBanner.getJSONObject(i);
                        mImageBannerList.add(jsonObjectBanner.getString("name_image"));
                    }
                    slider_image_banner.setListImage(mImageBannerList);
                    // Получение категорий
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}