package com.lulian.driver.adapter.base;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

/**
 * RecyclerView Adapter 抽象基类
 *
 * @param <T>  数据类型
 * @param <VH> ViewHolder类型
 * @author hxb
 */
public abstract class BaseRvAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    private List<T> mData;

    /**
     * 外部设置进来的条目子View监听器
     */
    private OnChildClickListener mChildClickListener;

    /**
     * 外部设置进来的条目子View长按监听器
     */
    private OnChildLongClickListener mChildLongClickListener;


    public interface OnChildClickListener{
        void onClick(BaseRvAdapter adapter, View view, int position);
    }

    public interface OnChildLongClickListener{
        boolean onLongClick(BaseRvAdapter adapter, View view, int position);
    }


    public BaseRvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    protected View inflate(ViewGroup parent, int layoutResId) {
        return LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, position, getItem(position));
    }

    /**
     * 子类实现此方法绑定数据到子View上
     */
    protected abstract void onBindViewHolder(VH holder, int position, T item);

    /**
     * 调用此方法来为子View绑定点击监听
     *
     * @param childIds 要绑定监听的子View的id
     */
    protected final void bindOnClickListener(VH holder,int... childIds) {
        for (int id : childIds) {
            View childView = holder.itemView.findViewById(id);
            if (childView != null) {
                childView.setOnClickListener(new InnerOnClickListener<>(this,holder));
            }
        }
    }

    /**
     * 调用此方法来为子View绑定长按点击监听
     *
     * @param childIds 要绑定监听的子View的id
     */
    protected final void bindOnLongClickListener(VH holder,int... childIds){
        for (int id : childIds) {
            View childView = holder.itemView.findViewById(id);
            if (childView != null) {
                childView.setOnLongClickListener(new InnerOnLongClickListener<>(this,holder));
            }
        }
    }

    /**
     * 外部调用此方法，设置条目子View点击监听器
     */
    public void setChildClickListener(OnChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    /**
     * 外部调用此方法，设置条目子View的长按点击监听器
     */
    public void setChildLongClickListener(OnChildLongClickListener childLongClickListener) {
        this.mChildLongClickListener = childLongClickListener;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setDataRaw(List<T> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    /**
     * 获取一个条目的数据对象
     *
     * @param position 想要获得数据对象的位置
     * @return
     */
    public T getItem(int position) {
        T itemData = null;
        if (mData != null) {
            try {
                itemData = mData.get(position);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return itemData;
    }


    public int getItemPosition(T item){
        return mData.indexOf(item);
    }


    /**
     * 添加一个条目数据
     *
     * @param item 要添加的数据
     */
    public void addItem(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    /**
     * 添加一个条目到position位置
     *
     * @param position 指定的位置
     * @param item     要添加的数据
     */
    public void addItem(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }


    /**
     * 将一个数据列表添加到当前adapter数据列表的末尾
     */
    public void addItemAll(Collection<? extends T> collection) {
        int notifyStartPosition = mData.size();
        mData.addAll(collection);
        notifyItemRangeInserted(notifyStartPosition, collection.size());
    }

    /**
     * 添加多个条目到position位置
     *
     * @param position   指定的位置
     * @param collection 要添加的数据集合
     */
    public void addItemAll(int position, Collection<? extends T> collection) {
        mData.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }


    /**
     * 删除一个条目
     * <p>为了避免notifyItemRemoved后导致position错乱,所以再调用notifyItemRangeChanged刷新position</p>
     *
     * @param position 要删除的位置
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        int itemCount = mData.size() - position;
        notifyItemRangeChanged(position, itemCount);
    }

    /**
     * 删除多个条目
     *
     * @param collection 要删除的数据集合
     */
    public void removeItemAll(Collection<? extends T> collection) {
        mData.removeAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 清空适配器数据
     */
    public void clear() {
        mData.clear();
        notifyItemRangeRemoved(0, mData.size());
    }

    private static class InnerOnClickListener<VH extends RecyclerView.ViewHolder> implements View.OnClickListener {
        private BaseRvAdapter mAdapter;
        private VH mHolder;

        public InnerOnClickListener(BaseRvAdapter mAdapter,VH mHolder) {
            this.mAdapter = mAdapter;
            this.mHolder = mHolder;
        }

        @Override
        public void onClick(View view) {
            if(mAdapter.mChildClickListener!=null){
                int position = mHolder.getLayoutPosition();
                mAdapter.mChildClickListener.onClick(mAdapter,view,position);
            }
        }
    }



    private static class InnerOnLongClickListener<VH extends RecyclerView.ViewHolder> implements View.OnLongClickListener{
        private BaseRvAdapter mAdapter;
        private VH mHolder;

        public InnerOnLongClickListener(BaseRvAdapter mAdapter, VH mHolder) {
            this.mAdapter = mAdapter;
            this.mHolder = mHolder;
        }

        @Override
        public boolean onLongClick(View view) {
            int position = mHolder.getLayoutPosition();
            return mAdapter.mChildLongClickListener.onLongClick(mAdapter,view,position);
        }
    }


}
