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
import com.wallet.xlo.walletforandroid.view.ChangeEdgeActivity;

/**
 * Created by xlo on 16/2/22.
 * it's hte edge update handler
 */
public class EdgeUpdateHandler extends PageHandler {

    public EdgeUpdateHandler(Activity activity, TableLayout tableLayout) {
        super(activity, tableLayout);
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle data = msg.getData();
        int size = data.getInt("size");
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);
        for (int i = 0; i < size; i++) {
            TableRow tableRow = (TableRow) activity.getLayoutInflater().inflate(R.layout.table_row_edge,
                    tableLayout, false);
            final TextView from = (TextView) tableRow.findViewById(R.id.edge_row_from);
            final TextView to = (TextView) tableRow.findViewById(R.id.edge_row_to);

            final String fromString = data.getString("from" + i);
            final String toString = data.getString("to" + i);
            final String scriptString = data.getString("script" + i);

            from.setText(fromString);
            to.setText(toString);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ChangeEdgeActivity.class);
                    intent.putExtra("from", fromString);
                    intent.putExtra("to", toString);
                    intent.putExtra("script", scriptString);
                    activity.startActivity(intent);
                }
            });

            tableLayout.addView(tableRow);
        }
    }

}
