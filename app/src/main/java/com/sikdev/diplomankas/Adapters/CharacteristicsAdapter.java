package com.sikdev.diplomankas.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sikdev.diplomankas.Objects.Characteristics;
import com.sikdev.diplomankas.R;

import java.util.List;

public class CharacteristicsAdapter extends BaseAdapter {

    List<Characteristics> mListCharacteristics;
    Context mContext;

    public CharacteristicsAdapter(List<Characteristics> mListCharacteristics, Context mContext) {
        this.mListCharacteristics = mListCharacteristics;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListCharacteristics.size();
    }

    @Override
    public Object getItem(int i) {
        return mListCharacteristics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewCharacteristic = View.inflate(mContext, R.layout.item_characteristics, null);
        TextView txt_name = viewCharacteristic.findViewById(R.id.txt_name);
        TextView txt_characteristic = viewCharacteristic.findViewById(R.id.txt_characteristic);
        Characteristics characteristics = mListCharacteristics.get(i);
        txt_name.setText(characteristics.getName());
        txt_characteristic.setText(characteristics.getCharacteristic());
        return viewCharacteristic;
    }
}
