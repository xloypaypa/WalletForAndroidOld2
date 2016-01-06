package control;

import com.wallet.xlo.walletforandroid.control.ControlService;

import net.ProtocolSender;

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
//            JOptionPane.showMessageDialog(null, "server refuse", "info", JOptionPane.ERROR_MESSAGE);
        }
        //TODO
//        Main.unLockNowPage();
    }
}
