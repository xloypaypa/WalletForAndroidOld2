package control;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.dialog.DialogBuilder;

import net.ProtocolSender;

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
            System.out.println("fail");
//            DialogBuilder.showMessageDialog("fail", "login fail", controlService.getActivity());
        }
    }

    public void register(String result) {
        if (result.equals("ok")) {
            controlService.finishNowActivity();
        } else {
            System.out.println("fail");
//            DialogBuilder.showMessageDialog("fail", "register fail", controlService.getActivity());
        }
    }
}
