package com.lulian.driver.view.activity.caractivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.server.resulte.ExcepRecordItem;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 异常记录列表界面
 *
 * @author hxb
 */
public class ExcepRecordListActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private ExcepRecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excep_record_list);
        ButterKnife.bind(this);
        initData();
        initViews();

    }

    @SuppressWarnings("unchecked")
    private void initData() {
        ArrayList<ExcepRecordItem> list= (ArrayList<ExcepRecordItem>) getIntent().getSerializableExtra("excepList");
        adapter = new ExcepRecordAdapter(this);
        adapter.setList(list);
    }

    private void initViews() {
        TextView title = (TextView) findViewById(R.id.text_content);
        title.setText("异常记录");

        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.common_list_divider));
        rv.addItemDecoration(decor);
        rv.setAdapter(adapter);
    }


    @OnClick({R.id.img_return})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.img_return:
                finish();
                break;
        }
    }


    public static class ExcepRecordAdapter extends RecyclerView.Adapter<ExcepRecordAdapter.Holder>{

        private List<ExcepRecordItem> list;
        private LayoutInflater lif;
        private Context context;

        public ExcepRecordAdapter(Context context) {
            lif= LayoutInflater.from(context);
            this.context=context;
        }

        public void setList(List<ExcepRecordItem> list){
            this.list=list;
            notifyDataSetChanged();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = lif.inflate(R.layout.item_excep_record_list, parent, false);
            Holder holder = new Holder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            ExcepRecordItem item = list.get(position);
            //异常内容描述
            holder.tvExcepContent.setText(item.getProblemDiscript());

            //异常图片
            List<ExcepRecordItem.File> files = item.getFiles();
            if(files.size()>0){
                ExcepRecordItem.File f = files.get(0);
                holder.ivExcepImg.setVisibility(View.VISIBLE);
                holder.ivExcepImg.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
                String url=GlobalValue.BASEURL + f.getUrl().replaceFirst("/", "");

                Picasso.get().load(url).into(holder.ivExcepImg);
            }else{
                holder.ivExcepImg.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }

        public static class Holder extends RecyclerView.ViewHolder{
            @BindView(R.id.tv_excep_content)
            TextView tvExcepContent;
            @BindView(R.id.iv_excep_img)
            ImageView ivExcepImg;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


}
