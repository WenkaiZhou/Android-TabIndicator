package com.kevin.tabindicator.samples;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kevin.tabindicator.TabPageIndicatorEx;

import java.util.ArrayList;
import java.util.List;

public class TabPageIndicatorActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabPageIndicatorEx mTabPageIndictor;
    private List<Fragment> mTabs = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    private boolean isGradualChange;

    private String[] mTitles = new String[] { "First Fragment!",
            "Second Fragment!", "Third Fragment!", "Fourth Fragment!" };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabpage_indicator);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabPageIndictor = (TabPageIndicatorEx) this.findViewById(R.id.second_act_tpi);

        initDatas();

        mViewPager.setAdapter(mAdapter);
    }

    private void initDatas() {

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

        initTabIndicator();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("改变切换模式");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("改变切换模式")) {
            mTabPageIndictor.setIsGradualChange(!isGradualChange);
            isGradualChange = !isGradualChange;
        }
        return true;
    }

    private void initTabIndicator() {
        mTabPageIndictor.setViewPager(mViewPager);
        mTabPageIndictor.setOnTabSelectedListener(new TabPageIndicatorEx.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int index) {
                mViewPager.setCurrentItem(index, false);
            }
        });

        mTabPageIndictor.setIndicateDisplay(1, true);
    }


}
