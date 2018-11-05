package com.hzxmkuar.wumeihui.business.merchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/27.
 */

public class MerchantManagerActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_manager);
        ButterKnife.bind(this);
        setTitleName(Constant.MERCHANT_MANAGER);
    }

    @OnClick(R.id.merchant_info)
    public void onViewClicked() {
        Intent intent=new Intent(appContext, MerchantInfoActivity.class);
        startActivity(intent);
        goToAnimation(1);
    }
}
