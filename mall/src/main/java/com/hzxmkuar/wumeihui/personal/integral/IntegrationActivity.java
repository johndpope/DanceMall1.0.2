package com.hzxmkuar.wumeihui.personal.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.integral.adapter.IntegrationAdapter;
import com.hzxmkuar.wumeihui.personal.integral.presenter.GoodsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.integral.GoodsTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralInfoTo;

/**
 * Created by Administrator on 2018/9/3.
 */

public class IntegrationActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.integral_info)
    TextView integralInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration);
        ButterKnife.bind(this);
        setTitleName(Constant.MY_INTEGRATION);

        GoodsPresenter presenter = new GoodsPresenter(this);
        setRecycleView(new IntegrationAdapter(this), recycleView, presenter, 2);

    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        GoodsTo.ListsBean mode = (GoodsTo.ListsBean) data;
        Intent intent = new Intent(appContext, IntegrationDetailActivity.class);
        intent.putExtra("GoodsId", mode.getGoods_id());
        startActivity(intent);
        goToAnimation(1);
    }

    @Override
    protected void submitDataSuccess(Object data) {
        IntegralInfoTo infoTo = (IntegralInfoTo) data;
        integralInfo.setText(infoTo.getScore()+"");

    }

    @OnClick(R.id.use_record)
    public void onViewClicked() {
        Intent intent=new Intent(appContext,IntegralRecordActivity.class);
        startActivity(intent);
        goToAnimation(1);


    }
}
