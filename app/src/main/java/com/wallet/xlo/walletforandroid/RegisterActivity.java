package com.wallet.xlo.walletforandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

public class RegisterActivity extends AbstractActivity {

    private EditText username, password;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindControlService();

        username = (EditText) this.findViewById(R.id.registerUsername);
        password = (EditText) this.findViewById(R.id.registerPassword);
        register = (Button) this.findViewById(R.id.registerRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().register(username.getText().toString(),
                            password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
