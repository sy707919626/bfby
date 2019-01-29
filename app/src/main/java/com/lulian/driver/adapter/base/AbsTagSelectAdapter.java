package com.lulian.driver.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.driver.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 抽象 标签选项列表适配器
 * @author hxb
 * @param <T>
 */
public abstract class AbsTagSelectAdapter<T> extends BaseRvAdapter<T,AbsTagSelectAdapter.ViewHolder> {


    public AbsTagSelectAdapter(Context mContext) {
        super(mContext);

        setChildClickListener(new OnChildClickListener() {
            @Override
            public void onClick(BaseRvAdapter adapter, View view, int clickPosition) {
                switch(view.getId()){
                    case R.id.tv_tag_text://点击一个tag
                        onTagClicked((T) adapter.getItem(clickPosition),clickPosition);

                    break;
                }
            }
        });
    }

    /**
     * 子类实现此方法处理tag的点击
     * @param item
     * @param position
     */
    protected abstract void onTagClicked(T item,int position);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflate(parent, R.layout.item_tag_select_new);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, T item) {
        String tagText = getTagText(position, item);
        holder.tvTagText.setText(tagText);

        //控制选中的标签颜色的变化
        if(itemIsSelected(item,position)){
            holder.tvTagText.setBackgroundResource(R.drawable.tag_selected);
            holder.tvTagText.setTextColor(mContext.getResources().getColor(R.color.bacolor));
        }else{
            holder.tvTagText.setBackgroundResource(R.drawable.tag_normal);
            holder.tvTagText.setTextColor(mContext.getResources().getColor(R.color.somber1));

        }

        bindOnClickListener(holder,
                R.id.tv_tag_text);
    }


    /**
     * 子类实现此方法,来判断条目是否已被选择
     * @return
     */
    protected abstract boolean itemIsSelected(T item,int position);

    /**
     * 子类实现此方法,返回条目标签上要显示的文字
     * @param position
     * @param item
     * @return
     */
    protected abstract String getTagText(int position, T item);



    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_tag_text)
        TextView tvTagText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
