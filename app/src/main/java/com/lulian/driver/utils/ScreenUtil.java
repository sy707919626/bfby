package com.lulian.driver.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author hxb
 */
public class ScreenUtil {

    public static int getScreenWidth(Context context){
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context){
        return getDisplayMetrics(context).heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }
}
