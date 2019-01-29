package com.lulian.driver.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.DriverLeaderInfo;
import com.lulian.driver.presenter.PClass;
import com.lulian.driver.utils.GlobalValue;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MARK on 2018/6/12.
 */

public class DriverLeaderAllAdapter extends RecyclerView.Adapter<DriverLeaderAllAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    String id;//区分我的车队与全部,用来控制不同的布局  1:我的车队; 2:全部
    List<DriverLeaderInfo> driverLeaderList;
    Activity mActivity;
    AppData app;
    PClass pClass;
    Handler mHandler;

    public DriverLeaderAllAdapter(String id, List<DriverLeaderInfo> driverLeaderList, int type, Activity mActivity, Handler mHandler) {
        this.id = id;
        this.driverLeaderList = driverLeaderList;
        this.mActivity = mActivity;
        this.app = app;
        this.pClass = pClass;
        this.mHandler = mHandler;
    }

    public void setData(List<DriverLeaderInfo> driverLeaderList) {
        this.driverLeaderList = driverLeaderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_allandmy, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (driverLeaderList != null) {
            final DriverLeaderInfo driverLeaderInfo = driverLeaderList.get(position);
            setValue(driverLeaderInfo, holder);
        }
    }

    @Override
    public int getItemCount() {
        if (driverLeaderList != null) {
            return driverLeaderList.size();
        } else {
            return 0;
        }
    }

    public void setValue(final DriverLeaderInfo driverLeaderInfo, ViewHolder holder) {
        holder.itemDriverName.setText(driverLeaderInfo.getName());
        holder.ratingbar.setStar(driverLeaderInfo.getStar());
        holder.waybillDetailTxtStart.setText(driverLeaderInfo.getStartAreaId());
        holder.waybillDetailTxtEnd.setText(driverLeaderInfo.getEndAreaId());


        if (id.equals("2")) {
            if (driverLeaderInfo.getFavorite() == 1) {//收藏的车队长(我的)
                holder.mylaoutuser.setVisibility(View.VISIBLE);
                holder.driverTextStart.setText("已收藏");
                holder.driverImgStart.setImageResource(R.drawable.starton);

            } else {
                holder.mylaoutuser.setVisibility(View.GONE);
                holder.driverTextStart.setText("收藏");
                holder.driverImgStart.setImageResource(R.drawable.startno);
                holder.driverImgStart.setEnabled(true);
            }
        } else {
            holder.mylaoutuser.setVisibility(View.VISIBLE);
            holder.driverTextStart.setText("已收藏");
            holder.driverImgStart.setImageResource(R.drawable.starton);

        }

//        Picasso.with(context)
//                .load(assembleImgUrl(driverLeaderInfo.getHeaderUrl())).memoryPolicy(NO_CACHE, NO_STORE).into(holder.carlistImgHead);

        holder.driverImgHezuo.setText("合作" + driverLeaderInfo.getOrderCount() + "次");

        holder.driverImgStart.setOnClickListener(this);
        holder.driverImgCall.setOnClickListener(this);

        setClickListenerAndTag(driverLeaderInfo,
                holder.driverImgCall,
                holder.driverImgStart);
    }

    //获取图片完整路径
    public String assembleImgUrl(String url){
        String path = GlobalValue.BASEURL + url.replaceFirst("/", "");
        L.e("assembleImgUrl",path);
        return path;
    }

    /**
     *  为按钮设置点击监听 并且 将WayBill对象绑到按钮
     */
    private void setClickListenerAndTag(DriverLeaderInfo wb, View ... vs){
        for(View v : vs){
            v.setOnClickListener(this);
            v.setTag(wb);
        }
    }

    @Override
    public void onClick(View view) {
        DriverLeaderInfo deinfo = (DriverLeaderInfo) view.getTag();
        switch (view.getId()) {
            case R.id.driver_img_call:
                ProjectUtil.checkCallPhone(mActivity, deinfo.getPhone());
                break;
            case R.id.driver_img_start:
                if (deinfo.getFavorite() == 1) {
                    ProjectUtil.show(mActivity, "当前车队长已收藏");
                }
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("userid", deinfo.getId());  //往Bundle中存放数据
                msg.setData(bundle);//mes利用Bundle传递数据
                mHandler.sendEmptyMessage(1);

                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.carlist_img_head)
        ImageView carlistImgHead;
        @BindView(R.id.driver_img_hezuo)
        TextView driverImgHezuo;
        @BindView(R.id.item_driver_name)
        TextView itemDriverName;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.waybillDetail_txt_start)
        TextView waybillDetailTxtStart;
        @BindView(R.id.waybillDetail_txt_end)
        TextView waybillDetailTxtEnd;
        @BindView(R.id.driver_location_fill)
        TextView driverLocationFill;
        @BindView(R.id.item_moto_plateno)
        TextView itemMotoPlateno;
        @BindView(R.id.driver_img_adress)
        TextView driverImgAdress;
        @BindView(R.id.driver_img_start)
        ImageView driverImgStart;
        @BindView(R.id.driver_text_start)
        TextView driverTextStart;
        @BindView(R.id.driver_img_call)
        ImageView driverImgCall;
        @BindView(R.id.knownorder_layout_call)
        LinearLayout knownorderLayoutCall;
        @BindView(R.id.detail_layout_user1)
        ImageView detailLayoutUser1;
        @BindView(R.id.detail_txt_usertype1)
        TextView detailTxtUsertype1;
        @BindView(R.id.mylaoutuser)
        FrameLayout mylaoutuser;
        @BindView(R.id.allandmy_layout_centercontent)
        LinearLayout allandmyLayoutCentercontent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
