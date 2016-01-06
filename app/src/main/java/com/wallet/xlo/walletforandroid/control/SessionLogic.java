package com.wallet.xlo.walletforandroid.control;

import com.wallet.xlo.walletforandroid.view.LoginActivity;
import com.wallet.xlo.walletforandroid.control.Client;
import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.model.config.EncryptionConfig;

import com.wallet.xlo.walletforandroid.net.ProtocolSender;

import com.wallet.xlo.walletforandroid.model.tool.RSA;

/**
 * Created by xlo on 2015/11/4.
 * it's the session logic
 */
public class SessionLogic extends ProtocolSender {

    public SessionLogic(ControlService controlService) {
        super(controlService);
    }

    public void getSessionID(String id) {
        Client.getClient().setSessionID(id);
        this.controlService.startActivity(LoginActivity.class);
    }

    public void key(byte[] data) {
        Client.getClient().setServerKey(RSA.bytes2PublicKey(data));
        getSessionID();
    }

    public void clientKey() {
        this.controlService.sendMessage("/key",
                RSA.publicKey2Bytes(EncryptionConfig.getEncryptionConfig().getKeyPair().getPublic()));
        Client.getClient().setEncryption(true);
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
