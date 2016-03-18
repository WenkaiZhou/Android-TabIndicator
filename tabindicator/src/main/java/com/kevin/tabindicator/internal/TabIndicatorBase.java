package com.kevin.tabindicator.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.kevin.tabindicator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权所有：XXX有限公司
 *
 * TabIndicatorBase
 *
 * @author zhou.wenkai ,Created on 2016-2-25 21:15:04
 * 		   Major Function：<b>底部导航基类</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public abstract class TabIndicatorBase<T extends TabViewBase> extends LinearLayout {

    /** 底部菜单的文字数组 */
    protected CharSequence[] mLabels;
    /** 选中时颜色 */
    protected int mSelectedColor;
    /** 非选中时颜色 */
    protected int mUnselectedColor;
    /** 文本大小 */
    protected int mTextSize;
    /** 底部菜单padding */
    protected int mTabPadding;
    /** 指示点大小 */
    private int mIndicatorSize;
    /** 存放底部菜单 */
    protected List<T> mCheckedList = new ArrayList<>();
    /** 回调接口，用于获取tab的选中状态 */
    protected OnTabSelectedListener mTabListener;

    public TabIndicatorBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        //Load defaults from resources
        final Resources res = getResources();
        final int defaultSelectedColor = res.getColor(R.color.default_tab_view_selected_color);
        final int defaultUnselectedColor = res.getColor(R.color.default_tab_view_unselected_color);
        final float defaultTextSize = res.getDimension(R.dimen.default_tab_view_text_size);
        final float defaultTabPadding = res.getDimension(R.dimen.default_tab_view_padding);
        final float defaultIndicatorSize = res.getDimension(R.dimen.default_tab_view_indicator_size);

        // Styleables from XML
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicator);

        // 读取布局中，各个tab使用的文字
        if (a.hasValue(R.styleable.TabIndicator_tabLabels)) {
            mLabels = a.getTextArray(R.styleable.TabIndicator_tabLabels);
        }

        mSelectedColor = a.getColor(R.styleable.TabIndicator_tabSelectedColor, defaultSelectedColor);
        mUnselectedColor = a.getColor(R.styleable.TabIndicator_tabUnselectedColor, defaultUnselectedColor);
        mTextSize = (int) a.getDimension(R.styleable.TabIndicator_tabTextSize, defaultTextSize);
        mIndicatorSize = (int) a.getDimension(R.styleable.TabIndicator_TabIndicatorSize, defaultIndicatorSize);
        mTabPadding = (int) a.getDimension(R.styleable.TabIndicator_tabItemPadding, defaultTabPadding);

        handleStyledAttributes(a);
        a.recycle();

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        params.gravity = Gravity.CENTER;

        int size = getTabSize();
        for (int i = 0; i < size; i++) {
            final int index = i;

            T tabItemView = createTabView();
            tabItemView.setPadding(mTabPadding, mTabPadding, mTabPadding, mTabPadding);
            // 图标及文字
            if(null != mLabels) {
                tabItemView.setText(mLabels[index]);
                tabItemView.setTextSize(mTextSize);
            }
            tabItemView.setSelectedColor(mSelectedColor);
            tabItemView.setUnselectedColor(mUnselectedColor);
            tabItemView.setIndicatorSize(mIndicatorSize);

            setProperties(tabItemView, i);

            this.addView(tabItemView, params);

            tabItemView.setTag(index);						// CheckedTextView设置索引作为tag，以便后续更改颜色、图片等
            mCheckedList.add(tabItemView);				    // 将CheckedTextView添加到list中，便于操作
            tabItemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    setTabsDisplay(index);					// 设置底部图片和文字的显示
                    if (null != mTabListener) {
                        mTabListener.onTabSelected(index);	// tab项被选中的回调事件
                    }
                }
            });

            // 初始化 底部菜单选中状态,默认第一个选中
            if (i == 0) {
                tabItemView.setSelected(true);
//				tabItemView.setBackgroundColor(Color.rgb(240, 241, 242));
            } else {
                tabItemView.setSelected(false);
//				view.setBackgroundColor(Color.rgb(250, 250, 250));
            }
        }
    }

    /**
     * Allows Derivative classes to handle the XML Attrs without creating a
     * TypedArray themsevles
     *
     * @param a - TypedArray of TabIndicator Attributes
     */
    protected void handleStyledAttributes(TypedArray a) {
    }

    /**
     * 生成TabView
     * @return
     */
    protected abstract T createTabView();

    /**
     * 设置特殊属性
     * @param t
     */
    protected abstract void setProperties(T t, int index);

    /**
     * 获取条目个数
     * @return
     */
    protected abstract int getTabSize();

    /**
     * 设置底部导航中图片显示状态和字体颜色
     */
    public void setTabsDisplay(int index) {
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++) {
            T mIconView = mCheckedList.get(i);
            if ((Integer) (mIconView.getTag()) == index) {
                mIconView.setSelected(true);
//				mCheckedList.get(i).setBackgroundColor(Color.rgb(240, 241, 242));
            } else {
                mIconView.setSelected(false);
//				mCheckedList.get(i).setBackgroundColor(Color.rgb(250, 250, 250));
            }
        }
    }

    /**
     * 设置指示点的显示
     *
     * @param position
     *            显示位置
     * @param visible
     *            是否显示，如果false，则都不显示
     */
    public void setIndicateDisplay(int position, boolean visible) {
        int size = mCheckedList.size();
        if (size <= position) {
            return;
        }
        T tabView = mCheckedList.get(position);
        tabView.setIndicateDisplay(visible);
    }

    /**
     * 设置指示点图片
     * @param bitmap
     */
    public void setIndicateBitmap(Bitmap bitmap) {
        for(T t : mCheckedList) {
            t.setIndicatorBitmap(bitmap);
        }
    }

    /**
     * 设置指示点图片
     * @param resId
     */
    public void setIndicateBitmap(int resId) {
        for(T t : mCheckedList) {
            t.setIndicatorBitmap(resId);
        }
    }

    /**
     * 设置定义选中tab的接口回调
     * @param listener
     */
    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.mTabListener = listener;
    }

    /**
     * 定义选中tab的接口
     */
    public interface OnTabSelectedListener {
        void onTabSelected(int index);
    }

}
