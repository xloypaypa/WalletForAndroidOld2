package com.wallet.xlo.walletforandroid.model.data;

import com.wallet.xlo.walletforandroid.model.data.node.BudgetNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/12/11.
 * it's the budget data
 */
public class BudgetData extends WalletData<BudgetNode> {

    private static BudgetData budgetData;

    public static BudgetData getBudgetData() {
        if (budgetData == null) {
            synchronized (MoneyData.class) {
                if (budgetData == null) {
                    budgetData = new BudgetData();
                }
            }
        }
        return budgetData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        for (Map<String, String> now : data) {
            BudgetNode budgetNode = new BudgetNode(now.get("typename"), Double.valueOf(now.get("income")),
                    Double.valueOf(now.get("expenditure")), Double.valueOf(now.get("now income")),
                    Double.valueOf(now.get("now expenditure")));
            this.dataNodes.put(budgetNode.getName(), budgetNode);
        }
    }
}
