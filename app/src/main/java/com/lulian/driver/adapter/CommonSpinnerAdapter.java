package com.lulian.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lulian.driver.R;
import com.lulian.driver.base.SpinnerItem;

import java.util.List;

/**
 * 通用的spinner适配器
 * @author hxb
 */
public class CommonSpinnerAdapter extends BaseAdapter {

    private List<SpinnerItem> list;
    private Context context;

    public CommonSpinnerAdapter(List<SpinnerItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<SpinnerItem> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list!=null ? list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if(convertView==null){
            v = LayoutInflater.from(context).inflate(R.layout.item_common_spinner, null);
        }else{
            v=convertView;
        }

        SpinnerItem item = list.get(position);
        TextView tv= (TextView) v.findViewById(R.id.item_txt);
        tv.setText(item.getText());

        return v;
    }
}
