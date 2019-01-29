package com.lulian.driver.listener;

import android.app.Dialog;
import android.view.View;

import com.lulian.driver.R;

/**
 * Created by MARK on 2018/6/22.
 */

public class ReportClickListener implements View.OnClickListener {
    Dialog mDialog;
    public ReportClickListener(Dialog mDialog){
        this.mDialog=mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_img_delete:
                mDialog.dismiss();
                break;


        }
    }
}
