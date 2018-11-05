package com.hzxmkuar.wumeihui.business.myself;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.myself.presenter.ExtensionPresenter;
import com.hzxmkuar.wumeihui.personal.order.SelectPayActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.ExtensionTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;

/**
 * Created by Administrator on 2018/10/15.
 */

public class ExtensionActivity extends BaseActivity {


    @BindView(R.id.top_money)
    TextView topMoney;
    @BindView(R.id.top_valid_time)
    TextView topValidTime;
    @BindView(R.id.inquiry_money)
    TextView inquiryMoney;
    @BindView(R.id.inquiry_valid_time)
    TextView inquiryValidTime;
    private ExtensionPresenter presenter;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);
        ButterKnife.bind(this);
        setTitleName(Constant.I_WANT_EXTENSION);
        EventBus.getDefault().register(this);
        presenter = new ExtensionPresenter(this);

    }

    @Override
    public void loadDataSuccess(Object data) {
        ExtensionTo mode = (ExtensionTo) data;
        topMoney.setText(mode.getSjzd().getPrice_by_month() + "");
        topValidTime.setText("有效期：" + mode.getSjzd().getValid_time());
        topValidTime.setVisibility(mode.getSjzd().getIs_valid()==1?View.VISIBLE:View.GONE);
        inquiryMoney.setText(mode.getBjzd().getPrice_by_month() + "");
        inquiryValidTime.setText("有效期：" + mode.getBjzd().getValid_time());
        inquiryValidTime.setVisibility(mode.getBjzd().getIs_valid()==1?View.VISIBLE:View.GONE);
    }

    @OnClick({R.id.top_purchase, R.id.inquiry_purchase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_purchase:
                type=3;
                presenter.merchantTopOrder();
                break;
            case R.id.inquiry_purchase:
                type=4;
                presenter.inquiryTopOrder();
                break;
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {
        OrderSettleInfoTo mode= (OrderSettleInfoTo) data;
        Intent intent=new Intent(appContext, SelectPayActivity.class);
        intent.putExtra("Money",mode.getTotal_amount()+"");
        intent.putExtra("OrderId",mode.getOrder_id());
        intent.putExtra("Type",type);
        startActivity(intent);
        goToAnimation(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getPayResult(Event<Integer> event){
        if ("PayResultData".equals(event.getType())){
            if (event.getData()==10){
                presenter.getExtension();
            }
        }
    }
}
