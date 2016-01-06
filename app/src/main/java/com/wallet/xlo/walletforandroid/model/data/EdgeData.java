package com.wallet.xlo.walletforandroid.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wallet.xlo.walletforandroid.model.data.node.EdgeNode;

/**
 * Created by xlo on 2015/12/11.
 * it's the edge data
 */
public class EdgeData extends WalletData<EdgeNode> {

    private static EdgeData edgeData;

    public static EdgeData getEdgeData() {
        if (edgeData == null) {
            synchronized (MoneyData.class) {
                if (edgeData == null) {
                    edgeData = new EdgeData();
                }
            }
        }
        return edgeData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        for (Map<String, String> now : data) {
            EdgeNode edgeNode = new EdgeNode(now.get("from"), now.get("to"), now.get("script"));
            this.dataNodes.put(edgeNode.getFrom() + "-" + edgeNode.getTo(), edgeNode);
        }
    }

    public List<EdgeNode> getEdgeFromWith(String from) {
        List<EdgeNode> result = new ArrayList<>();
        for (EdgeNode now : this.dataNodes.values()) {
            if (now.getFrom().equalsIgnoreCase(from)) {
                result.add(now);
            }
        }
        return result;
    }

    public List<EdgeNode> getEdgeToWith(String to) {
        List<EdgeNode> result = new ArrayList<>();
        for (EdgeNode now : this.dataNodes.values()) {
            if (now.getTo().equalsIgnoreCase(to)) {
                result.add(now);
            }
        }
        return result;
    }

}
