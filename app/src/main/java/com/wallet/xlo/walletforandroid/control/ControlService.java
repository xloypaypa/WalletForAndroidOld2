package com.wallet.xlo.walletforandroid.control;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import com.wallet.xlo.walletforandroid.view.AbstractActivity;
import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.model.config.ProtocolConfig;
import com.wallet.xlo.walletforandroid.network.GetAble;
import com.wallet.xlo.walletforandroid.network.NetWorkService;
import com.wallet.xlo.walletforandroid.network.SendAble;

import com.wallet.xlo.walletforandroid.net.ProtocolSender;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ControlService extends Service implements SendAble {

    private NetWorkService.NetWorkBinder netWorkBinder;
    private ProtocolSender protocolSender;
    private AbstractActivity activity;

    public ControlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            ProtocolConfig.getProtocolConfig()
                    .loadConfig(ControlService.this.getResources().getXml(R.xml.protocol));
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        this.protocolSender = new ProtocolSender(this) {
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ControlBind();
    }

    @Override
    public void sendMessage(String command, byte[] message) {
        netWorkBinder.sendMessage(command, message);
    }

    public void startActivity(Class<? extends Activity> aClass) {
        startActivity(aClass, true);
    }

    public void startActivity(Class<? extends Activity> aClass, boolean isFinish) {
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
        if (isFinish) {
            finishNowActivity();
        }
    }

    public void finishNowActivity() {
        activity.finish();
    }

    public AbstractActivity getActivity() {
        return activity;
    }

    public class ControlBind extends Binder {

        public void startServer(final String ip, final int port) {
            Intent service = new Intent(ControlService.this, NetWorkService.class);
            bindService(service, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    netWorkBinder = (NetWorkService.NetWorkBinder) service;
                    netWorkBinder.startConnect(ip, port);
                    startListenNet();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, BIND_AUTO_CREATE);
        }

        public void setNowPage(AbstractActivity activity) {
            System.out.println("change");
            ControlService.this.activity = activity;
        }

        public void startActivity(Class<? extends Activity> aClass) {
            ControlService.this.startActivity(aClass);
        }

        public void startActivity(Class<? extends Activity> aClass, boolean isFinish) {
            ControlService.this.startActivity(aClass, isFinish);
        }

        public void finishNowActivity() {
            ControlService.this.finishNowActivity();
        }

        public AbstractActivity getActivity() {
            return ControlService.this.getActivity();
        }

        public ProtocolSender getProtocolSender() {
            return protocolSender;
        }

        private void startListenNet() {
            netWorkBinder.whenDisconnect(new NetWorkService.WhenDisconnectAction() {
                @Override
                public void action() {
                    System.out.println("disconnect");
                }
            });
            netWorkBinder.setGetCallBack(new GetAble.CallBack() {
                @Override
                public void action(byte[] message) {
                    solveMessage(message);
                }
            });
            new SessionLogic(ControlService.this).initSession();
        }

        private void solveMessage(byte[] body) {
            System.out.println(new String(body));
            byte[] url, message;
            for (int i = 0; i < body.length; i++) {
                if (body[i] == '#') {
                    url = new byte[i];
                    System.arraycopy(body, 0, url, 0, i);
                    message = new byte[body.length - i - 1];
                    System.arraycopy(body, i + 1, message, 0, body.length - i - 1);
                    try {
                        NetMessageSolver.getNetMessageSolver()
                                .sendEvent(new String(url), message, ControlService.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

    }

}
