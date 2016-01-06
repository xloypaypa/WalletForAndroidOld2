package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.control.ControlService;

import com.wallet.xlo.walletforandroid.net.ProtocolSender;

/**
 * Created by xlo on 2015/11/4.
 * it's user logic
 */
public class UserLogic extends ProtocolSender {

    public UserLogic(ControlService controlService) {
        super(controlService);
    }

    public void login(String result) {
        //TODO
        if (result.equals("ok")) {
            System.out.println("ok");
//            JOptionPane.showMessageDialog(null, "login successfully", "info", JOptionPane.INFORMATION_MESSAGE);
//            Main.startFrame(Money.class);
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
