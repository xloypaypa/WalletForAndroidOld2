package model.data;

import model.data.node.MoneyNode;

import java.util.*;

/**
 * Created by xlo on 2015/12/11.
 * it's the money data
 */
public class MoneyData extends WalletData<MoneyNode> {

    private static MoneyData moneyData;

    public static MoneyData getMoneyData() {
        if (moneyData == null) {
            synchronized (MoneyData.class) {
                if (moneyData == null) {
                    moneyData = new MoneyData();
                }
            }
        }
        return moneyData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        for (Map<String, String> now : data) {
            MoneyNode moneyNode = new MoneyNode(now.get("typename"), Double.valueOf(now.get("value")));
            this.dataNodes.put(moneyNode.getName(), moneyNode);
        }
    }

}
