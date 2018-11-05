package com.hzxmkuar.wumeihui.personal.inquiry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.hzxmkuar.wumeihui.personal.myself.MyInquiryActivity;
import com.hzxmkuar.wumeihui.personal.myself.MyOrderActivity;
import com.hzxmkuar.wumeihui.personal.order.PersonOrderActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/1.
 */

public class InquirySuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_success);
        ButterKnife.bind(this);
        setTitleName(Constant.SERVICE_ORDER);
        findViewById(R.id.back).setOnClickListener(view -> {
            Intent intent=new Intent(appContext,MainActivity.class);
            Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
            startActivity(intent);
            goToAnimation(2);
            finish();
        });
    }

    @OnClick({R.id.back_btn, R.id.look_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                Intent intent=new Intent(appContext,MainActivity.class);
                Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
                startActivity(intent);
                finish();
                goToAnimation(2);
                break;
            case R.id.look_order:
                 intent = new Intent(appContext, MyInquiryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                goToAnimation(1);
                break;
        }
    }
}
