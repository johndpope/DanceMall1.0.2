package com.hzxmkuar.wumeihui.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.circle.adapter.CircleAdapter;
import com.hzxmkuar.wumeihui.circle.presenter.MyCirclePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/4.
 **/

public class MyPostActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title_recycle_view);
        ButterKnife.bind(this);
        setTitleName(Constant.MY_PUBLISH);
        MyCirclePresenter presenter = new MyCirclePresenter(this);
        presenter.getMyPost();
        setRecycleView(new CircleAdapter(this), recycleView, presenter);
    }
}
