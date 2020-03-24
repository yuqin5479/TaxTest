package com.ruihua.geshuibao.Fragments;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruihua.geshuibao.Acivity.ChatActivity;
import com.ruihua.geshuibao.Acivity.SearchActivity;
import com.ruihua.geshuibao.Acivity.SelfHelpActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 咨询
 */
public class ConsultFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.consult_rl_self_service)
    RelativeLayout mRlSelfService;
    @BindView(R.id.consult_rl_contact_company)
    RelativeLayout mRlContactCompany;
    @BindView(R.id.consult_rl_cpoa)
    RelativeLayout mRlCpoa;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_consult;
    }

    @Override
    protected void setUpView() {
        tvHeadTitle.setText(getString(R.string.consult_title));
        ivBack.setVisibility(View.GONE);
        ivHeadRight.setVisibility(View.VISIBLE);
        ivHeadRight.setImageResource(R.drawable.search_black);
    }

    @Override
    protected void setUpData() {

    }

    @OnClick({R.id.iv_head_right,R.id.consult_rl_self_service,
            R.id.consult_rl_contact_company, R.id.consult_rl_cpoa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_right:
                //跳转搜索页面
                startActivity(new Intent(getActivity(),SearchActivity.class)
                        .putExtra("ConsultSearchFragment","ConsultSearchFragment")
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.consult_rl_self_service://跳转自助问答
                startActivity(new Intent(getActivity(),SelfHelpActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.consult_rl_contact_company://联系单位
                if(!TextUtils.isEmpty(HomeFragment.companyName)){
                    startActivity(new Intent(getActivity(),ChatActivity.class).putExtra("chatType",ChatActivity.CHAT_TYPE_COMPANY).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                }else toastShort("您还未加入单位");
                break;
            case R.id.consult_rl_cpoa://平台客服
                startActivity(new Intent(getActivity(),ChatActivity.class).putExtra("chatType",ChatActivity.CHAT_TYPE_PLATFOR).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }
}
