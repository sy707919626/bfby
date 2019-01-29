package com.lulian.driver.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.DriverVeDetailTag;
import com.lulian.driver.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARK on 2018/6/12.
 */

public class AppraiseAdapter extends RecyclerView.Adapter<AppraiseAdapter.ViewHolder> {
    Context context;
    int appraiseType;
    List<DriverVeDetailTag> tagList;
    List<DriverVeDetailTag> goodList=null;
    List<DriverVeDetailTag> badList=null;
    Handler mHandler;
    public AppraiseAdapter( List<DriverVeDetailTag> tagList,int appraiseType,Handler mHandler) {
        this.tagList=tagList;
        this.appraiseType=appraiseType;
        this.mHandler=mHandler;
        if(goodList==null){
            goodList=new ArrayList<>();
        }
        if(badList==null){
            badList=new ArrayList<>();
        }
        for(DriverVeDetailTag detailTag:tagList){
            if(detailTag.getFlag()==1){//好评
                goodList.add(detailTag);
            }else{//差评
                badList.add(detailTag);
            }
        }
    }

    public void setData(List<DriverVeDetailTag> tagList){
        this.tagList=tagList;
    }
    @Override
    public AppraiseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_appraise, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppraiseAdapter.ViewHolder holder, int position) {
        DriverVeDetailTag tag=null;
        L.e("test1","position:"+position);
        L.e("test1","goodList:"+goodList.size());
        L.e("test1","badList:"+badList.size());
        if(appraiseType==R.id.detail_list_goodreputaion) {
            tag= goodList.get(position);
        }else{
            tag=badList.get(position);
        }
        holder.item_txt_tag.setText(tag.getTag());
        holder.item_txt_tagnum.setText(tag.getCount()+"");
        if(goodList.size()==0){
            mHandler.sendEmptyMessage(1);
        }else if(badList.size()==0){
            mHandler.sendEmptyMessage(2);
        }
    }

    @Override
    public int getItemCount() {
        if(appraiseType==R.id.detail_list_goodreputaion) {
            return goodList.size();
        }else{
             return badList.size();
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_tag,item_txt_tagnum;
        public ViewHolder(View itemView) {
            super(itemView);
            item_txt_tag=(TextView)itemView.findViewById(R.id.item_txt_tag);
            item_txt_tagnum=(TextView)itemView.findViewById(R.id.item_txt_tagnum);
        }
    }
}
