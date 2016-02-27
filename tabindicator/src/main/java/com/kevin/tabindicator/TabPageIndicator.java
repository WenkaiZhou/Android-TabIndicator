package com.kevin.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;

import com.kevin.tabindicator.internal.TabPageIndicatorBase;

/**
 * 版权所有：XXX有限公司
 *
 * TabPageIndicator
 *
 * @author zhou.wenkai ,Created on 2016-2-27 16:04:53
 * 		   Major Function：<b>ViewPager的底部导航指示器</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class TabPageIndicator extends TabPageIndicatorBase<TabPageView> implements OnPageChangeListener {

	/** 底部菜单图标数组 */
	private int[] mSelectedDrawableIds;
	private int[] mUnselectedDrawableIds;

	public TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		// 读取布局中，各个tab使用的图标
		int iconsResId = a.getResourceId(R.styleable.TabIndicator_tabSelectedIcons, 0);
		TypedArray ta = getContext().getResources().obtainTypedArray(iconsResId);
		int len = ta.length();
		mSelectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mSelectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		iconsResId = a.getResourceId(R.styleable.TabIndicator_tabUnselectedIcons, 0);
		ta = getContext().getResources().obtainTypedArray(iconsResId);
		len = ta.length();
		mUnselectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mUnselectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		ta.recycle();
	}

	@Override
	protected TabPageView createTabView() {
		return new TabPageView(getContext());
	}

	@Override
	protected void setProperties(TabPageView tabPageView, int index) {
		tabPageView.setSelectedIcon(mSelectedDrawableIds[index]);
		tabPageView.setUnselectedIcon(mUnselectedDrawableIds[index]);
	}

	@Override
	protected int getTabSize() {
		return mSelectedDrawableIds.length;
	}
}
