package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.data.BudgetData;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.BudgetNode;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;

import org.json.JSONException;

import java.util.Collection;

public class UseMoneyActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_money);

        final Spinner moneyType = (Spinner) findViewById(R.id.useMoneyType);
        final Spinner budgetType = (Spinner) findViewById(R.id.useBudgetType);
        final EditText value = (EditText) findViewById(R.id.useMoneyValue);

        int pos;

        Collection<MoneyNode> moneyNodes = MoneyData.getMoneyData().getDataCollection();
        String[] moneys = new String[moneyNodes.size()];
        pos = 0;
        for (MoneyNode now : moneyNodes) {
            moneys[pos++] = now.getName();
        }
        ArrayAdapter<String> moneyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, moneys);
        moneyType.setAdapter(moneyAdapter);

        Collection<BudgetNode> budgetNodes = BudgetData.getBudgetData().getDataCollection();
        String[] budgets = new String[budgetNodes.size()];
        pos = 0;
        for (BudgetNode now : budgetNodes) {
            budgets[pos++] = now.getName();
        }
        ArrayAdapter<String> budgetAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, budgets);
        budgetType.setAdapter(budgetAdapter);

        final RadioButton isIncome = (RadioButton) findViewById(R.id.isIncome);
        final RadioButton isExpenditure = (RadioButton) findViewById(R.id.isExpenditure);

        Button useMoney = (Button) findViewById(R.id.useMoneyAction);
        useMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isIncome.isChecked()) {
                        controlBind.getProtocolSender().income(moneyType.getSelectedItem().toString(),
                                budgetType.getSelectedItem().toString(), value.getText().toString());
                    } else if (isExpenditure.isChecked()) {
                        controlBind.getProtocolSender().expenditure(moneyType.getSelectedItem().toString(),
                                budgetType.getSelectedItem().toString(), value.getText().toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
