package com.lulian.driver.utils.feature;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.gson.reflect.TypeToken;
import com.lulian.driver.entity.RegionBean;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 用来获取地区数据的工具类
 */
public class RegionDataParser {

    private Context mContext;
    private Callback mCallback;

    public interface Callback{
        void onResult(List<RegionBean> data);
    }

    public RegionDataParser(Context context) {
        this.mContext = context;
    }

    public void getData(Callback callback){
        this.mCallback = callback;

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, List<RegionBean>> task = new AsyncTask<Void, Void, List<RegionBean>>() {
            @Override
            protected List<RegionBean> doInBackground(Void... voids) {
                String json = getJson(mContext, "regions_data.json");

                return GsonUtil.get().fromJson(json,new TypeToken<List<RegionBean>>(){}.getType());
            }

            @Override
            protected void onPostExecute(List<RegionBean> regionBeans) {
                mCallback.onResult(regionBeans);
            }
        };

        task.execute();
    }


    private String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
