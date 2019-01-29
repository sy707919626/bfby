package com.lulian.driver.utils;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lulian.driver.R;


/**
 * 对RecyclerView进行配置的帮助类
 * @author hxb
 */
public class RvConfigHelper {


    public static void configToLLMgrVertical(Context context, RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setItemAnimator(null);
        DividerItemDecoration decor=new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        decor.setDrawable(context.getResources().getDrawable(R.drawable.common_list_divider));
        rv.addItemDecoration(decor);
    }

}
