package com.wallet.xlo.walletforandroid.network;

public interface GetAble {

    void setGetCallBack(CallBack callBack);

    interface CallBack {
        void action(byte[] message);
    }

}
