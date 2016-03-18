package com.kevin.tabindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

import com.kevin.tabindicator.internal.TabViewBase;

/**
 * 版权所有：XXX有限公司
 *
 * TabPageView
 *
 * @author zhou.wenkai ,Created on 2016-2-27 16:09:06
 * 		   Major Function：<b>TabPageIndicator的每一个条目</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class TabPageView extends TabViewBase {

	private Paint mPaint = new Paint();
	/** 透明度 0.0-1.0 */
	private float mAlpha = 0f;
	/** 图标 */
	private Bitmap mSelectedIconBitmap;
	private Bitmap mUnselectedIconBitmap;

	public TabPageView(Context context) {
		super(context);
	}

	public TabPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int alpha = (int) Math.ceil((255 * mAlpha));
		drawSourceBitmap(canvas, alpha);
		drawTargetBitmap(canvas, alpha);
		if(null != mText) {
			drawSourceText(canvas, alpha);
			drawTargetText(canvas, alpha);
		}
		drawIndicator(canvas);
	}

	/**
	 * 绘制未选中图标
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceBitmap(Canvas canvas, int alpha) {
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(255 - alpha);
		canvas.drawBitmap(mUnselectedIconBitmap, null, mIconRect, mPaint);
	}

	/**
	 * 绘制选中图标
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetBitmap(Canvas canvas, int alpha) {
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		canvas.drawBitmap(mSelectedIconBitmap, null, mIconRect, mPaint);
	}

	/**
	 * 绘制未选中文字
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(mUnselectedColor);
		mTextPaint.setAlpha(255 - alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
						- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);
	}

	/**
	 * 绘制选中文字
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mSelectedColor);
		mTextPaint.setAlpha(alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
						- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);
	}


	@Override
	public void setSelected(boolean selected) {
		if(selected) {
			setIconAlpha(1.0f);
		} else {
			setIconAlpha(0f);
		}
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

	public void setIconAlpha(float alpha) {
		this.mAlpha = alpha;
		invalidateView();
	}

}
