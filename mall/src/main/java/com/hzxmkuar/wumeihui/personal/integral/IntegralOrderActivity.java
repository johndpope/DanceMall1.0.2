package com.hzxmkuar.wumeihui.personal.integral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.integral.presenter.IntegralOrderPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzxmkuar.com.applibrary.domain.integral.IntegralDetailTo;

/**
 * Created by Administrator on 2018/9/3.
 */

public class IntegralOrderActivity extends BaseActivity {
    @BindView(R.id.statue_tip)
    TextView statueTip;
    @BindView(R.id.statue)
    TextView statue;
    @BindView(R.id.goods_image)
    ImageView goodsImage;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.goods_name)
    TextView goodsName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setTitleName(Constant.ORDER_DETAIL);
        IntegralOrderPresenter presenter = new IntegralOrderPresenter(this);

    }

    @Override
    public void loadDataSuccess(Object data) {
        IntegralDetailTo orderTo = (IntegralDetailTo) data;
        statue.setText(orderTo.getStatus_txt());
        displayImage(goodsImage, orderTo.getGoods_image());
        address.setText(orderTo.getAddress());
        userName.setText(orderTo.getConsignee() + "  " + orderTo.getTelephone());
        integral.setText(orderTo.getTotal_amount() + "积分");
        goodsName.setText(orderTo.getGoods_name());
    }
}
