package com.kevin.tabindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ClassName: TabPageView
 * @Description: TabPageIndicator的每一个条目
 *
 * @version 1.0
 * @date 2016-2-24 10:54:41
 * @Author zhouwk
 */
public class TabPageView extends View {
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint;
	/** 选中颜色 */
	private int mSelectedColor;
	/** 未选中颜色 */
	private int mUnselectedColor;
	/** 透明度 0.0-1.0 */
	private float mAlpha = 0f;
	/** 图标 */
	private Bitmap mIconBitmap;
	/** 限制绘制icon的范围 */
	private Rect mIconRect;
	/** 底部文本内容 */
	private String mText;
	/** 底部文本大小 */
	private int mTextSize;
	private Paint mTextPaint = new Paint();
	private Rect mTextBound = new Rect();

	public TabPageView(Context context) {
		this(context, null);
	}

	public TabPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 得到绘制icon的宽
		int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());

		int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
		int top = (getMeasuredHeight() - mTextBound.height()) / 2 - bitmapWidth / 2;
		// 设置icon的绘制范围
		mIconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int alpha = (int) Math.ceil((255 * mAlpha));
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
		setupTargetBitmap(alpha);
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}

	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mSelectedColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
	}

	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(mUnselectedColor);
		mTextPaint.setAlpha(255 - alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
				- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);
	}

	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mSelectedColor);
		mTextPaint.setAlpha(alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
				- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);
	}
	
	/**
	 * 设置文字
	 * @param id
	 */
    public void setText(int id) {
        setText(getContext().getResources().getText(id));
		measureText();
    }
	
    /**
     * 设置文字
     * @param text
     */
	public void setText(CharSequence text) {
		this.mText = (String) text;
		measureText();
	}

	public void setIconAlpha(float alpha) {
		this.mAlpha = alpha;
		invalidateView();
	}

	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	/**
	 * 设置选中时颜色
	 * @param selectedColor
	 */
	public void setSelectedColor(int selectedColor) {
		this.mSelectedColor = selectedColor;
	}
	
	/**
	 * @Description: 
	 * @param unselectedColor
	 */
	public void setUnselectedColor(int unselectedColor) {
		this.mUnselectedColor = unselectedColor;
	}
	
	/**
	 * 设置文本大小
	 * @param textSize
	 */
	public void setTextSize(int textSize) {
		this.mTextSize = textSize;
		mTextPaint.setTextSize(mTextSize);
		measureText();
	}
	
	// 重新测量文本绘制范围
	private void measureText(){
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	public void setIcon(int resId) {
		this.mIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
		if (mIconRect != null)
			invalidateView();
	}

	public void setIcon(Bitmap iconBitmap) {
		this.mIconBitmap = iconBitmap;
		if (mIconRect != null)
			invalidateView();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable("instance_state", super.onSaveInstanceState());
		bundle.putFloat("state_alpha", mAlpha);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat("state_alpha");
			super.onRestoreInstanceState(bundle.getParcelable("instance_state"));
		} else {
			super.onRestoreInstanceState(state);
		}
	}

}
