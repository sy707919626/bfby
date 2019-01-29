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
import com.lulian.driver.entity.server.resulte.DriverLeaderInfo;
import com.lulian.driver.utils.feature.InfoDisplayTool;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 车队长列表适配器
 */
public class DriverLeaderAdapter extends BaseRvAdapter<DriverLeaderInfo, DriverLeaderAdapter.ViewHolder> {

    public DriverLeaderAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, DriverLeaderInfo item) {
        holder.tvCooperationCount.setText(new StringBuilder().append("合作").append(item.getOrderCount()).append("次"));
        holder.tvCaptainName.setText(InfoDisplayTool.convertNameToAppellation(item.getName(), "队长"));
        holder.ratingBarGrade.setStar(item.getStar());

        if(item.getFavorite()==1){//是收藏的车队长
            holder.tvMineTag.setVisibility(View.VISIBLE);
            holder.tvFavoriteTag.setText("已收藏");
        }else{//不是收藏的车队长
            holder.tvMineTag.setVisibility(View.GONE);
            holder.tvFavoriteTag.setText("收藏");
        }

        holder.tvCaptainLineStart.setText(item.getStartAreaName());
        holder.tvCaptainLineEnd.setText(item.getEndAreaName());

        bindOnClickListener(holder,
                R.id.vg_favorite_click_stub,
                R.id.vg_contact_click_stub);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflate(parent, R.layout.item_driver_leader_list);
        return new ViewHolder(itemView);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;//头像
        @BindView(R.id.tv_cooperation_count)
        TextView tvCooperationCount;//合作次数
        @BindView(R.id.tv_captain_name)
        TextView tvCaptainName;//车队长名字
        @BindView(R.id.ratingbar_grade)
        RatingBar ratingBarGrade;//车队长等级
        @BindView(R.id.tv_mine_tag)
        TextView tvMineTag;//"我的"标签
        @BindView(R.id.tv_captain_line_start)
        TextView tvCaptainLineStart;//车队长负责路线起始地
        @BindView(R.id.tv_captain_line_end)
        TextView tvCaptainLineEnd;//车队长负责路线目的地

        @BindView(R.id.tv_favorite_tag)
        TextView tvFavoriteTag;//是否已收藏文字标签

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
