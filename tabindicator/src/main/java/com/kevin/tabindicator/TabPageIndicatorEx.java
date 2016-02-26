package com.kevin.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;

import com.kevin.tabindicator.internal.TabIndicatorBase;

/**
 * 版权所有：XXX有限公司
 *
 * TabPageIndicator
 *
 * @author zhou.wenkai ,Created on 2016-2-24 10:41:49
 * 		   Major Function：<b>ViewPager的底部导航指示器</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class TabPageIndicatorEx extends TabIndicatorBase<TabPageView> implements OnPageChangeListener {

	/** 底部菜单图标数组 */
	private int[] mDrawableIds;
	/** 用于ViewPager会渐变颜色 */
	private ViewPager mViewPager;

	public void setViewPager(ViewPager viewPager) {
		if(null == viewPager) return;
		this.mViewPager = viewPager;
		mViewPager.addOnPageChangeListener(this);
	}

	public TabPageIndicatorEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		// 读取布局中，各个tab使用的图标
		int iconsResId = a.getResourceId(R.styleable.TabIndicator_tabIcons, 0);
		TypedArray ta = getContext().getResources().obtainTypedArray(iconsResId);
		int len = ta.length();
		mDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mDrawableIds[i] = ta.getResourceId(i, 0);
		}
		ta.recycle();
	}

	@Override
	protected TabPageView createTabView() {
		return new TabPageView(getContext());
	}

	@Override
	protected void setProperties(TabPageView tabPageView, int index) {
		tabPageView.setIcon(mDrawableIds[index]);
	}
	
	@Override
	public void onPageScrollStateChanged(int position) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset > 0) {
			TabPageView left = mCheckedList.get(position);
			TabPageView right = mCheckedList.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
	}

	@Override
	public void onPageSelected(int position) {
//		setTabsDisplay(position);
	}
}
