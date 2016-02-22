package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.model.data.LoanData;
import com.wallet.xlo.walletforandroid.network.ProtocolSender;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 16/2/22.
 * it's the loan logic
 */
public class LoanLogic extends ProtocolSender {
    public LoanLogic(ControlService controlService) {
        super(controlService);
    }

    public void getLoan(List<Map<String, String>> data) {
        LoanData.getLoanData().updateData(data);
    }

    public void updateLoan(String result) throws JSONException {
        if (result.equals("ok")) {
            getMoney();
            getLoan();
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
