package model.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 16-1-1.
 * it's the message data
 */
public class MessageData extends WalletData<String> {

    private static MessageData messageData;

    public static MessageData getMessageData() {
        if (messageData == null) {
            synchronized (MoneyData.class) {
                if (messageData == null) {
                    messageData = new MessageData();
                }
            }
        }
        return messageData;
    }

    @Override
    protected void solveData(List<Map<String, String>> data) {
        this.dataNodes = new HashMap<>();
        for (Map<String, String> now : data) {
            this.dataNodes.putAll(now);
        }
    }
}
