package com.wallet.xlo.walletforandroid.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by xlo on 16-1-6.
 * it's the dialog builder
 */
public class DialogBuilder {

    public static void showMessageDialog(String title, String message, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
