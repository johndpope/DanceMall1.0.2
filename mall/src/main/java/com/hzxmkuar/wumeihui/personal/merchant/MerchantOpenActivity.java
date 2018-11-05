package com.hzxmkuar.wumeihui.personal.merchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.main.MainMerchantActivity;
import com.hzxmkuar.wumeihui.business.merchant.MerchantManagerActivity;
import com.hzxmkuar.wumeihui.business.myself.BondActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/3.
 */

public class MerchantOpenActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_open);
        ButterKnife.bind(this);
        setTitleName(Constant.OPEN_SUCCESS);
        SpUtil.put("FirstEnterMerchant", true);
    }

    @OnClick({R.id.bond, R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bond:
                Intent intent=new Intent(appContext, BondActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(appContext,MainMerchantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if (ActivityManager.mainActivity!=null)
            ActivityManager.mainActivity.finish();
        finish();
        goToAnimation(1);
    }
}
