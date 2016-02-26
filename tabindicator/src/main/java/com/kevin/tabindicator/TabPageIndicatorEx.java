package com.kevin.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.kevin.tabindicator.internal.IndicateView;
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
	/** 回调接口，用于获取tab的选中状态 */
	private OnTabSelectedListener mTabListener;
	/** 用于ViewPager会渐变颜色 */
	private ViewPager mViewPager;

	public void setViewPager(ViewPager viewPager) {
		if(null == viewPager) return;
		this.mViewPager = viewPager;
		mViewPager.addOnPageChangeListener(this);
	}

	public TabPageIndicatorEx(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 初始化控件
		initRealView(context);
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

	/**
	 * 初始化控件
	 */
	private void initRealView(final Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		params.weight = 1.0f;
		params.gravity = Gravity.CENTER;

		int size = mLabels.length;
		for (int i = 0; i < size; i++) {
			final int index = i;

			// 每个tab对应的layout
			final View view = inflater.inflate(R.layout.tab_item, null);
			final TabPageView tabItemView = (TabPageView) view.findViewById(R.id.tab_icon_view);
			tabItemView.setPadding(mTabPadding, mTabPadding, mTabPadding, mTabPadding);
			// 图标及文字
			tabItemView.setIcon(mDrawableIds[i]);
			tabItemView.setText(mLabels[i]);
			tabItemView.setSelectedColor(mSelectedColor);
			tabItemView.setUnselectedColor(mUnselectedColor);
			tabItemView.setTextSize(mTextSize);
			// 指示点ImageView
			final IndicateView indicateImg = (IndicateView) view.findViewById(R.id.indicate_img);
//			indicateImg.setBackgroundResource(R.drawable.indicate_background);

			this.addView(view, params);
			
			tabItemView.setTag(index);						// CheckedTextView设置索引作为tag，以便后续更改颜色、图片等
			mCheckedList.add(tabItemView);					// 将CheckedTextView添加到list中，便于操作
			mIndicateImgs.add(indicateImg);					// 将指示图片加到list，便于控制显示隐藏
			mViewList.add(view);							// 将各个tab的View添加到list
			view.setOnClickListener(new OnClickListener() {

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
				tabItemView.setIconAlpha(1.0f);
//				view.setBackgroundColor(Color.rgb(240, 241, 242));
			} else {
				tabItemView.setIconAlpha(0);
//				view.setBackgroundColor(Color.rgb(250, 250, 250));
			}

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
		setTabsDisplay(position);
	}

}
