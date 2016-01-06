package com.wallet.xlo.walletforandroid.control;

import android.os.Bundle;
import android.os.Message;

import com.wallet.xlo.walletforandroid.control.ControlService;

import com.wallet.xlo.walletforandroid.net.ProtocolSender;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import com.wallet.xlo.walletforandroid.model.data.EdgeData;

/**
 * Created by xlo on 15-11-8.
 * it's the edge logic
 */
public class EdgeLogic extends ProtocolSender {

    public EdgeLogic(ControlService controlService) {
        super(controlService);
    }

    public void getEdgeList(List<Map<String, String>> data) {
        EdgeData.getEdgeData().updateData(data);
    }

    public void updateEdge(String result) throws JSONException {
        if (result.equals("ok")) {
            getEdge();
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
