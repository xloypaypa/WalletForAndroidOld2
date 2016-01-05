package model.data;

import java.util.*;

/**
 * Created by xlo on 2015/12/11.
 * it's the wallet data
 */
public abstract class WalletData<T> {

    protected Set<DataUpdateAction> dataUpdateActions;
    protected Map<String, T> dataNodes;

    protected WalletData() {
        this.dataUpdateActions = new HashSet<>();
    }

    public void registerAction(DataUpdateAction dataUpdateAction) {
        this.dataUpdateActions.add(dataUpdateAction);
    }

    public void removeAction(DataUpdateAction dataUpdateAction) {
        this.dataUpdateActions.remove(dataUpdateAction);
    }

    public Collection<T> getDataCollection() {
        return this.dataNodes.values();
    }

    public T getData(String name) {
        return this.dataNodes.get(name);
    }

    public void updateData(List<Map<String, String>> data) {
        solveData(data);
        for (DataUpdateAction action : this.dataUpdateActions) {
            action.action();
        }
    }

    protected abstract void solveData(List<Map<String, String>> data);
}
