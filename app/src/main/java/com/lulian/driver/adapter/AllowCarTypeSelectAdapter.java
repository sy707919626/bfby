package com.lulian.driver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.base.BaseRvAdapter;
import com.lulian.driver.entity.AllowCarType;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 准驾车型选择列表适配器
 * @author hxb
 */
public class AllowCarTypeSelectAdapter extends BaseRvAdapter<AllowCarType,AllowCarTypeSelectAdapter.ViewHolder> {

    private int selectedPosition=-1;

    public AllowCarTypeSelectAdapter(Context mContext) {
        super(mContext);

        setChildClickListener(new OnChildClickListener() {
            @Override
            public void onClick(BaseRvAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.vg_item:
                        //局部刷新之前选择的条目 和 当前选择的条目
                        int beforeSelectedPosition=selectedPosition;
                        selectedPosition=position;
                        notifyItemChanged(beforeSelectedPosition);
                        notifyItemChanged(selectedPosition);
                        break;
                }
            }
        });
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, AllowCarType item) {
        holder.tvCarTypeCode.setText(item.getCode());
        holder.tvCarTypeText.setText(item.getText());
        holder.tvCarTypeDetail.setText(item.getDetail());

        //控制勾选框的显示
        if(selectedPosition==position){
            holder.cb.setChecked(true);
        }else{
            holder.cb.setChecked(false);
        }

        bindOnClickListener(holder,R.id.vg_item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflate(parent, R.layout.item_allow_car_type_select_list);
        return new ViewHolder(itemView);
    }


    /**
     * 获取已选择的条目的数据对象
     * @return
     */
    public AllowCarType getSelectedItem(){
        if(selectedPosition>=0){
            return getItem(selectedPosition);
        }else{
            return null;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_car_type_code)
        TextView tvCarTypeCode;
        @BindView(R.id.tv_car_type_text)
        TextView tvCarTypeText;
        @BindView(R.id.tv_car_type_detail)
        TextView tvCarTypeDetail;
        @BindView(R.id.cb)
        CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
