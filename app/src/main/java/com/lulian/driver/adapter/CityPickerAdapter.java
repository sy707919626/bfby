package com.lulian.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.Lines;

import java.util.ArrayList;

/**
 * Created by MARK on 2018/6/19.
 */

public class CityPickerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Lines> lines;
    public CityPickerAdapter(Context context, ArrayList<Lines> lines){
        this.context=context;
        this.lines=lines;
    }

    public void setData(ArrayList<Lines> list){
        this.lines=list;
    }

    @Override
    public int getCount() {
        if (lines != null) {
            return lines.size();
        } else {
            return 0;
        }
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
            view = LayoutInflater.from(context).inflate(R.layout.item_city, null);
            viewHandler = new ViewHandler();
            viewHandler.item_txt_start = (TextView) view.findViewById(R.id.item_txt_start);
            viewHandler.item_txt_end = (TextView) view.findViewById(R.id.item_txt_end);
            view.setTag(viewHandler);
        }else{
            view=convertView;
            viewHandler=(ViewHandler) view.getTag();
        }
        viewHandler.item_txt_start.setText(lines.get(position).getStartAreaId());
        viewHandler.item_txt_end.setText(lines.get(position).getEndAreaId());
        return view;
    }

    class ViewHandler{
        private TextView item_txt_start;
        private TextView item_txt_end;
    }
}
