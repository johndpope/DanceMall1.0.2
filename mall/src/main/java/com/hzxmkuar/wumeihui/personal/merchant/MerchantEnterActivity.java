package com.hzxmkuar.wumeihui.personal.merchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/3.
 */

public class MerchantEnterActivity extends BaseActivity {
    @BindView(R.id.agreement_view)
    View agreementView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_enter);
        ButterKnife.bind(this);
        ActivityManager.merchantEnterList.add(this);
        setTitleName(Constant.MERCHANT_ENTER);
    }

    @OnClick({R.id.person_enter, R.id.company_enter, R.id.enter_agreement, R.id.agreement_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_enter:
                if (!agreementView.isSelected()){
                    showMessage("请同意入驻协议");
                    return;
                }
                Intent intent = new Intent(appContext, MerchantEnterInputActivity.class);
                intent.putExtra("IsPerson", true);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.company_enter:
                if (!agreementView.isSelected()){
                    showMessage("请同意入驻协议");
                    return;
                }
                intent = new Intent(appContext, MerchantEnterInputActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.agreement_view:
                agreementView.setSelected(!agreementView.isSelected());
                agreementView.setBackgroundResource(agreementView.isSelected()?R.drawable.position_select:R.drawable.position_un_select);
                break;
            case R.id.enter_agreement:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 4);
                intent.putExtra("Title","入驻协议");
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }
}
