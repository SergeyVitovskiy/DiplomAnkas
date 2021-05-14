package com.sikdev.diplomankas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sikdev.diplomankas.BasketActivity;
import com.sikdev.diplomankas.CommonComponents.GeneralMethods;
import com.sikdev.diplomankas.Objects.Basket;
import com.sikdev.diplomankas.Objects.Product;
import com.sikdev.diplomankas.ProductActivity;
import com.sikdev.diplomankas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapterSearch extends BaseAdapter {

    List<Product> mProductList;

    public ProductAdapterSearch(List<Product> mProductList, Context mContext) {
        this.mProductList = mProductList;
        this.mContext = mContext;
    }

    Context mContext;

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mProductList.get(i).getId_();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View viewProduct = View.inflate(mContext, R.layout.item_searct, null);
        // Присвоение элементов
        TextView txt_name = viewProduct.findViewById(R.id.txt_name);
        ImageView img = viewProduct.findViewById(R.id.img);
        final TextView txt_by = viewProduct.findViewById(R.id.txt_by);
        final LinearLayout layout_txt_by = viewProduct.findViewById(R.id.layout_txt_by);
        // Вывод данных
        final Product product = mProductList.get(i);
        txt_name.setText(product.getName());
        Picasso.get().load("http://anndroidankas.h1n.ru/image/" + product.getName_image())
                .into(img);
        // Обработка нажатия
        viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("id_", product.getId_());
                mContext.startActivity(intent);
            }
        });
        return viewProduct;
    }
}
