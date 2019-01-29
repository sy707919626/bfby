package com.lulian.driver.utils;

import android.util.Log;

import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;


/**
 * Created by MARK on 2017/6/8.
 */

public class L {
    //开发测试模式
//    public static boolean debug = false;

    public static void v(String tag, String msg) {
        if (GlobalValue.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (GlobalValue.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (GlobalValue.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (GlobalValue.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (GlobalValue.DEBUG) {
            Log.e(tag, msg);
        }
    }
}
