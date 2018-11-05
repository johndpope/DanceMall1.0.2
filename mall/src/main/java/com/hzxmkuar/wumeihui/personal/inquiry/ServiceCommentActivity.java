package com.hzxmkuar.wumeihui.personal.inquiry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.merchant.presenter.MerchantCommentPresenter;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.ServiceCommentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ServiceCommentActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title_recycle_view);
        ButterKnife.bind(this);
        setTitleName(Constant.USER_COMMENT);

        setRecycleView(new ServiceCommentAdapter(this),recycleView,new MerchantCommentPresenter(this));
        recycleView.setLoadMoreEnabled(false);
    }
}
