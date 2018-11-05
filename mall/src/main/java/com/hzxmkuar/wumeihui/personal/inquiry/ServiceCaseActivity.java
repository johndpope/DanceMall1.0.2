package com.hzxmkuar.wumeihui.personal.inquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.merchant.presenter.MerchantCasePresenter;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.ServiceCaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCaseTo;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ServiceCaseActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title_recycle_view);
        ButterKnife.bind(this);
        setTitleName(Constant.MERCHANT_CASE);
        setRecycleView(new ServiceCaseAdapter(this),recycleView,new MerchantCasePresenter(this));
    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        Intent intent=new Intent(appContext,CaseDetailActivity.class);
        intent.putExtra("CaseId",((MerchantCaseTo.ListsBean)data).getId());
        startActivity(intent);
        goToAnimation(1);
    }
}
