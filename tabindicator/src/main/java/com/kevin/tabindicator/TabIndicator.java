package com.kevin.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.kevin.tabindicator.internal.TabIndicatorBase;

/**
 * 版权所有：XXX有限公司
 *
 * TabIndicator
 *
 * @author zhou.wenkai ,Created on 2016-2-26 22:47:35
 * 		   Major Function：<b>简单底部导航指示器</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class TabIndicator extends TabIndicatorBase<TabView> {

	private int[] mSelectedDrawableIds;
	private int[] mUnselectedDrawableIds;

	public TabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		// 读取布局中，各个tab使用的图标
		int selectedIconsResId = a.getResourceId(R.styleable.TabIndicator_tabSelectedIcons, 0);
		TypedArray ta = getContext().getResources().obtainTypedArray(selectedIconsResId);
		int len = ta.length();
		mSelectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mSelectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		int unselectedIconsResId = a.getResourceId(R.styleable.TabIndicator_tabUnselectedIcons, 0);
		ta = getContext().getResources().obtainTypedArray(unselectedIconsResId);
		len = ta.length();
		mUnselectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mUnselectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		ta.recycle();
	}

	@Override
	protected TabView createTabView() {
		return new TabView(getContext());
	}

	@Override
	protected void setProperties(TabView tabView, int index) {
		tabView.setSelectedIcon(mSelectedDrawableIds[index]);
		tabView.setUnselectedIcon(mUnselectedDrawableIds[index]);
	}

	@Override
	protected int getTabSize() {
		return mSelectedDrawableIds.length;
	}
}
