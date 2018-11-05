package com.hzxmkuar.wumeihui.business.myself;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.myself.presenter.BondPresenter;
import com.hzxmkuar.wumeihui.personal.order.SelectPayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.BondPageTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;

/**
 * Created by Administrator on 2018/9/5.
 */

public class BondActivity extends BaseActivity {

    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.submit_deposit)
    TextView submitDeposit;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.submit)
    TextView submit;
    private OrderSettleInfoTo orderTo;
    private BondPresenter presenter;
    private BondPageTo mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond);
        ButterKnife.bind(this);
        setTitleName(Constant.PAY_BOND);
        presenter = new BondPresenter(this);


    }

    @Override
    public void loadDataSuccess(Object data) {
        mode = (BondPageTo) data;
        payMoney.setText("￥" + mode.getNeed_pay_amount());
        submitDeposit.setText("缴纳" + mode.getMin_deposit_amount());
        content.setText(mode.getDeposit_desc());
        submitDeposit.setVisibility(mode.getIs_deposit()==0?View.VISIBLE:View.GONE);

    }

    @Override
    protected void submitDataSuccess(Object data) {
        orderTo = (OrderSettleInfoTo) data;
        Intent intent = new Intent(appContext, SelectPayActivity.class);
        intent.putExtra("Type", 2);
        intent.putExtra("OrderId", orderTo.getOrder_id());
        intent.putExtra("Money",orderTo.getTotal_amount()+"");
        startActivity(intent);
        goToAnimation(1);

//
    }

    @OnClick({R.id.submit, R.id.submit_deposit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
               presenter.bondOrder(mode.getIs_deposit()==0?1:3);
                break;
            case R.id.submit_deposit:
            presenter.bondOrder(2);
                break;
        }
    }
}
