package com.hzxmkuar.wumeihui.personal.myself;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.order.adapter.ReceiverCouponAdapter;
import com.hzxmkuar.wumeihui.personal.order.adapter.SelectCouponAdapter;
import com.hzxmkuar.wumeihui.personal.order.presenter.ReceiverCouponPresenter;
import com.hzxmkuar.wumeihui.personal.order.presenter.SelectCouponPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/1.
 */

public class ReceiverCouponActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        setTitleName(Constant.COUPON);

        ReceiverCouponPresenter presenter = new ReceiverCouponPresenter(this);
        ReceiverCouponAdapter adapter = new ReceiverCouponAdapter(this, 0);
        setRecycleView(adapter, recycleView, presenter);
        adapter.setReceiverListener((mode, position) -> presenter.receiverCoupon(mode.getCate_id(),position));
         findViewById(R.id.no_use_layout).setVisibility(View.GONE);
    }
}
