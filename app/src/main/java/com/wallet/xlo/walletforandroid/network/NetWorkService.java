package com.wallet.xlo.walletforandroid.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class NetWorkService extends Service implements DisConnectAble {
    private NetWorkReadThread netWorkReadThread;
    private NetWorkWriteThread netWorkWriteThread;
    private Set<WhenDisConnectAction> whenDisConnectActions;
    private volatile boolean isConnect;
    private Socket socket;

    public NetWorkService() {
        this.whenDisConnectActions = new HashSet<>();
        this.whenDisConnectActions.add(new WhenDisConnectAction() {
            @Override
            public void action() {
                netWorkReadThread.disConnect();
            }
        });
        this.whenDisConnectActions.add(new WhenDisConnectAction() {
            @Override
            public void action() {
                netWorkWriteThread.disConnect();
            }
        });
        isConnect = false;
    }

    public void startConnect(final String ip, final int port) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    isConnect = true;
                    netWorkWriteThread = new NetWorkWriteThread(socket, NetWorkService.this);
                    netWorkReadThread = new NetWorkReadThread(socket, NetWorkService.this);
                    netWorkWriteThread.start();
                    netWorkReadThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnect() {
        return isConnect;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new NetWorkBinder();
    }

    @Override
    public synchronized void disConnect() {
        if (isConnect()) {
            isConnect = false;
            for (WhenDisConnectAction now : whenDisConnectActions) {
                now.action();
            }
        }
    }

    public class NetWorkBinder extends Binder implements SendAble, GetAble {

        public Socket getSocket() {
            return socket;
        }

        public boolean isConnect() {
            return isConnect;
        }

        public void startConnect(String ip, int port) {
            NetWorkService.this.startConnect(ip, port);
        }

        public void stopNetWork() {
            NetWorkService.this.stopSelf();
        }

        public void whenConnect(WhenDisConnectAction whenDisConnectAction) {
            whenDisConnectActions.add(whenDisConnectAction);
        }

        @Override
        public void setGetCallBack(CallBack callBack) {
            netWorkReadThread.setGetCallBack(callBack);
        }

        @Override
        public void sendMessage(String command, byte[] message) {
            netWorkWriteThread.sendMessage(command, message);
        }

    }

    public interface WhenDisConnectAction {
        void action();
    }
}
