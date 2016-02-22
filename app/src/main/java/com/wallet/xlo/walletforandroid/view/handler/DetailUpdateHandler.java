package com.wallet.xlo.walletforandroid.view.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wallet.xlo.walletforandroid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xlo on 16/2/22.
 * it's the detail update handler
 */
public class DetailUpdateHandler extends PageHandler {
    public DetailUpdateHandler(Activity activity, TableLayout tableLayout) {
        super(activity, tableLayout);
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle data = msg.getData();
        int size = data.getInt("size");
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        for (int i = 0; i < size; i++) {
            TableRow tableRow = (TableRow) activity.getLayoutInflater().inflate(R.layout.table_row_detail,
                    tableLayout, false);
            final TextView id = (TextView) tableRow.findViewById(R.id.detail_row_id);
            final TextView event = (TextView) tableRow.findViewById(R.id.detail_row_event);
            TextView date = (TextView) tableRow.findViewById(R.id.detail_row_date);

            final String idString = data.getString("id" + i);
            final String eventString = data.getString("event" + i);
            final long dateLong = data.getLong("date" + i);

            id.setText(idString);
            event.setText(eventString);
            date.setText(simpleDateFormat.format(new Date(dateLong)));

            tableLayout.addView(tableRow);
        }
    }
}
