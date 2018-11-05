package com.hzxmkuar.wumeihui.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;

/**
 * Created by Administrator on 2018/9/1.
 */

public class GuaranteeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantee);
        setTitleName(Constant.GUARANTEE);
    }
}
