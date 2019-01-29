package com.lulian.driver.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.OrderNew;
import com.lulian.driver.listener.TransmitStrListener;
import com.lulian.driver.presenter.PClass;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.activity.caractivity.OrderDetailsActivity;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/12.
 */

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.ViewHolder> implements View.OnClickListener {
    TransmitStrListener transmitStrListener;
    Context context;
    List<OrderNew> orderList;
    AppData app;
    PClass pClass;
    RxAppCompatActivity mActivity;
    int neType;
    public AllOrderAdapter(){

    }
    public AllOrderAdapter(List<OrderNew> orderList, AppData app,
                           PClass pClass, RxAppCompatActivity mActivity, int neType) {
        this.orderList=orderList;
        this.app = app;
        this.pClass = pClass;
        this.mActivity = mActivity;
        this.neType=neType;
    }

    public void setData(List<OrderNew> orderList) {
        this.orderList=orderList;
    }

    @Override
    public AllOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_all, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AllOrderAdapter.ViewHolder holder, int position) {
        OrderNew order = orderList.get(position);
        setValue(order, holder);
        setBtnState(order, holder);
    }

    /**
     *  为按钮设置点击监听 并且 将WayBill对象绑到按钮
     */
    private void setClickListenerAndTag(OrderNew wb, View ... vs){
        for(View v : vs){
            v.setOnClickListener(this);
            v.setTag(wb);
        }
    }

    private void setValue(final OrderNew order, ViewHolder holder) {

        String goods = order.getGoods().substring(0, order.getGoods().indexOf("-"));
        String number = order.getGoods().substring(order.getGoods().indexOf("x")+1,  order.getGoods().length() - 1);

        //衣服-0.00-&#x20
        holder.allorder_txt_start.setText(order.getOnLoadArea().trim());
        holder.allorder_txt_end.setText(order.getUnLoadArea().trim());
        holder.allorder_txt_type.setText(goods);
        holder.order_name.setText(order.getName());
        holder.allorder_txt_weight.setText(order.getAutomobileTypName() + "/" + order.getAutomobileLength() + "米/" +
                number + "吨");
        holder.ratingbar.setStar((float) order.getStar()); //星级
        holder.order_txt_before.setText(order.getCreateTime());
        Picasso.get().load(order.getHeaderUrl()).memoryPolicy(NO_CACHE, NO_STORE).into(holder.allorder_img_head);

    }

    private void setBtnState(final OrderNew order, ViewHolder holder) {
        holder.order_layout_userinfo.setOnClickListener(this);
        holder.order_btn_phone.setOnClickListener(this);
        holder.allorderLayoutCentercontent.setOnClickListener(this);

        setClickListenerAndTag(order,
                holder.order_layout_userinfo,
                holder.order_btn_phone,
                holder.allorderLayoutCentercontent);
    }

    @Override
    public int getItemCount() {
        if (orderList.size() > 0) {
            return orderList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
        OrderNew order = (OrderNew) view.getTag();
        switch (view.getId()) {
            case R.id.allorder_layout_centercontent:
                OrderDetailsActivity.start(context, order.getId(), 1);
                break;

            case R.id.order_btn_phone:
                ProjectUtil.checkCallPhone((Activity)context, order.getPhone());
                break;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout allorderLayoutCentercontent,order_layout_userinfo, order_layout_right;
        RelativeLayout order_layout_content;
        Button order_btn_phone;
        TextView allorder_txt_start, allorder_txt_end,
                order_distance, allorder_txt_weight,
                order_name, order_txt_before, allorder_txt_type;
        com.hedgehog.ratingbar.RatingBar ratingbar;
        ImageView allorder_img_head, order_txt_userlevel;

        public ViewHolder(View itemView) {
            super(itemView);
            order_layout_content = (RelativeLayout) itemView.findViewById(R.id.order_layout_content);
            allorderLayoutCentercontent = (LinearLayout) itemView.findViewById(R.id.allorder_layout_centercontent);
            order_btn_phone = (Button) itemView.findViewById(R.id.order_btn_phone);
            allorder_txt_start = (TextView) itemView.findViewById(R.id.allorder_txt_start);
            allorder_txt_end = (TextView) itemView.findViewById(R.id.allorder_txt_end);
            order_distance = (TextView) itemView.findViewById(R.id.order_distance);

            allorder_txt_weight = (TextView) itemView.findViewById(R.id.allorder_txt_weight);
            order_name = (TextView) itemView.findViewById(R.id.order_name);
            allorder_txt_type = (TextView) itemView.findViewById(R.id.allorder_txt_type);

            ratingbar = (com.hedgehog.ratingbar.RatingBar) itemView.findViewById(R.id.ratingbar);
            order_txt_before = (TextView) itemView.findViewById(R.id.order_txt_before);
            allorder_img_head = (ImageView) itemView.findViewById(R.id.allorder_img_head);
            order_txt_userlevel = (ImageView) itemView.findViewById(R.id.order_txt_userlevel);
            order_layout_userinfo=(LinearLayout)itemView.findViewById(R.id.order_layout_userinfo);
            order_layout_right = (LinearLayout) itemView.findViewById(R.id.order_layout_right);
        }
    }
    public void setTransmitStrListener(TransmitStrListener listener){
        this.transmitStrListener=listener;
    }
}
