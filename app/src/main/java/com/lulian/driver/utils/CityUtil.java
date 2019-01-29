package com.lulian.driver.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;


/**
 * Created by MARK on 2018/6/19.
 */

public class CityUtil {
    private Context context;
    public CityUtil(Context cntext){
        this.context=cntext;
    }
    public void getCity(final Handler mHandler){

        CityPicker cityPicker = new CityPicker.Builder(context).textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#234Dfa")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("广东省")
                .city("深圳市")
                .district("宝安区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();;
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... strings) {
                 HashMap<String,String> map=new HashMap<>();
                //省份
                String province = strings[0];
                //城市
                String city = strings[1];
                //区县
                String district = strings[2];
                map.put("pcd",province+city+district);
                map.put("cd",city+district);
                //邮编
                String code = strings[3];
                Toast.makeText(context,province+"-"+city+"-"+district,Toast.LENGTH_LONG).show();
                Message msg=new Message();
                msg.what=2;
                msg.obj=map;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onCancel() {

            }
        });
    }

}
