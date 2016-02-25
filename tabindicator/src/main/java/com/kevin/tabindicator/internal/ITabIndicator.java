package com.kevin.tabindicator.internal;

/**
 * @ClassName: TabIndicator
 * @Description: TabIndicator操作接口
 *
 * @version 1.0
 * @date 2016-2-24 10:13:29
 * @Author zhouwk
 */
public interface ITabIndicator {
    
    /**
     * 设置当前TabIndicator及ViewPager选中下标
     *
     * @param item
     */
    void setCurrentItem(int item);
    
}
