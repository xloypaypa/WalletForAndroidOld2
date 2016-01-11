package com.wallet.xlo.walletforandroid.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xlo on 16-1-3.
 * it's the network read thread
 */
public class NetWorkReadThread extends Thread implements GetAble, DisConnectAble {

    private Socket socket;
    private int status, len;
    private InputStream inputStream;
    private List<Byte> body, head;
    private NetWorkService netWorkService;
    private CallBack callBack;

    public NetWorkReadThread(Socket socket, NetWorkService netWorkService) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.head = new LinkedList<>();
        this.body = new LinkedList<>();
        this.status = 0;
        this.netWorkService = netWorkService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte now = (byte) this.inputStream.read();
                nextByte(now);
            } catch (SocketTimeoutException ignored) {

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        this.netWorkService.disConnect();
    }

    private void nextByte(byte now) throws IOException {
        if (this.status == 0) {
            if (now == 'x') {
                byte[] ans = new byte[this.head.size()];
                for (int i = 0; i < this.head.size(); i++) {
                    ans[i] = this.head.get(i);
                }
                this.len = Integer.parseInt(new String(ans));
                this.status = 1;
            } else {
                this.head.add(now);
                if (this.head.size() > 100) {
                    this.head.clear();
                    throw new IOException("package error");
                }
            }
        } else {
            this.body.add(now);
            if (this.body.size() == this.len) {
                byte[] ans = new byte[this.body.size()];
                for (int i = 0; i < this.body.size(); i++) {
                    ans[i] = this.body.get(i);
                }
                solvePackage(ans);

                this.head.clear();
                this.body.clear();
                this.status = 0;
            }
        }
    }

    private void solvePackage(byte[] message) {
        this.callBack.action(message);
    }

    @Override
    public void setGetCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void disConnect() {

    }
}
