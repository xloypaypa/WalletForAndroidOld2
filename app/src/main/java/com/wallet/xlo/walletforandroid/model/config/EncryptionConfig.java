package com.wallet.xlo.walletforandroid.model.config;

import java.security.KeyPair;

import model.tool.RSA;

/**
 * Created by xlo on 16-1-5.
 * it's the encryption config
 */
public class EncryptionConfig {

    private static volatile EncryptionConfig encryptionConfig;

    private EncryptionConfig() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EncryptionConfig getEncryptionConfig() {
        if (encryptionConfig == null) {
            synchronized (EncryptionConfig.class) {
                if (encryptionConfig == null) {
                    encryptionConfig = new EncryptionConfig();
                }
            }
        }
        return encryptionConfig;
    }

    protected boolean encryption;
    protected KeyPair keyPair;

    public void init() throws Exception {
        RSA.keySize = 1024;
        this.keyPair = RSA.buildKeyPair();
    }

    public boolean isEncryption() {
        return encryption;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

}
