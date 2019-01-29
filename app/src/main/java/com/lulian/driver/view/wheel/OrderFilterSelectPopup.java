package com.lulian.driver.view.wheel;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lulian.driver.R;
import com.lulian.driver.adapter.SimpleBeanTagSelectAdapter;
import com.lulian.driver.adapter.TruckLenSelectAdapter;
import com.lulian.driver.adapter.TruckTypeSelectAdapter;
import com.lulian.driver.entity.SimpleBean;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.view.DictDataTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 订单 条件筛选项popup
 * @author hxb
 */
public class OrderFilterSelectPopup extends BaseFilterOpSelectPopup {

    @BindView(R.id.rv_truck_len)
    RecyclerView rvTruckLen; //车长rv
    @BindView(R.id.rv_truck_type)
    RecyclerView rvTruckType;//车型rv
    @BindView(R.id.rv_load_time_scope)
    RecyclerView rvLoadTimeScope; //装货时间返回选择

    private TruckLenSelectAdapter truckLenSelectAdapter;//车长选择适配器
    private TruckTypeSelectAdapter truckTypeSelectAdapter;//车型选择适配器
    private SimpleBeanTagSelectAdapter loadTimeScopeAdapter;//装货时间范围选择适配器

    private List<CarType> truckTypeList;//车型数据
    private List<String> truckLenList;//车长数据

    private Callback callback;//外部设置进来的监听器

    public interface Callback{
        void onSelectConfirm(List<CarType> truckType, List<String> truckLen, List<SimpleBean> timeScope);//确定选择回调
    }

    public OrderFilterSelectPopup(Context context, int height,List<CarType> truckTypeList,List<String> truckLenList) {
        super(context, height);
        ButterKnife.bind(this, getContentView());
        this.truckLenList = truckLenList;
        this.truckTypeList = truckTypeList;
        initView();

    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_order_filter;
    }


    private void initView(){
        //装货时间范围
        rvLoadTimeScope.setNestedScrollingEnabled(false);
        rvLoadTimeScope.setAnimation(null);
        rvLoadTimeScope.setLayoutManager(createGridLayoutManager());
        loadTimeScopeAdapter = new SimpleBeanTagSelectAdapter(mContext);
        loadTimeScopeAdapter.setMultiSelect(true);
        loadTimeScopeAdapter.setData(DictDataTool.getTimeScopeList(true));
        rvLoadTimeScope.setAdapter(loadTimeScopeAdapter);


        //车长
        rvTruckLen.setNestedScrollingEnabled(false);
        rvTruckLen.setAnimation(null);
        rvTruckLen.setLayoutManager(createGridLayoutManager());
        truckLenSelectAdapter = new TruckLenSelectAdapter(mContext);
        truckLenSelectAdapter.setMultiSelect(true);
        truckLenSelectAdapter.setData(truckLenList);
        rvTruckLen.setAdapter(truckLenSelectAdapter);

        //车型
        rvTruckType.setNestedScrollingEnabled(false);
        rvTruckType.setAnimation(null);
        rvTruckType.setLayoutManager(createGridLayoutManager());
        truckTypeSelectAdapter = new TruckTypeSelectAdapter(mContext);
        truckTypeSelectAdapter.setMultiSelect(true);
        truckTypeSelectAdapter.setData(truckTypeList);
        rvTruckType.setAdapter(truckTypeSelectAdapter);

    }


    private GridLayoutManager createGridLayoutManager(){
        return new GridLayoutManager(mContext, 4);
    }


    @OnClick({R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_clear://点击清空条件
                truckLenSelectAdapter.clearAllSelected();
                truckTypeSelectAdapter.clearAllSelected();
                loadTimeScopeAdapter.clearAllSelected();
                break;
            case R.id.tv_confirm://点击确定
                performClickConfirm();
                break;
        }
    }


    private void performClickConfirm(){
        dismiss();
        if(callback!=null){
            List<CarType> selectTruckTypeList = truckTypeSelectAdapter.getSelectList();
            List<String> selectTruckLenList = truckLenSelectAdapter.getSelectList();
            List<SimpleBean> selectTimeScopeList = loadTimeScopeAdapter.getSelectList();

            /*
             * 如果选择了不限,应该把null传给外部
             */
            String firstTruckTypeId = selectTruckTypeList.get(0).getId();
            if(TextUtils.isEmpty(firstTruckTypeId) || firstTruckTypeId.equals(DictDataTool.TRUCK_TYPE_UNLIMITED_ID)){
                selectTruckTypeList = null;
            }

            if(selectTruckLenList.get(0).equals(DictDataTool.DEFAULT_UNLIMITED_TEXT)){
                selectTruckLenList = null;
            }

            if(TextUtils.isEmpty(selectTimeScopeList.get(0).getCode())){
                selectTimeScopeList = null;
            }

            callback.onSelectConfirm(selectTruckTypeList,selectTruckLenList,selectTimeScopeList);
        }
    }

}
