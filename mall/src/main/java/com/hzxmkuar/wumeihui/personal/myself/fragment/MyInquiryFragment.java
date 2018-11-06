package com.hzxmkuar.wumeihui.personal.myself.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseFragment;

import com.hzxmkuar.wumeihui.personal.inquiry.InquiryActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.InquiryDesActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.MyInquiryAdapter;
import com.hzxmkuar.wumeihui.personal.myself.presenter.MyInquiryPresenter;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/3.
 */

@SuppressLint("ValidFragment")
public class MyInquiryFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;

    private int type;
    private String sid;
    private MyInquiryPresenter presenter;
    private MyInquiryAdapter adapter;

    public MyInquiryFragment(int type){
        this.type=type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        unbinder = ButterKnife.bind(this, mView);
        adapter = new MyInquiryAdapter(getActivity());
        presenter = new MyInquiryPresenter(this,type);
        setRecycleView(adapter,recycleView, presenter,true);
        recycleView.setLoadMoreEnabled(true);
        setAdapter();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMyInquiry();
    }

    private void setAdapter() {

        adapter.setCancelListener(new MyInquiryAdapter.CancelClickListener() {
            @Override
            public void cancelClick(MyInquiryTo.ListsBean mode) {
                AlertDialog.show(getActivity(),"确定放弃询价").setOnClickListener(view -> {
                    presenter.cancelInquiry(mode.getId());
                    AlertDialog.dismiss();
                });
            }

            @Override
            public void confirmClick(MyInquiryTo.ListsBean mode) {
                sid = "";
                Observable.from(mode.getService_list()).subscribe(serviceListBean -> sid = sid +serviceListBean.getSid()+",");
                presenter.confirmInquiry(sid.substring(0,sid.length()-1));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        super.recycleItemClick(view, position, data);
        MyInquiryTo.ListsBean mode= (MyInquiryTo.ListsBean) data;

                Intent intent = new Intent(appContext, InquiryActivity.class);
                intent.putExtra("InquiryId", ((MyInquiryTo.ListsBean) data).getId());
                startActivity(intent);
                goToAnimation(1);


    }

    @Override
    protected void submitDataSuccess(Object data) {
        Intent intent = new Intent(appContext, InquiryDesActivity.class);
        startActivity(intent);
        goToAnimation(1);
    }
}
