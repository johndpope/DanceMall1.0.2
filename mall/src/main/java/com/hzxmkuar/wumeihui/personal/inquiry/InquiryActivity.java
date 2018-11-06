package com.hzxmkuar.wumeihui.personal.inquiry;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.business.merchant.MerchantDetailActivity;
import com.hzxmkuar.wumeihui.databinding.InqueryMerchantDetailItemBinding;
import com.hzxmkuar.wumeihui.databinding.MerchantScreenLayoutBinding;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.InquiryPresenter;
import com.hzxmkuar.wumeihui.personal.order.OrderSettleActivity;
import com.ruffian.library.RTextView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import hzxmkuar.com.applibrary.domain.inquery.InquiryInfoTo;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/8/30.
 **/

public class InquiryActivity extends BaseActivity {
    @BindView(R.id.merchant_layout)
    GridLayout merchantLayout;
    @BindView(R.id.inquiry_name)
    TextView inquiryName;
    @BindView(R.id.statue)
    TextView statue;

    @BindView(R.id.use_time)
    TextView useTime;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.service_num)
    TextView serviceNum;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.merchant_number)
    TextView merchantNumber;
    @BindView(R.id.count_time)
    CountdownView countTime;
    @BindView(R.id.pop_layout)
    AutoRelativeLayout popLayout;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.count_time_layout)
    AutoLinearLayout countTimeLayout;
    private InquiryInfoTo inquiryInfoTo;
    private RTextView lastTag;
    private int sortType;
    private InquiryPresenter presenter;
    private List<MerchantCityTo.ListsBean> cityList;
    private List<ServiceListTo.ListsBean> serviceList;
    private View lastView;
    private int scrollY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiry);
        ButterKnife.bind(this);
        setTitleName(Constant.INQUIRY_DETAIL);

        presenter = new InquiryPresenter(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setView(InquiryInfoTo inquiryInfoTo) {
        this.inquiryInfoTo = inquiryInfoTo;
        inquiryName.setText(inquiryInfoTo.getInquiry_sn());
        statue.setText(inquiryInfoTo.getStatus_arr().getStatus_txt());
        useTime.setText(inquiryInfoTo.getUse_time());
        userName.setText(inquiryInfoTo.getContact_name() + "   " + inquiryInfoTo.getContact_telphone());
        serviceNum.setText("共" + inquiryInfoTo.getService_num() + "项服务");
        address.setText(inquiryInfoTo.getService_address());
        countTime.start(inquiryInfoTo.getValid_time() * 1000);
        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            scrollY = i1;
        });
        countTimeLayout.setVisibility((inquiryInfoTo.getStatus_arr().getStatus()==1||inquiryInfoTo.getStatus_arr().getStatus()==2)?View.VISIBLE:View.GONE);
    }

    public void setMerchantLayout(List<MainMerchantTo.ListsBean> merchantList) {
        merchantLayout.removeAllViews();
        if (merchantList == null || merchantList.size() == 0) {
            return;
        }
        merchantNumber.setText("商家报价 共" + merchantList.size() + "家");
        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.inquery_merchant_detail_item, null);
            MainMerchantTo.ListsBean mode = merchantList.get(i);
            InqueryMerchantDetailItemBinding binding = DataBindingUtil.bind(mView);
            binding.fee.setText(mode.getOffer_amount());
            binding.isTop.setVisibility(mode.getIs_offertop() == 1 ? View.VISIBLE : View.GONE);
            binding.haveTicket.setText(mode.getInvoice_txt());
            binding.rank.setText(mode.getRatings());
            binding.shopType.setText(mode.getStype_txt());
            binding.shopName.setText(mode.getShop_name());
            binding.area.setText(mode.getArea());
            merchantLayout.addView(mView);
            binding.settle.setOnClickListener(view -> {
                if (inquiryInfoTo.getStatus_arr().getStatus()!=1&&inquiryInfoTo.getStatus_arr().getStatus()!=2){
                    showMessage("订单不能结算");
                    return;
                }
                Intent intent = new Intent(appContext, OrderSettleActivity.class);
                intent.putExtra("MerchantTo", mode);
                intent.putExtra("InquiryTo", inquiryInfoTo);
                intent.putExtra("BusofferId", mode.getBusoffer_id());
                startActivity(intent);
            });
            binding.aboutShop.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, MerchantDetailActivity.class);
                intent.putExtra("MerchantId", mode.getBus_uid());
                startActivity(intent);
                goToAnimation(1);
            });
            binding.chat.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, ChatActivity.class);
                intent.putExtra("UserId", mode.getMerchant_telephone());
                intent.putExtra("Name", mode.getShop_name());
                startActivity(intent);
                goToAnimation(1);
            });
            binding.phone.setOnClickListener(v -> {
                if (!AppUtil.readSIMCard(appContext))
                    return;
                getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                    @Override
                    public void accept(String permission) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + mode.getMerchant_telephone());
                        intent.setData(data);
                        startActivity(intent);

                    }

                    @Override
                    public void refuse(String permission) {

                    }
                });
            });
        }
    }

    @OnClick({R.id.inquiry_btn, R.id.service_detail_layout, R.id.sort_layout, R.id.screen_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inquiry_btn:
                cancelInquiry();
                break;
            case R.id.service_detail_layout:
                Intent intent = new Intent(appContext, ServiceDetailActivity.class);
                intent.putExtra("Id", inquiryInfoTo.getId());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.sort_layout:
                sortPopWindow(popLayout);
                break;
            case R.id.screen_layout:
                screenPopWindow(popLayout);
                break;
        }
    }

    private void sortPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_sort_layout, null);


        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() - popLayout.getBottom() - getScreenWidth() * 204 / 750, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0, 0);
        contentView.findViewById(R.id.sort_text1).setOnClickListener(view -> {
            sortType = 1;
            presenter.param.setSort_type(1);
            presenter.getInquiryMerchant();

            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text2).setOnClickListener(view -> {
            sortType = 2;
            presenter.param.setSort_type(2);
            presenter.getInquiryMerchant();
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text3).setOnClickListener(view -> {
            sortType = 3;
            presenter.param.setSort_type(3);
            presenter.getInquiryMerchant();
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text4).setOnClickListener(view -> {
            sortType = 4;
            presenter.param.setSort_type(4);
            presenter.getInquiryMerchant();
            window.dismiss();
        });
    }

    private void topicPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.circle_topic_layout, null);

        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0, 0);
        GridLayout sortLeftLayout = contentView.findViewById(R.id.sort_left_layout);
        GridLayout sortRightLayout = contentView.findViewById(R.id.sort_right_layout);


        for (ServiceListTo.ListsBean mainServiceTo : serviceList) {

            View mView = View.inflate(appContext, R.layout.communication_sort_left_item, null);
            ((TextView) mView.findViewById(R.id.type_name)).setText(mainServiceTo.getService_name());
            if (sortLeftLayout.getChildCount() == 0) {
                mView.setBackgroundColor(Color.parseColor("#f7f7f7"));
                lastView = mView;
            }
            sortLeftLayout.addView(mView);
            sortRightLayout.removeAllViews();
            mView.setOnClickListener(view -> {
                lastView.setBackgroundColor(Color.parseColor("#ffffff"));
                mView.setBackgroundColor(Color.parseColor("#f7f7f7"));
                lastView = mView;
                sortRightLayout.removeAllViews();
                for (ServiceListTo.ListsBean value : mainServiceTo.getList2()) {
                    View childView = View.inflate(appContext, R.layout.communication__sort_right_item, null);
                    ((TextView) childView.findViewById(R.id.type_name)).setText(value.getService_name());
                    sortRightLayout.addView(childView);
                    childView.setOnClickListener(view2 -> {

                        presenter.param.setSort_type(value.getId());
                        presenter.getInquiryMerchant();
                        window.dismiss();
                    });
                }
            });

        }
        for (ServiceListTo.ListsBean value : serviceList.get(0).getList2()) {
            View childView = View.inflate(appContext, R.layout.communication__sort_right_item, null);
            ((TextView) childView.findViewById(R.id.type_name)).setText(value.getService_name());
            sortRightLayout.addView(childView);
            childView.setOnClickListener(view -> {
                presenter.param.setSort_type(value.getId());
                presenter.getInquiryMerchant();
                window.dismiss();
            });
        }
    }

    private void screenPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_screen_layout, null);
        MerchantScreenLayoutBinding binding = DataBindingUtil.bind(contentView);
        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() - popLayout.getBottom() - getScreenWidth() * 204 / 750, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0, 0);
        binding.person.setOnClickListener(view -> {
            binding.person.setBorderColorNormal(Color.parseColor("#3bafd9"));
            binding.person.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
            binding.person.setTextColorNormal(Color.parseColor("#ffffff"));
            binding.company.setBorderColorNormal(Color.parseColor("#999999"));
            binding.company.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            binding.company.setTextColorNormal(Color.parseColor("#999999"));
            presenter.param.setBusiness_type(2);
        });

        binding.company.setOnClickListener(view -> {
            binding.company.setBorderColorNormal(Color.parseColor("#3bafd9"));
            binding.company.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
            binding.company.setTextColorNormal(Color.parseColor("#ffffff"));
            binding.person.setBorderColorNormal(Color.parseColor("#999999"));
            binding.person.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            binding.person.setTextColorNormal(Color.parseColor("#999999"));
            presenter.param.setBusiness_type(1);
        });

        contentView.findViewById(R.id.reset).setOnClickListener(view -> {
            presenter.param.setOnum_range("");
            presenter.param.setBusiness_type(0);
            presenter.param.setArea_ids("");
            presenter.param.setRatings_range("");

            binding.person.setBorderColorNormal(Color.parseColor("#999999"));
            binding.person.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            binding.person.setTextColorNormal(Color.parseColor("#999999"));
            binding.company.setBorderColorNormal(Color.parseColor("#999999"));
            binding.company.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            binding.company.setTextColorNormal(Color.parseColor("#999999"));
            lastTag.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            lastTag.setTextColorNormal(Color.parseColor("#999999"));
            lastTag.setBorderColorNormal(Color.parseColor("#999999"));
            binding.orderNumText.setText("0单以上");
            binding.scoreRank.setText("0分以上");
            binding.orderNum.setProgress(0);
            binding.score.setProgress(0);
        });
        contentView.findViewById(R.id.screen_confirm).setOnClickListener(view -> {
            presenter.getInquiryMerchant();
            window.dismiss();
        });
        binding.score.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                binding.scoreRank.setText(progress / 20 + "分以上");
                presenter.param.setRatings_range(progress / 20 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.orderNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                binding.orderNumText.setText(progress + "单以上");
                presenter.param.setOnum_range(progress / 20 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.flowLayout.setMaxSelectCount(1);
        binding.flowLayout.setAdapter(new TagAdapter<MerchantCityTo.ListsBean>(cityList) {
            @Override
            public View getView(FlowLayout parent, int position, MerchantCityTo.ListsBean cityTo) {
                View mView = View.inflate(appContext, R.layout.merchant_city_item, null);
                RTextView cityName = mView.findViewById(R.id.city_name);
                cityName.setText(cityTo.getArea());
                lastTag = cityName;
                return mView;
            }
        });
        binding.flowLayout.setOnTagClickListener((view, position, parent) -> {
            lastTag.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            lastTag.setTextColorNormal(Color.parseColor("#999999"));
            lastTag.setBorderColorNormal(Color.parseColor("#999999"));
            RTextView cityName = view.findViewById(R.id.city_name);
            cityName.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
            cityName.setTextColorNormal(Color.parseColor("#ffffff"));
            cityName.setBorderColorNormal(Color.parseColor("#3bafd9"));
            presenter.param.setArea_ids(cityList.get(position).getId() + "");
            lastTag = cityName;
            return false;
        });
    }

    public void setCityLayout(List<MerchantCityTo.ListsBean> cityList) {
        this.cityList = cityList;
        lastTag = null;

    }

    public void setSort(List<ServiceListTo.ListsBean> serviceList) {
        this.serviceList = serviceList;

    }

    public void cancelInquiry() {
        AlertDialog.show(this, "确定放弃询价").setOnClickListener(view -> {
            presenter.cancelInquiry();
            AlertDialog.dismiss();
        });
    }
}
