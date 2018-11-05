package com.hzxmkuar.wumeihui.personal.merchant;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.databinding.MainShopItemBinding;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.SelectMerchantPresenter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;

/**
 * Created by Administrator on 2018/8/30.
 */

public class MerchantListActivity extends BaseActivity {

    @BindView(R.id.carefully_select_layout)
    GridLayout carefullySelectLayout;
    @BindView(R.id.more_shop_layout)
    GridLayout moreShopLayout;
    @BindView(R.id.select_merchant_layout)
    GridLayout selectMerchantLayout;
    @BindView(R.id.select_number)
    TextView selectNumber;
    private List<MainMerchantTo.ListsBean> selectList = new ArrayList<>();
    private List<View> merchantViewList = new ArrayList<>();
    private String selectMerchant = "";
    private String selectMerchantName = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_list);
        ButterKnife.bind(this);
        SelectMerchantPresenter presenter = new SelectMerchantPresenter(this);
    }

    public void setCarefullyLayout(List<MainMerchantTo.ListsBean> merchantList) {
        if (merchantList == null || merchantList.size() == 0)
            return;
        selectList.addAll(merchantList);
        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.main_shop_item, null);
            MainMerchantTo.ListsBean mode = merchantList.get(i);
            MainShopItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getShop_logo());
            binding.merchantName.setText(mode.getShop_name());
            binding.typeName.setText(mode.getStype_txt());
            binding.orderNum.setText("成交" + mode.getOrder_num() + "比");
            binding.area.setText(mode.getArea());
            binding.serviceName.setText(mode.getService_exam());
            carefullySelectLayout.addView(mView);
            merchantViewList.add(mView);
            binding.selectIcon.setOnClickListener(view -> {
                mode.setSelect(!mode.isSelect());
                binding.selectIcon.setBackgroundResource(mode.isSelect() ? R.drawable.service_select : R.drawable.service_un_select);
                setSelectLayout();
            });
        }

    }

    public void setMoreShopLayout(List<MainMerchantTo.ListsBean> merchantList) {
        if (merchantList == null || merchantList.size() == 0)
            return;
        selectList.addAll(merchantList);
        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.main_shop_item, null);
            MainMerchantTo.ListsBean mode = merchantList.get(i);
            MainShopItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getShop_logo());
            binding.merchantName.setText(mode.getShop_name());
            binding.typeName.setText(mode.getStype_txt());
            binding.orderNum.setText("成交" + mode.getOrder_num() + "比");
            binding.area.setText(mode.getArea());
            binding.serviceName.setText(mode.getService_exam());
            moreShopLayout.addView(mView);
            merchantViewList.add(mView);
            binding.selectIcon.setOnClickListener(view -> {
                mode.setSelect(!mode.isSelect());
                binding.selectIcon.setBackgroundResource(mode.isSelect() ? R.drawable.service_select : R.drawable.service_un_select);
                setSelectLayout();
            });
        }
    }

    private void setSelectLayout() {
        selectMerchantLayout.removeAllViews();
        selectMerchant = "[";
        for (int i = 0; i < selectList.size(); i++) {
            MainMerchantTo.ListsBean mode = selectList.get(i);
            if (mode.isSelect()) {
                View mView = View.inflate(appContext, R.layout.select_inquiry_item, null);
                ((TextView) mView.findViewById(R.id.name)).setText(mode.getShop_name());
                mView.setTag(i);
                selectMerchantLayout.addView(mView);
                selectMerchant = selectMerchant + mode.getBus_uid() + ",";
                selectMerchantName=selectMerchantName+mode.getShop_name()+"  ";

                mView.findViewById(R.id.delete_icon).setOnClickListener(view -> {
                    selectList.get((Integer) mView.getTag()).setSelect(false);
                    merchantViewList.get((Integer) mView.getTag()).findViewById(R.id.select_icon).setBackgroundResource(R.drawable.service_un_select);
                    selectMerchantLayout.removeView(mView);
                    selectNumber.setText(selectMerchantLayout.getColumnCount() + "");
                });
            }
        }
        selectNumber.setText(selectMerchantLayout.getChildCount() + "");
    }

    @OnClick({R.id.select_merchant, R.id.no_merchant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_merchant:
                Intent intent = new Intent();
                if (selectMerchant.length() > 1)
                    selectMerchant = selectMerchant.substring(0, selectMerchant.length() - 1) + "]";
                intent.putExtra("SelectMerchant", selectMerchant);
                intent.putExtra("SelectMerchantName", selectMerchantName);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.no_merchant:
                break;
        }
    }
}
