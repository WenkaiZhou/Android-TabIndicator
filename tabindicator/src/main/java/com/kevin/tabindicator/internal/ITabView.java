package com.kevin.tabindicator.internal;

import android.graphics.Bitmap;

/**
 * 版权所有：XXX有限公司
 *
 * ITabView
 *
 * @author zhou.wenkai ,Created on 2016-2-25 21:50:39
 * 		   Major Function：<b>底部导航条目操作接口</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public interface ITabView {
    /**
     * 设置文字
     * @param id
     */
    void setText(int id);

    /**
     * 设置文字
     * @param text
     */
    void setText(CharSequence text);

    /**
     * 设置选中时颜色
     * @param selectedColor
     */
    void setSelectedColor(int selectedColor);

    /**
     * 设置未选中时颜色
     * @param unselectedColor
     */
    void setUnselectedColor(int unselectedColor);

    /**
     * 设置文本大小
     * @param textSize
     */
    void setTextSize(int textSize);

    /**
     * 设置指示点大小
     * @param indicatorSize
     */
    void setIndicatorSize(int indicatorSize);

    /**
     * 设置指示点图片
     * @param bitmap
     */
    void setIndicatorBitmap(Bitmap bitmap);

    /**
     * 设置指示点图片
     * @param resId
     */
    void setIndicatorBitmap(int resId);

    /**
     * 设置是否选中
     * @param isSelected
     */
    void setSelected(boolean isSelected);
}
