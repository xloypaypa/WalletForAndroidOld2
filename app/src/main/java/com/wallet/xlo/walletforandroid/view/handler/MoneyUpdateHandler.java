package com.wallet.xlo.walletforandroid.view.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.view.ChangeMoneyActivity;

import java.text.DecimalFormat;

/**
 * Created by xlo on 16/2/22.
 * it's the money update handler
 */
public class MoneyUpdateHandler extends PageHandler {
    public MoneyUpdateHandler(Activity activity, TableLayout tableLayout) {
        super(activity, tableLayout);
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle data = msg.getData();
        int size = data.getInt("size");
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        for (int i = 0; i < size; i++) {
            TableRow tableRow = (TableRow) activity.getLayoutInflater().inflate(R.layout.table_row_money,
                    tableLayout, false);
            TextView money = (TextView) tableRow.findViewById(R.id.money_row_money);
            TextView value = (TextView) tableRow.findViewById(R.id.money_row_value);
            final String typename = data.getString("money" + i);
            final double moneyValue = data.getDouble("value" + i);
            money.setText(typename);
            value.setText(decimalFormat.format(moneyValue));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ChangeMoneyActivity.class);
                    intent.putExtra("typename", typename);
                    intent.putExtra("value", moneyValue);
                    activity.startActivity(intent);
                }
            });

            tableLayout.addView(tableRow);
        }
    }
}
