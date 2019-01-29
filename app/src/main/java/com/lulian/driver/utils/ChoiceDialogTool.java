package com.lulian.driver.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.lulian.driver.R;

/**
 * 选择提示框工具(两个选择按钮)
 * @author hxb
 */
public class ChoiceDialogTool {

    public interface Callback{
        void onLeftBtnClick();
        void onRightBtnClick();
    }


    public static void showDialog(Context context,String msg,String leftBtnText,String rightBtnText,Callback callBack){
        buildDialog(context, msg, leftBtnText, rightBtnText, callBack).show();

    }


    public static void showDialog(Context context,String msg,Callback callBack){
        buildDialog(context, msg, null, null, callBack).show();

    }


    public static AlertDialog.Builder buildDialog(Context context,String msg,String leftBtnText,String rightBtnText, final Callback callBack){

        String defaultTitle = context.getResources().getString(R.string.notifyTitle);
        String defaultLeftBtnText = "取消";
        String defaultRightBtnText = "确定";


        msg = TextUtils.isEmpty(msg) ? "" : msg;
        leftBtnText = TextUtils.isEmpty(leftBtnText) ? defaultLeftBtnText : leftBtnText;
        rightBtnText = TextUtils.isEmpty(rightBtnText) ? defaultRightBtnText : rightBtnText;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(defaultTitle);
        builder.setMessage(msg);

        builder.setNegativeButton(leftBtnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callBack!=null){
                            callBack.onLeftBtnClick();
                        }
                    }
                });

        builder.setPositiveButton(rightBtnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callBack!=null){
                            callBack.onRightBtnClick();
                        }
                    }
                });

        builder.setCancelable(false);

        return builder;
    }

}
