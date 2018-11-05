package com.hzxmkuar.wumeihui.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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

public class PoiSearchActivity extends BaseActivity {


    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.search)
    TextView search;

    private List<PoiInfo> poiList = new ArrayList<>();
    private PoiSearch poiSearch;
    private SearchPositionAdapter adapter;
    private PoiCitySearchOption poiCitySearchOption;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_position);
        ButterKnife.bind(this);


        setView();
        poiSearch = PoiSearch.newInstance();
        // 城市内检索
        poiCitySearchOption = new PoiCitySearchOption();
        poiCitySearchOption.mIsReturnAddr = true;
        // 关键字
        poiCitySearchOption.keyword(" ");
        // 城市
        poiCitySearchOption.city("杭州");
        // 设置每页容量，默认为每页10条
        poiCitySearchOption.pageCapacity(10);
        // 分页编号
        poiCitySearchOption.pageNum(0);

        setSearch();

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedLocationPoiList(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void setSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                poiCitySearchOption.keyword(charSequence + "");
                poiSearch.searchInCity(poiCitySearchOption);
                poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {

                        poiList = poiResult.getAllPoi();
                        PoiInfo poiInfoTop = new PoiInfo();
                        poiInfoTop.name="不显示位置";
                        if (poiList==null)
                            poiList= new ArrayList<>();
                        poiList.add(0,poiInfoTop);
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


        adapter = new SearchPositionAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        adapter.setList(poiList);
//        View footView = View.inflate(appContext, R.layout.search_position_bottom_view, null);
//        lRecyclerViewAdapter.addFooterView(footView);
        recycleView.setAdapter(lRecyclerViewAdapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent();
            intent.putExtra("PoiInfo", poiList.get(position).name);
            intent.putExtra("City",poiList.get(position).city);
            setResult(40, intent);
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

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取周边POI信息相关的结果
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            List<Poi> resultList = location.getPoiList();
            PoiInfo poiInfoTop = new PoiInfo();
            poiInfoTop.name="不显示位置";
            poiList.add(poiInfoTop);
            for (int i = 0; i < resultList.size(); i++) {
                PoiInfo poiInfo = new PoiInfo();
                poiInfo.name=resultList.get(i).getName();

                adapter.setList(poiList);
                poiList.add(poiInfo);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
