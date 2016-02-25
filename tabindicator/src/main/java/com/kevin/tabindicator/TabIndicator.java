package com.kevin.tabindicator;

import android.content.Context;
import android.util.AttributeSet;
import com.kevin.tabindicator.internal.TabViewBase;

/**
 * @ClassName: TabHostView
 * @Description: 
 *
 * @version 1.0
 * @date 2016-2-24 10:16:20
 * @Author zhouwk
 */
public class TabIndicator extends TabViewBase {

	public TabIndicator(Context context) {
		super(context);
	}
	
	public TabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setSelected(boolean selected) {

	}

}
