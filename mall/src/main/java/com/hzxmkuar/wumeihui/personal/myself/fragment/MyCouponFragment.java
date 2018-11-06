package com.hzxmkuar.wumeihui.personal.myself.fragment;

import android.annotation.SuppressLint;
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
import com.hzxmkuar.wumeihui.personal.order.adapter.MyCouponAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.order.MyCouponTo;

/**
 * Created by Administrator on 2018/9/1.
 **/

@SuppressLint("ValidFragment")
public class MyCouponFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private int type;
    private List<MyCouponTo.CouponTo>couponList;

    public MyCouponFragment(int type, List<MyCouponTo.CouponTo>couponList) {
        this.type = type;
        this.couponList=couponList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);

        unbinder = ButterKnife.bind(this, mView);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyCouponAdapter adapter = new MyCouponAdapter(getActivity(),type);
        adapter.setList(couponList);
        recycleView.setAdapter(new LRecyclerViewAdapter(adapter));
        recycleView.setPullRefreshEnabled(false);
        recycleView.setLoadMoreEnabled(false);

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
