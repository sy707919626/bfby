package com.lulian.driver.view.wheel;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.adapter.base.AbsRegionSelectAdapter;
import com.lulian.driver.adapter.RegionSingleSelectAdapter;
import com.lulian.driver.entity.RegionBean;
import com.lulian.driver.utils.feature.RegionDataParser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 起始地地区选择popup
 * @author hxb
 */
public class StartRegionSelectPopup extends BaseFilterOpSelectPopup {

    private AppData app;

    @BindView(R.id.rv_history_selected)
    RecyclerView rvHistory;
    @BindView(R.id.rv_all_region)
    RecyclerView rvAllRegion;

    @BindView(R.id.tv_back_to_prev_grade)
    TextView tvBackToPrevGrade; //返回上一级按钮

    @BindView(R.id.tv_current_selected_parent_name)
    TextView tvCurrentParent;//显示当前所处的地区列表的父地区名称

    private RegionSingleSelectAdapter allSelectAdapter;//全部地区选择适配器

    private List<RegionBean> regions;//地区数据

    private Callback callback;//外部设置进来的监听器

    public interface Callback{
        void onRegionSelected(RegionBean region);
    }


    public StartRegionSelectPopup(AppData app,Context context, int height) {
        super(context, height);
        this.app = app;
        ButterKnife.bind(this, getContentView());
        init();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_start_region_select;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @OnClick({R.id.tv_back_to_prev_grade})
    public void onViewClicked(View v){
        switch (v.getId()) {
            case R.id.tv_back_to_prev_grade://点击返回上一级
                allSelectAdapter.backToPrevGrade();
                break;
        }
    }

    private void initView(){
        rvAllRegion.setNestedScrollingEnabled(false);
        rvAllRegion.setAnimation(null);
        rvAllRegion.setLayoutManager(createGridLayoutManager());
        allSelectAdapter = new RegionSingleSelectAdapter(mContext);
        rvAllRegion.setAdapter(allSelectAdapter);

        /*
         * 地区选择监听
         */
        allSelectAdapter.setCallback(new RegionSingleSelectAdapter.Callback() {
            @Override
            public void onRegionSelected(RegionBean region) {
                dismiss();
                if(callback!=null){
                    callback.onRegionSelected(region);
                }
            }
        });


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


        allSelectAdapter.setData(regions);
    }



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
