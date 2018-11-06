package com.hzxmkuar.wumeihui.personal.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.banner.BannerMenuView;
import com.hzxmkuar.wumeihui.base.banner.BannerUtil;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.merchant.MerchantDetailActivity;
import com.hzxmkuar.wumeihui.circle.PostDetailActivity;
import com.hzxmkuar.wumeihui.databinding.MainShopItemBinding;
import com.hzxmkuar.wumeihui.databinding.MerchantScreenLayoutBinding;
import com.hzxmkuar.wumeihui.personal.inquiry.SelectDemandActivity;
import com.hzxmkuar.wumeihui.personal.main.SelectProvinceActivity;
import com.hzxmkuar.wumeihui.personal.main.presenter.ResourcePresenter;
import com.hzxmkuar.wumeihui.personal.order.MerchantListActivity;
import com.ruffian.library.RTextView;
import com.superluo.textbannerlibrary.TextBannerView;
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
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.main.MainBannerTo;
import hzxmkuar.com.applibrary.domain.main.MerchantInfoTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by xzz on 2018/8/15.
 **/

public class ResourceFragment extends BaseFragment {

    @BindView(R.id.carefully_select_layout)
    GridLayout carefullySelectLayout;
    @BindView(R.id.more_shop_layout)
    GridLayout moreShopLayout;
    Unbinder unbinder;
    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.menu_layout)
    ConvenientBanner menuLayout;
    @BindView(R.id.sort_text1)
    TextView sortText1;
    @BindView(R.id.sort_select_icon_1)
    View sortSelectIcon1;
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
    @BindView(R.id.sort_select_layout)
    public AutoLinearLayout sortSelectLayout;
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
    @BindView(R.id.screen_confirm)
    TextView screenConfirm;
    @BindView(R.id.select_screen_layout)
    public AutoLinearLayout selectScreenLayout;
    @BindView(R.id.sort_left_layout)
    GridLayout sortLeftLayout;
    @BindView(R.id.sort_right_layout)
    GridLayout sortRightLayout;
    @BindView(R.id.type_layout)
    public AutoLinearLayout typeLayout;
    @BindView(R.id.circle_title)
    TextBannerView circleTitle;
    @BindView(R.id.pop_layout)
    AutoLinearLayout popLayout;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.city_layout)
    AutoLinearLayout cityLayout;
    @BindView(R.id.down_icon)
    View downIcon;
    @BindView(R.id.search_layout)
    AutoLinearLayout searchLayout;
    @BindView(R.id.video_layout_top)
    AutoRelativeLayout videoLayoutTop;
    @BindView(R.id.sort_layout_top)
    AutoRelativeLayout sortLayoutTop;
    @BindView(R.id.screen_layout_top)
    AutoRelativeLayout screenLayoutTop;
    @BindView(R.id.pop_layout_top)
    AutoLinearLayout popLayoutTop;
    @BindView(R.id.main_city_layout)
    AutoLinearLayout mainCityLayout;
    @BindView(R.id.child_scroll)
    ScrollView childScroll;
    private int sortType;
    private View lastView;

    private RTextView lastTag;
    private ResourcePresenter presenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private List<ServiceListTo.ListsBean> serviceList;
    private List<MerchantCityTo.ListsBean> cityList;
    private int scrollY;
    private int dScrollY;

    private long lastTime;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(getContext(), R.layout.fragment_resource, null);
        unbinder = ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);


          presenter = new ResourcePresenter(this);
          setSeekBar();

          setLocate();
          setScroll();


        return mView;
    }

    private void setScroll() {

        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {

            scrollY = i1;
            if (scrollY > banner.getBottom()) {
                cityLayout.setBackgroundColor(Color.WHITE);
                cityName.setTextColor(Color.parseColor("#000000"));
                searchLayout.setBackgroundColor(Color.parseColor("#f7f7f7"));
                downIcon.setBackgroundResource(R.drawable.down_icon_black);

            } else {
                cityLayout.setBackgroundColor(Color.TRANSPARENT);
                cityName.setTextColor(Color.parseColor("#ffffff"));
                searchLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                downIcon.setBackgroundResource(R.drawable.main_bottom_icon);
            }
            if (scrollY > (popLayout.getTop() - cityLayout.getBottom())) {
                popLayoutTop.setVisibility(View.VISIBLE);
                mainCityLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                System.out.println(System.currentTimeMillis()-lastTime+"time");
                if (scrollY - (popLayout.getTop() - cityLayout.getBottom())>100&&(System.currentTimeMillis()-lastTime<1000)){
                    typeLayout.setVisibility(View.GONE);
                    selectScreenLayout.setVisibility(View.GONE);
                    sortSelectLayout.setVisibility(View.GONE);
                }

                lastTime=System.currentTimeMillis();

            } else {
                popLayoutTop.setVisibility(View.GONE);
                mainCityLayout.setBackgroundColor(Color.parseColor("#00000000"));
            }
        });

        scrollView.setOnTouchListener((view, motionEvent) -> {
                    switch (motionEvent.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            dScrollY= (int) motionEvent.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            System.out.println(motionEvent.getY()-dScrollY);

                            if ((selectScreenLayout.getVisibility()==View.VISIBLE||typeLayout.getVisibility()==View.VISIBLE||sortSelectLayout.getVisibility()==View.VISIBLE)&&motionEvent.getY()-dScrollY<0&&scrollY > (popLayout.getTop() - cityLayout.getBottom())) {
                                dScrollY= (int) motionEvent.getY();
                                return true;
                            }
                            dScrollY= (int) motionEvent.getY();
                            break;
                    }

                    return false;
                }
        );
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
            carefullySelectLayout.addView(mView);
            binding.selectIcon.setVisibility(View.GONE);
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
            moreShopLayout.addView(mView);
            mView.setOnClickListener(view -> {
                Intent intent = new Intent(appContext, MerchantDetailActivity.class);
                intent.putExtra("MerchantId", mode.getBus_uid());
                startActivity(intent);
                goToAnimation(1);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Subscribe
    public void selectCity(Event<String> event) {
        if ("SelectCity".equals(event.getType())) {
            String city = event.getData().replaceAll("市", "");
            city = city.replaceAll("特别行政区", "");
            cityName.setText(city);
            presenter.param.setPos_city(presenter.getCityId(city));
            presenter.getCityList(city);
            presenter.getMerchant();
            SpUtil.put("LocateCity", city);
            SpUtil.put("LocateCityId", presenter.param.getPos_city());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.city_name, R.id.inquiry,
            R.id.search_layout, R.id.sort_layout, R.id.screen_layout, R.id.sort_text1, R.id.sort_text2, R.id.sort_text3, R.id.sort_text4, R.id.sort_select_layout, R.id.video_layout, R.id.person, R.id.company, R.id.reset, R.id.screen_confirm,
            R.id.parent, R.id.sort_layout_top, R.id.screen_layout_top, R.id.video_layout_top,R.id.select_screen_layout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.parent:
                typeLayout.setVisibility(View.GONE);
                selectScreenLayout.setVisibility(View.GONE);
                sortSelectLayout.setVisibility(View.GONE);
                break;
            case R.id.select_screen_layout:
                System.out.println();
                break;
            case R.id.city_name:
                Intent intent = new Intent(appContext, SelectProvinceActivity.class);

                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.inquiry:
                intent = new Intent(appContext, SelectDemandActivity.class);

                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.search_layout:
                intent = new Intent(appContext, MerchantListActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.sort_layout:
            case R.id.sort_layout_top:
//                sortPopWindow(popLayout);
                sortSelectLayout.setVisibility(View.VISIBLE);
                selectScreenLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.GONE);
                setSortView();
                break;
            case R.id.screen_layout:
            case R.id.screen_layout_top:
//                screenPopWindow(view);

                selectScreenLayout.setVisibility(View.VISIBLE);
                sortSelectLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.GONE);
                break;
            case R.id.video_layout:
            case R.id.video_layout_top:
//                topicPopWindow(popLayout);
                typeLayout.setVisibility(View.VISIBLE);
                sortSelectLayout.setVisibility(View.GONE);
                selectScreenLayout.setVisibility(View.GONE);

                break;


            case R.id.person:

                break;
            case R.id.company:

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


        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        MainBannerTo bannerTo = (MainBannerTo) data;
        banner.startTurning(5000);
        BannerUtil.setBanner(banner, bannerTo.getIndex_slideshow(), 0);
        List<MainBannerTo.IndexMenuBean> index_menu = bannerTo.getIndex_menu();
        List<String> titleList = new ArrayList<>();
        Observable.from(bannerTo.getIndex_news_hot()).subscribe(indexNewsHotBean -> titleList.add(indexNewsHotBean.getNews_title()));
        circleTitle.setDatas(titleList);

        circleTitle.setItemOnClickListener((data1, position) -> {
            Intent intent = new Intent(appContext, PostDetailActivity.class);
            intent.putExtra("PostId", bannerTo.getIndex_news_hot().get(position).getId());
            startActivity(intent);
            goToAnimation(1);
        });
        setMenu(index_menu);
    }

    private void setMenu(List<MainBannerTo.IndexMenuBean> menuList) {
        List<List<MainBannerTo.IndexMenuBean>> mainMenuList = new ArrayList<>();
        List<MainBannerTo.IndexMenuBean> childMenuList = null;
        for (int i = 0; i < menuList.size(); i++) {
            if (i % 8 == 0) {
                childMenuList = new ArrayList<>();
                childMenuList.add(menuList.get(i));
                mainMenuList.add(childMenuList);
            } else {
                childMenuList.add(menuList.get(i));
            }

        }
//        if (menuList.size() > 8) {

        menuLayout.setPages(() -> new BannerMenuView(), mainMenuList).setPageIndicator(new int[]{R.drawable.page_indicate_un_focus, R.drawable.page_indicate_focus});
//        }
//        else
//            menuLayout.setPages(() -> new BannerMenuView(), mainMenuList);
//        for (int i = 0; i < menuList.size(); i++) {
//            MainBannerTo.IndexMenuBean menu = menuList.get(i);
//            View mView = View.inflate(appContext, R.layout.menu_layout, null);
//            ((TextView) mView.findViewById(R.id.menu_name)).setText(menu.getService_name());
//            displayImage(mView.findViewById(R.id.menu_image), menu.getService_img());
//            menuLayout.addView(mView);
//            mView.setOnClickListener(view -> {
//                Intent intent = new Intent(appContext, MerchantListActivity.class);
//                intent.putExtra("CateId", menu.getId());
//                startActivity(intent);
//                goToAnimation(1);
//            });
//        }
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
                        presenter.param.setSort_type(value.getId());
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
                presenter.param.setSort_type(value.getId());
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

    private class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();

            if (!getActivity().getIntent().getBooleanExtra("IsSplash",false)){
                cityName.setText(SpUtil.getString("LocateCity"));
                presenter.param.setPos_city(presenter.getCityId(SpUtil.getString("LocateCity")));
                presenter.getCityList(SpUtil.getString("LocateCity"));
                presenter.getMerchant();

            }else {
                if (!TextUtils.isEmpty(city)) {
                    city = city.replaceAll("市", "");
                    if (cityName != null)
                        cityName.setText(city);
                    presenter.param.setPos_city(presenter.getCityId(city));
                    presenter.getCityList(city);
                    presenter.getMerchant();

                    SpUtil.put("LocateCity", city);
                    SpUtil.put("LocateCityId", presenter.param.getPos_city());


                }
            }

        }
    }


    private void setLocate() {

        mLocationClient = new LocationClient(appContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        getPermission(Manifest.permission.ACCESS_FINE_LOCATION, new PermissionListener() {
            @Override
            public void accept(String permission) {
                new Thread(() -> presenter.initJson(true)).start();
            }

            @Override
            public void refuse(String permission) {
                new Thread(() -> presenter.initJson(false)).start();
            }
        });

    }

    private void sortPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_sort_layout, null);

        ((TextView) contentView.findViewById(R.id.sort_text1)).setTextColor(sortType == 1 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_1).setVisibility(sortType == 1 ? View.VISIBLE : View.GONE);
        ((TextView) contentView.findViewById(R.id.sort_text2)).setTextColor(sortType == 2 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_2).setVisibility(sortType == 2 ? View.VISIBLE : View.GONE);
        ((TextView) contentView.findViewById(R.id.sort_text3)).setTextColor(sortType == 3 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_3).setVisibility(sortType == 3 ? View.VISIBLE : View.GONE);
        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() + scrollY - popLayout.getBottom(), true);
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

        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() - popLayout.getBottom() + scrollY, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor);
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
                        presenter.param.setSort_type(value.getId());
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
                presenter.param.setSort_type(value.getId());
                presenter.getMerchant();
                window.dismiss();
            });
        }
    }

    private void screenPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.merchant_screen_layout, null);
        MerchantScreenLayoutBinding binding = DataBindingUtil.bind(contentView);
        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight() + scrollY - popLayout.getBottom(), true);
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

}
