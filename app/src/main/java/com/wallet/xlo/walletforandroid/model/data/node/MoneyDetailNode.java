package com.wallet.xlo.walletforandroid.model.data.node;

import java.util.Date;

/**
 * Created by xlo on 2015/12/21.
 * it's the detail node
 */
public class MoneyDetailNode {

    protected String typename;
    protected double value;
    protected Date date;

    public MoneyDetailNode(String typename, double value, Date date) {
        this.typename = typename;
        this.value = value;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public String getTypename() {
        return typename;
    }
}
