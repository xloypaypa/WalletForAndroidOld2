package com.wallet.xlo.walletforandroid.network;

import com.wallet.xlo.walletforandroid.control.Client;
import com.wallet.xlo.walletforandroid.model.tool.RSA;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xlo on 16-1-3.
 * it's the net work thread
 */
public class NetWorkWriteThread extends Thread implements SendAble, DisConnectAble {

    private OutputStream outputStream;
    private volatile Queue<byte[]> queue;
    private NetWorkService netWorkService;

    public NetWorkWriteThread(Socket socket, NetWorkService netWorkService) throws IOException {
        this.outputStream = socket.getOutputStream();
        this.queue = new LinkedBlockingQueue<>();
        this.netWorkService = netWorkService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                if (this.queue.isEmpty()) {
                    System.out.println("write error");
                    break;
                }
                try {
                    sendMessage();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    break;
                }
            }
        }
        this.netWorkService.disConnect();
    }

    private void sendMessage() throws IOException {
        synchronized (NetWorkWriteThread.class) {
            while (!this.queue.isEmpty()) {
                byte[] message = this.queue.poll();
                for (int i = 0; i < message.length; i++) {
                    byte now = message[i];
                    try {
                        this.outputStream.write(now);
                    } catch (SocketTimeoutException e) {
                        i--;
                    }
                }
            }
        }
    }

    @Override
    public void sendMessage(String command, byte[] message) {
        byte[] all = buildPackage(command, message);
        synchronized (NetWorkWriteThread.class) {
            queue.add(all);
            if (this.queue.size() == 1) {
                this.interrupt();
            }
        }
    }

    private byte[] buildPackage(String command, byte[] message) {
        command += "#";
        byte[] pg = new byte[command.getBytes().length + message.length];
        System.arraycopy(command.getBytes(), 0, pg, 0, command.getBytes().length);
        System.arraycopy(message, 0, pg, command.getBytes().length, message.length);
        if (Client.getClient().isEncryption()) {
            try {
                pg = RSA.encrypt(Client.getClient().getServerKey(), pg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int length = pg.length;
        String head = length + "x";
        byte[] all = new byte[head.getBytes().length + pg.length];
        System.arraycopy(head.getBytes(), 0, all, 0, head.getBytes().length);
        System.arraycopy(pg, 0, all, head.getBytes().length, pg.length);
        return all;
    }

    @Override
    public void disConnect() {
        this.interrupt();
    }
}
