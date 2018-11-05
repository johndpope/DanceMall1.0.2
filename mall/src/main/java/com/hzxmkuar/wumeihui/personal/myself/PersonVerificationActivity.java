package com.hzxmkuar.wumeihui.personal.myself;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/5.
 */

public class PersonVerificationActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_verification);
        ButterKnife.bind(this);
        setTitleName(Constant.IDENTITY_VERIFICATION);
    }
}
