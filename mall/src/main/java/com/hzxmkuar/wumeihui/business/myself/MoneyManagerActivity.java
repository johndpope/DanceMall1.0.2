package com.hzxmkuar.wumeihui.business.myself;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.myself.presenter.MoneyManagerPresenter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.MoneyManagerTo;

/**
 * Created by Administrator on 2018/10/15.
 */

public class MoneyManagerActivity extends BaseActivity {
    @BindView(R.id.deposit_money)
    TextView depositMoney;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.back)
    AutoRelativeLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_layout)
    AutoRelativeLayout titleLayout;
    @BindView(R.id.deposit)
    TextView deposit;
    @BindView(R.id.deposit_layout)
    AutoLinearLayout depositLayout;
    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.out_deposit)
    TextView outDeposit;
    @BindView(R.id.pay_layout)
    AutoLinearLayout payLayout;
    private MoneyManagerTo mode;
    private MoneyManagerPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_manager);
        ButterKnife.bind(this);
        setTitleName(Constant.MONEY_MANAGER);
        presenter = new MoneyManagerPresenter(this);

    }

    @OnClick({R.id.deposit, R.id.deposit_layout, R.id.pay, R.id.out_deposit, R.id.pay_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.deposit:
                Intent intent = new Intent(appContext, DepositActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.padding:

                break;
            case R.id.pay:
                intent = new Intent(appContext, BondActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.pay_layout:
                intent = new Intent(appContext, BondRecordActivity.class);
                intent.putExtra("ShopId", mode.getShop_id());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.out_deposit:
                 intent = new Intent(appContext, DepositActivity.class);
                intent.putExtra("IsOut",true);
                intent.putExtra("OutMoney",payMoney.getText().toString());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.deposit_layout:
                intent = new Intent(appContext, IncomeRecordActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        mode = (MoneyManagerTo) data;
        payMoney.setText(mode.getDeposit() + "");
        depositMoney.setText(mode.getAccount_balance() + "");
        if (mode.getIs_deposit() == 2) {
            pay.setEnabled(false);
            pay.setBackgroundResource(R.drawable.common_gray_btn);
        } else {
            pay.setEnabled(true);
            pay.setBackgroundResource(R.drawable.common_btn);
        }
        if (mode.getAccount_balance()<=0) {
//            deposit.setEnabled(false);
            deposit.setBackgroundResource(R.drawable.common_gray_btn);
        }

    }
}
