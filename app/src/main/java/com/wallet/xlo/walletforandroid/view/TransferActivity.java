package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;

import org.json.JSONException;

import java.util.Collection;

public class TransferActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        final Spinner from = (Spinner) findViewById(R.id.transferFrom);
        final Spinner to = (Spinner) findViewById(R.id.transferTo);
        final EditText value = (EditText) findViewById(R.id.transferValue);

        int pos;

        Collection<MoneyNode> moneyNodes = MoneyData.getMoneyData().getDataCollection();
        String[] moneys = new String[moneyNodes.size()];
        pos = 0;
        for (MoneyNode now : moneyNodes) {
            moneys[pos++] = now.getName();
        }
        ArrayAdapter<String> moneyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, moneys);
        from.setAdapter(moneyAdapter);
        to.setAdapter(moneyAdapter);

        Button button = (Button) findViewById(R.id.transferAction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().transferMoney(from.getSelectedItem().toString(),
                            to.getSelectedItem().toString(), value.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
