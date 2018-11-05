package com.hzxmkuar.wumeihui.personal.integral.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.personal.integral.IntegralOrderActivity;
import com.hzxmkuar.wumeihui.personal.integral.adapter.MyIntegralAdapter;
import com.hzxmkuar.wumeihui.personal.integral.presenter.MyIntegralPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.integral.IntegralOrderTo;

/**
 * Created by Administrator on 2018/9/3.
 */

@SuppressLint("ValidFragment")
public class MyIntegralFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private int type;
    public MyIntegralFragment(int type){
        this.type=type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        unbinder = ButterKnife.bind(this, mView);

         setRecycleView(new MyIntegralAdapter(getActivity()),recycleView,new MyIntegralPresenter(this,type));
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        Intent intent=new Intent(appContext, IntegralOrderActivity.class);
        intent.putExtra("OrderId", ((IntegralOrderTo.ListsBean)data).getOrder_id());
        startActivity(intent);
        goToAnimation(1);
    }
}
