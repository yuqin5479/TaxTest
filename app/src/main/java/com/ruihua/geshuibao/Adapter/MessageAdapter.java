package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ruihua.geshuibao.Bean.ChatMessage;
import com.ruihua.geshuibao.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {
        private int resourceId;
        public MessageAdapter(Context context, int textViewResourceId, List<ChatMessage> data) {
                super(context, textViewResourceId, data);
                resourceId = textViewResourceId;

        }


        public View getView(int position, View convertView, ViewGroup parent) {
                ChatMessage msg = getItem(position);
                View view;
                ViewHolder viewHolder;
                if(convertView == null) {
                        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                        viewHolder = new ViewHolder();
                        viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
                        viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
                        viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
                        viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
                        viewHolder.head_from = (ImageView)view.findViewById(R.id.head_left);
                        viewHolder.head_to = (ImageView)view.findViewById(R.id.head_right);
                        view.setTag(viewHolder);
                } else {
                        view = convertView;
                        viewHolder = (ViewHolder) view.getTag();
                }
                if(msg.getType() == ChatMessage.TYPE_RECEIVED) {
                        viewHolder.leftLayout.setVisibility(View.VISIBLE);
                        viewHolder.head_from.setVisibility(View.VISIBLE);
                        viewHolder.rightLayout.setVisibility(View.GONE);
                        viewHolder.head_to.setVisibility(View.GONE);
                        viewHolder.leftMsg.setText(msg.getContent());
                        Glide.with(getContext()).load(msg.getMegHeadUrl())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop())//按圆形图加载
                                        .error(R.drawable.home_header)//加载错误图
                                        .placeholder(R.drawable.home_header))//占位图
                                .into(viewHolder.head_from);
                } else if(msg.getType() == ChatMessage.TYPE_SEND) {
                        viewHolder.rightLayout.setVisibility(View.VISIBLE);
                        viewHolder.head_to.setVisibility(View.VISIBLE);
                        viewHolder.leftLayout.setVisibility(View.GONE);
                        viewHolder.head_from.setVisibility(View.GONE);
                        viewHolder.rightMsg.setText(msg.getContent());
                        Glide.with(getContext()).load(msg.getMegHeadUrl())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop())//按圆形图加载
                                .error(R.drawable.home_header)//加载错误图
                                .placeholder(R.drawable.home_header))//占位图
                                .into(viewHolder.head_to);

                }
                return view;
        }

        class ViewHolder {
            LinearLayout leftLayout;
            LinearLayout rightLayout;
            TextView leftMsg;
            TextView rightMsg;
            ImageView head_to,head_from;
        }

}