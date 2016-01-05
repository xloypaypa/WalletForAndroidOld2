package control;

import com.wallet.xlo.walletforandroid.control.ControlService;

import net.ProtocolSendAble;

/**
 * Created by xlo on 2015/11/4.
 * it's user logic
 */
public class UserLogic extends ProtocolSendAble {

    public UserLogic(ControlService controlService) {
        super(controlService);
    }

    public void login(String result) {
        //TODO
//        if (result.equals("ok")) {
//            JOptionPane.showMessageDialog(null, "login successfully", "info", JOptionPane.INFORMATION_MESSAGE);
//            Main.startFrame(Money.class);
//        } else {
//            JOptionPane.showMessageDialog(null, "login failed", "info", JOptionPane.ERROR_MESSAGE);
//        }
//        Main.unLockNowPage();
    }

    public void register(String result) {
//        if (result.equals("ok")) {
//            JOptionPane.showMessageDialog(null, "register successfully", "info", JOptionPane.INFORMATION_MESSAGE);
//            Main.startFrame(Login.class);
//        } else {
//            JOptionPane.showMessageDialog(null, "register failed", "info", JOptionPane.ERROR_MESSAGE);
//        }
//        Main.unLockNowPage();
    }
}
