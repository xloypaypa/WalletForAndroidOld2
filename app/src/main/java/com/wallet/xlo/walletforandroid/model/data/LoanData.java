package com.wallet.xlo.walletforandroid.model.data;

import com.wallet.xlo.walletforandroid.model.data.node.LoanNode;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 16/2/22.
 * it's the loan data
 */
public class LoanData extends WalletData<LoanNode> {

    private static LoanData loanData;

    public static LoanData getLoanData() {
        if (loanData == null) {
            synchronized (MoneyData.class) {
                if (loanData == null) {
                    loanData = new LoanData();
                }
            }
        }
        return loanData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        for (Map<String, String> now : data) {
            LoanNode loanEntity = new LoanNode(now.get("id"), now.get("creditor"),
                    Double.parseDouble(now.get("value")),
                    new Date(Long.parseLong(now.get("deadline"))),
                    "true".equals(now.get("isIn")) ? "in" : "out");
            this.dataNodes.put(loanEntity.getId(), loanEntity);
        }
    }
}
