package com.lulian.driver.view.wheel;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.adapter.RegionMultiSelectAdapter;
import com.lulian.driver.adapter.base.AbsRegionSelectAdapter;
import com.lulian.driver.entity.RegionBean;
import com.lulian.driver.utils.feature.RegionDataParser;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 目的地地区选择popup
 * @author hxb
 */
public class EndRegionSelectPopup extends BaseFilterOpSelectPopup {

    @BindView(R.id.rv_all_region)
    RecyclerView rvAllRegion;

    @BindView(R.id.flow_view_selected)
    FlowLayout flowLayoutSelected;//已选地区

    @BindView(R.id.tv_back_to_prev_grade)
    TextView tvBackToPrevGrade; //返回上一级按钮

    @BindView(R.id.tv_current_selected_parent_name)
    TextView tvCurrentParent;//显示当前所处的地区列表的父地区名称

    private List<RegionBean> regions;//地区数据

    private RegionMultiSelectAdapter allSelectAdapter;//全部地区选择适配器

    private AppData app;

    private Callback callback;//外部设置进来的监听器

    public interface Callback{
        void onRegionSelected(List<RegionBean> regionList);
    }

    public EndRegionSelectPopup(AppData app, Context context, int height) {
        super(context, height);
        this.app = app;
        ButterKnife.bind(this, getContentView());
        init();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_end_region_select;
    }


    @OnClick({R.id.tv_back_to_prev_grade,
            R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View v) {

        switch (v.getId()) {
            case R.id.tv_back_to_prev_grade://点击返回上一级
                allSelectAdapter.backToPrevGrade();
                break;
            case R.id.tv_clear://点击清空条件
                allSelectAdapter.clearAllSelectedRegion();
                break;
            case R.id.tv_confirm://点击确定
                if(callback!=null){
                    dismiss();
                    callback.onRegionSelected(allSelectAdapter.getSelectedList());
                }
                break;
        }
    }


    private void initView(){
        rvAllRegion.setNestedScrollingEnabled(false);
        rvAllRegion.setAnimation(null);
        rvAllRegion.setLayoutManager(createGridLayoutManager());
        allSelectAdapter = new RegionMultiSelectAdapter(mContext);
        rvAllRegion.setAdapter(allSelectAdapter);

        /*
         * 地区列表级别变化监听
         */
        allSelectAdapter.setOnRegionGradeChangedListener(new AbsRegionSelectAdapter.OnRegionGradeChangedListener() {
            @Override
            public void onRegionListChanged(RegionBean currRegion,int grade) {
                //如果当前显示的地区级别是一级以上,要显示"返回上一级"按钮,否则就隐藏
                if(grade>1){
                    tvBackToPrevGrade.setVisibility(View.VISIBLE);
                }else{
                    tvBackToPrevGrade.setVisibility(View.GONE);
                }

                tvCurrentParent.setText(currRegion.getName());
            }
        });


        /*
         * 设置已选列表变化监听
         */
        allSelectAdapter.setCallback(new RegionMultiSelectAdapter.Callback() {
            @Override
            public void onSelectedListChanged(List<RegionBean> list) {
                refreshSelectedRegionsUi(list);
            }
        });

        allSelectAdapter.setData(regions);
    }


    /**
     * 显示数据到界面上的已选地区列表
     */
    private void refreshSelectedRegionsUi(List<RegionBean> selectedList){
        LayoutInflater lif = LayoutInflater.from(mContext);
        flowLayoutSelected.removeAllViews();
        for(RegionBean bean:selectedList){
            View v = lif.inflate(R.layout.item_deletable_tag, null);
            v.setOnClickListener(selectedTagClickListener);
            v.setTag(bean);

            TextView tvRegionName=v.findViewById(R.id.tv_region_name);
            tvRegionName.setText(bean.getName());
            flowLayoutSelected.addView(v);
        }
    }


    /**
     * 已选地区标签点击监听
     */
    private View.OnClickListener selectedTagClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            RegionBean region = (RegionBean) v.getTag();
            allSelectAdapter.removeOneItemSelectedRegion(region);
        }
    };


    private GridLayoutManager createGridLayoutManager(){
        return new GridLayoutManager(mContext, 4);
    }

    private void init(){
        List<RegionBean> data = app.getRegionData();
        if(data!=null){
            regions = data;
            initView();
            return;
        }

        RegionDataParser parser = new RegionDataParser(mContext);
        parser.getData(new RegionDataParser.Callback() {
            @Override
            public void onResult(List<RegionBean> data) {
                regions=data;
                initView();
            }
        });
    }

}
