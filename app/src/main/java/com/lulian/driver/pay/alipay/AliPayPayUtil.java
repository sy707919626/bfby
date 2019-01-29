package com.lulian.driver.pay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 支付宝支付
 *
 * @author Administrator
 */
public class AliPayPayUtil {
    private Activity context;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {

                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();

                        if (listener != null) {
                            listener.onSucceed();
                        }

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT)
                                    .show();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            Toast.makeText(context, "支付已取消", Toast.LENGTH_SHORT)
                                    .show();
                            if (listener != null) {
                                listener.onCancel();
                            }
                        } else {

                            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT)
                                    .show();

                            if (listener != null) {
                                listener.onFailure();
                            }
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

    };


    public AliPayPayUtil(Activity context) {
        this.context = context;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(final String orderInfo) {

       final String info = orderInfo.replace("amp;", "");

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(info, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);

            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private AliPayPayListener listener;

    public void setPayListener(AliPayPayListener listener) {
        this.listener = listener;
    }

    public interface AliPayPayListener {
        void onSucceed();

        void onFailure();

        void onCancel();
    }


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(context);
        String version = payTask.getVersion();
        Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
    }


}
