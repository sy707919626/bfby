package com.lulian.driver.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lulian.driver.R;
import com.lulian.driver.view.activity.caractivity.MyDatumActivity;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MARK on 2017/6/9.
 */

public class ProjectUtil {
    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 判断是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9])|(17)[0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static void showUploadFileDialog(FragmentActivity context, AdapterView.OnItemClickListener onItemClickListener) {
        final String[] items = {"拍照", "从相册选择"};
        new CircleDialog.Builder(context)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = android.R.style.Animation_Dialog;
                    }
                })
                .setItems(items, onItemClickListener)
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = Color.RED;
                    }
                })
                .show();
    }

    /**
     * 显示吐司提示
     *
     * @param text
     * @param context
     */
    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * 网络连接是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void citypicker(CityPickerView cityPickerView, final Handler mHandler) {
        //添加默认的配置，不需要自己定义
        CityConfig cityConfig = new CityConfig.Builder().build();
        cityPickerView.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        cityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //省份
                if (province != null) {

                }
                //城市
                if (city != null) {

                }
                //地区
                if (district != null) {
//                    factorTxtStart.setText(district.getName());
//                    ProjectUtil.show(mActivity,"地区ID:"+district.getId());
                    Message msg = new Message();
                    msg.what = 100;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("fullName", province.getName() + city.getName() + district.getName());
                    map.put("aName", district.getName());
                    map.put("aId", district.getId());
                    msg.obj = map;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onCancel() {
//                ToastUtils.showLongToast(mActivity, "已取消");
            }
        });
        //显示
        cityPickerView.showCityPicker();
    }

    public static void checkCallPhone(Activity mActivity, String phone) {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
// 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CALL_PHONE)) {
// 返回值：
//如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
// 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(mActivity, "请授权！", Toast.LENGTH_LONG).show();
// 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                intent.setData(uri);
                mActivity.startActivity(intent);
            } else {
// 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
// 已经获得授权，可以打电话
            CallPhone(mActivity, phone);
        }


    }


    /**
     * 弹出 "去完善资料" 的提示框
     */
    public static void showGoToComplateDatumTip(final Context context){
        String msg="您还没有完善资料,无法进行该操作,请在我的资料里进行完善";
        String leftBtnText = "取消";
        String rightBtnText = "去完善";
        ChoiceDialogTool.showDialog(context, msg, leftBtnText, rightBtnText,
                new ChoiceDialogTool.Callback() {
                    @Override
                    public void onLeftBtnClick() {
                    }

                    @Override
                    public void onRightBtnClick() {
                        context.startActivity(new Intent(context, MyDatumActivity.class));
                    }
                });
    }


    @SuppressLint("MissingPermission")
    private static void CallPhone(final Activity mActivity, final String phone) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage("是否确定要拨打电话");

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        //跳转到拨号界面，同时传递电话号码
//                        Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phone));
//                        mActivity.startActivity(dialIntent);

                        //直接拨打电话
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + phone);
                        intent.setData(data);
                        mActivity.startActivity(intent);

                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

}
