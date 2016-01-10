package com.wallet.xlo.walletforandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;

import org.json.JSONException;

import java.text.DecimalFormat;

public class ChangeBudgetActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_budget);

        Intent intent = getIntent();

        final String typenameString = intent.getStringExtra("typename");
        final double incomeDouble = intent.getDoubleExtra("income", 0);
        final double expenditureDouble = intent.getDoubleExtra("expenditure", 0);
        final double nowIncomeDouble = intent.getDoubleExtra("now income", 0);
        final double nowExpenditureDouble = intent.getDoubleExtra("now expenditure", 0);

        final EditText typename = (EditText) findViewById(R.id.changeBudgetType);
        final EditText income = (EditText) findViewById(R.id.changeBudgetIncome);
        final EditText expenditure = (EditText) findViewById(R.id.changeBudgetExpenditure);
        EditText nowIncome = (EditText) findViewById(R.id.changeBudgetNowIncome);
        EditText nowExpenditure = (EditText) findViewById(R.id.changeBudgetNowExpenditure);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        typename.setText(typenameString);
        income.setText(decimalFormat.format(incomeDouble));
        expenditure.setText(decimalFormat.format(expenditureDouble));
        nowIncome.setText(decimalFormat.format(nowIncomeDouble));
        nowExpenditure.setText(decimalFormat.format(nowExpenditureDouble));

        Button changeBudget = (Button) findViewById(R.id.changeBudgetAction);
        changeBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().changeBudget(typenameString, typename.getText().toString(),
                            income.getText().toString(), expenditure.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });

        Button removeBudget = (Button) findViewById(R.id.changeBudgetRemoveBudget);
        removeBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().removeBudget(typenameString);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
