package com.hzxmkuar.wumeihui.personal.integral;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.personal.integral.presenter.IntegralDetailPresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.integral.GoodsDetailTo;

/**
 * Created by Administrator on 2018/8/30.
 */

public class IntegrationDetailActivity extends BaseActivity {

    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.goods_detail)
    TextView goodsDetail;
    @BindView(R.id.purchase_tip)
    TextView purchaseTip;
    @BindView(R.id.move_line)
    AutoRelativeLayout moveLine;
    @BindView(R.id.goods_image)
    ImageView goodsImage;
    private GoodsDetailTo mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration_detail);
        ButterKnife.bind(this);
        new IntegralDetailPresenter(this);
        setTitleName("");
        ActivityManager.integrationDetailActivity=this;

    }

    @Override
    public void loadDataSuccess(Object data) {
        mode = (GoodsDetailTo) data;
        goodsName.setText(mode.getGoods_name());
        integral.setText(mode.getGoods_integral() + "积分");
        content.setText(mode.getDesc());
       displayImage(goodsImage,mode.getGoods_image());
    }


    @OnClick({R.id.goods_detail, R.id.purchase_tip,R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goods_detail:
                content.setText(mode.getDesc());
                goodsDetail.setTextColor(Color.parseColor("#000000"));
                purchaseTip.setTextColor(Color.parseColor("#999999"));
                moveLine.setX(0);
                break;
            case R.id.purchase_tip:
                content.setText(mode.getPurchase_notes());
                goodsDetail.setTextColor(Color.parseColor("#999999"));
                purchaseTip.setTextColor(Color.parseColor("#000000"));
                moveLine.setX(getScreenWidth()/2);
                break;
            case R.id.submit:
                Intent intent = new Intent(appContext, ConfirmOrderActivity.class);
                intent.putExtra("GoodsTo", mode);
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }
}
