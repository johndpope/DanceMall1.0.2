package com.hzxmkuar.wumeihui.personal.myself.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.business.merchant.EvaluateActivity;
import com.hzxmkuar.wumeihui.business.myself.MerchantOrderActivity;
import com.hzxmkuar.wumeihui.business.myself.adapter.MerchantOrderAdapter;
import com.hzxmkuar.wumeihui.personal.myself.presenter.MyOrderPresenter;
import com.hzxmkuar.wumeihui.personal.order.PersonOrderActivity;
import com.hzxmkuar.wumeihui.personal.order.SelectPayActivity;
import com.hzxmkuar.wumeihui.personal.order.adapter.PersonOrderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.order.MyMerchantOrderTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/3.
 */

@SuppressLint("ValidFragment")
public class MyOrderFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private PersonOrderAdapter adapter;
    private MyOrderPresenter presenter;
    private int type;

    public MyOrderFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        unbinder = ButterKnife.bind(this, mView);

        presenter = new MyOrderPresenter(this, type);
        adapter = new PersonOrderAdapter(getActivity());
        setAdapterListener();
        setRecycleView(adapter, recycleView, presenter,true);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMerchantOrder();
    }

    private void setAdapterListener() {
        adapter.setOrderClickListener(new PersonOrderAdapter.OrderClickListener() {
            @Override
            public void confirmClick(MyMerchantOrderTo.ListsBean mode) {
                if (!TextUtils.isEmpty(mode.getTitle())) {
                    AlertDialog.show(getActivity(), mode.getTitle()).setOnClickListener(view -> {
                        AlertDialog.dismiss();
                        if (mode.getType()==6) {
                            presenter.confirmFinish(mode.getId());
                        }
                        if (mode.getType()==4){
                            if (!AppUtil.readSIMCard(appContext))
                                return;
                            getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                                @Override
                                public void accept(String permission) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    Uri data = Uri.parse("tel:" + mode.getMerchant_telephone());
                                    intent.setData(data);
                                    startActivity(intent);

                                }

                                @Override
                                public void refuse(String permission) {

                                }
                            });
                        }

                    });
                } else {
                  if (mode.getType()==3||mode.getType()==1){
                      Intent intent = new Intent(appContext, SelectPayActivity.class);
                      intent.putExtra("OrderId", mode.getId());
                      intent.putExtra("Money", (mode.getNew_status()==1?((mode.getPayment_mode()==1?mode.getTotal_amount():mode.getTotal_amount1())):mode.getTotal_amount2())+"");
                      intent.putExtra("Type",10);
                      intent.putExtra("PayTypeModel",mode.getNew_status()==1);
                      startActivity(intent);
                      goToAnimation(1);
                  }
                  if (mode.getType()==7){
                      Intent intent=new Intent(appContext,EvaluateActivity.class);
                      intent.putExtra("OrderId",mode.getId());
                      startActivity(intent);
                      goToAnimation(1);
                  }

                }
            }

            @Override
            public void cancleClick(MyMerchantOrderTo.ListsBean mode) {
                AlertDialog.show(getActivity(), "确定取消订单").setOnClickListener(view ->
                        {
                            AlertDialog.dismiss();
                            presenter.cancelOrder(mode.getId());
                        }
                );
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
        Intent intent = new Intent(appContext, PersonOrderActivity.class);
        MyMerchantOrderTo.ListsBean listData = (MyMerchantOrderTo.ListsBean) data;
        intent.putExtra("OrderId", ((MyMerchantOrderTo.ListsBean) data).getId());
        intent.putExtra("PayType",listData.getTotal_amount2()>0?1:0);
        startActivity(intent);
        goToAnimation(1);
    }
}
