package com.lulian.driver.adapter;

import android.content.Context;

import com.lulian.driver.adapter.base.AbsCommonTagSelectAdapter;
import com.lulian.driver.entity.server.resulte.CarType;

/**
 * 车型选择适配器
 * @author hxb
 */
public class TruckTypeSelectAdapter extends AbsCommonTagSelectAdapter<CarType> {

    public TruckTypeSelectAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    protected String getTagText(int position, CarType item) {
        return item.getText();
    }


}
