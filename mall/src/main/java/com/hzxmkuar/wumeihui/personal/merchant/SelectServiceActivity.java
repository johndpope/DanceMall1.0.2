package com.hzxmkuar.wumeihui.personal.merchant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;

import com.hzxmkuar.wumeihui.personal.inquiry.InquiryDesActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.SearchDemandActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SelectDemandLeftAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SelectDemandRightAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.SelectInquiryPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.inquery.InquiryListTo;
import hzxmkuar.com.applibrary.domain.inquery.InquiryTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/27.
 */

public class SelectServiceActivity extends BaseActivity {
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.select_layout)
    TagFlowLayout selectLayout;
    @BindView(R.id.select_number)
    TextView selectNumber;
    private InquiryTo inquiryTo;
    private List<InquiryListTo> selectInquiryList = new ArrayList<>();
    private SelectDemandRightAdapter demandAdapter;
    private SelectInquiryPresenter presenter;
    private String serviceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityselectdemand);
        ButterKnife.bind(this);
        setTitleName(Constant.SELECT_DEMAND_TYPE);

        presenter = new SelectInquiryPresenter(this);
        serviceId = getIntent().getStringExtra("ServiceId")==null?"":getIntent().getStringExtra("ServiceId");
    }

    private void setView() {
        List<InquiryListTo> menuList = inquiryTo.getLists();
        List<InquiryListTo> demandList = new ArrayList<>();

        SelectDemandLeftAdapter menuAdapter = new SelectDemandLeftAdapter(this);
        menuAdapter.setList(menuList);
        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        leftMenu.setAdapter(menuAdapter);
        for (int i = 0; i < menuList.size(); i++) {
            demandList.addAll(menuList.get(i).getList2());
            for (InquiryListTo inquiryListTo:menuList.get(i).getList2()){
                for (InquiryListTo inquiryListTo2:inquiryListTo.getList3()) {
                    if (serviceId != null && serviceId.contains(inquiryListTo2.getId() + "")) {
                        selectInquiryList.add(inquiryListTo2);
                    }
                }
            }
        }
        setSelectLayout();
        for (int i = 0; i < menuList.size(); i++)
            demandList.addAll(menuList.get(i).getList2());
        demandAdapter = new SelectDemandRightAdapter(this,serviceId,true);
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
                    leftMenu.scrollToPosition(firstItemPosition + 1);
                    menuAdapter.setNameColor(firstItemPosition + 1);

                }
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
            public View getView(FlowLayout parent, int position, InquiryListTo o) {
                View mView = View.inflate(appContext, R.layout.select_inquiry_item, null);
            ((TextView) mView.findViewById(R.id.name)).setText(selectInquiryList.get(position).getService_name());
            mView.setTag(position);
            mView.findViewById(R.id.delete_icon).setOnClickListener(view -> {
                selectLayout.removeView(mView);
                selectInquiryList.get((Integer) mView.getTag()).setSelect(false);
                selectInquiryList.remove(selectInquiryList.get((Integer) mView.getTag()));
                demandAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.search_layout, R.id.clean, R.id.submit})
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
                for (int i = 0; i < inquiryTo.getLists().size(); i++) {

                    for (InquiryListTo inquiryListTo:inquiryTo.getLists().get(i).getList2()){
                        for (InquiryListTo inquiryListTo2:inquiryListTo.getList3()) {
                            if (serviceId != null && serviceId.contains(inquiryListTo2.getId() + "")) {
                                selectInquiryList.add(inquiryListTo2);
                            }
                        }
                    }
                }

                setSelectLayout();
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
                Intent intent1 = new Intent();
                intent1.putExtra("ServiceId", serviceId);
                setResult(RESULT_OK, intent1);
                finish();
                break;
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {
        Intent intent = new Intent(appContext, InquiryDesActivity.class);
        intent.putExtra("SelectInquiryList", (Serializable) selectInquiryList);
        startActivity(intent);
        goToAnimation(1);
    }
}
