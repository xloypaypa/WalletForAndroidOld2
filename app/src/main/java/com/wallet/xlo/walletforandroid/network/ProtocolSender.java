package com.wallet.xlo.walletforandroid.network;

import com.wallet.xlo.walletforandroid.control.ControlService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xlo on 2015/12/21.
 * it's the protocol sender
 */
public abstract class ProtocolSender {

    protected ControlService controlService;

    public ProtocolSender(ControlService controlService) {
        this.controlService = controlService;
    }

    public void login(String username, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        controlService.sendMessage("/login", jsonObject.toString().getBytes());
    }

    public void register(String username, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        controlService.sendMessage("/register", jsonObject.toString().getBytes());
    }

    public void getMoney() {
        controlService.sendMessage("/getMoney", "{}".getBytes());
    }

    public void getBudget() {
        controlService.sendMessage("/getBudget", "{}".getBytes());
    }

    public void getEdge() {
        controlService.sendMessage("/getEdgeList", "{}".getBytes());
    }

    public void createMoney(String typename, String value) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typename", typename);
        jsonObject.put("value", value);
        controlService.sendMessage("/createMoney", jsonObject.toString().getBytes());
    }

    public void createBudget(String typename, String income, String expenditure) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typename", typename);
        jsonObject.put("income", income);
        jsonObject.put("expenditure", expenditure);
        controlService.sendMessage("/createBudget", jsonObject.toString().getBytes());
    }

    public void addEdge(String from, String to, String script) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromType", from);
        jsonObject.put("toType", to);
        jsonObject.put("script", script);
        controlService.sendMessage("/addEdge", jsonObject.toString().getBytes());
    }

    public void renameMoney(String name, String newName) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typename", name);
        jsonObject.put("newName", newName);
        controlService.sendMessage("/renameMoney", jsonObject.toString().getBytes());
    }

    public void removeMoney(String typename) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typename", typename);
        controlService.sendMessage("/removeMoney", jsonObject.toString().getBytes());
    }

    public void changeBudget(String typename, String newName, String income, String expenditure) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typename", typename);
        jsonObject.put("newName", newName);
        jsonObject.put("income", income);
        jsonObject.put("expenditure", expenditure);
        controlService.sendMessage("/changeBudget", jsonObject.toString().getBytes());
    }

    public void removeBudget(String typename) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typename", typename);
        controlService.sendMessage("/removeBudget", jsonObject.toString().getBytes());
    }

    public void updateEdge(String from, String to, String script) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromType", from);
        jsonObject.put("toType", to);
        jsonObject.put("script", script);
        controlService.sendMessage("/updateEdge", jsonObject.toString().getBytes());
    }

    public void removeEdge(String from, String to) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromType", from);
        jsonObject.put("toType", to);
        controlService.sendMessage("/removeEdge", jsonObject.toString().getBytes());
    }

    public void expenditure(String moneyType, String budgetName, String value) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("moneyName", moneyType);
        jsonObject.put("budgetName", budgetName);
        jsonObject.put("value", value);
        controlService.sendMessage("/expenditure", jsonObject.toString().getBytes());
    }

    public void income(String moneyType, String budgetType, String value) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("moneyName", moneyType);
        jsonObject.put("budgetName", budgetType);
        jsonObject.put("value", value);
        controlService.sendMessage("/income", jsonObject.toString().getBytes());
    }

    public void transferMoney(String from, String to, String value) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from", from);
        jsonObject.put("to", to);
        jsonObject.put("value", value);
        controlService.sendMessage("/transferMoney", jsonObject.toString().getBytes());
    }

    public void getMoneyDetail() throws JSONException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        getMoneyDetail(calendar.getTime(), new Date());
    }

    public void getMoneyDetail(Date from, Date to) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromTime", from.getTime());
        jsonObject.put("toTime", to.getTime());
        controlService.sendMessage("/getMoneyDetail", jsonObject.toString().getBytes());
    }

    public void getAllDetail() throws JSONException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        getAllDetail(calendar.getTime(), new Date(new Date().getTime() + 10000));
    }

    public void getAllDetail(Date from, Date to) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromTime", from.getTime());
        jsonObject.put("toTime", to.getTime());
        controlService.sendMessage("/getAllDetail", jsonObject.toString().getBytes());
    }

    public void getDetailDetail(String id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        controlService.sendMessage("/getDetailDetail", jsonObject.toString().getBytes());
    }

    public void rollback() {
        controlService.sendMessage("/rollBack", new JSONObject().toString().getBytes());
    }

    public void clear() {
        controlService.sendMessage("/clear", "{}".getBytes());
    }

}
