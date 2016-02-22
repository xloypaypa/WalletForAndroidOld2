package com.wallet.xlo.walletforandroid.model.data.node;

import java.util.Date;

/**
 * Created by xlo on 16/2/22.
 * it's the loan node
 */
public class LoanNode {

    private String id, creditor, isIn;
    private double value;
    private Date deadline;

    public LoanNode(String id, String creditor, double value, Date deadline, String isIn) {
        this.id = id;
        this.creditor = creditor;
        this.isIn = isIn;
        this.value = value;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public String getCreditor() {
        return creditor;
    }

    public String getIsIn() {
        return isIn;
    }

    public double getValue() {
        return value;
    }

    public Date getDeadline() {
        return deadline;
    }
}
