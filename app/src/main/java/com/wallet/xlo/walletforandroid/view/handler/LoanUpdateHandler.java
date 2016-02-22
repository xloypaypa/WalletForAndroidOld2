package com.wallet.xlo.walletforandroid.view.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wallet.xlo.walletforandroid.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xlo on 16/2/22.
 * loan update handler
 */
public class LoanUpdateHandler extends PageHandler {
    public LoanUpdateHandler(Activity activity, TableLayout tableLayout) {
        super(activity, tableLayout);
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle data = msg.getData();
        int size = data.getInt("size");
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (int i = 0; i < size; i++) {
            TableRow tableRow = (TableRow) activity.getLayoutInflater().inflate(R.layout.table_row_loan,
                    tableLayout, false);
            final TextView id = (TextView) tableRow.findViewById(R.id.loan_row_id);
            final TextView creditor = (TextView) tableRow.findViewById(R.id.loan_row_creditor);
            TextView value = (TextView) tableRow.findViewById(R.id.loan_row_value);
            TextView deadline = (TextView) tableRow.findViewById(R.id.loan_row_deadline);
            TextView isIn = (TextView) tableRow.findViewById(R.id.loan_row_in_or_out);

            final String idString = data.getString("id" + i);
            final String creditorString = data.getString("creditor" + i);
            final double valueDouble = data.getDouble("value" + i);
            final long deadlineLong = data.getLong("deadline" + i);
            final String isInString = data.getString("isIn" + i);

            id.setText(idString);
            creditor.setText(creditorString);
            value.setText(decimalFormat.format(valueDouble));
            deadline.setText(simpleDateFormat.format(new Date(deadlineLong)));
            isIn.setText(isInString);

            tableLayout.addView(tableRow);
        }
    }
}
