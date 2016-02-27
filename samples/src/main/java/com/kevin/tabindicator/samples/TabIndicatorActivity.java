package com.kevin.tabindicator.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.kevin.tabindicator.TabIndicator;
import com.kevin.tabindicator.TabPageIndicatorEx;

public class TabIndicatorActivity extends AppCompatActivity {

    private TextView mTextView;
    private TabIndicator mTabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_indicator);

        initViews();
        initEvents();
    }

    /**
     * 初始化View
     */
    private void initViews() {
        mTextView = (TextView) this.findViewById(R.id.tab_indicator_act_tv);
        mTabIndicator = (TabIndicator) this.findViewById(R.id.tab_indicator_act_ti);
        mTextView.setText("Page1");
        mTabIndicator.setIndicateDisplay(2, true);
    }

    /**
     * 初始化事件
     */
    private void initEvents() {
        mTabIndicator.setOnTabSelectedListener(new TabPageIndicatorEx.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int index) {
                mTextView.setText("Page"+(index+1));
            }
        });
    }
}