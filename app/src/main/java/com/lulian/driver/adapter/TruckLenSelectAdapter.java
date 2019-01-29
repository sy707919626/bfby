package com.lulian.driver.adapter;


import android.content.Context;

import com.lulian.driver.adapter.base.AbsCommonTagSelectAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 车长选择适配器
 * @author
 */
public class TruckLenSelectAdapter extends AbsCommonTagSelectAdapter<String> {


    public TruckLenSelectAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public void setData(List<String> data) {
        if(isHasUnLimitedItem()){
            //加一个不限选项到车长列表中
            List<String> newList = new ArrayList<>(Arrays.asList(new String[data.size()]));
            Collections.copy(newList, data);
            newList.add(0,defaultUnlimitedText);
            super.setData(newList);
        }else{
            super.setData(data);
        }
    }


    @Override
    protected String getTagText(int position, String item) {
        String unLimitedItem = getUnLimitedItem();
        if(item.equals(unLimitedItem)){
            return item;
        }else{
            return item + "米";
        }
    }
}
