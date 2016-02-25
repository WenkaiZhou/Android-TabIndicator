package com.kevin.tabindicator.internal;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

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

    protected Paint mTextPaint = new Paint();
    protected Rect mTextBound = new Rect();

    public TabViewBase(Context context) {
        super(context);
    }

    public TabViewBase(Context context, AttributeSet attrs) {
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
    public abstract void setSelected(boolean selected);

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
