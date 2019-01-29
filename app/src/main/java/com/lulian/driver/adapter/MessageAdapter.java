package com.lulian.driver.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.MessageService;
import com.lulian.driver.view.activity.caractivity.MessageDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MARK on 2018/6/12.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    List<MessageService> messageList;

    public MessageAdapter(List<MessageService> messageList) {
        this.messageList = messageList;
    }

    public void setData(List<MessageService> messageList) {
        this.messageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_system_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessageService bean = messageList.get(position);

        holder.messageState.setText(bean.getTitle());
        holder.messageDatetime.setText(bean.getCreateTime());
        holder.systemTxtMessage.setText(bean.getContent());

        if (bean.getReaded() == 0) {
            holder.msgSystemState.setText("未读");
            holder.msgSystemState.setTextColor(R.color.red1);
        } else {
            holder.msgSystemState.setText("已读");
            holder.msgSystemState.setTextColor(R.color.qianhui);
        }

        holder.systemMessageLayoutDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MessageDetailActivity.class);
                intent.putExtra("messageid", bean.getId());
                intent.putExtra("MessageType", bean.getMessageType());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (messageList != null) {
            return messageList.size();
        }
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_state)
        TextView messageState;
        @BindView(R.id.message_datetime)
        TextView messageDatetime;
        @BindView(R.id.system_txt_message)
        TextView systemTxtMessage;
        @BindView(R.id.msg_system_state)
        TextView msgSystemState;
        @BindView(R.id.system_message_layout_detail)
        LinearLayout systemMessageLayoutDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
