package com.sikdev.diplomankas.CommonComponents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sikdev.diplomankas.R;

public class CallBack {
    Activity mActivity;

    public CallBack(final Activity mActivity) {
        this.mActivity = mActivity;
        TextView txt_tell = mActivity.findViewById(R.id.txt_tell);
        // Звонок оператору
        txt_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+73517501889"));
                mActivity.startActivity(intent);
            }
        });
        // Запросить обратный звонок
        TextView txt_callBack = mActivity.findViewById(R.id.txt_callBack);
        txt_callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallBack();
            }
        });
    }

    private void dialogCallBack() {
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(mActivity);
        View viewDialog = View.inflate(mActivity, R.layout.dialog_call_back, null);
        builderDialog.setView(viewDialog);
        // Обьявление компонентов диалога
        final LinearLayout layout_name = viewDialog.findViewById(R.id.layout_name);
        final LinearLayout layout_tell = viewDialog.findViewById(R.id.layout_tell);
        final EditText txt_name = viewDialog.findViewById(R.id.txt_name);
        final EditText txt_tell = viewDialog.findViewById(R.id.txt_tell);
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
                String name = txt_name.getText().toString();
                String tell = txt_tell.getText().toString();
                String message = txt_message.getText().toString();
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
                if (check >= 2) {
                    Toast.makeText(mActivity, "Заявка отправлена. Ожидайте звонка оператора.", Toast.LENGTH_LONG).show();
                    dialogCallBack.cancel();
                } else {
                    Toast.makeText(mActivity, "Некорректное заполнение полей.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialogCallBack.show();
    }
}
