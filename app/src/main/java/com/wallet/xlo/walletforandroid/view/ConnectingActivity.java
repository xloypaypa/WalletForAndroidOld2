package com.wallet.xlo.walletforandroid.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.config.EncryptionConfig;

public class ConnectingActivity extends AbstractActivity {

    private Button connect;
    private EditText ip, port;
    private CheckBox encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);

        connect = (Button) this.findViewById(R.id.connect);
        ip = (EditText) this.findViewById(R.id.connectIp);
        port = (EditText) this.findViewById(R.id.connectPort);
        encrypt = (CheckBox) this.findViewById(R.id.connectIsEncrypt);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ip.setText(sharedPreferences.getString("ip", ""));
        port.setText(sharedPreferences.getInt("port", 8080) + "");
        encrypt.setChecked(sharedPreferences.getBoolean("encrypt", false));

        connect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("ip", ip.getText().toString()).commit();
                sharedPreferences.edit().putInt("port", Integer.parseInt(port.getText().toString())).commit();
                sharedPreferences.edit().putBoolean("encrypt", encrypt.isChecked()).commit();
                EncryptionConfig.getEncryptionConfig().setEncryption(encrypt.isChecked());
                controlBind.startServer(ip.getText().toString(), Integer.parseInt(port.getText().toString()));
            }
        });
    }
}
