package com.sikdev.diplomankas.CommonComponents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sikdev.diplomankas.R;

public class BottomMenu {
    Activity mActivity;

    public BottomMenu(Activity mActivity) {
        this.mActivity = mActivity;
        // Отзыв
        LeaveReview();
        // Социальные сети
        SocialNetwork();
        // Телефоны
        tell();
    }

    private void tell() {
        LinearLayout layout_tell_1 = mActivity.findViewById(R.id.layout_tell_1);
        layout_tell_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+73517514035"));
                mActivity.startActivity(intent);
            }
        });
        LinearLayout layout_tell_2 = mActivity.findViewById(R.id.layout_tell_2);
        layout_tell_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+73517501886"));
                mActivity.startActivity(intent);
            }
        });
        LinearLayout layout_tell_3 = mActivity.findViewById(R.id.layout_tell_3);
        layout_tell_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+73517514012"));
                mActivity.startActivity(intent);
            }
        });
    }

    private void SocialNetwork() {
        ImageView youtube = mActivity.findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/channel/UCQwr68VnnTJzpG7cLAoptmw"));
                mActivity.startActivity(browserIntent);
            }
        });
        ImageView vk = mActivity.findViewById(R.id.vk);
        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://vk.com/ankas_ru?roistat_visit=1491250"));
                mActivity.startActivity(browserIntent);
            }
        });
        ImageView instagram = mActivity.findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/ankas.ru/?hl=ru&roistat_visit=1491250"));
                mActivity.startActivity(browserIntent);
            }
        });
        ImageView facebook = mActivity.findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/ankas.ru/?roistat_visit=1491250"));
                mActivity.startActivity(browserIntent);
            }
        });
    }

    private void LeaveReview(){
        TextView txt_leaveReview = mActivity.findViewById(R.id.txt_leaveReview);
        txt_leaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://market.yandex.ru/shop--ankas/346604/reviews?roistat_visit=1491250"));
                mActivity.startActivity(browserIntent);
            }
        });
    }
}
