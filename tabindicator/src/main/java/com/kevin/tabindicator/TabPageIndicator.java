package com.kevin.tabindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kevin.tabindicator.internal.ITabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TabPageIndicator
 * @Description: ViewPager的指示器
 *
 * @version 1.0
 * @date 2016-2-24 10:41:49
 * @Author zhouwk
 */
public class TabPageIndicator extends TabIndicator implements ITabPageIndicator, OnPageChangeListener {

	/** 底部菜单图标数组 */
	private int[] mDrawableIds;
	/** 底部菜单的文字数组 */
	private CharSequence[] mLabels;
	/** 存放底部菜单的各TabPageView */
	private List<TabPageView> mCheckedList = new ArrayList<>();
	/** 存放底部菜单每项View */
	private List<View> mViewList = new ArrayList<>();
	/** 存放指示点 */
	private List<ImageView> mIndicateImgs = new ArrayList<>();
	/** 选中时颜色 */
	private int mSelectedColor;
	/** 非选中时颜色 */
	private int mUnselectedColor;
	/** 文本大小 */
	private int mTextSize;
	/** 回调接口，用于获取tab的选中状态 */
	private OnTabSelectedListener mTabListener;
	/** 用于ViewPager会渐变颜色 */
	private ViewPager mViewPager;

	public void setViewPager(ViewPager viewPager) {
		if(null == viewPager) return;
		this.mViewPager = viewPager;
		mViewPager.setOnPageChangeListener(this);
	}

	public TabPageIndicator(Context context) {
		this(context, null);
	}

	public TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);

		//Load defaults from resources
        final Resources res = getResources();
        final int defaultSelectedColor = res.getColor(R.color.default_page_selected_color);
        final int defaultUnselectedColor = res.getColor(R.color.default_page_unselected_color);
        final float defaultTextSize = res.getDimension(R.dimen.default_page_text_size);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabPageIndictor);
		mSelectedColor = a.getColor(R.styleable.TabPageIndictor_selectedColor, defaultSelectedColor);
		mUnselectedColor = a.getColor(R.styleable.TabPageIndictor_unselectedColor, defaultUnselectedColor);
		mTextSize = (int) a.getDimension(R.styleable.TabPageIndictor_android_textSize, defaultTextSize);

		// 读取布局中，各个tab使用的文字
		mLabels = a.getTextArray(R.styleable.TabPageIndictor_labels);

		// 读取布局中，各个tab使用的图标
		int iconsResId = a.getResourceId(R.styleable.TabPageIndictor_icons, 0);
		TypedArray ta = context.getResources().obtainTypedArray(iconsResId);
		int len = ta.length();
		mDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mDrawableIds[i] = ta.getResourceId(i, 0);
		}
		ta.recycle();

		a.recycle();

		// 初始化控件
		initRealView(context);
	}

	/**
	 * 初始化控件
	 */
	private void initRealView(final Context context) {
		this.setOrientation(LinearLayout.HORIZONTAL);
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
			// 图标及文字
			tabItemView.setIcon(mDrawableIds[i]);
			tabItemView.setText(mLabels[i]);
			tabItemView.setSelectedColor(mSelectedColor);
			tabItemView.setUnselectedColor(mUnselectedColor);
			tabItemView.setTextSize(mTextSize);
			// 指示点ImageView
			final ImageView indicateImg = (ImageView) view.findViewById(R.id.indicate_img);
			this.addView(view, params);
			
			tabItemView.setTag(index);						// CheckedTextView设置索引作为tag，以便后续更改颜色、图片等
			mCheckedList.add(tabItemView);					// 将CheckedTextView添加到list中，便于操作
			mIndicateImgs.add(indicateImg);					// 将指示图片加到list，便于控制显示隐藏
			mViewList.add(view);							// 将各个tab的View添加到list
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setTabsDisplay(context, index);			// 设置底部图片和文字的显示
					if (null != mTabListener) {
						mTabListener.onTabSelected(index);	// tab项被选中的回调事件
					}
				}
			});

			// 初始化 底部菜单选中状态,默认第一个选中
			if (i == 0) {
				tabItemView.setIconAlpha(1.0f);
//				itemName.setTextColor(Color.rgb(247, 88, 123));
//				view.setBackgroundColor(Color.rgb(240, 241, 242));
			} else {
				tabItemView.setIconAlpha(0);
//				itemName.setTextColor(Color.rgb(19, 12, 14));
//				view.setBackgroundColor(Color.rgb(250, 250, 250));
			}

		}
		
	}

	/**
	 * 设置指示点的显示
	 * 
	 * @param context
	 * @param position
	 *            显示位置
	 * @param visible
	 *            是否显示，如果false，则都不显示
	 */
	public void setIndicateDisplay(Context context, int position,
			boolean visible) {
		int size = mIndicateImgs.size();
		if (size <= position) {
			return;
		}
		ImageView indicateImg = mIndicateImgs.get(position);
		indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置底部导航中图片显示状态和字体颜色
	 */
	public void setTabsDisplay(Context context, int index) {
		int size = mCheckedList.size();
		for (int i = 0; i < size; i++) {
			TabPageView mIconView = mCheckedList.get(i);
			if ((Integer) (mIconView.getTag()) == index) {
				Log.i("TabPageIndictor", mLabels[index] + " is selected...");
				mIconView.setIconAlpha(1.0f);
//				checkedTextView.setTextColor(Color.rgb(247, 88, 123));
//				mViewList.get(i).setBackgroundColor(Color.rgb(240, 241, 242));
			} else {
				mIconView.setIconAlpha(0);
//				checkedTextView.setTextColor(Color.rgb(19, 12, 14));
//				mViewList.get(i).setBackgroundColor(Color.rgb(250, 250, 250));
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
		setTabsDisplay(getContext(), position);
	}

	@Override
	public void setViewPager(ViewPager view, int initialPosition) {
		
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		
	}

}
