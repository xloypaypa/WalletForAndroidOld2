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
import com.wallet.xlo.walletforandroid.view.ChangeBudgetActivity;

import java.text.DecimalFormat;

/**
 * Created by xlo on 16/2/22.
 * it's the budeget update handler
 */
public class BudgetUpdateHandler extends PageHandler {
    public BudgetUpdateHandler(Activity activity, TableLayout tableLayout) {
        super(activity, tableLayout);
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle data = msg.getData();
        int size = data.getInt("size");
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        for (int i = 0; i < size; i++) {
            TableRow tableRow = (TableRow) activity.getLayoutInflater().inflate(R.layout.table_row_budget,
                    tableLayout, false);
            final TextView typename = (TextView) tableRow.findViewById(R.id.budget_row_typename);
            final TextView income = (TextView) tableRow.findViewById(R.id.budget_row_income);
            TextView expenditure = (TextView) tableRow.findViewById(R.id.budget_row_expenditure);
            TextView nowIncome = (TextView) tableRow.findViewById(R.id.budget_row_nowIncome);
            TextView nowExpenditure = (TextView) tableRow.findViewById(R.id.budget_row_nowExpenditure);

            final String typenameString = data.getString("typename" + i);
            final double incomeDouble = data.getDouble("income" + i);
            final double expenditureDouble = data.getDouble("expenditure" + i);
            final double nowIncomeDouble = data.getDouble("now income" + i);
            final double nowExpenditureDouble = data.getDouble("now expenditure" + i);

            typename.setText(typenameString);
            income.setText(decimalFormat.format(incomeDouble));
            expenditure.setText(decimalFormat.format(expenditureDouble));
            nowIncome.setText(decimalFormat.format(nowIncomeDouble));
            nowExpenditure.setText(decimalFormat.format(nowExpenditureDouble));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ChangeBudgetActivity.class);
                    intent.putExtra("typename", typenameString);
                    intent.putExtra("income", incomeDouble);
                    intent.putExtra("expenditure", expenditureDouble);
                    intent.putExtra("now income", nowIncomeDouble);
                    intent.putExtra("now expenditure", nowExpenditureDouble);
                    activity.startActivity(intent);
                }
            });

            tableLayout.addView(tableRow);
        }
    }
}
