package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.control.ControlService;

import com.wallet.xlo.walletforandroid.net.ProtocolSender;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import com.wallet.xlo.walletforandroid.model.data.MoneyData;

/**
 * Created by xlo on 2015/11/5.
 * it's the money logic
 */
public class MoneyLogic extends ProtocolSender {

    public MoneyLogic(ControlService controlService) {
        super(controlService);
    }

    public void getMoney(List<Map<String, String>> data) {
        MoneyData.getMoneyData().updateData(data);
    }

    public void updateMoney(String result) throws JSONException {
        if (result.equals("ok")) {
            getMoney();
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
