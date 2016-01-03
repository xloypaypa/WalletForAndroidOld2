package com.wallet.xlo.walletforandroid.control;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.wallet.xlo.walletforandroid.network.GetAble;
import com.wallet.xlo.walletforandroid.network.NetWorkService;
import com.wallet.xlo.walletforandroid.network.SendAble;

public class ControlService extends Service implements SendAble {

    private NetWorkService.NetWorkBinder netWorkBinder;

    public ControlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bindService(new Intent(this, NetWorkService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                netWorkBinder = (NetWorkService.NetWorkBinder) service;
                startListenNet();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }

    private void startListenNet() {
        netWorkBinder.whenConnect(new NetWorkService.WhenDisConnectAction() {
            @Override
            public void action() {
                System.out.println("disconnect");
            }
        });
        netWorkBinder.setGetCallBack(new GetAble.CallBack() {
            @Override
            public void action(byte[] message) {
                System.out.println(new String(message));
            }
        });
        sendMessage("/session", "{}".getBytes());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void sendMessage(String command, byte[] message) {
        netWorkBinder.sendMessage(command, message);
    }

}
