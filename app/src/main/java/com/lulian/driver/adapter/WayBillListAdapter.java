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
import com.lulian.driver.entity.server.resulte.WayBillListItem;
import com.lulian.driver.utils.feature.InfoDisplayTool;
import com.lulian.driver.utils.feature.WayBillBtnOp;
import com.lulian.driver.view.DictDataTool;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运单列表适配器
 * @author hxb
 */
public class WayBillListAdapter extends BaseRvAdapter<WayBillListItem,WayBillListAdapter.ViewHolder> {

    private Map<Integer,String> statusMap;

    public WayBillListAdapter(Context mContext) {
        super(mContext);
        statusMap = DictDataTool.getWayBillStatusMap();
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, WayBillListItem item) {
        holder.tvStatus.setText(statusMap.get(item.getStatus()));
        holder.tvStartRegion.setText(item.getOnLoadArea());
        holder.tvEndRegion.setText(item.getUnLoadArea());

        String assembledInfo = InfoDisplayTool.assemble_truckType_truckLength_weight_volume(item.getAutomobileTypName(),
                null, item.getWeight() + "",
                item.getVolume() + "");
        holder.tvTruckTypeLenWeightVolume.setText(assembledInfo);

        holder.tvGoodsName.setText(item.getGoodsName());
        holder.tvShipperName.setText(item.getUserName());
        holder.tvShipperType.setText(item.getUsertype());
        holder.ratingBarGrade.setStar(item.getStar());
        holder.tvTime.setText(item.getCreateTime());

        bindOnClickListener(holder,
                R.id.vg_operation,
                R.id.vg_item);

        setOpToBtn(holder.vgOperation,item.getStatus());
    }


    /**
     * 设置按钮的操作状态
     */
    private void setOpToBtn(ViewGroup vgOperation,int status){
        WayBillBtnOp op = null;
        switch (status) {
            case 1://待取货
                op = WayBillBtnOp.CONFIRM_ARRIVE_LOAD_POINT;
                break;
            case 2://装车
                op = WayBillBtnOp.CONFIRM_START_OFF;
                break;
            case 3://运送中
                op = WayBillBtnOp.CONFIRM_ARRIVE_DEST;
                break;
            case 4://已到达
                op = WayBillBtnOp.CONFIRM_GOODS_ARRIVE;
                break;
            case 12://已卸货
                op = WayBillBtnOp.REMIND_PAY;
                break;
            case 5://已完成
                op = WayBillBtnOp.APPRAISE;
                break;
        }
        if(op!=null){
            vgOperation.setTag(op);
            TextView tvOpText = vgOperation.findViewById(R.id.tv_operation_text);
            tvOpText.setText(op.getText());
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflate(parent, R.layout.list_item_way_bill);
        return new ViewHolder(itemView);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_way_bill_status)
        TextView tvStatus;//运单状态
        @BindView(R.id.tv_load_region)
        TextView tvStartRegion;//起始地
        @BindView(R.id.tv_unload_region)
        TextView tvEndRegion;//目的地
        @BindView(R.id.tv_truck_type_len_weight_volume)
        TextView tvTruckTypeLenWeightVolume;//车型车长重量体积
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;//货物名称
        @BindView(R.id.tv_time)
        TextView tvTime;//时间范围(派车时间)
        @BindView(R.id.ratingbar_grade)
        RatingBar ratingBarGrade;//发货人的等级
        @BindView(R.id.iv_head)
        ImageView ivHead;//发货人的头像
        @BindView(R.id.tv_shipper_name)
        TextView tvShipperName;//发货人姓名
        @BindView(R.id.tv_shipper_user_type)
        TextView tvShipperType;//发货人的用户类型
        @BindView(R.id.vg_operation)
        ViewGroup vgOperation;//底部操作按钮
        @BindView(R.id.tv_operation_text)
        TextView tvOperationText;//操作按钮显示的文字

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
