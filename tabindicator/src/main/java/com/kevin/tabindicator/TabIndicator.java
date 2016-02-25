package com.kevin.tabindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.kevin.tabindicator.internal.ITabIndicator;

/**
 * @ClassName: TabHostView
 * @Description: 
 *
 * @version 1.0
 * @date 2016-2-24 10:16:20
 * @Author zhouwk
 */
public class TabIndicator extends LinearLayout implements ITabIndicator {

	public TabIndicator(Context context) {
		this(context, null);
	}
	
	public TabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

}
