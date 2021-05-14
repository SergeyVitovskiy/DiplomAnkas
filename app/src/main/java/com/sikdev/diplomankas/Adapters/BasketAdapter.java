package com.sikdev.diplomankas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sikdev.diplomankas.BasketActivity;
import com.sikdev.diplomankas.CommonComponents.GeneralMethods;
import com.sikdev.diplomankas.Objects.Basket;
import com.sikdev.diplomankas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketAdapter extends BaseAdapter {

    Context mContext;

    public BasketAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Basket.mListBasket.size();
    }

    @Override
    public Object getItem(int i) {
        return Basket.mListBasket.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Basket.mListBasket.get(i).getId_();
    }

    @Override
    public View getView(final int i, final View view, ViewGroup viewGroup) {
        final View viewBasket = View.inflate(mContext, R.layout.item_product_basket, null);
        ImageView img = viewBasket.findViewById(R.id.img);
        TextView txt_name = viewBasket.findViewById(R.id.txt_name);
        TextView txt_price = viewBasket.findViewById(R.id.txt_price);
        TextView txt_minus = viewBasket.findViewById(R.id.txt_minus);
        final TextView txt_plus = viewBasket.findViewById(R.id.txt_plus);
        final TextView txt_quantity = viewBasket.findViewById(R.id.txt_quantity);
        ImageView img_cancel_product = viewBasket.findViewById(R.id.img_cancel_product);
        Basket basket = Basket.mListBasket.get(i);
        // Вывод данных
        txt_name.setText(basket.getName());
        txt_price.setText(new GeneralMethods().priceChange(String.valueOf(basket.getPrice())) + " ₽");
        Picasso.get().load("http://anndroidankas.h1n.ru/image/" + basket.getName_image())
                .into(img);
        txt_quantity.setText(String.valueOf(basket.getQuantity()));
        txt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Basket.mListBasket.get(i).getQuantity() > 1) {
                    Basket.mListBasket.get(i).setQuantity(Basket.mListBasket.get(i).getQuantity()-1, mContext);
                    txt_quantity.setText(String.valueOf(Basket.mListBasket.get(i).getQuantity()));
                    BasketActivity.updateSumPrice();
                }
            }
        });
        txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.mListBasket.get(i).setQuantity(Basket.mListBasket.get(i).getQuantity()+1, mContext);
                txt_quantity.setText(String.valueOf(Basket.mListBasket.get(i).getQuantity()));
                BasketActivity.updateSumPrice();
            }
        });
        img_cancel_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.deleteProduct(mContext, i);
                BasketActivity.basketAdapterNotifyDataSetChanged();
                BasketActivity.updateSumPrice();
            }
        });
        return viewBasket;
    }
}
