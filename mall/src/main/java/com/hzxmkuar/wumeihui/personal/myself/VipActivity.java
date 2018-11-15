package com.hzxmkuar.wumeihui.personal.myself;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.personal.myself.presenter.VipPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.user.VipTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/5.
 */

public class VipActivity extends BaseActivity implements PermissionListener {
    @BindView(R.id.vip_btn)
    TextView vipBtn;
    @BindView(R.id.web_view)
    WebView webView;
    private VipTo vipTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        setTitleName(Constant.VIP_RIGHT);
        VipPresenter presenter = new VipPresenter(this);
        webView.loadUrl(MainApp.webBaseUrl+"8");
    }

    @Override
    public void loadDataSuccess(Object data) {
        vipTo = (VipTo) data;
        vipBtn.setText(vipTo.getStatus() == 0 ? "联系客服成为VIP" : ("有效期" + vipTo.getValid_time()));


    }

    @OnClick(R.id.vip_btn)
    public void onViewClicked() {
        if (!AppUtil.readSIMCard(appContext,this))
            return;
        if (vipTo.getStatus() == 0) {
            getPermission(Manifest.permission.CALL_PHONE, this);
        }
    }

    @Override
    public void accept(String permission) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + vipTo.getKf_tel());
        intent.setData(data);
        startActivity(intent);

    }
}
