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
import com.wallet.xlo.walletforandroid.model.data.AllDetailData;
import com.wallet.xlo.walletforandroid.model.data.BudgetData;
import com.wallet.xlo.walletforandroid.model.data.DataUpdateAction;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.BudgetNode;
import com.wallet.xlo.walletforandroid.model.data.node.DetailNode;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AbstractActivity {

    private Handler moneyUpdateHandler, budgetUpdateHandler, detailUpdateHandler;
    private TableLayout moneyTable, budgetTable, detailTable;

    private ViewPager viewPager;
    private View moneyPage, budgetPage, detailPage;

    private DataUpdateAction moneyUpdateAction, budgetUpdateAction, detailUpdateAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view);
        List<View> listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        moneyPage = mInflater.inflate(R.layout.layout_money, null);
        budgetPage = mInflater.inflate(R.layout.layout_budget, null);
        detailPage = mInflater.inflate(R.layout.layout_detail, null);
        listViews.add(moneyPage);
        listViews.add(budgetPage);
        listViews.add(detailPage);
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

        Button addBudget = (Button) budgetPage.findViewById(R.id.budgetAddBudget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBudgetActivity.class);
                startActivity(intent);
            }
        });

        Button rollBack = (Button) detailPage.findViewById(R.id.rollBackAction);
        rollBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBind.getProtocolSender().rollback();
            }
        });
    }

    protected void buildServiceConnection() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                controlBind = (ControlService.ControlBind) service;
                controlBind.setNowPage(MainActivity.this);
                try {
                    controlBind.getProtocolSender().getMoney();
                    controlBind.getProtocolSender().getBudget();
                    controlBind.getProtocolSender().getAllDetail();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void connectToData() {
        connectToMoneyData();
        connectToBudget();
        connectToDetail();
    }

    private void connectToDetail() {
        detailUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int size = data.getInt("size");
                detailTable.removeViews(1, detailTable.getChildCount() - 1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                for (int i = 0; i < size; i++) {
                    TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row_detail,
                            detailTable, false);
                    final TextView id = (TextView) tableRow.findViewById(R.id.detail_row_id);
                    final TextView event = (TextView) tableRow.findViewById(R.id.detail_row_event);
                    TextView date = (TextView) tableRow.findViewById(R.id.detail_row_date);

                    final String idString = data.getString("id" + i);
                    final String eventString = data.getString("event" + i);
                    final long dateLong = data.getLong("date" + i);

                    id.setText(idString);
                    event.setText(eventString);
                    date.setText(simpleDateFormat.format(new Date(dateLong)));

                    detailTable.addView(tableRow);
                }
            }
        };

        detailUpdateAction = new DataUpdateAction() {
            @Override
            public void action() {
                Collection<DetailNode> detailCollection = AllDetailData.getAllDetailData().getDataCollection();
                List<DetailNode> detailNodes = new ArrayList<>();
                for (DetailNode now : detailCollection) {
                    detailNodes.add(now);
                }
                Collections.sort(detailNodes, new Comparator<DetailNode>() {
                    @Override
                    public int compare(DetailNode o1, DetailNode o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });

                Bundle data = new Bundle();
                data.putInt("size", detailNodes.size());
                int pos = 0;
                for (DetailNode now : detailNodes) {
                    data.putString("id" + pos, now.getId());
                    data.putString("event" + pos, now.getEvent());
                    data.putLong("date" + pos, now.getDate().getTime());
                    pos++;
                }
                Message message = new Message();
                message.setData(data);
                detailUpdateHandler.sendMessage(message);
            }
        };
    }

    private void connectToBudget() {
        budgetUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int size = data.getInt("size");
                budgetTable.removeViews(1, budgetTable.getChildCount() - 1);
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < size; i++) {
                    TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row_budget,
                            budgetTable, false);
                    final TextView typename = (TextView) tableRow.findViewById(R.id.budget_row_typename);
                    final TextView income = (TextView) tableRow.findViewById(R.id.budget_row_income);
                    TextView expenditure = (TextView) tableRow.findViewById(R.id.budget_row_expenditure);
                    TextView nowIncome = (TextView) tableRow.findViewById(R.id.budget_row_nowIncome);
                    TextView nowExpenditure = (TextView) tableRow.findViewById(R.id.budget_row_nowExpenditure);

                    final String typenameString = data.getString("typename" + i);
                    final double incomeDouble = data.getDouble("income" + i);
                    final double expenditureDouble = data.getDouble("expenditure" + i);
                    final double nowIncomeDouble = data.getDouble("now income" + i);
                    final double nowExpenditureDouble = data.getDouble("now expenditure" + i);

                    typename.setText(typenameString);
                    income.setText(decimalFormat.format(incomeDouble));
                    expenditure.setText(decimalFormat.format(expenditureDouble));
                    nowIncome.setText(decimalFormat.format(nowIncomeDouble));
                    nowExpenditure.setText(decimalFormat.format(nowExpenditureDouble));

                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ChangeBudgetActivity.class);
                            intent.putExtra("typename", typenameString);
                            intent.putExtra("income", incomeDouble);
                            intent.putExtra("expenditure", expenditureDouble);
                            intent.putExtra("now income", nowIncomeDouble);
                            intent.putExtra("now expenditure", nowExpenditureDouble);
                            startActivity(intent);
                        }
                    });

                    budgetTable.addView(tableRow);
                }
            }
        };

        budgetUpdateAction = new DataUpdateAction() {
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
        };
    }

    private void connectToMoneyData() {
        moneyUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int size = data.getInt("size");
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

        moneyUpdateAction = new DataUpdateAction() {
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
        };
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
            if (position == 0) {
                MoneyData.getMoneyData().removeAction(moneyUpdateAction);
            } else if (position == 1) {
                BudgetData.getBudgetData().removeAction(budgetUpdateAction);
            } else if (position == 2) {
                AllDetailData.getAllDetailData().removeAction(detailUpdateAction);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                moneyTable = (TableLayout) mListViews.get(0).findViewById(R.id.moneyTable);
                MoneyData.getMoneyData().registerAction(moneyUpdateAction);
                moneyUpdateAction.action();
            } else if (position == 1) {
                budgetTable = (TableLayout) mListViews.get(1).findViewById(R.id.budgetTable);
                BudgetData.getBudgetData().registerAction(budgetUpdateAction);
                budgetUpdateAction.action();
            } else if (position == 2) {
                detailTable = (TableLayout) mListViews.get(2).findViewById(R.id.detailTable);
                AllDetailData.getAllDetailData().registerAction(detailUpdateAction);
                detailUpdateAction.action();
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
