package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;

import org.json.JSONException;

public class AddBudgetActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        final EditText typename = (EditText) findViewById(R.id.addBudgetTypename);
        final EditText income = (EditText) findViewById(R.id.addBudgetIncome);
        final EditText expenditure = (EditText) findViewById(R.id.addBudgetExpenditure);
        Button action = (Button) findViewById(R.id.AddBudgetAction);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().createBudget(typename.getText().toString(),
                            income.getText().toString(), expenditure.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
