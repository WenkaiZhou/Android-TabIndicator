package com.kevin.tabindicator.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.kevin.tabindicator.R;

/**
 * 版权所有：XXX有限公司
 *
 * IndicateView
 *
 * @author zhou.wenkai ,Created on 2016-2-26 15:05:08
 * 		   Major Function：<b>提醒指示点</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class IndicateView extends TextView {

    /** 背景图 */
    private Bitmap mBackgroundBitmap;
    /** 限制绘制背景的范围 */
    protected Rect mBackgroundRect;

    public IndicateView(Context context) {
        super(context);
    }

    public IndicateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("a");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 得到绘制icon的宽
        int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom());

        int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
        int top = getMeasuredHeight() / 2 - bitmapWidth / 2;
        // 设置绘制背景的范围
        mBackgroundRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(mBackgroundBitmap, null, mBackgroundRect, null);
//    }

    /**
     * 设置指示点的显示
     *
     * @param visible
     *            是否显示，如果false，则都不显示
     */
    public void setDisplay(boolean visible) {
        setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置背景资源
     * @param resId
     */
//    public void setBackground(int resId) {
//        this.mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), resId);
//        if (mBackgroundRect != null)
//            invalidateView();
//    }

    /**
     * 设置背景图片
     * @param bitmap
     */
    public void setBackground(Bitmap bitmap) {
        this.mBackgroundBitmap = bitmap;
        if (mBackgroundRect != null)
            invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
