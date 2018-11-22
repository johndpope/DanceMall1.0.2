package com.hzxmkuar.wumeihui.personal.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.business.merchant.MerchantDetailActivity;
import com.hzxmkuar.wumeihui.databinding.MainShopItemBinding;
import com.hzxmkuar.wumeihui.databinding.MerchantScreenLayoutBinding;
import com.hzxmkuar.wumeihui.personal.merchant.presenter.MerchantListPresenter;
import com.ruffian.library.RTextView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.main.MerchantInfoTo;
import hzxmkuar.com.applibrary.domain.main.MerchantTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;

/**
 * Created by Administrator on 2018/8/30.
 **/

public class MerchantListActivity extends BaseActivity {

    @BindView(R.id.carefully_select_layout)
    GridLayout carefullySelectLayout;
    @BindView(R.id.more_shop_layout)
    GridLayout moreShopLayout;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.sort_select_layout)
    AutoLinearLayout sortSelectLayout;
    @BindView(R.id.sort_text1)
    TextView sortText1;
    @BindView(R.id.sort_text2)
    TextView sortText2;
    @BindView(R.id.sort_select_icon_2)
    View sortSelectIcon2;
    @BindView(R.id.sort_text3)
    TextView sortText3;
    @BindView(R.id.sort_select_icon_3)
    View sortSelectIcon3;
    @BindView(R.id.sort_text4)
    TextView sortText4;
    @BindView(R.id.sort_select_icon_4)
    View sortSelectIcon4;
    @BindView(R.id.sort_select_icon_1)
    View sortSelectIcon1;
    @BindView(R.id.screen_layout)
    AutoLinearLayout screenLayout;

    @BindView(R.id.select_screen_layout)
    AutoLinearLayout selectScreenLayout;
    @BindView(R.id.sort_left_layout)
    GridLayout sortLeftLayout;
    @BindView(R.id.sort_right_layout)
    GridLayout sortRightLayout;
    @BindView(R.id.type_layout)
    AutoLinearLayout typeLayout;
    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;
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
    @BindView(R.id.sort_text)
    TextView sortText;
    @BindView(R.id.pop_layout)
    AutoLinearLayout popLayout;
    private MerchantListPresenter presenter;
    private int sortType;
    private View lastView;
    private RTextView lastView1;
    private RTextView lastTag;
    private List<ServiceListTo.ListsBean> serviceList;
    private List<MerchantCityTo.ListsBean> cityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_list);
        ButterKnife.bind(this);
        setTitleName(Constant.MERCHANT_LIST);
        presenter = new MerchantListPresenter(this);
        setSearch();
        setSeekBar();

    }


    private void setSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.param.setKeyword(charSequence.toString());
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

        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.main_shop_item, null);
            MerchantInfoTo mode = merchantList.get(i);
            MainShopItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getShop_logo(), R.drawable.shop_defalut_icon);
            binding.merchantName.setText(mode.getShop_name());
            binding.typeName.setText(mode.getStype_txt());
            binding.orderNum.setText("成交" + mode.getOrder_num() + "笔");
            binding.area.setText(mode.getArea());
            binding.serviceName.setText(mode.getService_exam());
            carefullySelectLayout.addView(mView);
            binding.selectIcon.setVisibility(View.GONE);
            binding.starLayout.removeAllViews();
            for (int k=0;k<5;k++){
                View star=new View(appContext);
                GridLayout.LayoutParams starParam=new GridLayout.LayoutParams();
                starParam.width=30*getScreenWidth()/750;
                starParam.height=30*getScreenWidth()/750;
                starParam.setMarginEnd(10*getScreenWidth()/750);
                star.setLayoutParams(starParam);
                star.setBackgroundResource(((int)mode.getRatings())>=(k+1)?R.drawable.main_start:R.drawable.main_star_gray);
                binding.starLayout.addView(star);
            }
            mView.setOnClickListener(view -> {
                Intent intent = new Intent(appContext, MerchantDetailActivity.class);
                intent.putExtra("MerchantId", mode.getBus_uid());
                startActivity(intent);
                goToAnimation(1);
            });
        }

    }

    public void setMoreShopLayout(List<MerchantInfoTo> merchantList) {
        moreShopLayout.removeAllViews();
        if (merchantList == null || merchantList.size() == 0)
            return;

        for (int i = 0; i < merchantList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.main_shop_item, null);
            MerchantInfoTo mode = merchantList.get(i);
            MainShopItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getShop_logo(), R.drawable.shop_defalut_icon);
            binding.merchantName.setText(mode.getShop_name());
            binding.typeName.setText(mode.getStype_txt());
            binding.orderNum.setText("成交" + mode.getOrder_num() + "笔");
            binding.area.setText(mode.getArea());
            binding.serviceName.setText(mode.getService_exam());
            moreShopLayout.addView(mView);
            binding.starLayout.removeAllViews();
            for (int k=0;k<5;k++){
                View star=new View(appContext);
                GridLayout.LayoutParams starParam=new GridLayout.LayoutParams();
                starParam.width=30*getScreenWidth()/750;
                starParam.height=30*getScreenWidth()/750;
                starParam.setMarginEnd(10*getScreenWidth()/750);
                star.setLayoutParams(starParam);
                star.setBackgroundResource(((int)mode.getRatings())>=(i+1)?R.drawable.main_start:R.drawable.main_star_gray);
                binding.starLayout.addView(star);
            }
            binding.selectIcon.setVisibility(View.GONE);
            mView.setOnClickListener(view -> {
                Intent intent = new Intent(appContext, MerchantDetailActivity.class);
                intent.putExtra("MerchantId", mode.getBus_uid());
                startActivity(intent);
                goToAnimation(1);
            });
        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        MerchantTo merchantTo = (MerchantTo) data;
        setCarefullyLayout(merchantTo.getLists().getChoiceness_merchant());
        setMoreShopLayout(merchantTo.getLists().getMore_merchant());
    }

    @OnClick({R.id.sort_layout, R.id.screen_layout, R.id.sort_text1, R.id.sort_text2, R.id.sort_text3, R.id.sort_text4, R.id.sort_select_layout, R.id.video_layout, R.id.person, R.id.company, R.id.reset, R.id.screen_confirm,R.id.select_screen_layout,R.id.type_layout,R.id.select_screen_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sort_layout:
//                sortPopWindow(popLayout);
                sortSelectLayout.setVisibility(View.VISIBLE);
                selectScreenLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.GONE);
                setSortView();
                break;
            case R.id.select_screen_layout:
                System.out.println();
                break;
            case R.id.screen_layout:
//                screenPopWindow(popLayout);
                selectScreenLayout.setVisibility(View.VISIBLE);
                sortSelectLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.GONE);
                break;
            case R.id.video_layout:
