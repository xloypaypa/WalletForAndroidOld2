package control;

import com.wallet.xlo.walletforandroid.control.ControlService;

import net.ProtocolSendAble;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import model.data.EdgeData;

/**
 * Created by xlo on 15-11-8.
 * it's the edge logic
 */
public class EdgeLogic extends ProtocolSendAble {

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
            //TODO
//            JOptionPane.showMessageDialog(null, "server refuse", "info", JOptionPane.ERROR_MESSAGE);
//            Main.unLockNowPage();
        }
    }
}
