package com.hzxmkuar.wumeihui.personal.inquiry;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.databinding.MainShopItemBinding;
import com.hzxmkuar.wumeihui.databinding.MerchantScreenLayoutBinding;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.SelectMerchantPresenter;
import com.hzxmkuar.wumeihui.personal.main.SelectProvinceActivity;
import com.ruffian.library.RTextView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.main.MerchantInfoTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/30.
 */

public class SelectMerchantActivity extends BaseActivity {

    @BindView(R.id.carefully_select_layout)
    GridLayout carefullySelectLayout;
    @BindView(R.id.more_shop_layout)
    GridLayout moreShopLayout;
    @BindView(R.id.select_merchant_layout)
    TagFlowLayout selectMerchantLayout;
    @BindView(R.id.select_number)
    TextView selectNumber;
    @BindView(R.id.change_city)
    TextView changeCity;
    @BindView(R.id.pop_layout)
    AutoLinearLayout popLayout;
    @BindView(R.id.sort_select_layout)
    AutoLinearLayout sortSelectLayout;
    @BindView(R.id.select_screen_layout)
    AutoLinearLayout selectScreenLayout;
    @BindView(R.id.back)
    AutoRelativeLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_layout)
    AutoRelativeLayout titleLayout;
    @BindView(R.id.sort_layout)
    AutoLinearLayout sortLayout;
    @BindView(R.id.screen_layout)
    AutoLinearLayout screenLayout;
    @BindView(R.id.search_layout)
    AutoLinearLayout searchLayout;

    @BindView(R.id.no_merchant)
    TextView noMerchant;
    @BindView(R.id.bottom_layout)
    AutoLinearLayout bottomLayout;
    @BindView(R.id.sort_text1)
    TextView sortText1;
    @BindView(R.id.sort_text2)
    TextView sortText2;
    @BindView(R.id.sort_select_icon_2)
    View sortSelectIcon2;
    @BindView(R.id.sort_select_icon_1)
    View sortSelectIcon1;
    @BindView(R.id.sort_text3)
    TextView sortText3;
    @BindView(R.id.sort_select_icon_3)
    View sortSelectIcon3;
    @BindView(R.id.sort_text4)
    TextView sortText4;
    @BindView(R.id.sort_select_icon_4)
    View sortSelectIcon4;
    @BindView(R.id.person)
    RTextView person;
    @BindView(R.id.company)
    RTextView company;
    @BindView(R.id.score_rank)
    TextView scoreRank;
    @BindView(R.id.score)
    SeekBar score;
    @BindView(R.id.order_num_text)
    TextView orderNumText;
    @BindView(R.id.order_num)
    SeekBar orderNum;
    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.screen_confirm)
    TextView screenConfirm;
    @BindView(R.id.search)
    EditText search;
    public List<MerchantInfoTo> selectList = new ArrayList<>();
    private List<View> merchantViewList = new ArrayList<>();
    private String selectMerchant = "";
    private String selectMerchantName = "";
    private SelectMerchantPresenter presenter;
    private int sortType;
    private RTextView lastTag;
    private List<MerchantCityTo.ListsBean> cityList;
    private List<String> selectIdList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_merchant);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = new SelectMerchantPresenter(this);
        setTitleName(Constant.SELECT_MERCHANT);
        setSeekBar();
        setSearch();
    }

    private void setSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.param.setKeyword(search.getText().toString());
                presenter.getMerchant();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setCarefullyLayout(List<MerchantInfoTo> merchantList) {
        carefullySelectLayout.removeAllViews();
        if (merchantList == null || merchantList.size() == 0)
            return;
        selectList.addAll(merchantList);
        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.main_shop_item, null);
            MerchantInfoTo mode = merchantList.get(i);
            MainShopItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getShop_logo(), R.drawable.shop_defalut_icon);
            binding.merchantName.setText(mode.getShop_name());
            binding.typeName.setText(mode.getStype_txt());
            binding.orderNum.setText("成交" + mode.getOrder_num() + "比");
            binding.area.setText(mode.getArea());
            binding.serviceName.setText(mode.getService_exam());
            carefullySelectLayout.addView(mView);
            merchantViewList.add(mView);

            if (selectIdList.contains(mode.getId() + "")) {
                mode.setSelect(true);
                binding.selectIcon.setBackgroundResource(R.drawable.service_select);
                setSelectLayout();
            }
            binding.selectIcon.setOnClickListener(view -> {
                mode.setSelect(!mode.isSelect());
                binding.selectIcon.setBackgroundResource(mode.isSelect() ? R.drawable.service_select : R.drawable.service_un_select);
                if (!mode.isSelect()) {
                    selectIdList.remove(mode.getId() + "");
                }
                setSelectLayout();
            });
        }

    }

    public void setMoreShopLayout(List<MerchantInfoTo> merchantList) {
        moreShopLayout.removeAllViews();
        if (merchantList == null || merchantList.size() == 0)
            return;
        selectList.addAll(merchantList);
        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.main_shop_item, null);
            MerchantInfoTo mode = merchantList.get(i);
            MainShopItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getShop_logo(), R.drawable.shop_defalut_icon);
            binding.merchantName.setText(mode.getShop_name());
            binding.typeName.setText(mode.getStype_txt());
            binding.orderNum.setText("成交" + mode.getOrder_num() + "比");
            binding.area.setText(mode.getArea());
            binding.serviceName.setText(mode.getService_exam());
            moreShopLayout.addView(mView);
            merchantViewList.add(mView);
            if (selectIdList.contains(mode.getId() + "")) {
                mode.setSelect(true);
                binding.selectIcon.setBackgroundResource(R.drawable.service_select);
                setSelectLayout();
            }
            binding.selectIcon.setOnClickListener(view -> {
                mode.setSelect(!mode.isSelect());
                binding.selectIcon.setBackgroundResource(mode.isSelect() ? R.drawable.service_select : R.drawable.service_un_select);
                if (!mode.isSelect()) {
                    selectIdList.remove(mode.getId() + "");
                }
                setSelectLayout();
            });
        }
    }

    private void setSelectLayout() {
        selectMerchantLayout.removeAllViews();
        selectMerchant = "[";
        selectMerchantName = "";
        List<MerchantInfoTo> merchantSelectList = new ArrayList<>();
        merchantSelectList.clear();
        for (int i = 0; i < selectList.size(); i++) {
            MerchantInfoTo mode = selectList.get(i);
            if (mode.isSelect()) {
                merchantSelectList.add(mode);
                if (!selectIdList.contains(mode.getId()))
                    selectIdList.add(mode.getId() + "");
            }
        }

        TagAdapter<MerchantInfoTo> tagAdapter = new TagAdapter<MerchantInfoTo>(merchantSelectList) {
            @Override
            public View getView(FlowLayout parent, int position, MerchantInfoTo mode) {
                View mView = View.inflate(appContext, R.layout.select_inquiry_item, null);
                ((TextView) mView.findViewById(R.id.name)).setText(mode.getShop_name());
                selectMerchant = selectMerchant + mode.getBus_uid() + ",";
                selectMerchantName = selectMerchantName + mode.getShop_name() + "  ";

                mView.findViewById(R.id.delete_icon).setOnClickListener(view -> {
                    merchantSelectList.remove(mode);
                    for (int i = 0; i < selectList.size(); i++) {
                        if (selectList.get(i).getId() == mode.getId()) {
                            selectList.get(i).setSelect(false);
                            selectIdList.remove(mode.getId() + "");
                            merchantViewList.get(i).findViewById(R.id.select_icon).setBackgroundResource(R.drawable.service_un_select);
                        }
                    }
                    mView.setVisibility(View.GONE);
                    selectNumber.setText(merchantSelectList.size() + "");

                });
                return mView;
            }
        };
        selectMerchantLayout.setAdapter(tagAdapter);

//        for (int i = 0; i < selectList.size(); i++) {
//            MerchantInfoTo mode = selectList.get(i);
//            if (mode.isSelect()) {
//                View mView = View.inflate(appContext, R.layout.select_inquiry_item, null);
//                ((TextView) mView.findViewById(R.id.name)).setText(mode.getShop_name());
//                mView.setTag(i);
//                selectMerchantLayout.addView(mView);
//                selectMerchant = selectMerchant + mode.getBus_uid() + ",";
//                selectMerchantName = selectMerchantName + mode.getShop_name() + "  ";
//
//                mView.findViewById(R.id.delete_icon).setOnClickListener(view -> {
//                    selectList.get((Integer) mView.getTag()).setSelect(false);
//                    merchantViewList.get((Integer) mView.getTag()).findViewById(R.id.select_icon).setBackgroundResource(R.drawable.service_un_select);
//                    selectMerchantLayout.removeView(mView);
//                    selectNumber.setText(selectMerchantLayout.getColumnCount() + "");
//                });
//            }
//        }
        selectNumber.setText(selectMerchantLayout.getChildCount() + "");
    }

    @OnClick({R.id.select_merchant, R.id.no_merchant, R.id.change_city, R.id.sort_layout, R.id.screen_layout, R.id.reset, R.id.screen_confirm,R.id.clean})
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
                intent = new Intent();

                intent.putExtra("SelectMerchant", "[]");
                intent.putExtra("SelectMerchantName", "不限");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.change_city:
                intent = new Intent(appContext, SelectProvinceActivity.class);
                startActivity(intent);
                break;
            case R.id.sort_layout:
