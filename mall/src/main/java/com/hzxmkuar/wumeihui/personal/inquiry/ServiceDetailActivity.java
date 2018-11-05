package com.hzxmkuar.wumeihui.personal.inquiry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.ServiceDetailAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.ServiceDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/30.
 **/

public class ServiceDetailActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title_recycle_view);
        ButterKnife.bind(this);
        setTitleName(Constant.SERVICE_DETAIL);
        ServiceDetailAdapter adapter=new ServiceDetailAdapter(this);
        ServiceDetailPresenter presenter=new ServiceDetailPresenter(this);
        setRecycleView(adapter,recycleView,presenter);
    }
}
