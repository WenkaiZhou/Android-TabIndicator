package com.kevin.tabindicator.internal;

import android.support.v4.view.ViewPager;

/**
 * @ClassName: ITabPageIndicator
 * @Description: TabPageIndicator操作接口
 *
 * @version 1.0
 * @date 2016-2-24 10:39:43
 * @Author zhouwk
 */
public interface ITabPageIndicator {
	
    /**
     * 绑定TabIndicator到ViewPager
     *
     * @param view
     */
    void setViewPager(ViewPager view);
    
    /**
     * 绑定TabIndicator到ViewPager
     *
     * @param view
     * @param initialPosition
     */
    void setViewPager(ViewPager view, int initialPosition);
    
    /**
     * 设置TabIndicator选中变化监听
     *
     * @param listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);
}
