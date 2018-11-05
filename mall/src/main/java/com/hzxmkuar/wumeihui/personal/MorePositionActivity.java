package com.hzxmkuar.wumeihui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SearchPositionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/29.
 */

public class MorePositionActivity extends BaseActivity {


    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;


    private List<PoiInfo> poiList = new ArrayList<>();

    private SearchPositionAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_position);
        ButterKnife.bind(this);
setTitleName("历史记录");

        setView();

    }




    private void setView() {
    findViewById(R.id.search_layout).setVisibility(View.GONE);
       if (TextUtils.isEmpty(SpUtil.getString("PoiHistory"))){
           return;
       }
       poiList=new Gson().fromJson(SpUtil.getString("PoiHistory"),new TypeToken<List<PoiInfo>>(){}.getType());
        adapter = new SearchPositionAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        adapter.setList(poiList);
        recycleView.setAdapter(lRecyclerViewAdapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent();
            intent.putExtra("PoiInfo",poiList.get(position).name);
            setResult(RESULT_OK,intent);
            finish();
            goToAnimation(2);
        });


    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;

        }
    }


}
