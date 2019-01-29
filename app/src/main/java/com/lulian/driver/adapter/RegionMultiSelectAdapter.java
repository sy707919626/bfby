package com.lulian.driver.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.lulian.driver.adapter.base.AbsRegionSelectAdapter;
import com.lulian.driver.entity.RegionBean;
import com.lulian.driver.utils.ProjectUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 地区多选列表适配器
 * @author hxb
 */
public class RegionMultiSelectAdapter extends AbsRegionSelectAdapter {

    private int maxAllowSelectCount =5;//最大选择数量

    private List<RegionBean> selectedList=new ArrayList<>();//已经选择的地区列表

    //外部设置进来的监听器
    private Callback callback;

    public interface Callback{
        void onSelectedListChanged(List<RegionBean> selectedList);
    }


    public RegionMultiSelectAdapter(Context mContext) {
        super(mContext);
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setMaxAllowSelectCount(int maxSelectCount) {
        this.maxAllowSelectCount = maxSelectCount;
    }

    public List<RegionBean> getSelectedList() {
        if(selectedList.size()<=0){
            selectedList.add(allRegion.get(0));
            if(callback!=null){
                callback.onSelectedListChanged(selectedList);
                notifyDataSetChanged();
            }
        }
        return selectedList;
    }

    /**
     * 删除一个已选地区
     */
    public void removeOneItemSelectedRegion(RegionBean shouldRemoveRegion){
        selectedList.remove(shouldRemoveRegion);
        if(callback!=null){
            callback.onSelectedListChanged(selectedList);
        }
        notifyDataSetChanged();
    }

    /**
     * 清空所有已选地区
     */
    public void clearAllSelectedRegion(){
        selectedList.clear();
        if(callback!=null){
            callback.onSelectedListChanged(selectedList);
        }
        notifyDataSetChanged();
    }

    @Override
    protected void onRegionSelected(RegionBean region) {
        if(selectedList.contains(region)){//如果选择的地区已经在已选列表中,就移除掉
            selectedList.remove(region);
            if(callback!=null){
                callback.onSelectedListChanged(selectedList);
            }
            return;
        }


        String id = region.getId();
        if(TextUtils.isEmpty(id)){//选择了第一级地区中的"全国"
            //此时应该清空已选列表中,再添加"选项"进去
            selectedList.clear();
            selectedList.add(region);

        }else{//选择的不是全国
            List<RegionBean> shouldRemoveList = new ArrayList<>();
            String trimmedCurrSelectId = trimRegionId(region);
            for (RegionBean bean : selectedList) {
                String trimmedSelectedId = trimRegionId(bean);
                if(bean.getId().startsWith(trimmedCurrSelectId) || region.getId().startsWith(trimmedSelectedId)){
                    shouldRemoveList.add(bean);
                }
            }
            selectedList.removeAll(shouldRemoveList);

            if (selectedList.size() >= maxAllowSelectCount) {//已选列表中的数量已经达到最大允许数量
                ProjectUtil.show(mContext, "目的地最多选择" + maxAllowSelectCount + "个");
                return;
            } else {
                selectedList.add(region);
            }
        }

        if(callback!=null){
            callback.onSelectedListChanged(selectedList);
        }

    }

    @Override
    protected boolean itemIsSelected(RegionBean item, int position) {
        return selectedList.contains(item);
    }


    @Override
    public void setData(List<RegionBean> data) {
        /*
         * 多选地区列表中,在第一区地区列表中需要有一个"全国"选项
         * 所以这里需要重写setData方法,加工一下数据
         */
        List<RegionBean> newList = new ArrayList<>(Arrays.asList(new RegionBean[data.size()]));

        Collections.copy(newList,data);

        RegionBean bean = createFullScopeOpBean("全国", "");
        newList.add(0,bean);

        super.setData(newList);
    }

    /**
     * 截取地区编码的有效位
     * 例如:湖南省的地区编码是 430000,实际有效位是43
     *      长沙市的地区编码是 430100,实际有效位是4301
     */
    private String trimRegionId(RegionBean region){
        String trimmedId="";
        String rawId = region.getId();
        if(!TextUtils.isEmpty(rawId)){//如果编码为空,表示这是"全国",应该返回空字符串
            int grade = region.getGrade();
            List<RegionBean> childRegions = region.getChildRegions();
            /*
             * 有子地区列表的时候才进行截取,如果没有子地区列表就不进行截取,返回原始的地区编码
             */
            if (childRegions != null && childRegions.size() > 0) {
                if (grade == 1) {//第一级地区,截取前2位
                    trimmedId = rawId.substring(0, 2);
                } else if (grade == 2) {//第二级地区,截取前4位
                    trimmedId = rawId.substring(0, 4);
                } else {
                    trimmedId = rawId;
                }
            } else {
                trimmedId = rawId;
            }
        }

        return trimmedId;
    }

}
