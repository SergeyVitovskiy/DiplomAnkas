package com.sikdev.diplomankas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sikdev.diplomankas.CategoryAndProductActivity;
import com.sikdev.diplomankas.Objects.Category;
import com.sikdev.diplomankas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapterSearch extends BaseAdapter {

    List<Category> mCategoryList;
    Context mContext;

    public CategoryAdapterSearch(List<Category> mCategoryList, Context mContext) {
        this.mCategoryList = mCategoryList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mCategoryList.get(i).getId_();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewItemProduct = View.inflate(mContext, R.layout.item_searct, null);
        TextView txt_name = viewItemProduct.findViewById(R.id.txt_name);
        ImageView img = viewItemProduct.findViewById(R.id.img);
        final Category category = mCategoryList.get(i);
        // Вывод информации о категории
        txt_name.setText(category.getName());
        Picasso.get().load("http://anndroidankas.h1n.ru/image/" + category.getImage())
                .into(img);
        // Перход по категориям
        viewItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CategoryAndProductActivity.class);
                intent.putExtra("id_", category.getId_());
                intent.putExtra("title", category.getName());
                mContext.startActivity(intent);
            }
        });
        return viewItemProduct;
    }
}
