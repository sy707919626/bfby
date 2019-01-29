package com.lulian.driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MARK on 2018/6/12.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {



    private Context context;
    int type;//0,货主;1,司机

    public CollectAdapter() {

    }

    public CollectAdapter(int type) {
        this.type = type;
    }
/*
    public void setData(List<Order> orderList) {
        this.orderList=orderList;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_collect, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (type == 0) {
            holder.collectorderyf.setVisibility(View.VISIBLE);
            holder.collectlayoutline.setVisibility(View.GONE);
            holder.collectImgStar.setVisibility(View.GONE);
        } else if (type == 1) {
            holder.collectorderyf.setVisibility(View.GONE);
            holder.collectlayoutline.setVisibility(View.VISIBLE);
            holder.collectImgStar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.carlist_img_head)
        ImageView carlistImgHead;
        @BindView(R.id.driver_img_hezuo)
        TextView driverImgHezuo;
        @BindView(R.id.item_moto_name)
        TextView itemMotoName;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.driver_txt_cardetail)
        TextView driverTxtCardetail;
        @BindView(R.id.item_moto_plateno)
        TextView itemMotoPlateno;
        @BindView(R.id.collectorderyf)
        LinearLayout collectorderyf;
        @BindView(R.id.collectlayoutline)
        LinearLayout collectlayoutline;
        @BindView(R.id.driver_img_adress)
        TextView driverImgAdress;
        @BindView(R.id.driver_img_location)
        ImageView driverImgLocation;
        @BindView(R.id.collect_img_star)
        ImageView collectImgStar;
        @BindView(R.id.driver_img_call)
        ImageView driverImgCall;
        @BindView(R.id.knownorder_layout_call)
        LinearLayout knownorderLayoutCall;
        @BindView(R.id.driver_img_select)
        TextView driverImgSelect;
        @BindView(R.id.knownorder_layout_call1)
        LinearLayout knownorderLayoutCall1;
        @BindView(R.id.allandmy_layout_centercontent)
        LinearLayout allandmyLayoutCentercontent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
