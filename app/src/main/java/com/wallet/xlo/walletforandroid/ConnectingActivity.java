package com.wallet.xlo.walletforandroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.model.config.EncryptionConfig;
import com.wallet.xlo.walletforandroid.network.NetWorkService;

public class ConnectingActivity extends AppCompatActivity {

    private ControlService.ControlBind controlBind;

    private Button connect;
    private EditText ip, port;
    private CheckBox encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);

        Intent service = new Intent(this, ControlService.class);
        bindService(service, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                controlBind = (ControlService.ControlBind) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);

        connect = (Button) this.findViewById(R.id.connect);
        ip = (EditText) this.findViewById(R.id.connectIp);
        port = (EditText) this.findViewById(R.id.connectPort);
        encrypt = (CheckBox) this.findViewById(R.id.connectIsEncrypt);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EncryptionConfig.getEncryptionConfig().setEncryption(encrypt.isChecked());
                controlBind.startServer(ip.getText().toString(), Integer.parseInt(port.getText().toString()));
            }
        });
    }
}
