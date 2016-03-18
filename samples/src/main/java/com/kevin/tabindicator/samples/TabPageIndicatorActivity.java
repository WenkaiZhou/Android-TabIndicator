package com.kevin.tabindicator.samples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kevin.tabindicator.TabPageIndicator;
import com.kevin.tabindicator.TabPageIndicatorEx;

import java.util.ArrayList;
import java.util.List;

public class TabPageIndicatorActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Button mChangeModeBtn;
    private TabPageIndicator mTabPageIndicator;
    private List<Fragment> mTabs = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    private boolean isGradualChange;

    private String[] mTitles = new String[] { "First Fragment!",
            "Second Fragment!", "Third Fragment!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabpage_indicator);

        initViews();
        initEvents();
    }

    /**
     * 初始化View
     */
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabPageIndicator = (TabPageIndicator) findViewById(R.id.tabpage_act_tpi);
        mChangeModeBtn = (Button) findViewById(R.id.tabpage_act_btn_change);
        initTabIndicator();
        initViewPager();
    }

    private void initViewPager() {

        for (String title : mTitles) {
            TabFragment tabFragment = new TabFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            tabFragment.setArguments(args);
            mTabs.add(tabFragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mTabs.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 初始化事件
     */
    private void initEvents() {
        mChangeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabPageIndicator.setIsGradualChange(!isGradualChange);
                isGradualChange = !isGradualChange;
            }
        });
        mTabPageIndicator.setOnTabSelectedListener(new TabPageIndicatorEx.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int index) {
                mViewPager.setCurrentItem(index, false);
            }
        });
    }

    private void initTabIndicator() {
        mTabPageIndicator.setViewPager(mViewPager);
        mTabPageIndicator.setIndicateDisplay(2, true);
    }

}