//                sortPopWindow(popLayout);
                sortSelectLayout.setVisibility(View.VISIBLE);
                selectScreenLayout.setVisibility(View.GONE);

                setSortView();
                break;
            case R.id.screen_layout:
//                screenPopWindow(popLayout);
                selectScreenLayout.setVisibility(View.VISIBLE);
                sortSelectLayout.setVisibility(View.GONE);

                break;

            case R.id.reset:
                presenter.param.setOnum_range("");
                presenter.param.setBusiness_type(0);
                presenter.param.setArea_ids("");
                presenter.param.setRatings_range("");

                person.setBorderColorNormal(Color.parseColor("#999999"));
                person.setBackgroundColorNormal(Color.parseColor("#ffffff"));
                person.setTextColorNormal(Color.parseColor("#999999"));
                company.setBorderColorNormal(Color.parseColor("#999999"));
                company.setBackgroundColorNormal(Color.parseColor("#ffffff"));
                company.setTextColorNormal(Color.parseColor("#999999"));
                lastTag.setBackgroundColorNormal(Color.parseColor("#ffffff"));
                lastTag.setTextColorNormal(Color.parseColor("#999999"));
                lastTag.setBorderColorNormal(Color.parseColor("#999999"));
                orderNumText.setText("0单以上");
                scoreRank.setText("0分以上");
                orderNum.setProgress(0);
                score.setProgress(0);
                break;
            case R.id.screen_confirm:
                presenter.getMerchant();
                selectScreenLayout.setVisibility(View.GONE);
                break;
            case R.id.clean:
                Observable.from(selectList).subscribe(merchantInfoTo -> merchantInfoTo.setSelect(false));

                selectIdList.clear();
                presenter.getMerchant();
                setSelectLayout();
                break;
        }
    }

    @Subscribe
    public void selectCity(Event<String> event) {
        if ("SelectCity".equals(event.getType())) {
            String city = event.getData().replaceAll("市", "");
            changeCity.setText(city);
            presenter.param.setPos_city(presenter.getCityId(city));
            presenter.getMerchant();
            presenter.getCityList(presenter.param.getPos_city());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void screenPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_screen_layout, null);
        MerchantScreenLayoutBinding binding = DataBindingUtil.bind(contentView);
        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() - popLayout.getBottom() - popLayout.getHeight() / 2, true);
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
            presenter.getMerchant();
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
        lastTag = null;
        person.setOnClickListener(view -> {
            person.setBorderColorNormal(Color.parseColor("#3bafd9"));
            person.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
            person.setTextColorNormal(Color.parseColor("#ffffff"));
            company.setBorderColorNormal(Color.parseColor("#999999"));
            company.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            company.setTextColorNormal(Color.parseColor("#999999"));
            presenter.param.setBusiness_type(2);
        });

        company.setOnClickListener(view -> {
            company.setBorderColorNormal(Color.parseColor("#3bafd9"));
            company.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
            company.setTextColorNormal(Color.parseColor("#ffffff"));
            person.setBorderColorNormal(Color.parseColor("#999999"));
            person.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            person.setTextColorNormal(Color.parseColor("#999999"));
            presenter.param.setBusiness_type(1);
        });

        flowLayout.setMaxSelectCount(1);
        flowLayout.setAdapter(new TagAdapter<MerchantCityTo.ListsBean>(cityList) {
            @Override
            public View getView(FlowLayout parent, int position, MerchantCityTo.ListsBean cityTo) {
                View mView = View.inflate(appContext, R.layout.merchant_city_item, null);
                RTextView cityName = mView.findViewById(R.id.city_name);
                cityName.setText(cityTo.getArea());
                lastTag = cityName;
                return mView;
            }
        });
        flowLayout.setOnTagClickListener((view, position, parent) -> {
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

    private void sortPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_sort_layout, null);

        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() - popLayout.getBottom() - popLayout.getHeight() / 2, true);
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
            presenter.getMerchant();

            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text2).setOnClickListener(view -> {
            sortType = 2;
            presenter.param.setSort_type(2);
            presenter.getMerchant();
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text3).setOnClickListener(view -> {
            sortType = 3;
            presenter.param.setSort_type(3);
            presenter.getMerchant();
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text4).setOnClickListener(view -> {
            sortType = 4;
            presenter.param.setSort_type(4);
            presenter.getMerchant();
            window.dismiss();
        });
    }

    @Override
    public void onBackPressed() {

        if (sortSelectLayout.getVisibility() == View.VISIBLE) {
            sortSelectLayout.setVisibility(View.GONE);
            return;
        }
        if (selectScreenLayout.getVisibility() == View.VISIBLE) {
            selectScreenLayout.setVisibility(View.GONE);
            return;
        }
        finish();
        goToAnimation(2);
    }

    private void setSortView() {
        sortText1.setTextColor(sortType == 1 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        sortSelectIcon1.setVisibility(sortType == 1 ? View.VISIBLE : View.GONE);
        sortText2.setTextColor(sortType == 2 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        sortSelectIcon2.setVisibility(sortType == 2 ? View.VISIBLE : View.GONE);
        sortText3.setTextColor(sortType == 3 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        sortSelectIcon3.setVisibility(sortType == 3 ? View.VISIBLE : View.GONE);
//        sortText4.setTextColor(sortType==3? Color.parseColor("#3bafd9"):Color.parseColor("#000000"));
//        sortSelectIcon4.setVisibility(sortType==3?View.VISIBLE:View.GONE);
        sortText1.setOnClickListener(view -> {
            sortType = 1;
            presenter.param.setSort_type(1);
            presenter.getMerchant();

            sortSelectLayout.setVisibility(View.GONE);
        });
        sortText2.setOnClickListener(view -> {
            sortType = 2;
            presenter.param.setSort_type(2);
            presenter.getMerchant();
            sortSelectLayout.setVisibility(View.GONE);
        });
        sortText3.setOnClickListener(view -> {
            sortType = 3;
            presenter.param.setSort_type(3);
            presenter.getMerchant();
            sortSelectLayout.setVisibility(View.GONE);
        });
        sortText4.setOnClickListener(view -> {
            sortType = 4;
            presenter.param.setSort_type(4);
            presenter.getMerchant();
            sortSelectLayout.setVisibility(View.GONE);
        });
    }

    private void setSeekBar() {
        score.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                scoreRank.setText(progress / 20 + "分以上");
                presenter.param.setRatings_range(progress / 20 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        orderNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                orderNumText.setText(progress + "单以上");
                presenter.param.setOnum_range(progress / 20 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
