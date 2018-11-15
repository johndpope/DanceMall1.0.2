package com.hzxmkuar.wumeihui.personal.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hzxmkuar.wumeihui.ActivityManager;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.util.SideBar;
import com.hzxmkuar.wumeihui.base.util.city.CityJsonTo;
import com.hzxmkuar.wumeihui.base.util.city.ProvinceTo;
import com.hzxmkuar.wumeihui.personal.main.adapter.SelectProvinceAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by xzz on 2018/8/23.
 **/

public class SelectProvinceActivity extends BaseActivity {
    public LocationClient mLocationClient = null;
    @BindView(R.id.position_name)
    TextView positionName;

    @BindView(R.id.position_select)
    View positionSelect;
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    @BindView(R.id.select_dialog)
    TextView selectDialog;

    private MyLocationListener myListener = new MyLocationListener();
    private SelectProvinceAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_province);
        ButterKnife.bind(this);
        ActivityManager.selectProvinceActivity = this;
        setTitleName(Constant.SETTING_AREA);
        setLocate();
        setProvince();
    }

    private void setProvince() {
        List<ProvinceTo> provinceList = CityJsonTo.getProVinceList();
        String[] arr = new String[provinceList.size()];
        for (int i=0;i<provinceList.size();i++ ){
            arr[i]=provinceList.get(i).getProvinceName();
        }
        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
        Arrays.sort(arr, cmp);
        List<ProvinceTo>sortList=new ArrayList<>();

        for (String name:arr){
            for (ProvinceTo provinceTo:provinceList){
                if (name.equals(provinceTo.getProvinceName())){
                    sortList.add(provinceTo);
                }
            }
        }
        adapter = new SelectProvinceAdapter(this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        adapter.setList(sortList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(lRecyclerViewAdapter);
        showMessage(sortList+"");
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            if (sortList.get(position).getCitys() != null && sortList.get(position).getCitys().size() > 0) {
                Intent intent = new Intent(appContext, SelectCityActivity.class);
                intent.putExtra("ProvinceTo", sortList.get(position));
                startActivity(intent);
                goToAnimation(1);
            } else {
                EventBus.getDefault().post(new Event<>("SelectCity", positionName.getText().toString()));
                ActivityManager.selectProvinceActivity.finish();
                finish();
                goToAnimation(2);
            }
        });
        setSideBar();

    }

    private void setLocate() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @OnClick(R.id.locate_layout)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(positionName.getText().toString())) {
            EventBus.getDefault().post(new Event<>("SelectCity", positionName.getText().toString()));
            ActivityManager.selectProvinceActivity.finish();
            finish();
            goToAnimation(2);
        }
    }

    private class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
            positionName.setText(city);
        }
    }



    private void setSideBar() {
        sideBar.setTextView(selectDialog);
        sideBar.setOnTouchingLetterChangedListener(s -> {
            int position = adapter.getSectionForPosition(s.toUpperCase().charAt(0) + "");

            if (position != -1) {
                recycleView.smoothScrollToPosition(position);
            }

        });
    }


}
