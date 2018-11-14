package com.hzxmkuar.wumeihui.personal.merchant;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/3.
 **/

public class MerchantCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_check);
        ButterKnife.bind(this);
        setTitleName(Constant.MERCHANT_CHECK);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        finish();
    }
}
