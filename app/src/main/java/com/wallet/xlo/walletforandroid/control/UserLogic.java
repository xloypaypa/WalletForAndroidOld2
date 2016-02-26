package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.network.ProtocolSender;
import com.wallet.xlo.walletforandroid.view.MainActivity;

import org.json.JSONException;

/**
 * Created by xlo on 2015/11/4.
 * it's user logic
 */
public class UserLogic extends ProtocolSender {

    public UserLogic(ControlService controlService) {
        super(controlService);
    }

    public void loginApp(String result) {
        if (result.equals("ok")) {
            controlService.startActivity(MainActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", "error");
            bundle.putString("message", "login app fail");
            Message message = new Message();
            message.setData(bundle);
            controlService.getActivity().dialogHandler.sendMessage(message);
        }
    }

    public void useApp(String result) {
        if (!result.equals("ok")) {
            Bundle bundle = new Bundle();
            bundle.putString("title", "error");
            bundle.putString("message", "user app fail");
            Message message = new Message();
            message.setData(bundle);
            controlService.getActivity().dialogHandler.sendMessage(message);
        }
    }

    public void login(String result) throws JSONException {
        if (result.equals("ok")) {
            this.useApp();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", "error");
            bundle.putString("message", "server refuse");
            Message message = new Message();
            message.setData(bundle);
            controlService.getActivity().dialogHandler.sendMessage(message);
        }
    }

    public void register(String result) {
        if (result.equals("ok")) {
            controlService.finishNowActivity();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", "error");
            bundle.putString("message", "server refuse");
            Message message = new Message();
            message.setData(bundle);
            controlService.getActivity().dialogHandler.sendMessage(message);
        }
    }
}
