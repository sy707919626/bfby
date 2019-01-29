package com.lulian.driver.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.City;

import java.util.List;

/**
 * Created by MARK on 2018/6/12.
 */
public class SelectCityAdapter extends BaseAdapter {
    Context context;
    List<City> cityList;
    int location=0;
    public SelectCityAdapter(Context context, List<City> cityList){
        this.context=context;
        this.cityList=cityList;
    }

    public void setData(List<City> cityList){
        this.cityList=cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_citypicker, null);
            viewHandler = new ViewHandler();
            viewHandler.item_txt_end = (TextView) view.findViewById(R.id.item_txt_end);
            viewHandler.ll_ba=(RelativeLayout)view.findViewById(R.id.ll_ba);
            view.setTag(viewHandler);
        }else{
            view=convertView;
            viewHandler=(ViewHandler) view.getTag();
        }
        viewHandler.item_txt_end.setText(cityList.get(position).getName());
        if(location==position){
            viewHandler.ll_ba.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }else{
            viewHandler.ll_ba.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        return view;
    }

    class ViewHandler{
        private TextView item_txt_end;
        private RelativeLayout ll_ba;
    }

    public void setSeclection(int position) {
        location = position;
    }
}

