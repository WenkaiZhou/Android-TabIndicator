package com.kevin.tabindicator.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.kevin.tabindicator.R;

/**
 * 版权所有：XXX有限公司
 *
 * TabPageIndicatorBase
 *
 * @author zhou.wenkai ,Created on 2016-2-26 21:58:25
 * 		   Major Function：<b>ViewPager类型底部导航基类</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public abstract class TabPageIndicatorBase<T extends TabViewBase> extends TabIndicatorBase<T> implements ViewPager.OnPageChangeListener {

    /** 是否渐变切换 */
    protected boolean mIsGradualChange;
    /** 用于ViewPager会渐变颜色 */
    private ViewPager mViewPager;

    public TabPageIndicatorBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void handleStyledAttributes(TypedArray a) {
        super.handleStyledAttributes(a);
        mIsGradualChange = a.getBoolean(R.styleable.TabIndicator_tabGradualChange, true);
    }

    /**
     * 设置是否渐变渐变切换
     * @param isGradualChange
     */
    public void setIsGradualChange(boolean isGradualChange) {
        this.mIsGradualChange = isGradualChange;
    }

    /**
     * 获取是否渐变切换
     * @return
     */
    public boolean getIsGradualChange() {
        return mIsGradualChange;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIsGradualChange && positionOffset > 0) {
            T left = mCheckedList.get(position);
            T right = mCheckedList.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(!mIsGradualChange) {
            setTabsDisplay(position);
        }
    }
}
