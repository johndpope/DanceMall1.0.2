package com.hzxmkuar.wumeihui.personal.integral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.integral.presenter.ConfirmIntegralPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.integral.GoodsDetailTo;
import hzxmkuar.com.applibrary.domain.integral.GoodsTo;

/**
 * Created by Administrator on 2018/9/3.
 */

public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.goods_Image)
    ImageView goodsImage;
    private GoodsDetailTo mode;
    private ConfirmIntegralPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_confirm_order);
        ButterKnife.bind(this);
        setTitleName(Constant.CONFIRM_ORDER);
        mode = (GoodsDetailTo) getIntent().getSerializableExtra("GoodsTo");
        setView();
        presenter = new ConfirmIntegralPresenter(this);
    }

    private void setView() {
        goodsName.setText(mode.getGoods_name());
        integral.setText(mode.getGoods_integral() + "积分");
        displayImage(goodsImage,mode.getGoods_image());
        pay.setText(mode.getGoods_integral() + "积分");
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(userName.getText().toString())){
            showMessage("请填写收货人");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())){
            showMessage("请填写手机号码");
            return;
        }
        if (TextUtils.isEmpty(address.getText().toString())){
            showMessage("请填写收获地址");
            return;
        }
        presenter.confirmOrder(mode.getGoods_id(),address.getText().toString(),phone.getText().toString(),userName.getText().toString());
    }
}
