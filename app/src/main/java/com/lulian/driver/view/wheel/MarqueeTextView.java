package com.lulian.driver.view.wheel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by MARK on 2017/8/18.
 */

public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        setClickable(true);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
