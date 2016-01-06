package control;

import com.wallet.xlo.walletforandroid.control.ControlService;

import net.ProtocolSender;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import model.data.BudgetData;

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
            //TODO
//            JOptionPane.showMessageDialog(null, "server refuse", "info", JOptionPane.ERROR_MESSAGE);
//            Main.unLockNowPage();
        }
    }
}
