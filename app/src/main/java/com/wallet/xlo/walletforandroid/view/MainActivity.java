package com.wallet.xlo.walletforandroid.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.model.data.BudgetData;
import com.wallet.xlo.walletforandroid.model.data.DataUpdateAction;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.BudgetNode;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AbstractActivity {

    private Handler moneyUpdateHandler, budgetUpdateHandler;
    private TableLayout moneyTable, budgetTable;

    private ViewPager viewPager;
    private View moneyPage, budgetPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view);
        List<View> listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        moneyPage = mInflater.inflate(R.layout.layout_money, null);
        budgetPage = mInflater.inflate(R.layout.layout_budget, null);
        listViews.add(moneyPage);
        listViews.add(budgetPage);
        viewPager.setAdapter(new MyPagerAdapter(listViews));
        viewPager.setCurrentItem(0);

        connectToData();

        Button addMoney = (Button) moneyPage.findViewById(R.id.moneyAddMoney);
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void buildServiceConnection() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                controlBind = (ControlService.ControlBind) service;
                controlBind.setNowPage(MainActivity.this);
                controlBind.getProtocolSender().getMoney();
                controlBind.getProtocolSender().getBudget();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void connectToData() {
        connectToMoneyData();
        connectToBudget();
    }

    private void connectToBudget() {
        budgetUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int size = data.getInt("size");
                System.out.println(size);
                budgetTable.removeViews(1, budgetTable.getChildCount() - 1);
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < size; i++) {
                    TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row_budget,
                            budgetTable, false);
                    TextView typename = (TextView) tableRow.findViewById(R.id.budget_row_typename);
                    TextView income = (TextView) tableRow.findViewById(R.id.budget_row_income);
                    TextView expenditure = (TextView) tableRow.findViewById(R.id.budget_row_expenditure);
                    TextView nowIncome = (TextView) tableRow.findViewById(R.id.budget_row_nowIncome);
                    TextView nowExpenditure = (TextView) tableRow.findViewById(R.id.budget_row_nowExpenditure);
                    typename.setText(data.getString("typename" + i));
                    income.setText(decimalFormat.format(data.getDouble("income" + i)));
                    expenditure.setText(decimalFormat.format(data.getDouble("expenditure" + i)));
                    nowIncome.setText(decimalFormat.format(data.getDouble("now income" + i)));
                    nowExpenditure.setText(decimalFormat.format(data.getDouble("now expenditure" + i)));
                    budgetTable.addView(tableRow);
                }
            }
        };

        BudgetData.getBudgetData().registerAction(new DataUpdateAction() {
            @Override
            public void action() {
                Collection<BudgetNode> budgetNodes = BudgetData.getBudgetData().getDataCollection();

                Bundle data = new Bundle();
                data.putInt("size", budgetNodes.size());
                int pos = 0;
                for (BudgetNode now : budgetNodes) {
                    data.putString("typename" + pos, now.getName());
                    data.putDouble("income" + pos, now.getIncome());
                    data.putDouble("expenditure" + pos, now.getExpenditure());
                    data.putDouble("now income" + pos, now.getNowIncome());
                    data.putDouble("now expenditure" + pos, now.getNowExpenditure());
                    pos++;
                }
                Message message = new Message();
                message.setData(data);
                budgetUpdateHandler.sendMessage(message);
            }
        });
    }

    private void connectToMoneyData() {
        moneyUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int size = data.getInt("size");
                System.out.println(size);
                moneyTable.removeViews(1, moneyTable.getChildCount() - 1);
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < size; i++) {
                    TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row_money,
                            moneyTable, false);
                    TextView money = (TextView) tableRow.findViewById(R.id.money_row_money);
                    TextView value = (TextView) tableRow.findViewById(R.id.money_row_value);
                    final String typename = data.getString("money" + i);
                    final double moneyValue = data.getDouble("value" + i);
                    money.setText(typename);
                    value.setText(decimalFormat.format(moneyValue));

                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ChangeMoneyActivity.class);
                            intent.putExtra("typename", typename);
                            intent.putExtra("value", moneyValue);
                            startActivity(intent);
                        }
                    });

                    moneyTable.addView(tableRow);
                }
            }
        };

        MoneyData.getMoneyData().registerAction(new DataUpdateAction() {
            @Override
            public void action() {
                Collection<MoneyNode> moneyNodes = MoneyData.getMoneyData().getDataCollection();

                Bundle data = new Bundle();
                data.putInt("size", moneyNodes.size());
                int pos = 0;
                for (MoneyNode now : moneyNodes) {
                    data.putString("money" + pos, now.getName());
                    data.putDouble("value" + pos, now.getValue());
                    pos++;
                }
                Message message = new Message();
                message.setData(data);
                moneyUpdateHandler.sendMessage(message);
            }
        });
    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                moneyTable = (TableLayout) mListViews.get(0).findViewById(R.id.moneyTable);
            } else if (position == 1) {
                budgetTable = (TableLayout) mListViews.get(1).findViewById(R.id.budgetTable);
            }
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }
    }

}
