package com.lulian.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.CarType;

import java.util.List;

/**
 * Created by MARK on 2018/6/19.
 */

public class CarLengthAdapter extends BaseAdapter {
    Context context;
    List<String> lines;
    public CarLengthAdapter(Context context, List<String> lines){
        this.context=context;
        this.lines=lines;
    }

    public void setData(List<String> lines){
        this.lines=lines;
    }

    @Override
    public int getCount() {
        return lines.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_cartype, null);
            viewHandler = new ViewHandler();
            viewHandler.item_txt_type = (TextView) view.findViewById(R.id.item_txt_type);
            view.setTag(viewHandler);
        }else{
            view=convertView;
            viewHandler=(ViewHandler) view.getTag();
        }
        viewHandler.item_txt_type.setText(lines.get(position));
        return view;
    }

    class ViewHandler{
        private TextView item_txt_type;
    }
}
