package com.wallet.xlo.walletforandroid.control;

import java.security.PublicKey;

/**
 * Created by xlo on 16-1-5.
 * it's the client
 */
public class Client {

    protected boolean isEncryption;

    protected PublicKey serverKey;
    protected String sessionID;

    private static Client client;

    public static Client getClient() {
        if (client == null) {
            synchronized (Client.class) {
                if (client == null) {
                    client = new Client();
                }
            }
        }
        return client;
    }

    public void setEncryption(boolean encryption) {
        isEncryption = encryption;
    }

    public boolean isEncryption() {
        return isEncryption;
    }

    public void setServerKey(PublicKey serverKey) {
        this.serverKey = serverKey;
    }

    public PublicKey getServerKey() {
        return serverKey;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionID() {
        return sessionID;
    }

}
