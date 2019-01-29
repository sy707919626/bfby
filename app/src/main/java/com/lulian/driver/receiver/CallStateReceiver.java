package com.lulian.driver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


/**
 * Created by MARK on 2018/7/4.
 */

public class CallStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
//            TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
//            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
            Intent intent1=new Intent("com.xu.mytest");
            context.startActivity(intent1);
        }
    }




    PhoneStateListener listener=new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    System.out.println("挂断");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("接听");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("响铃:来电号码"+incomingNumber);
                    //输出来电号码
                    break;
            }
        }
    };

}