//                topicPopWindow(popLayout);
                typeLayout.setVisibility(View.VISIBLE);
                sortSelectLayout.setVisibility(View.GONE);
                selectScreenLayout.setVisibility(View.GONE);
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
          case  R.id.type_layout:
              System.out.println();
            break;


        }
    }

    private void sortPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_sort_layout, null);
//         showMessage(popLayout.getBottom()+"=="+popLayout.getY()+"=="+(popLayout.getY()+popLayout.getHeight()));
        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), (int) (getScreenHeight()-getScreenWidth()*0.38), true);
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

    private void topicPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.circle_topic_layout, null);

        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), (int) (getScreenHeight()-getScreenWidth()*0.38), true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0, 0);
        sortLeftLayout = contentView.findViewById(R.id.sort_left_layout);
        sortRightLayout = contentView.findViewById(R.id.sort_right_layout);


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
                        typeLayout.setVisibility(View.GONE);
                        presenter.param.setService_cate(value.getId());
                        sortText.setText(value.getService_name());
                        presenter.getMerchant();
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
                typeLayout.setVisibility(View.GONE);
                presenter.param.setService_cate(value.getId());
                sortText.setText(value.getService_name());
                presenter.getMerchant();
                window.dismiss();
            });
        }
    }

    private void screenPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_screen_layout_full, null);
        MerchantScreenLayoutBinding binding = DataBindingUtil.bind(contentView);
        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), (int) (getScreenHeight()-getScreenWidth()*0.38), true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0, 0);
        person = contentView.findViewById(R.id.person);
        binding.person.setOnClickListener(view -> {
            binding.person.setBorderColorNormal(Color.parseColor("#3bafd9"));
            binding.person.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
            binding.person.setTextColorNormal(Color.parseColor("#ffffff"));
            binding.company.setBorderColorNormal(Color.parseColor("#999999"));
            binding.company.setBackgroundColorNormal(Color.parseColor("#ffffff"));
            binding.company.setTextColorNormal(Color.parseColor("#999999"));
            presenter.param.setBusiness_type(2);
        });

        company = contentView.findViewById(R.id.company);
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
            view.setSelected(!view.isSelected());
            RTextView cityName = view.findViewById(R.id.city_name);
            if (!view.isSelected()){
                cityName.setBackgroundColorNormal(Color.parseColor("#ffffff"));
                cityName.setTextColorNormal(Color.parseColor("#999999"));
                cityName.setBorderColorNormal(Color.parseColor("#999999"));
            }else {
                cityName.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
                cityName.setTextColorNormal(Color.parseColor("#ffffff"));
                cityName.setBorderColorNormal(Color.parseColor("#3bafd9"));
            }
            String areaId="";
            for (int i=0;i<flowLayout.getChildCount();i++){
                if (flowLayout.getChildAt(i).isSelected())
                    areaId=areaId+cityList.get(i).getId()+",";
            }
            if (areaId.length()>0)
                presenter.param.setArea_ids(areaId.substring(0,areaId.length()-1));
            return false;
        });
    }

    public void setSort(List<ServiceListTo.ListsBean> serviceList) {
        this.serviceList = serviceList;
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
                        typeLayout.setVisibility(View.GONE);

                        presenter.param.setService_cate(value.getId());
                        presenter.getMerchant();
                        typeLayout.setVisibility(View.GONE);
                    });
                }
            });

        }
        for (ServiceListTo.ListsBean value : serviceList.get(0).getList2()) {
            View childView = View.inflate(appContext, R.layout.communication__sort_right_item, null);
            ((TextView) childView.findViewById(R.id.type_name)).setText(value.getService_name());
            sortRightLayout.addView(childView);
            childView.setOnClickListener(view -> {
                typeLayout.setVisibility(View.GONE);
                presenter.param.setService_cate(value.getId());
                presenter.getMerchant();
                typeLayout.setVisibility(View.GONE);
            });
        }
    }

    public void setCityLayout(List<MerchantCityTo.ListsBean> cityList) {
        this.cityList = cityList;
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

    @Override
    public void onBackPressed() {
        if (typeLayout.getVisibility()==View.VISIBLE){
            typeLayout.setVisibility(View.GONE);
            return;
        }
        if (sortSelectLayout.getVisibility()==View.VISIBLE){
            sortSelectLayout.setVisibility(View.GONE);
            return;
        }
        if (selectScreenLayout.getVisibility()==View.VISIBLE){
            selectScreenLayout.setVisibility(View.GONE);
            return;
        }
        finish();
        goToAnimation(2);
    }
}
