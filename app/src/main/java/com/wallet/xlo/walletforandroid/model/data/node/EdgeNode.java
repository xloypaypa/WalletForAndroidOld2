package com.wallet.xlo.walletforandroid.model.data.node;

/**
 * Created by xlo on 2015/12/11.
 * it's the edge node
 */
public class EdgeNode {

    private String from, to, script;

    public EdgeNode(String from, String to, String script) {
        this.from = from;
        this.to = to;
        this.script = script;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getScript() {
        return script;
    }
}
