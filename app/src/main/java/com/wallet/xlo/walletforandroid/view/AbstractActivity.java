package com.wallet.xlo.walletforandroid.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.dialog.DialogBuilder;

/**
 * Created by xlo on 16-1-6.
 * it's the abstract activity
 */
public abstract class AbstractActivity extends AppCompatActivity {

    protected volatile ControlService.ControlBind controlBind;

    public Handler dialogHandler;
    protected ServiceConnection conn;

    protected AbstractActivity() {
        dialogHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                DialogBuilder.showMessageDialog(msg.getData().getString("title"),
                        msg.getData().getString("message"), AbstractActivity.this);
            }
        };
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        controlBind.setNowPage(this);
    }

    protected void bindControlService() {
        Intent service = new Intent(this, ControlService.class);
        buildServiceConnection();
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    protected void buildServiceConnection() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                controlBind = (ControlService.ControlBind) service;
                controlBind.setNowPage(AbstractActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    protected void unbindControlService() {
        unbindService(conn);
    }

}
