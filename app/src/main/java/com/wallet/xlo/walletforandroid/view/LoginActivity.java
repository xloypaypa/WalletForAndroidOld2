package com.wallet.xlo.walletforandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.control.Client;

import org.json.JSONException;

import com.wallet.xlo.walletforandroid.model.tool.PasswordEncoder;

public class LoginActivity extends AbstractActivity {

    private EditText username, password;
    private Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindControlService();

        username = (EditText) this.findViewById(R.id.loginUsername);
        password = (EditText) this.findViewById(R.id.loginPassword);
        register = (Button) this.findViewById(R.id.loginRegister);
        login = (Button) this.findViewById(R.id.loginLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controlBind.getProtocolSender().login(username.getText().toString(),
                            PasswordEncoder.encode(password.getText().toString()
                                    + Client.getClient().getSessionID()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBind.startActivity(RegisterActivity.class, false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindControlService();
    }
}
