package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.control.ControlService;

import com.wallet.xlo.walletforandroid.net.ProtocolSender;
import com.wallet.xlo.walletforandroid.view.MainActivity;

/**
 * Created by xlo on 2015/11/4.
 * it's user logic
 */
public class UserLogic extends ProtocolSender {

    public UserLogic(ControlService controlService) {
        super(controlService);
    }

    public void login(String result) {
        if (result.equals("ok")) {
            controlService.startActivity(MainActivity.class);
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
