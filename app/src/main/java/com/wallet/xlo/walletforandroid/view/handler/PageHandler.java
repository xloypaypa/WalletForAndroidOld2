package com.wallet.xlo.walletforandroid.view.handler;

import android.app.Activity;
import android.os.Handler;
import android.widget.TableLayout;

/**
 * Created by xlo on 16/2/22.
 * it's the page handler
 */
public abstract class PageHandler extends Handler {

    protected Activity activity;
    protected TableLayout tableLayout;

    public PageHandler(Activity activity, TableLayout tableLayout) {
        super();
        this.activity = activity;
        this.tableLayout = tableLayout;
    }

}
