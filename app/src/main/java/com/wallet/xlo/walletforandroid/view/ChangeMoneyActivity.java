package com.wallet.xlo.walletforandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;

import org.json.JSONException;

import java.text.DecimalFormat;

public class ChangeMoneyActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_money);

        Intent intent = getIntent();
        final EditText typename = (EditText) findViewById(R.id.changeMoneyType);
        EditText value = (EditText) findViewById(R.id.changeMoneyValue);
        final String oldName = intent.getStringExtra("typename");
        typename.setText(oldName);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        value.setText(decimalFormat.format(intent.getDoubleExtra("value", 0)));

        Button button = (Button) findViewById(R.id.changeMoneyAction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().renameMoney(oldName, typename.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
