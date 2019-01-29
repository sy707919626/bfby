package com.lulian.driver.view.wheel;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 *
 * @author hxb
 */
public abstract class BaseFilterOpSelectPopup extends PopupWindow {

    protected Context mContext;

    public BaseFilterOpSelectPopup(Context context, int height) {
        super(context);
        this.mContext = context;
        initPopup(height);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initPopup(int height){
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(height);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setFocusable(true);
        setOutsideTouchable(false);

        View rootView = LayoutInflater.from(mContext).inflate(getLayoutResId(), null);
        setContentView(rootView);
    }

    /**
     * 子类实现此方法决定布局
     * @return
     */
    protected abstract int getLayoutResId();

}
