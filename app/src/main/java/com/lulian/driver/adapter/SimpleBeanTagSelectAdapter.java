package com.lulian.driver.adapter;

import android.content.Context;

import com.lulian.driver.adapter.base.AbsCommonTagSelectAdapter;
import com.lulian.driver.entity.SimpleBean;

/**
 * 简单的数据对象标签选择适配器
 * 此适配器用于数据类型是 {@link com.lulian.driver.entity.SimpleBean}的选项列表
 * @author hxb
 */
public class SimpleBeanTagSelectAdapter extends AbsCommonTagSelectAdapter<SimpleBean> {


    public SimpleBeanTagSelectAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    protected String getTagText(int position, SimpleBean item) {
        return item.getText();
    }

}
