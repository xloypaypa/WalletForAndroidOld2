package com.wallet.xlo.walletforandroid.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class NetWorkService extends Service {
    private NetWorkReadThread netWorkReadThread;
    private NetWorkWriteThread netWorkWriteThread;

    public NetWorkService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.1.123", 9090);
                    netWorkWriteThread = new NetWorkWriteThread(socket);
                    netWorkReadThread = new NetWorkReadThread(socket);
                    netWorkWriteThread.start();
                    netWorkReadThread.start();
                    netWorkWriteThread.addMessage("/session", "{}".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("net work", "bind");
        return null;
    }
}
