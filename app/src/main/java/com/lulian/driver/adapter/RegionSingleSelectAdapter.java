package com.lulian.driver.adapter;

import android.content.Context;

import com.lulian.driver.adapter.base.AbsRegionSelectAdapter;
import com.lulian.driver.entity.RegionBean;


/**
 * 地区单选列表适配器
 */
public class RegionSingleSelectAdapter extends AbsRegionSelectAdapter {

    /**
     * 当前选择的区域
     */
    private RegionBean selectedRegion;


    /**
     * 外部设置进来的监听器
     */
    private Callback callback;

    public interface Callback{
        void onRegionSelected(RegionBean region);//选择了一个地区的回调
    }

    public RegionSingleSelectAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onRegionSelected(RegionBean region) {
        selectedRegion = region;
        if(callback!=null){
            callback.onRegionSelected(selectedRegion);
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    @Override
    protected boolean itemIsSelected(RegionBean item, int position) {
        if(selectedRegion!=null && selectedRegion.equals(item)){
            return true;
        }
        return false;
    }



}
