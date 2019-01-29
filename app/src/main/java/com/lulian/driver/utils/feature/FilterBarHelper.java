package com.lulian.driver.utils.feature;


import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.driver.R;

/**
 * 筛选栏帮助类
 */
public class FilterBarHelper {

    private View mFilterBarView;
    private Context mContext;

    private SparseArray<ViewGroup> itemVgArray =new SparseArray<>();


    public FilterBarHelper(Context context, View filterBarView) {
        this.mFilterBarView=filterBarView;
        mContext = context;
    }


    public void setShouldControlItemVgId(int ... itemVgIds){
        for(int id:itemVgIds){
            ViewGroup vg = mFilterBarView.findViewById(id);
            itemVgArray.put(id,vg);
        }
    }


    public void selectItem(int itemVgId){
        resetAllItemToNormal();

        ViewGroup selectedVg = itemVgArray.get(itemVgId);
        TextView selectedTv = (TextView) selectedVg.getChildAt(0);
        selectedTv.setTextColor(mContext.getResources().getColor(R.color.bacolor));
    }


    public void setItemTvText(int itemVgId,String text){
        ViewGroup vg = itemVgArray.get(itemVgId);
        TextView tv = (TextView) vg.getChildAt(0);
        tv.setText(text);
    }


    public void resetAllItemToNormal(){
        for(int i = 0; i< itemVgArray.size(); i++){
            ViewGroup vg = itemVgArray.valueAt(i);
            TextView tv = (TextView) vg.getChildAt(0);
            tv.setTextColor(mContext.getResources().getColor(R.color.somber1));
        }
    }




}
