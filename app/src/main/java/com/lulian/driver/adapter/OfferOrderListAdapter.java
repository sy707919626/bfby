package com.lulian.driver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.adapter.base.BaseRvAdapter;
import com.lulian.driver.entity.server.resulte.OrderListItem;
import com.lulian.driver.utils.feature.InfoDisplayTool;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报价订单列表适配器
 * @author hxb
 */
public class OfferOrderListAdapter extends BaseRvAdapter<OrderListItem,OfferOrderListAdapter.ViewHolder> {

    public OfferOrderListAdapter(final Context mContext) {
        super(mContext);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, OrderListItem item) {
        holder.tvStartRegion.setText(item.getOnLoadArea());
        holder.tvEndRegion.setText(item.getUnLoadArea());

        String assembledInfo = InfoDisplayTool.assemble_truckType_truckLength_weight_volume(item.getAutomobileTypName(),
                item.getAutomobileLength() + "",
                item.getWeight() + "",
                item.getVolume() + "");
        holder.tvTruckTypeLenWeightVolume.setText(assembledInfo);
        holder.tvGoodsName.setText(item.getGoodsName());

        String convertedName = InfoDisplayTool.convertNameToAppellation(item.getName(), "队长");
        holder.captainName.setText(convertedName);
        holder.ratingBarGrade.setStar(item.getStar());

        bindOnClickListener(holder,R.id.iv_call,
                R.id.vg_item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflate(parent, R.layout.list_item_offer_already_order);
        return new ViewHolder(itemView);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_head)
        ImageView ivHead;//头像
        @BindView(R.id.tv_start_region)
        TextView tvStartRegion;//起始地
        @BindView(R.id.tv_end_region)
        TextView tvEndRegion;//目的地
        @BindView(R.id.tv_truck_type_len_weight_volume)
        TextView tvTruckTypeLenWeightVolume;//车型车长重量体积
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;//货物名称
        @BindView(R.id.tv_captain_name)
        TextView captainName;//车队长名字
        @BindView(R.id.ratingbar_grade)
        RatingBar ratingBarGrade;//等级

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
