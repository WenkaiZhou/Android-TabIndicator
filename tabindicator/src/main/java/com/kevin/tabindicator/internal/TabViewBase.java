package com.kevin.tabindicator.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kevin.tabindicator.R;

/**
 * 版权所有：XXX有限公司
 *
 * TabViewBase
 *
 * @author zhou.wenkai ,Created on 2016-2-25 22:20:27
 * 		   Major Function：<b>底部导航条目操作基类</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public abstract class TabViewBase extends View implements ITabView {

    /** 底部文本内容 */
    protected String mText;
    /** 底部文本大小 */
    protected int mTextSize;
    /** 选中颜色 */
    protected int mSelectedColor;
    /** 未选中颜色 */
    protected int mUnselectedColor;
    /** 限制绘制icon的范围 */
    protected Rect mIconRect;
    /** 限制绘制指示点的范围 */
    private Rect mIndicatorRect;
    /** 是否显示指示点 */
    private boolean isIndicateDisplay;
    /** 指示点大小 */
    private int mIndicatorSize;
    /** 指示点图片 */
    private Bitmap mIndicatorBitmap;

    protected boolean isSelected;

    protected Paint mTextPaint = new Paint();
    protected Rect mTextBound = new Rect();

    public TabViewBase(Context context) {
        this(context, null);
    }

    public TabViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        setIndicatorBitmap(R.drawable.update_hint);
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
        // 设置指示点的范围
//        mIndicatorRect = new Rect(left + bitmapWidth * 2/3, top, left+bitmapWidth, top + bitmapWidth /3);
        int indicatorRadius = mIndicatorSize / 2;
        int tabRealHeight = bitmapWidth + mTextBound.height();
        mIndicatorRect = new Rect(left + tabRealHeight* 4/5 - indicatorRadius, top, left+tabRealHeight* 4/5 + indicatorRadius, top + mIndicatorSize);
    }

    /**
     * 绘制文字
     * @param canvas
     */
    protected void drawTargetText(Canvas canvas) {
        mTextPaint.setColor(isSelected ? mSelectedColor : mUnselectedColor);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
                        - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    /**
     * 绘制指示点
     * @param canvas
     */
    protected void drawIndicator(Canvas canvas) {
        if(isIndicateDisplay) {
            canvas.drawBitmap(mIndicatorBitmap, null, mIndicatorRect, null);
        }
    }

    @Override
    public void setText(int id) {
        setText(getContext().getResources().getText(id));
        measureText();
    }

    @Override
    public void setText(CharSequence text) {
        this.mText = (String) text;
        measureText();
    }

    @Override
    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
        invalidateView();
    }

    @Override
    public void setUnselectedColor(int unselectedColor) {
        this.mUnselectedColor = unselectedColor;
        invalidateView();
    }

    @Override
    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        measureText();
    }

    @Override
    public void setIndicatorSize(int indicatorSize) {
        this.mIndicatorSize = indicatorSize;
    }

    @Override
    public void setIndicatorBitmap(Bitmap bitmap) {
        this.mIndicatorBitmap = bitmap;
    }

    @Override
    public void setIndicatorBitmap(int resId) {
        this.mIndicatorBitmap = BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    public abstract void setSelected(boolean selected);

    /**
     * 设置切换颜色渐变
     * @param alpha
     */
    protected void setIconAlpha(float alpha){}

    /**
     * 设置指示点的显示
     *
     * @param visible
     *            是否显示，如果false，则都不显示
     */
    public void setIndicateDisplay(boolean visible) {
        this.isIndicateDisplay = visible;
    }

    // 重新测量文本绘制范围
    private void measureText(){
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    protected void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
