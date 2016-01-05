package model.data;

import model.data.node.MoneyDetailNode;

import java.util.*;

/**
 * Created by xlo on 2015/12/21.
 * it's the detail data
 */
public class MoneyDetailData extends WalletData<MoneyDetailNode> {

    private static MoneyDetailData moneyDetailData;

    public static MoneyDetailData getMoneyDetailData() {
        if (moneyDetailData == null) {
            synchronized (MoneyData.class) {
                if (moneyDetailData == null) {
                    moneyDetailData = new MoneyDetailData();
                }
            }
        }
        return moneyDetailData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        int i = 0;
        for (Map<String, String> now : data) {
            i++;
            MoneyDetailNode moneyDetailNode
                    = new MoneyDetailNode(now.get("moneyType"),
                    Double.parseDouble(now.get("value")),
                    new Date(Long.parseLong(now.get("date"))));
            this.dataNodes.put(i + "", moneyDetailNode);
        }
    }

    public Collection<List<MoneyDetailNode>> getAllTypeDetail() {
        Map<String, List<MoneyDetailNode>> map = new HashMap<>();
        Collection<MoneyDetailNode> collection = this.getDataCollection();
        for (MoneyDetailNode now : collection) {
            if (!map.containsKey(now.getTypename())) {
                map.put(now.getTypename(), new LinkedList<MoneyDetailNode>());
            }
            map.get(now.getTypename()).add(now);
        }

        Collection<List<MoneyDetailNode>> ans = map.values();
        for (List<MoneyDetailNode> now : ans) {
            Collections.sort(now, new Comparator<MoneyDetailNode>() {
                @Override
                public int compare(MoneyDetailNode o1, MoneyDetailNode o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }

        return ans;
    }
}
