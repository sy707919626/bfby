package com.lulian.driver.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lulian.driver.R;
import com.lulian.driver.base.CarLengthNew;
import com.lulian.driver.utils.L;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MARK on 2018/6/12.
 */

public class CarTypeBroker1Adapter extends RecyclerView.Adapter<CarTypeBroker1Adapter.ViewHolder> {
    Context context;
    List<CarLengthNew> tagList;
    private int defItem = -1;
    private OnItemListener onItemListener;

    public CarTypeBroker1Adapter(List<CarLengthNew> tagList) {
        this.tagList=tagList;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
    public interface OnItemListener {
        void onClick(ViewHolder holder, int position);
    }
    public void setDefSelect(int position) {
        this.defItem = position;
//        notifyDataSetChanged();
    }

    public void setData(List<CarLengthNew> tagList){
        this.tagList=tagList;
    }
    @Override
    public CarTypeBroker1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_appraise, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CarTypeBroker1Adapter.ViewHolder holder, final int position) {
        final String tag= tagList.get(position).lenght;
        holder.item_txt_tag.setText(tag);
        holder.item_txt_tagnum.setText("");

        boolean isSelect = tagList.get(position).isSelect;

        if (defItem != -1) {
//            if (defItem == position) {
            if(isSelect==true){
                // 选中状态
                holder.item_txt_tag.setTextColor(Color.parseColor("#ED9210"));
            }else {
                holder.item_txt_tag.setTextColor(Color.parseColor("#4D4D4D"));
            }
        }

        holder.itemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.onClick(holder, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(tagList!=null) {
            return tagList.size();
        }else{
            return 0;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_tag,item_txt_tagnum;
        LinearLayout itemlayout;
        public ViewHolder(View itemView) {
            super(itemView);
            item_txt_tag=(TextView)itemView.findViewById(R.id.item_txt_tag);
            item_txt_tagnum=(TextView)itemView.findViewById(R.id.item_txt_tagnum);
            itemlayout=(LinearLayout)itemView.findViewById(R.id.itemlayout);
        }
    }
}
