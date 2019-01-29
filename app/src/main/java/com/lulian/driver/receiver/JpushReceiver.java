package com.lulian.driver.receiver;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lulian.driver.utils.L;
import com.lulian.driver.utils.RxBus;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by MARK on 2017/6/21.
 */

public class JpushReceiver extends BroadcastReceiver {

    private static final String TAG = "JpushReceiver";
    private static final String TYPE_THIS = "detail";
    private static final String TYPE_ANOTHER = "setting";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + bundle.toString());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            L.e(TAG, "接受到推送下来的自定义消息");


//            if (isMainActivity(context)) {
//                L.e(TAG, "当前在MainActivity");
//
////                receivingMessage(context, bundle);
//            } else {
//                receivingNotification(context, bundle);
//            }

            receivingMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            L.e(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            L.e(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            L.e(TAG, "用户注册了极光推送:id::" + title);
        } else {
            L.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
    //"cn.jpush.android.intent.REGISTRATION"   JPushInterface.ACTION_REGISTRATION_ID

    /**
     * 判断主页界面是否在前台
     *
     * @return 是否在前台显示
     */
    public static boolean isMainActivity(Context context) {
        String className = "com.lulian.driver.MainActivity";
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }

    private void receivingMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);

//        ProjectUtil.show(context, "消息extras: " +extras);

        RxBus.getInstance().post(extras);
   /*     NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(com.allenliu.versionchecklib.R.mipmap.ic_launcher);
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setTicker(context.getString(R.string.versionchecklib_downloading));
        builder.setContentText("message:"+message);

        Notification notification = builder.build();
        notification.vibrate = new long[]{500, 500};
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
        manager.notify(0, notification);*/

//        Intent mIntent = new Intent(context, MainActivity.class);
//        mIntent.putExtra("message", message);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(mIntent);


//        Gson gson = new Gson();
           /* ResultBean result = gson.fromJson(extras, ResultBean.class);
            if ("ok".equals(result.getOk())) {
                Intent mIntent = new Intent(context, DetailActivity.class);
                mIntent.putExtra("id", result.getId());
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            }*/
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);
//        ProjectUtil.show(context, "通知extras:"+extras);
        int notiId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        GlobalValue.NOTIFICATION_ID = notiId;


        RxBus.getInstance().post(extras);

    }

    private void openNotification(Context context, Bundle bundle) {
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        String myValue = "";
//
//        Intent mIntent = new Intent(context, MainActivity.class);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(mIntent);

         /*   try {
                JSONObject extrasJson = new JSONObject(extras);
                myValue = extrasJson.optString("myKey");
            } catch (Exception e) {
                Log.w(TAG, "Unexpected: extras is not a valid json", e);
                return;
            }
            if (TYPE_THIS.equals(myValue)) {
                Intent mIntent = new Intent(context, DetailActivity.class);
                mIntent.putExtras(bundle);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            } else if (TYPE_ANOTHER.equals(myValue)) {
                Intent mIntent = new Intent(context, SettingActivity.class);
                mIntent.putExtras(bundle);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            }*/
    }
}

/*
*
*清除通知 API

支持的版本

开始的版本：1.3.5。
功能说明

推送通知到客户端时，由 JPush SDK 展现通知到通知栏上。
此 API 提供清除通知的功能，包括：清除所有 JPush 展现的通知（不包括非 JPush SDK 展现的）；清除指定某个通知。
API - clearAllNotifications

接口定义

public static void clearAllNotifications(Context context);
参数说明

Context context： 应用的ApplicationContext
API - clearNotificationById

接口定义

public static void clearNotificationById(Context context, int notificationId);
参数说明

+ Context context：应用的ApplicationContext
+ int notificationId：通知ID
 此 notificationId 来源于intent参数 JPushInterface.EXTRA_NOTIFICATION_ID，可参考文档 接收推送消息Receiver
*
* */