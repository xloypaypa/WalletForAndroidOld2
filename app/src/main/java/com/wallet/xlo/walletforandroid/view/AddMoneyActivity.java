package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;

import org.json.JSONException;

public class AddMoneyActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        final EditText typename = (EditText) findViewById(R.id.addMoneyTypename);
        final EditText value = (EditText) findViewById(R.id.addMoneyValue);

        Button addAction = (Button) findViewById(R.id.addMoneyAction);
        addAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().createMoney(typename.getText().toString(), value.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
