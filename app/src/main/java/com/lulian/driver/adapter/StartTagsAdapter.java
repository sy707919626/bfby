package com.lulian.driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.StartTags;
import com.lulian.driver.utils.L;

import java.util.List;

/**
 * Created by MARK on 2018/6/12.
 */

public class StartTagsAdapter extends RecyclerView.Adapter<StartTagsAdapter.ViewHolder> {
    Context context;
    List<StartTags> tagList;
    public StartTagsAdapter(List<StartTags> tagList) {
        this.tagList=tagList;
    }

    public void setData(List<StartTags> tagList){
        this.tagList=tagList;
    }
    @Override
    public StartTagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_appraise, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StartTagsAdapter.ViewHolder holder, int position) {
        StartTags tag=tagList.get(position);
        holder.item_txt_tag.setText(tag.getTag());
        holder.item_txt_tagnum.setText(tag.getDeleted()+"");
        L.e("test","gciaTags:"+tag.getTag());
    }

    @Override
    public int getItemCount() {
        if(tagList!=null) {
            return tagList.size();
        }else{
            return 0;
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
