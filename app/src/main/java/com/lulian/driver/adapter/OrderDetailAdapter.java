package com.lulian.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.OrderReturnHistory;

import java.util.List;

/**
 * Created by MARK on 2018/6/19.
 */

public class OrderDetailAdapter extends BaseAdapter {
    Context context;
    List<OrderReturnHistory> backList;
    public OrderDetailAdapter(Context context,List<OrderReturnHistory> backList){
        this.context=context;
        this.backList=backList;
    }

    public void setData(List<OrderReturnHistory> backList){
        this.backList=backList;
    }

    @Override
    public int getCount() {
        return backList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=null;
        ViewHandler viewHandler;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_orderdetail_reason, null);
            viewHandler = new ViewHandler();
            viewHandler.reason_txt_name = (TextView) view.findViewById(R.id.reason_txt_name);
            viewHandler.reason_txt_remark = (TextView) view.findViewById(R.id.reason_txt_remark);

            view.setTag(viewHandler);
        }else{
            view=convertView;
            viewHandler=(ViewHandler) view.getTag();
        }
        viewHandler.reason_txt_name.setText(backList.get(position).getTobackuser());
        viewHandler.reason_txt_remark.setText(backList.get(position).getRemark());
        return view;
    }

    class ViewHandler{
        private TextView reason_txt_name,reason_txt_remark;
    }
}
