package com.lulian.driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.MyWalletDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MARK on 2018/6/12.
 */

public class WalletDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyWalletDetail> list;
    private LayoutInflater lif;
    private String userId;


    public WalletDetailAdapter(Context context, List<MyWalletDetail> list, String userId) {
        this.context = context;
        this.list = list;
        this.userId=userId;
        lif=LayoutInflater.from(context);
    }


    public void setData(List<MyWalletDetail> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=lif.inflate(R.layout.item_wallet_detail,parent,false);
        ItemHolder holder = new ItemHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyWalletDetail itemData=list.get(position);
        ItemHolder ih= (ItemHolder) holder;

        //判断是出账还是入账(取付款账户id来判断)
        String pId=itemData.getPayerAccountId();
        String amountPrefix;
        int textColor=0;
        if(pId==userId){//出账
            ih.tvRecordType.setText("出账");
            textColor=context.getResources().getColor(R.color.green);
            amountPrefix = "-";
        }else{//入账
            ih.tvRecordType.setText("入账");
            textColor=context.getResources().getColor(R.color.red);
            amountPrefix = "+";
        }
        ih.tvRecordType.setTextColor(textColor);

        //金额
        ih.tvAmount.setText(amountPrefix+itemData.getMoney());
        ih.tvAmount.setTextColor(textColor);
        //时间
        ih.tvTime.setText(itemData.getCreateTime());

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }


    public static class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_record_type)
        TextView tvRecordType;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
