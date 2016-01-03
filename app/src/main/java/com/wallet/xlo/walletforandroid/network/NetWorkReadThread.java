package com.wallet.xlo.walletforandroid.network;

import android.util.Log;

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
public class NetWorkReadThread extends Thread {

    private int status, len;
    private InputStream inputStream;
    private List<Byte> body, head;

    public NetWorkReadThread(Socket socket) throws IOException {
        this.inputStream = socket.getInputStream();
        this.head = new LinkedList<>();
        this.body = new LinkedList<>();
        this.status = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte now = (byte) this.inputStream.read();
                if (now == -1) {
                    break;
                }
                nextByte(now);
            } catch (SocketTimeoutException ignored) {

            } catch (IOException e) {
                break;
            }
        }
        Log.e("net", "error");
    }

    private void nextByte(byte now) {
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
        Log.i("get", new String(message));
    }

}
