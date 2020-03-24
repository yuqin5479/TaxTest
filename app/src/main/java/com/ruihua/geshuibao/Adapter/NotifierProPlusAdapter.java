package com.ruihua.geshuibao.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ruihua.geshuibao.Bean.NotifierProPlusBean;
import com.ruihua.geshuibao.R;
import java.util.List;

public class NotifierProPlusAdapter extends ArrayAdapter {
    private final int resourceId;
    private Context context;
    private List<NotifierProPlusBean.DataBean.ListBean> datas;
    public NotifierProPlusAdapter(@NonNull Context context, int textViewResourceId, List<NotifierProPlusBean.DataBean.ListBean> datas) {
        super(context,textViewResourceId,datas);
        this.resourceId = textViewResourceId;
        this.context = context;
        this.datas = datas;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resourceId, null);
        ImageView img = view.findViewById(R.id.item_iv_notifierpro_img);
        ImageView redHint = view.findViewById(R.id.item_iv_notifierpro_red_hint);
        TextView nameText = view.findViewById(R.id.item_tv_notifierpro_text);
        TextView firstMsg = view.findViewById(R.id.item_tv_notifierpro_first_msg);
        TextView time = view.findViewById(R.id.item_tv_notifierpro_time);
        Glide.with(context).load(datas.get(position).getClassifyPicture().get(0))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(img);
        if (datas.get(position).isIsRead())
            redHint.setVisibility(View.GONE);
        else redHint.setVisibility(View.VISIBLE);
        nameText.setText(datas.get(position).getClassifyName());
        firstMsg.setText(datas.get(position).getContentText());
        time.setText(datas.get(position).getCreateDate());
        return view;
    }
}
