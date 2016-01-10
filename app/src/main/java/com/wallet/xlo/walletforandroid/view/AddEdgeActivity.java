package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.data.BudgetData;
import com.wallet.xlo.walletforandroid.model.data.node.BudgetNode;

import org.json.JSONException;

import java.util.Collection;

public class AddEdgeActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edge);

        final Spinner from = (Spinner) findViewById(R.id.addEdgeFrom);
        final Spinner to = (Spinner) findViewById(R.id.addEdgeTo);
        final EditText script = (EditText) findViewById(R.id.addEdgeScript);

        script.setText("(defn f[x] x) f");

        Collection<BudgetNode> budgetNodes = BudgetData.getBudgetData().getDataCollection();
        String[] budgets = new String[budgetNodes.size()];
        int pos = 0;
        for (BudgetNode now : budgetNodes) {
            budgets[pos++] = now.getName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, budgets);
        from.setAdapter(arrayAdapter);
        to.setAdapter(arrayAdapter);

        Button button = (Button) findViewById(R.id.addEdgeAction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().addEdge(from.getSelectedItem().toString(),
                            to.getSelectedItem().toString(), script.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
