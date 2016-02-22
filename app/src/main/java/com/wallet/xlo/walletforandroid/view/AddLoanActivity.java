package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Collection;

/**
 * Created by xlo on 16/2/22.
 * it's the add loan activity
 */
public class AddLoanActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        final EditText creditor = (EditText) findViewById(R.id.creditor);
        final Spinner moneyType = (Spinner) findViewById(R.id.moneyType);
        final EditText value = (EditText) findViewById(R.id.value);
        final EditText deadlineYear = (EditText) findViewById(R.id.deadlineYear);
        final EditText deadlineMonth = (EditText) findViewById(R.id.deadlineMonth);
        final EditText deadlineDay = (EditText) findViewById(R.id.deadlineDay);
        final RadioButton in = (RadioButton) findViewById(R.id.loanIn);

        Calendar calendar = Calendar.getInstance();
        deadlineYear.setText(String.format("%d", calendar.get(Calendar.YEAR)));
        deadlineMonth.setText(String.format("%d", calendar.get(Calendar.MONTH) + 1));
        deadlineDay.setText(String.format("%d", calendar.get(Calendar.DAY_OF_MONTH)));

        Collection<MoneyNode> moneyNodes = MoneyData.getMoneyData().getDataCollection();
        String[] moneys = new String[moneyNodes.size()];
        int pos = 0;
        for (MoneyNode now : moneyNodes) {
            moneys[pos++] = now.getName();
        }
        ArrayAdapter<String> moneyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, moneys);
        moneyType.setAdapter(moneyAdapter);

        Button action = (Button) findViewById(R.id.addLoan);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(deadlineYear.getText().toString()),
                            Integer.parseInt(deadlineMonth.getText().toString()) - 1,
                            Integer.parseInt(deadlineDay.getText().toString()));
                    controlBind.getProtocolSender().createLoan(creditor.getText().toString(),
                            moneyType.getSelectedItem().toString(),
                            value.getText().toString(), calendar.getTimeInMillis() + "", in.isChecked() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }

}
