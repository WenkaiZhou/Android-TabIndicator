package com.kevin.tabindicator.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kevin.tabindicator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权所有：XXX有限公司
 *
 * TabViewBase
 *
 * @author zhou.wenkai ,Created on 2016-2-25 21:15:04
 * 		   Major Function：<b>底部导航基类</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class TabIndicatorBase<T extends TabViewBase> extends LinearLayout implements ITabIndicator {

    /** 底部菜单的文字数组 */
    protected CharSequence[] mLabels;
    /** 选中时颜色 */
    protected int mSelectedColor;
    /** 非选中时颜色 */
    protected int mUnselectedColor;
    /** 文本大小 */
    protected int mTextSize;
    /** 存放底部菜单的各TabPageView */
    protected List<T> mCheckedList = new ArrayList<>();
    /** 存放底部菜单每项View */
    protected List<View> mViewList = new ArrayList<>();
    /** 存放指示点 */
    protected List<ImageView> mIndicateImgs = new ArrayList<>();

    public TabIndicatorBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        //Load defaults from resources
        final Resources res = getResources();
        final int defaultSelectedColor = res.getColor(R.color.default_page_selected_color);
        final int defaultUnselectedColor = res.getColor(R.color.default_page_unselected_color);
        final float defaultTextSize = res.getDimension(R.dimen.default_page_text_size);

        // Styleables from XML
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicator);

        // 读取布局中，各个tab使用的文字
        if (a.hasValue(R.styleable.TabIndicator_labels)) {
            mLabels = a.getTextArray(R.styleable.TabIndicator_labels);
        } else {
            throw new IllegalArgumentException("Please set labels attr in XML!");
        }

        mSelectedColor = a.getColor(R.styleable.TabIndicator_selectedColor, defaultSelectedColor);
        mUnselectedColor = a.getColor(R.styleable.TabIndicator_unselectedColor, defaultUnselectedColor);
        mTextSize = (int) a.getDimension(R.styleable.TabIndicator_android_textSize, defaultTextSize);

        handleStyledAttributes(a);
        a.recycle();

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
     * 设置底部导航中图片显示状态和字体颜色
     */
    public void setTabsDisplay(int index) {
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++) {
            T mIconView = mCheckedList.get(i);
            if ((Integer) (mIconView.getTag()) == index) {
                mIconView.setSelected(true);
//				mViewList.get(i).setBackgroundColor(Color.rgb(240, 241, 242));
            } else {
                mIconView.setSelected(false);
//				mViewList.get(i).setBackgroundColor(Color.rgb(250, 250, 250));
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
        int size = mIndicateImgs.size();
        if (size <= position) {
            return;
        }
        ImageView indicateImg = mIndicateImgs.get(position);
        indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
