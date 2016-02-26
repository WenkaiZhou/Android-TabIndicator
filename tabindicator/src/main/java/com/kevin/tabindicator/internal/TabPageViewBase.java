package com.kevin.tabindicator.internal;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 版权所有：XXX有限公司
 *
 * TabPageViewBase
 *
 * @author zhou.wenkai ,Created on 2016-2-26 22:25:34
 * 		   Major Function：<b>ViewPager类型底部导航条目操作基类</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public abstract class TabPageViewBase extends TabViewBase{

    public TabPageViewBase(Context context) {
        super(context);
    }

    public TabPageViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置切换颜色渐变
     * @param alpha
     */
    protected abstract void setIconAlpha(float alpha);
}
