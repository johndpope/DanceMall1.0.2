package com.hzxmkuar.wumeihui.business.myself.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.jdsjlzx.recyclerview.LRecyclerView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.business.myself.MerchantOrderActivity;
import com.hzxmkuar.wumeihui.business.myself.adapter.MerchantOrderAdapter;
import com.hzxmkuar.wumeihui.business.myself.presenter.MyMerchantPresenter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.order.MyMerchantOrderTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/3.
 **/

@SuppressLint("ValidFragment")
public class MerchantOrderFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private MyMerchantPresenter presenter;
    private MerchantOrderAdapter adapter;

    private int type;

    public MerchantOrderFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        unbinder = ButterKnife.bind(this, mView);

        presenter = new MyMerchantPresenter(this, type);
        adapter = new MerchantOrderAdapter(getActivity());
        setRecycleView(adapter, recycleView, presenter,true);
        setAdapterListener();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMerchantOrder();
    }

    private void setAdapterListener() {
        adapter.setOrderClickListener(new MerchantOrderAdapter.OrderClickListener() {
            @Override
            public void confirmClick(MyMerchantOrderTo.ListsBean mode) {

                AlertDialog.show(getActivity(), "待确认".equals(mode.getStatus_txt()) ? "确认接收订单" :
                        "待付尾款".equals(mode.getStatus_txt()) ? "联系客户" :
                                "待服务".equals(mode.getStatus_txt()) ? "确认开始订单" : "确认完成订单"
                ).setOnClickListener(view -> {
                    AlertDialog.dismiss();
                    if ("待确认".equals(mode.getStatus_txt()))
                    presenter.confirmReceiver(mode.getId());
                    if ("待服务".equals(mode.getStatus_txt()))
                        presenter.startOrder(mode.getId());
                    if ("服务中".equals(mode.getStatus_txt()))
                        presenter.finishOrder(mode.getId());
                    if ("待付尾款".equals(mode.getStatus_txt()))
                        if (!AppUtil.readSIMCard(appContext))
                            return;
                        getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                            @Override
                            public void accept(String permission) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                Uri data = Uri.parse("tel:" + mode.getCustomer_telephone());
                                intent.setData(data);
                                startActivity(intent);
                            }

                            @Override
                            public void refuse(String permission) {

                            }
                        });

                });

            }

            @Override
            public void cancleClick(MyMerchantOrderTo.ListsBean mode) {
                AlertDialog.show(getActivity(), "取消订单").setOnClickListener(view -> {
                    AlertDialog.dismiss();
                    presenter.cancelReceiver(mode.getId());
                });
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
        Intent intent = new Intent(appContext, MerchantOrderActivity.class);
        intent.putExtra("OrderId", ((MyMerchantOrderTo.ListsBean) data).getId());
        startActivity(intent);
        goToAnimation(1);
    }


}
