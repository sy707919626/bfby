package com.lulian.driver.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.AllowCarTypeSelectAdapter;
import com.lulian.driver.entity.AllowCarType;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.RvConfigHelper;
import com.lulian.driver.view.DictDataTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 准驾车型选择Dialog
 * @author hxb
 */
public class AllowCarTypeSelectDialog extends Dialog {

    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightBottom;

    @BindView(R.id.rv)
    RecyclerView rv;

    private AllowCarTypeSelectAdapter adapter;

    private Callback callback;

    public interface Callback{
        void onAllowCarTypeSelected(AllowCarType allowCarType);
    }

    public AllowCarTypeSelectDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_allow_car_type_select);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
        textContent.setText("选择准驾车型");
        titleTxtRightBottom.setText("确定");
        titleTxtRightBottom.setVisibility(View.VISIBLE);

        adapter = new AllowCarTypeSelectAdapter(getContext());
        adapter.setData(DictDataTool.getLicAllowCarTypeList());

        RvConfigHelper.configToLLMgrVertical(getContext(),rv);
        rv.setItemAnimator(null);

        rv.setAdapter(adapter);

    }


    @OnClick({R.id.img_return,R.id.title_txt_rightbottom})
    public void onViewClicked(View v){
        switch (v.getId()) {
            case R.id.img_return:
                dismiss();
                break;
            case R.id.title_txt_rightbottom://点击确定
                AllowCarType selectedItem = adapter.getSelectedItem();
                if (selectedItem != null) {
                    if(callback!=null){
                        callback.onAllowCarTypeSelected(selectedItem);
                        dismiss();
                    }
                } else {
                    ProjectUtil.show(getContext(),"请选择");
                }
                break;
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
