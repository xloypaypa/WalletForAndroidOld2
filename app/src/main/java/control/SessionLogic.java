package control;

import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.model.config.EncryptionConfig;

import net.ProtocolSendAble;

/**
 * Created by xlo on 2015/11/4.
 * it's the session logic
 */
public class SessionLogic extends ProtocolSendAble {

    public SessionLogic(ControlService controlService) {
        super(controlService);
    }

    public void getSessionID(String id) {
        //TODO
//        Client.getClient().setSessionID(id);
//        Main.startFrame(Login.class);
    }

    public void key(byte[] data) {
//        Client.getClient().setServerKey(RSA.bytes2PublicKey(data));
        getSessionID();
    }

    public void clientKey() {
//        this.packageServer.addMessage("/key", RSA.publicKey2Bytes(EncryptionConfig.getConfig().getKeyPair().getPublic()));
//        Client.getClient().setEncryption(true);
    }

    public void getSessionID() {
        this.controlService.sendMessage("/session", "{}".getBytes());
    }

    public void initSession() {
        if (EncryptionConfig.getEncryptionConfig().isEncryption()) {
            clientKey();
        } else {
            getSessionID();
        }

    }
}
