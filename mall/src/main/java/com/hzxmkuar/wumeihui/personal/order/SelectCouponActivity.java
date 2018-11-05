package com.hzxmkuar.wumeihui.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.personal.order.adapter.SelectCouponAdapter;
import com.hzxmkuar.wumeihui.personal.order.presenter.SelectCouponPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;

/**
 * Created by Administrator on 2018/9/1.
 */

public class SelectCouponActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.select_coupon_view)
    View selectCouponView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        setTitleName(Constant.COUPON);

        SelectCouponPresenter presenter = new SelectCouponPresenter(this);
        setRecycleView(new SelectCouponAdapter(this, 0), recycleView, presenter);


    }

    @OnClick(R.id.select_coupon_layout)
    public void onViewClicked() {
        EventBus.getDefault().post(new Event("NoUseCoupon"));
        finish();
        goToAnimation(2);
    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        SelectCouponTo.ListsBean mode= (SelectCouponTo.ListsBean) data;
        EventBus.getDefault().post(new Event<>("SelectCoupon",mode));
        finish();
        goToAnimation(2);
    }
}
