package com.lulian.driver.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.adapter.TruckLenSelectAdapter;
import com.lulian.driver.adapter.TruckTypeSelectAdapter;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.utils.ProjectUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 车长车型选择dialog
 * @author hxb
 */
public class TruckTypeLenSelectDialog extends Dialog {

    @BindView(R.id.text_content)
    TextView textContent;

    @BindView(R.id.cargotyperecylist)
    RecyclerView rvTruckLength; //车长列表
    private TruckLenSelectAdapter truckLengthAdapter;

    @BindView(R.id.cargotype1recylist)
    RecyclerView rvTruckType;//车型列表
    private TruckTypeSelectAdapter truckTypeAdapter;

    @BindView(R.id.vg_load_weight_block)
    ViewGroup vgLoadWeightBlock;//载重输入布局块
    @BindView(R.id.et_load_weight)
    EditText etLoadWeight;//载重输入框

    private AppData mApp;

    private Callback callback;

    public interface Callback{
        void onSelectConfirm(CarType truckType, String truckLen, String loadWeight);
    }

    public TruckTypeLenSelectDialog(@NonNull Context context, AppData app) {
        super(context, R.style.common_dialog);
        this.mApp = app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_truck_typle_len_select);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, this);

        initView();
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Dialog loadWeightBlockVisible(boolean isVisible){
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        vgLoadWeightBlock.setVisibility(visibility);
        return this;
    }

    private void initView() {
        textContent.setText("选择车型车长");

        //车型
        rvTruckType.setNestedScrollingEnabled(false);
        rvTruckType.setLayoutManager(createGridLayoutManager());
        rvTruckType.setItemAnimator(null);
        truckTypeAdapter = new TruckTypeSelectAdapter(getContext());
        rvTruckType.setAdapter(truckTypeAdapter);
        truckTypeAdapter.setData(mApp.getTrucktypeList());

        //车长
        rvTruckLength.setNestedScrollingEnabled(false);
        rvTruckLength.setLayoutManager(createGridLayoutManager());
        rvTruckLength.setItemAnimator(null);
        truckLengthAdapter = new TruckLenSelectAdapter(getContext());
        truckLengthAdapter.setHasUnLimitedItem(false);
        rvTruckLength.setAdapter(truckLengthAdapter);
        truckLengthAdapter.setData(mApp.getCarLenghtList());




    }

    @OnClick({R.id.cargotypesubmit, R.id.img_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cargotypesubmit://点击保存
                if(checkInputIsCorrect()){
                    if(callback!=null){
                        CarType selectedCarType = truckTypeAdapter.getSelectedItem();
                        String selectedCarLen = truckLengthAdapter.getSelectedItem();
                        String loadWeight = etLoadWeight.getText().toString().trim();

                        callback.onSelectConfirm(selectedCarType,selectedCarLen,loadWeight);
                        dismiss();
                    }
                }

                break;
            case R.id.img_return:
                dismiss();
                break;
        }
    }


    private boolean checkInputIsCorrect(){
        CarType selectedCarType = truckTypeAdapter.getSelectedItem();
        String selectedCarLen = truckLengthAdapter.getSelectedItem();
        String loadWeight = etLoadWeight.getText().toString().trim();


        if(selectedCarLen==null){
            ProjectUtil.show(getContext(),"请选择车长");
            return false;
        }

        if(selectedCarType==null){
            ProjectUtil.show(getContext(),"请选择车型");
            return false;
        }

        boolean loadWeightVisible=vgLoadWeightBlock.getVisibility() == View.VISIBLE;
        if(loadWeightVisible){
            if(TextUtils.isEmpty(loadWeight)){
                ProjectUtil.show(getContext(),"请填写载重");
                return false;
            }else{
                Double loadWeightD = Double.valueOf(loadWeight);
                if (loadWeightD <= 0) {
                    ProjectUtil.show(getContext(),"载重不能小于0");
                    return false;
                }
            }
        }

        return true;
    }


    private GridLayoutManager createGridLayoutManager(){
        return new GridLayoutManager(getContext(), 4);
    }


}
