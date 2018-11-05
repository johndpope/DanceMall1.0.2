package com.hzxmkuar.wumeihui.personal.myself;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.merchant.MerchantDetailActivity;
import com.hzxmkuar.wumeihui.business.merchant.adapter.CollectionListAdapter;
import com.hzxmkuar.wumeihui.personal.myself.presenter.CollectMerchantPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;

/**
 * Created by Administrator on 2018/10/15.
 */

public class CollectMerchantActivity extends BaseActivity {
    @BindView(R.id.merchant_layout)
    LRecyclerView merchantLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_merchant);
        ButterKnife.bind(this);
        setTitleName(Constant.COLLECTION_MERCAHNT);
        CollectMerchantPresenter presenter=new CollectMerchantPresenter(this);
        setRecycleViewRefresh(new CollectionListAdapter(this),merchantLayout,presenter);

    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        Intent intent=new Intent(appContext, MerchantDetailActivity.class);
        intent.putExtra("MerchantId",((MainMerchantTo.ListsBean)data).getBus_uid());
        startActivity(intent);
        goToAnimation(1);
    }
}
