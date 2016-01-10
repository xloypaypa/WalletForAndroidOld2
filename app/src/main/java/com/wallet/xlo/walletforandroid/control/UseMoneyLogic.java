package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.network.ProtocolSender;

import org.json.JSONException;

/**
 * Created by xlo on 15/12/12.
 * it's the use money logic
 */
public class UseMoneyLogic extends ProtocolSender {


    public UseMoneyLogic(ControlService controlService) {
        super(controlService);
    }

    public void update(String result) throws JSONException {
        if (result.equals("ok")) {
            getMoney();
            getBudget();
            getMoneyDetail();
            getAllDetail();
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
