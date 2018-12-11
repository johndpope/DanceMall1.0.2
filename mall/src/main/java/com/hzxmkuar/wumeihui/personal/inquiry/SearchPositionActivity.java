package com.hzxmkuar.wumeihui.personal.inquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.hzxmkuar.wumeihui.personal.MorePositionActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SearchPositionAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/29.
 */

public class SearchPositionActivity extends BaseActivity {


    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.search)
    TextView search;

    private List<PoiInfo> poiList = new ArrayList<>();
    private List<PoiInfo> historyPoiList = new ArrayList<>();
    private PoiSearch poiSearch;
    private SearchPositionAdapter adapter;
    private PoiCitySearchOption poiCitySearchOption;
    private int count;
    private boolean isFirst=true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_position);
        ButterKnife.bind(this);


        setView();
        poiSearch = PoiSearch.newInstance();
        // 城市内检索
        poiCitySearchOption = new PoiCitySearchOption();
        // 关键字

        // 城市
        poiCitySearchOption.city(SpUtil.getString("LocateCity"));
        // 设置每页容量，默认为每页10条
        poiCitySearchOption.pageCapacity(10);
        // 分页编号
        poiCitySearchOption.pageNum(0);

        setSearch();
    }

    private void setSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if (isFirst){
                  isFirst=false;
                  poiCitySearchOption.keyword(charSequence + "");
                  poiSearch.searchInCity(poiCitySearchOption);

                  poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                      @Override
                      public void onGetPoiResult(PoiResult poiResult) {
                          poiList = poiResult.getAllPoi();
                          if (historyPoiList!=null){
                              for (int i=0;i<historyPoiList.size()&&i<5;i++)
                                  poiList.add(historyPoiList.get(i));
                          }
                          adapter.setList(poiList);
                          adapter.notifyDataSetChanged();
                      }

                      @Override
                      public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                      }

                      @Override
                      public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

                      }

                      @Override
                      public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                      }
                  });
              }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                poiCitySearchOption.keyword(charSequence + "");
                poiSearch.searchInCity(poiCitySearchOption);
                poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {
                        poiList = poiResult.getAllPoi();
                        if(poiList==null)
                            poiList= new ArrayList<>();
                        if (historyPoiList!=null){
                            for (int i=0;i<historyPoiList.size()&&i<5;i++)
                                poiList.add(historyPoiList.get(i));
                        }
                        adapter.setList(poiList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void setView() {
        count = 0;
        if (!TextUtils.isEmpty(SpUtil.getString("PoiHistory"))) {
            historyPoiList = new Gson().fromJson(SpUtil.getString("PoiHistory"), new TypeToken<List<PoiInfo>>() {
            }.getType());
        }

        adapter = new SearchPositionAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        if (historyPoiList!=null){
            for (int i=0;i<historyPoiList.size()&&i<5;i++)
                poiList.add(historyPoiList.get(i));
        }

        adapter.setList(poiList);
        View footView = View.inflate(appContext, R.layout.search_position_bottom_view, null);
        lRecyclerViewAdapter.addFooterView(footView);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setPullRefreshEnabled(false);

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent();
            intent.putExtra("PoiInfo", poiList.get(position).name);
            setResult(RESULT_OK, intent);
            for (int i = 0; i < historyPoiList.size(); i++) {
                if (historyPoiList.get(i).getName().equals(poiList.get(position).getName()) && historyPoiList.get(i).getAddress().equals(poiList.get(position).getAddress()))
                    count++;
            }
            if (count==0) {
                historyPoiList.add(poiList.get(position));
                SpUtil.put("PoiHistory", JSON.toJSONString(historyPoiList));
            }
            finish();
            goToAnimation(2);
        });

        footView.findViewById(R.id.clean_history).setOnClickListener(view -> SpUtil.put("PoiHistory", ""));
        footView.findViewById(R.id.more_history).setOnClickListener(view -> {
            Intent intent = new Intent(appContext, MorePositionActivity.class);
            startActivityForResult(intent, 30);
            goToAnimation(1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==30){
            if (data!=null){
                Intent intent = new Intent();
                intent.putExtra("PoiInfo", data.getStringExtra("PoiInfo"));
                setResult(RESULT_OK, intent);
                finish();
                goToAnimation(2);
            }
        }
    }
}
