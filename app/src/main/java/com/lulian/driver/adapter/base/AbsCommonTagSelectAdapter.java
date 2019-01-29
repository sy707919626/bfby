package com.lulian.driver.adapter.base;

import android.content.Context;

import com.lulian.driver.view.DictDataTool;

import java.util.LinkedList;
import java.util.List;


/**
 * 通用的标签选择列表适配器
 * 封装简单的单选,多选逻辑
 * 具体的数据类型由子类来决定
 * @author hxb
 * @param <T>
 */
public abstract class AbsCommonTagSelectAdapter<T> extends AbsTagSelectAdapter<T> {

    protected final String defaultUnlimitedText = DictDataTool.DEFAULT_UNLIMITED_TEXT;

    private boolean isMultiSelect;//true:多选,false:单选
    private boolean allowEmptySelect;//是否允许空选择
    /*
     *  这个字段用来标识数据列表中是否使用了不限选项,这个类里面只控制"不限"选项的选择逻辑,
     *  "不限"选项的数据项需要子类自己去添加,在这个类的逻辑中,会强制的认为数据列表中第一条是"不限"选项
     */
    private boolean hasUnLimitedItem=true;

    private List<T> selectedList = new LinkedList<>();//已选数据列表

    public AbsCommonTagSelectAdapter(Context mContext) {
        super(mContext);
    }

    public List<T> getSelectList(){
        return selectedList;
    }

    public T getSelectedItem(){
        if(selectedList.size()>0){
            return selectedList.get(0);
        }else{
            return null;
        }
    }

    public void setHasUnLimitedItem(boolean hasUnLimitedItem) {
        this.hasUnLimitedItem = hasUnLimitedItem;
    }

    public boolean isHasUnLimitedItem() {
        return hasUnLimitedItem;
    }

    @Override
    public void setData(List<T> data) {
        super.setDataRaw(data);
        if(hasUnLimitedItem){//如果开启了使用"不限"选项,初始化默认选择
            selectedList.add(getUnLimitedItem());
        }
        notifyDataSetChanged();
    }

    /**
     * 清空所有已选项
     */
    public void clearAllSelected(){
        selectedList.clear();
        if(hasUnLimitedItem){//如果开启了使用不限选项,清空后再将"不限"选项添加进去
            selectedList.add(getUnLimitedItem());
        }
        notifyDataSetChanged();
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public void setAllowEmptySelect(boolean allowEmptySelect) {
        this.allowEmptySelect = allowEmptySelect;
    }

    @Override
    protected void onTagClicked(T item, int position) {
        if(isMultiSelect){
            performMultiSelectTagClick(item,position);
        }else{
            performSingleSelectTagClick(item);
        }
        notifyDataSetChanged();
    }

    /**
     * 处理多选点击标签时的逻辑
     */
    private void performMultiSelectTagClick(T item,int position){
        if(selectedList.contains(item)){
            if(allowEmptySelect || selectedList.size()>1){
                selectedList.remove(item);
            }
        }else{
            if(hasUnLimitedItem){//开启了使用"不限"选项,需要做特殊处理
                if(getUnLimitedItem().equals(item)){//当前点击的是不限选项,清空已经列表
                    selectedList.clear();
                }else{//点击的是非不限选项,移出掉已选列表中的"不限"选项
                    removeUnlimitedItem();
                }
            }
            selectedList.add(item);
        }
    }

    /**
     * 移出掉已选列表中的"不限"选项
     */
    private void removeUnlimitedItem(){
        if(selectedList.size()==1){
            T unLimitedItem = getUnLimitedItem();
            selectedList.remove(unLimitedItem);
        }
    }

    /**
     * 处理多选点击标签时的逻辑
     */
    private void performSingleSelectTagClick(T item){
        if(!selectedList.contains(item)){
            selectedList.clear();
            selectedList.add(item);
        }
    }

    @Override
    protected boolean itemIsSelected(T item, int position) {
        return selectedList.contains(item);
    }


    /**
     * 获取"不限"选项的数据项
     * @return
     */
    protected T getUnLimitedItem(){
        return getItem(0);
    }

}
