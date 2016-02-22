package com.wallet.xlo.walletforandroid.view;

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

import org.json.JSONException;

import java.util.Collection;

/**
 * Created by xlo on 16/2/22.
 * it's the remove loan activity
 */
public class RemoveLoanActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_loan);

        final Spinner id = (Spinner) findViewById(R.id.loanId);

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

        Button action = (Button) findViewById(R.id.removeLoan);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().removeLoan(id.getSelectedItem().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
