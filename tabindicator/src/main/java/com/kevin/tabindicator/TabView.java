package com.kevin.tabindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.kevin.tabindicator.internal.TabViewBase;

/**
 * 版权所有：XXX有限公司
 *
 * TabView
 *
 * @author zhou.wenkai ,Created on 2016-2-25 23:15:39
 * 		   Major Function：<b>TabPageIndicator的每一个条目</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class TabView extends TabViewBase {

	/** 图标 */
	private Bitmap mSelectedIconBitmap;
	private Bitmap mUnselectedIconBitmap;

	public TabView(Context context) {
		super(context);
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		setupTargetBitmap(canvas);
		drawIndicator(canvas);
		if(null != mText) {
			drawTargetText(canvas);
		}
	}

	/**
	 * 绘制图标图片
	 * @param canvas
	 */
	private void setupTargetBitmap(Canvas canvas) {
		canvas.drawBitmap(isSelected ? mSelectedIconBitmap : mUnselectedIconBitmap, null, mIconRect, null);
	}

	@Override
	public void setSelected(boolean selected) {
		this.isSelected = selected;
		invalidateView();
	}

	public void setSelectedIcon(int resId) {
		this.mSelectedIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
		if (mIconRect != null)
			invalidateView();
	}

	public void setSelectedIcon(Bitmap iconBitmap) {
		this.mSelectedIconBitmap = iconBitmap;
		if (mIconRect != null)
			invalidateView();
	}

	public void setUnselectedIcon(int resId) {
		this.mUnselectedIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
		if (mIconRect != null)
			invalidateView();
	}

	public void setUnselectedIcon(Bitmap iconBitmap) {
		this.mUnselectedIconBitmap = iconBitmap;
		if (mIconRect != null)
			invalidateView();
	}
}
