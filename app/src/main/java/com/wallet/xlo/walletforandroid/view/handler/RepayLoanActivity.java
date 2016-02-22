package com.wallet.xlo.walletforandroid.view.handler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.data.LoanData;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.LoanNode;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;
import com.wallet.xlo.walletforandroid.view.AbstractActivity;

import org.json.JSONException;

import java.util.Collection;

/**
 * Created by xlo on 16/2/22.
 * it's the repay loan activity
 */
public class RepayLoanActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_loan);

        final Spinner id = (Spinner) findViewById(R.id.id);
        final Spinner moneyType = (Spinner) findViewById(R.id.moneyType);
        final EditText value = (EditText) findViewById(R.id.value);

        int pos;

        Collection<LoanNode> loanNodes = LoanData.getLoanData().getDataCollection();
        String[] loans = new String[loanNodes.size()];
        pos = 0;
        for (LoanNode now : loanNodes) {
            loans[pos++] = now.getId();
        }
        ArrayAdapter<String> loanAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, loans);
        id.setAdapter(loanAdapter);

        Collection<MoneyNode> moneyNodes = MoneyData.getMoneyData().getDataCollection();
        String[] moneys = new String[moneyNodes.size()];
        pos = 0;
        for (MoneyNode now : moneyNodes) {
            moneys[pos++] = now.getName();
        }
        ArrayAdapter<String> moneyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, moneys);
        moneyType.setAdapter(moneyAdapter);

        Button action = (Button) findViewById(R.id.repayLoan);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().repay(id.getSelectedItem().toString(),
                            moneyType.getSelectedItem().toString(), value.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }

}
