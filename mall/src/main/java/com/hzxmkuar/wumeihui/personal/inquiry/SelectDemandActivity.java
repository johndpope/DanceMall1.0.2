package com.hzxmkuar.wumeihui.personal.inquiry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SelectDemandLeftAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SelectDemandRightAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.SelectInquiryPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.inquery.InquiryListTo;
import hzxmkuar.com.applibrary.domain.inquery.InquiryTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantDetailTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/27.
 */

public class SelectDemandActivity extends BaseActivity {
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.select_layout)
    TagFlowLayout selectLayout;
    @BindView(R.id.select_number)
    TextView selectNumber;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private InquiryTo inquiryTo;
    private List<InquiryListTo> selectInquiryList = new ArrayList<>();
    private SelectDemandRightAdapter demandAdapter;
    private SelectInquiryPresenter presenter;
    private String serviceId;
    private String serviceIds;
    private int scrollY;
    private boolean isDown;
    private int lastScrollY;
    private List<InquiryListTo> menuList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityselectdemand);
        ButterKnife.bind(this);
        setTitleName(Constant.SELECT_DEMAND_TYPE);
        serviceIds = getIntent().getStringExtra("ServiceList");
        presenter = new SelectInquiryPresenter(this);
        ActivityManager.demandActivity = this;
        EventBus.getDefault().register(this);
        setSearch();
    }

    private void setSearch() {


    }

    private void setView() {
        menuList = inquiryTo.getLists();
        List<InquiryListTo> demandList = new ArrayList<>();


        SelectDemandLeftAdapter menuAdapter = new SelectDemandLeftAdapter(this);
        menuAdapter.setList(menuList);
        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        leftMenu.setAdapter(menuAdapter);
        for (int i = 0; i < menuList.size(); i++) {
//            demandList.addAll(menuList.get(i).getList2());
            for (InquiryListTo inquiryListTo : menuList.get(i).getList2()) {
                for (InquiryListTo inquiryListTo2 : inquiryListTo.getList3()) {
                    if (serviceIds != null && serviceIds.contains(inquiryListTo2.getId() + "")) {
                        selectInquiryList.add(inquiryListTo2);
                    }
                }
            }
        }
        setSelectLayout();
        demandAdapter = new SelectDemandRightAdapter(this, serviceIds);
        demandAdapter.setList(menuList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(demandAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    leftMenu.scrollToPosition(firstItemPosition + ((lastScrollY - scrollY > 0 && scrollY < 10) ? 0 : 1));
                    menuAdapter.setNameColor(firstItemPosition + ((lastScrollY - scrollY > 0 && scrollY < 10) ? 0 : 1));

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastScrollY = scrollY;
                scrollY = scrollY + dy;

                System.out.println(scrollY + "==========");
            }
        });

        menuAdapter.setOnItemClickListener((mode, position, view) -> {
            menuAdapter.setNameColor(position);
            recycleView.scrollToPosition(position);
        });

        demandAdapter.setInquiryListener(inquiryListTo -> {
            if (inquiryListTo.isSelect()) {
                selectInquiryList.add(inquiryListTo);
            } else {
                for (int i = 0; i < selectInquiryList.size(); i++)
                    if (selectInquiryList.get(i).getId() == inquiryListTo.getId())
                        selectInquiryList.remove(i);
            }
            setSelectLayout();

        });


    }

    @SuppressLint("SetTextI18n")
    private void setSelectLayout() {
        selectLayout.removeAllViews();
        selectNumber.setText(selectInquiryList.size() + "");
//        for (int i = 0; i < selectInquiryList.size(); i++) {
//            View mView = View.inflate(appContext, R.layout.select_inquiry_item, null);
//            ((TextView) mView.findViewById(R.id.name)).setText(selectInquiryList.get(i).getService_name());
//            selectLayout.addView(mView);
//            mView.setTag(i);
//            mView.findViewById(R.id.delete_icon).setOnClickListener(view -> {
//                selectLayout.removeView(mView);
//                selectInquiryList.get((Integer) mView.getTag()).setSelect(false);
//                selectInquiryList.remove(selectInquiryList.get((Integer) mView.getTag()));
//                demandAdapter.notifyDataSetChanged();
//            });
//        }
        selectLayout.setAdapter(new TagAdapter<InquiryListTo>(selectInquiryList) {
            @Override
            public View getView(FlowLayout parent, int position, InquiryListTo mode) {
                View mView = View.inflate(appContext, R.layout.select_inquiry_item, null);
                ((TextView) mView.findViewById(R.id.name)).setText(mode.getService_name());
                mView.setTag(mode);
                mView.findViewById(R.id.delete_icon).setOnClickListener(view -> {
                    mView.setVisibility(View.GONE);
                    ((InquiryListTo) mView.getTag()).setSelect(false);
                    selectInquiryList.remove(mView.getTag());
                    demandAdapter.notifyDataSetChanged();
                    selectNumber.setText(selectInquiryList.size() + "");
                });
                return mView;
            }

        });
    }


    @Override
    public void loadDataSuccess(Object data) {
        inquiryTo = (InquiryTo) data;
        setView();
    }

    @OnClick({R.id.search_layout, R.id.clean, R.id.submit, R.id.already_select_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_layout:
                Intent intent = new Intent(appContext, SearchDemandActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.clean:
                Observable.from(selectInquiryList).subscribe(inquiryListTo -> inquiryListTo.setSelect(false));
                selectInquiryList.clear();
                selectLayout.removeAllViews();
                demandAdapter.notifyDataSetChanged();
                selectNumber.setText("0");
                break;

            case R.id.submit:
                if (selectInquiryList.size() == 0) {
                    showMessage("请添加服务");
                    return;
                }
                serviceId = "";
                Observable.from(selectInquiryList).subscribe(inquiryListTo -> {
                    serviceId = serviceId + inquiryListTo.getId() + ",";
                });
                presenter.confirmInquiry(serviceId.substring(0, serviceId.length() - 1));
                break;
            case R.id.already_select_layout:

                if (selectLayout.getHeight()>220*getScreenWidth()/750) {
                    scrollView.setSelected(!scrollView.isSelected());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getScreenWidth(), scrollView.isSelected() ? ViewGroup.LayoutParams.WRAP_CONTENT : 130 * getScreenWidth() / 750);
                    scrollView.setLayoutParams(layoutParams);
                }
                break;
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {
        MerchantDetailTo merchantDetailTo = (MerchantDetailTo) getIntent().getSerializableExtra("MerchantDetailTo");
        Intent intent = new Intent(appContext, InquiryDesActivity.class);
        intent.putExtra("SelectInquiryList", (Serializable) selectInquiryList);

        intent.putExtra("MerchantDetailTo", merchantDetailTo);
        startActivity(intent);
        goToAnimation(1);

    }

    @Subscribe
    public void getSelectDemand(Event<DemandSearchTo.ListsBeanX.ListsBean> event) {
        String selectIdString = "";

        if ("SelectDemandTo".equals(event.getType())) {
            for (InquiryListTo inquiryListTo : selectInquiryList)
                selectIdString = selectIdString + inquiryListTo.getId() + ",";
            if (selectIdString.contains(event.getData().getId()))
                return;
            for (int i = 0; i < menuList.size(); i++) {

                for (InquiryListTo inquiryListTo : menuList.get(i).getList2()) {
                    for (InquiryListTo inquiryListTo2 : inquiryListTo.getList3()) {
                        if (inquiryListTo2.getId().equals(event.getData().getId())) {
                            inquiryListTo2.setSelect(true);

                            selectInquiryList.add(inquiryListTo2);
                        }
                    }
                }
            }
            demandAdapter.notifyDataSetChanged();
            setSelectLayout();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
