package com.hzxmkuar.wumeihui.personal.integral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.integral.adapter.IntegralRecordAdapter;
import com.hzxmkuar.wumeihui.personal.integral.presenter.IntegralRecordPresenter;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/3.
 */

public class IntegralRecordActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_record);
        ButterKnife.bind(this);
        setTitleName(Constant.INTEGRAL_USE_RECORD);
        setRecycleView(new IntegralRecordAdapter(this), recycleView, new IntegralRecordPresenter(this));
    }
}
