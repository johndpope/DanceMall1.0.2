package com.hzxmkuar.wumeihui.personal.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.myself.MerchantOrderActivity;
import com.hzxmkuar.wumeihui.personal.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/1.
 */

public class PayFinishActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_finish);
        ButterKnife.bind(this);
        setTitleName(Constant.SERVICE_ORDER);
    }

    @OnClick({R.id.back, R.id.look_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent=new Intent(appContext,MainActivity.class);
                Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
                startActivity(intent);
                break;
            case R.id.look_order:
                intent=new Intent(appContext, PersonOrderActivity.class);
                intent.putExtra("OrderId",getIntent().getIntExtra("OrderId",0));
                intent.putExtra("IsFinishPay",true);
                Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }
}
