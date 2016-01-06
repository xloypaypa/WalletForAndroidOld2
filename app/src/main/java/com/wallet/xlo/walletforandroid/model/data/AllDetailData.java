package com.wallet.xlo.walletforandroid.model.data;

import com.wallet.xlo.walletforandroid.model.data.node.DetailNode;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/12/30.
 * it's the all detail data
 */
public class AllDetailData extends WalletData<DetailNode> {

    private static AllDetailData allDetailData;

    public static AllDetailData getAllDetailData() {
        if (allDetailData == null) {
            synchronized (MoneyData.class) {
                if (allDetailData == null) {
                    allDetailData = new AllDetailData();
                }
            }
        }
        return allDetailData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        for (Map<String, String> now : data) {
            DetailNode detailNode = new DetailNode(now.get("event"), now.get("id"), new Date(Long.parseLong(now.get("date"))));
            this.dataNodes.put(detailNode.getId(), detailNode);
        }
    }
}
