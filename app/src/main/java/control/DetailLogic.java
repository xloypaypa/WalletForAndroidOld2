package control;

import com.wallet.xlo.walletforandroid.control.ControlService;

import model.data.AllDetailData;
import model.data.MessageData;
import model.data.MoneyDetailData;
import net.ProtocolSender;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/12/21.
 * it's the detail logic
 */
public class DetailLogic extends ProtocolSender {

    public DetailLogic(ControlService controlService) {
        super(controlService);
    }

    public void getMoneyDetail(List<Map<String, String>> data) {
        MoneyDetailData.getMoneyDetailData().updateData(data);
    }

    public void getAllDetail(List<Map<String, String>> data) {
        AllDetailData.getAllDetailData().updateData(data);
    }

    public void getDetailDetail(String message) {
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("detail detail", message);
        list.add(map);
        MessageData.getMessageData().updateData(list);
    }

    public void rollBack() throws JSONException {
        getMoney();
        getBudget();
        getEdge();
        getMoneyDetail();
        getAllDetail();
    }

}
