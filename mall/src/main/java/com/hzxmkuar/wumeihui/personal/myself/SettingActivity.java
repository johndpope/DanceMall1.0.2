package com.hzxmkuar.wumeihui.personal.myself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.WebActivity;
import com.hzxmkuar.wumeihui.base.util.GlideCacheUtil;
import com.hzxmkuar.wumeihui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/5.
 **/

public class SettingActivity extends BaseActivity {
    @BindView(R.id.cache)
    TextView cache;
    private GlideCacheUtil cacheUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setTitleName(Constant.SETTING);
        cacheUtil = GlideCacheUtil.getInstance();
        setView();
    }

    private void setView() {
         cache.setText(cacheUtil.getCacheSize(appContext));
    }

    @OnClick({R.id.suggest_layout, R.id.dispute_layout, R.id.report_layout, R.id.platform_layout, R.id.standard_layout, R.id.enter_layout, R.id.clean_cache, R.id.login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.suggest_layout:
                Intent intent = new Intent(appContext, SuggestActivity.class);
                startActivity(intent);
                goToAnimation(1);

                break;
            case R.id.dispute_layout:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Title", "交易纠纷");
                intent.putExtra("Type", 1);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.report_layout:
                intent = new Intent(appContext, ReportActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.platform_layout:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 4);
                intent.putExtra("Title", "平台规范");
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.standard_layout:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Title", "招商标准");
                intent.putExtra("Type", 3);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.enter_layout:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Title", "入驻事项");
                intent.putExtra("Type", 2);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.clean_cache:
               AlertDialog.show(this,"确定清除缓存").setOnClickListener(view1 -> {
                   AlertDialog.dismiss();
                   cacheUtil.clearImageAllCache(appContext);
                   cache.setText("0MB");
               });
                break;
            case R.id.login_out:
                AlertDialog.show(this, "退出登录?").setOnClickListener(view1 -> {
                    Intent out = new Intent(appContext, LoginActivity.class);
                    startActivity(out);
                    userInfoHelp.saveUserInfo(null);
                    userInfoHelp.saveUserLogin(false);
                    goToAnimation(1);
                    Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
                });
                break;
        }
    }
}
