package com.wallet.xlo.walletforandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wallet.xlo.walletforandroid.model.config.EncryptionConfig;

public class ConnectingActivity extends AbstractActivity {

    private Button connect;
    private EditText ip, port;
    private CheckBox encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);
        bindControlService();

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
