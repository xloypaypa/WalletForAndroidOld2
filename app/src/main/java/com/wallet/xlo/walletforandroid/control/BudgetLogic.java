package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.model.data.BudgetData;
import com.wallet.xlo.walletforandroid.net.ProtocolSender;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/11/5.
 * it's the budget logic
 */
public class BudgetLogic extends ProtocolSender {

    public BudgetLogic(ControlService controlService) {
        super(controlService);
    }

    public void getBudget(List<Map<String, String>> data) {
        BudgetData.getBudgetData().updateData(data);
    }

    public void updateBudget(String result) throws JSONException {
        if (result.equals("ok")) {
            getBudget();
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
