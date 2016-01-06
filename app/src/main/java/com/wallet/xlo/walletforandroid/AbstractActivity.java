package com.wallet.xlo.walletforandroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.wallet.xlo.walletforandroid.control.ControlService;

/**
 * Created by xlo on 16-1-6.
 * it's the abstract activity
 */
public abstract class AbstractActivity extends AppCompatActivity {

    protected ControlService.ControlBind controlBind;

    protected void bindControlService() {
        Intent service = new Intent(this, ControlService.class);
        bindService(service, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                controlBind = (ControlService.ControlBind) service;
                controlBind.setNowPage(AbstractActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }

}
