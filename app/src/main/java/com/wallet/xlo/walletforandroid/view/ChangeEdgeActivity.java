package com.wallet.xlo.walletforandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;

import org.json.JSONException;

public class ChangeEdgeActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_edge);

        Intent intent = getIntent();

        final String fromString = intent.getStringExtra("from");
        final String toString = intent.getStringExtra("to");
        final String scriptString = intent.getStringExtra("script");

        EditText from = (EditText) findViewById(R.id.changeEdgeFrom);
        EditText to = (EditText) findViewById(R.id.changeEdgeTo);
        final EditText script = (EditText) findViewById(R.id.changeEdgeScript);

        from.setText(fromString);
        to.setText(toString);
        script.setText(scriptString);

        Button changeEdge = (Button) findViewById(R.id.changeEdgeAction);
        changeEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().updateEdge(fromString, toString,
                            script.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });

        Button removeEdge = (Button) findViewById(R.id.removeEdgeAction);
        removeEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().removeEdge(fromString, toString);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        });
    }
}
