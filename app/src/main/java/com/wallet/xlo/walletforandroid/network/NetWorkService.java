package com.wallet.xlo.walletforandroid.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class NetWorkService extends Service implements DisConnectAble {
    private volatile NetWorkReadThread netWorkReadThread;
    private volatile NetWorkWriteThread netWorkWriteThread;
    private volatile Set<WhenDisconnectAction> whenDisconnectActions;
    private volatile boolean isConnect;
    private volatile Socket socket;

    public NetWorkService() {
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
            for (WhenDisconnectAction now : whenDisconnectActions) {
                now.action();
            }
        }
    }

    public class NetWorkBinder extends Binder implements SendAble, GetAble {

        public void init() {
            isConnect = false;
            netWorkWriteThread = null;
            netWorkReadThread = null;
            socket = null;
            whenDisconnectActions = new HashSet<>();
            whenDisconnectActions.add(new WhenDisconnectAction() {
                @Override
                public void action() {
                    netWorkReadThread.disConnect();
                }
            });
            whenDisconnectActions.add(new WhenDisconnectAction() {
                @Override
                public void action() {
                    netWorkWriteThread.disConnect();
                }
            });
        }

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

        public void whenDisconnect(WhenDisconnectAction whenDisconnectAction) {
            whenDisconnectActions.add(whenDisconnectAction);
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

    public interface WhenDisconnectAction {
        void action();
    }
}
