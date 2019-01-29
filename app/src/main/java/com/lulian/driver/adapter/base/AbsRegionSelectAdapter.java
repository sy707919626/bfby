package com.lulian.driver.adapter.base;

import android.content.Context;

import com.lulian.driver.entity.RegionBean;

import java.util.List;
import java.util.Stack;


/**
 * 抽象的地区选择适配器
 * @author hxb
 */
public abstract class AbsRegionSelectAdapter extends AbsTagSelectAdapter<RegionBean> {

    /**
     * 所有地区数据
     */
    protected List<RegionBean> allRegion;

    /**
     * 当前的下级区域数据栈
     */
    private Stack<RegionBean> currentRegionStack=new Stack<>();


    /**
     * 外部设置进来的 地区列表级别变化的 监听器
     */
    private OnRegionGradeChangedListener listener;

    public interface OnRegionGradeChangedListener {
        void onRegionListChanged(RegionBean currRegion,int grade);//地区列表级别变化回调
    }

    public AbsRegionSelectAdapter(Context mContext) {
        super(mContext);
    }

    public void setOnRegionGradeChangedListener(OnRegionGradeChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void setData(List<RegionBean> data) {
        this.allRegion=data;
        //初始显示第一级区域数据
        super.setData(allRegion);
        if(listener!=null){
            listener.onRegionListChanged(getCurrentDisplayRegion(),getCurrentGrade());
        }
    }


    private RegionBean getCurrentDisplayRegion(){
        if (currentRegionStack.size() > 0) {
            return currentRegionStack.peek();
        }else{
            RegionBean allBean = new RegionBean();
            allBean.setName("全国");
            return allBean;
        }
    }


    @Override
    protected void onTagClicked(RegionBean item, int position) {
        List<RegionBean> childRegions = item.getChildRegions();
        if (childRegions != null && childRegions.size() > 0) {//有子地区列表

            if(!childRegions.get(0).isFullScopeOp()){
                RegionBean fullScopeOpBean=null;

                //为下级列表增加"全省","全市"选项
                int currentGrade = getCurrentGrade();
                if(currentGrade==1){
                    if(item.isMunicipal()){//是否为直辖市
                        fullScopeOpBean = createFullScopeOpBean("全市", item.getId());
                    }else {
                        fullScopeOpBean = createFullScopeOpBean("全省", item.getId());
                    }
                }else if(currentGrade==2){
                    fullScopeOpBean = createFullScopeOpBean("全市", item.getId());
                }

                item.getChildRegions().add(0,fullScopeOpBean);
            }

            currentRegionStack.push(item);
            super.setData(currentRegionStack.peek().getChildRegions());

            if (listener != null) {
                listener.onRegionListChanged(getCurrentDisplayRegion(),getCurrentGrade());
            }

        } else {//无子地区列表

            /*
             * 此处要判断选择的项是否为"全省","全市"选项
             */
            int grade = getCurrentGrade();
            if(item.isFullScopeOp() && grade!=1){
                RegionBean peekedRegion = currentRegionStack.peek();
                peekedRegion.setGrade(grade-1);
                onRegionSelected(peekedRegion);
            }else{
                item.setGrade(grade);
                onRegionSelected(item);
            }
            notifyDataSetChanged();

        }
    }

    /**
     * 子类实现此方法,当地选择了某个地区时,此方法会被调用
     */
    protected abstract void onRegionSelected(RegionBean region);

    /**
     * 创建一个"全省","全市"选项的数据对象
     * @param name
     * @param id
     * @return
     */
    protected RegionBean createFullScopeOpBean(String name,String id){
        RegionBean bean = new RegionBean();
        bean.setId(id);
        bean.setName(name);
        bean.setFullScopeOp(true);
        return bean;
    }



    @Override
    protected String getTagText(int position, RegionBean item) {
        return item.getName();
    }


    /**
     * 外部调用此方法获取当前所显示的区域列表级别
     * @return
     */
    public int getCurrentGrade(){
        if(currentRegionStack.size()<=0){
            return 1;
        }else{
            return currentRegionStack.size() + 1;
        }
    }


    /**
     * 外部调用此方法,返回上一级区域列表
     */
    public void backToPrevGrade(){
        /*
         * 当前的下级区域数据栈中有数据时候,表示当前所处所显示的地区级别是1级以上,
         * 只有这种情况下才能允许进行回退操作
         */
        if (currentRegionStack.size() > 0) {
            currentRegionStack.pop();
            if(currentRegionStack.size()>0){
                RegionBean topRegion = currentRegionStack.peek();
                super.setData(topRegion.getChildRegions());
            }else{
                super.setData(allRegion);
            }
        }

        if(listener!=null){
            listener.onRegionListChanged(getCurrentDisplayRegion(),getCurrentGrade());
        }
    }

}
