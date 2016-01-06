package com.wallet.xlo.walletforandroid.view;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.xlo.walletforandroid.R;
import com.wallet.xlo.walletforandroid.control.ControlService;
import com.wallet.xlo.walletforandroid.model.data.DataUpdateAction;
import com.wallet.xlo.walletforandroid.model.data.MoneyData;
import com.wallet.xlo.walletforandroid.model.data.node.MoneyNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AbstractActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler moneyUpdateHandler;
    private View moneyView, budgetView;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moneyUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int size = data.getInt("size");
                System.out.println(size);
                for (int i = 0; i < size; i++) {
                    System.out.println(data.getString("money" + i) + " " + data.getDouble("value" + i));
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

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindControlService();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.view);
        List<View> listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.layout_money, null));
        listViews.add(mInflater.inflate(R.layout.layout_budget, null));
        viewPager.setAdapter(new MyPagerAdapter(listViews));
        viewPager.setCurrentItem(0);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_money) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_budget) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_detail) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                moneyView = mListViews.get(0);
            } else if (position == 1) {
                budgetView = mListViews.get(1);
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
