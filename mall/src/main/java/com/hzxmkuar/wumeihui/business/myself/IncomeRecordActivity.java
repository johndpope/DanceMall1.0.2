package com.hzxmkuar.wumeihui.business.myself;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.business.myself.adapter.IncomeRecordAdapter;
import com.hzxmkuar.wumeihui.business.myself.presenter.IncomeRecordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/15.
 */

public class IncomeRecordActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_bond_record);
        ButterKnife.bind(this);
        setTitleName("我的收入记录");
        setRecycleView(new IncomeRecordAdapter(this),recycleView,new IncomeRecordPresenter(this));
    }
}
