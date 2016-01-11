package com.wallet.xlo.walletforandroid.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.dialog.DialogBuilder;
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

    @Override
    protected void onResume() {
        super.onResume();
        boolean flag = getIntent().getBooleanExtra("flag", false);
        if (flag) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("can't connect server");
            builder.setTitle("error");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.create().show();
        }
    }
}
