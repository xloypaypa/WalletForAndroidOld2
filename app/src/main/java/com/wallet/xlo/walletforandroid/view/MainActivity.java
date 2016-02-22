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

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.model.data.AllDetailData;
import com.wallet.xlo.walletforandroid.model.data.BudgetData;
import com.wallet.xlo.walletforandroid.model.data.DataUpdateAction;
import com.wallet.xlo.walletforandroid.model.data.EdgeData;
import com.wallet.xlo.walletforandroid.model.data.LoanData;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.BudgetNode;
import com.wallet.xlo.walletforandroid.model.data.node.DetailNode;
import com.wallet.xlo.walletforandroid.model.data.node.EdgeNode;
import com.wallet.xlo.walletforandroid.model.data.node.LoanNode;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;
import com.wallet.xlo.walletforandroid.view.handler.BudgetUpdateHandler;
import com.wallet.xlo.walletforandroid.view.handler.DetailUpdateHandler;
import com.wallet.xlo.walletforandroid.view.handler.EdgeUpdateHandler;
import com.wallet.xlo.walletforandroid.view.handler.LoanUpdateHandler;
import com.wallet.xlo.walletforandroid.view.handler.MoneyUpdateHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AbstractActivity {

    private Handler moneyUpdateHandler, budgetUpdateHandler, loanUpdateHandler, detailUpdateHandler, edgeUpdateHandler;

    private DataUpdateAction moneyUpdateAction, budgetUpdateAction, loanUpdateAction, detailUpdateAction, edgeUpdateAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view);
        List<View> listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        View moneyPage = mInflater.inflate(R.layout.layout_money, null);
        View budgetPage = mInflater.inflate(R.layout.layout_budget, null);
        View edgePage = mInflater.inflate(R.layout.layout_edge, null);
        View loanPage = mInflater.inflate(R.layout.layout_loan, null);
        View detailPage = mInflater.inflate(R.layout.layout_detail, null);
        listViews.add(moneyPage);
        listViews.add(budgetPage);
        listViews.add(edgePage);
        listViews.add(loanPage);
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

        Button transfer = (Button) moneyPage.findViewById(R.id.moneyTransfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransferActivity.class);
                startActivity(intent);
            }
        });

        Button useMoney = (Button) moneyPage.findViewById(R.id.moneyUseMoney);
        useMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UseMoneyActivity.class);
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

//        Button addLoan = (Button) loanPage.findViewById(R.id.mainAddLoan);
//        addLoan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddBudgetActivity.class);
//                startActivity(intent);
//            }
//        });

        Button rollBack = (Button) detailPage.findViewById(R.id.rollBackAction);
        rollBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBind.getProtocolSender().rollback();
            }
        });

        Button addEdge = (Button) edgePage.findViewById(R.id.addEdgeAction);
        addEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEdgeActivity.class);
                startActivity(intent);
            }
        });

        Button clear = (Button) budgetPage.findViewById(R.id.settlementAccounts);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBind.getProtocolSender().clear();
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
                    controlBind.getProtocolSender().getEdge();
                    controlBind.getProtocolSender().getLoan();
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
        connectToEdge();
        connectToLoan();
        connectToDetail();
    }

    private void connectToLoan() {
        loanUpdateAction = new DataUpdateAction() {
            @Override
            public void action() {
                Collection<LoanNode> loanNodes = LoanData.getLoanData().getDataCollection();

                Bundle data = new Bundle();
                data.putInt("size", loanNodes.size());
                int pos = 0;
                for (LoanNode now : loanNodes) {
                    data.putString("id" + pos, now.getId());
                    data.putString("creditor" + pos, now.getCreditor());
                    data.putDouble("value" + pos, now.getValue());
                    data.putLong("deadline" + pos, now.getDeadline().getTime());
                    data.putString("isIn" + pos, now.getIsIn());
                    pos++;
                }
                Message message = new Message();
                message.setData(data);
                loanUpdateHandler.sendMessage(message);
            }
        };
    }

    private void connectToEdge() {
        edgeUpdateAction = new DataUpdateAction() {
            @Override
            public void action() {
                Collection<EdgeNode> edgeNodes = EdgeData.getEdgeData().getDataCollection();

                Bundle data = new Bundle();
                data.putInt("size", edgeNodes.size());
                int pos = 0;
                for (EdgeNode now : edgeNodes) {
                    data.putString("from" + pos, now.getFrom());
                    data.putString("to" + pos, now.getTo());
                    data.putString("script" + pos, now.getScript());
                    pos++;
                }
                Message message = new Message();
                message.setData(data);
                edgeUpdateHandler.sendMessage(message);
            }
        };
    }

    private void connectToDetail() {
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
                EdgeData.getEdgeData().removeAction(edgeUpdateAction);
            } else if (position == 3) {
                LoanData.getLoanData().removeAction(loanUpdateAction);
            } else if (position == 4) {
                AllDetailData.getAllDetailData().removeAction(detailUpdateAction);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                TableLayout moneyTable = (TableLayout) mListViews.get(0).findViewById(R.id.moneyTable);
                MoneyData.getMoneyData().registerAction(moneyUpdateAction);
                moneyUpdateHandler = new MoneyUpdateHandler(MainActivity.this, moneyTable);
                moneyUpdateAction.action();
            } else if (position == 1) {
                TableLayout budgetTable = (TableLayout) mListViews.get(1).findViewById(R.id.budgetTable);
                BudgetData.getBudgetData().registerAction(budgetUpdateAction);
                budgetUpdateHandler = new BudgetUpdateHandler(MainActivity.this, budgetTable);
                budgetUpdateAction.action();
            } else if (position == 2) {
                TableLayout edgeTable = (TableLayout) mListViews.get(2).findViewById(R.id.edgeTable);
                EdgeData.getEdgeData().registerAction(edgeUpdateAction);
                edgeUpdateHandler = new EdgeUpdateHandler(MainActivity.this, edgeTable);
                edgeUpdateAction.action();
            } else if (position == 3) {
                TableLayout loanTable = (TableLayout) mListViews.get(3).findViewById(R.id.loanTable);
                LoanData.getLoanData().registerAction(loanUpdateAction);
                loanUpdateHandler = new LoanUpdateHandler(MainActivity.this, loanTable);
                loanUpdateAction.action();
            } else if (position == 4) {
                TableLayout detailTable = (TableLayout) mListViews.get(4).findViewById(R.id.detailTable);
                AllDetailData.getAllDetailData().registerAction(detailUpdateAction);
                detailUpdateHandler = new DetailUpdateHandler(MainActivity.this, detailTable);
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
