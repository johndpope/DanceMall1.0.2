package com.hzxmkuar.wumeihui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.main.MainMerchantActivity;
import com.hzxmkuar.wumeihui.personal.MainActivity;


/**
 * Created by Administrator on 2018/9/11.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            if (userInfoHelp.getUserLogin()){
                if (SpUtil.getBoolean("IsMerchant")){
                    Intent intent=new Intent(appContext,MainMerchantActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(appContext, MainActivity.class);
                    startActivity(intent);
                }

            }else{
                Intent intent=new Intent(appContext,LoginActivity.class);
                startActivity(intent);
            }
            goToAnimation(1);
            finish();

        },3000);

    }
}
