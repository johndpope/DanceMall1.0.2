package com.hzxmkuar.wumeihui.business.myself.fragment;

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
import com.hzxmkuar.wumeihui.business.myself.adapter.MyQuoteAdapter;

import com.hzxmkuar.wumeihui.business.myself.presenter.MyQuotePresenter;
import com.hzxmkuar.wumeihui.business.quote.QuoteDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.quote.MyQuoteTo;

/**
 * Created by Administrator on 2018/9/3.
 **/

@SuppressLint("ValidFragment")
public class MyQuoteFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private int type;
    private MyQuoteAdapter adapter;
    private MyQuotePresenter presenter;

    public MyQuoteFragment(int type){
        this.type=type;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        unbinder = ButterKnife.bind(this, mView);
        adapter = new MyQuoteAdapter(getActivity());
        presenter = new MyQuotePresenter(this,type);
        setRecycleView(adapter,recycleView, presenter,true);
        setAdapter();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMyQuote();
    }

    private void setAdapter() {
        adapter.setCancelClickListener(mode -> {
            AlertDialog.show(getActivity(),"放弃报价").setOnClickListener(view -> {
                AlertDialog.dismiss();
                presenter.cancelQuote(mode.getId());
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        Intent intent=new Intent(appContext, QuoteDetailActivity.class);
        intent.putExtra("QuoteId",((MyQuoteTo.ListsBean)data).getId());
        startActivity(intent);
    }

}
