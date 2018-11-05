package com.hzxmkuar.wumeihui.personal.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.ActivityManager;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.util.city.CityTo;
import com.hzxmkuar.wumeihui.base.util.city.ProvinceTo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by xzz on 2018/8/23.
 **/

public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.province_layout)
    GridLayout provinceLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        setTitleName(Constant.SETTING_AREA);

        setCity();
    }

    private void setCity() {
        List<CityTo>cityList=((ProvinceTo)getIntent().getSerializableExtra("ProvinceTo")).getCitys();
        List<View>selectViewList=new ArrayList<>();
        for (int i=0;i<cityList.size();i++){
            CityTo provinceTo=cityList.get(i);
            View mView=View.inflate(appContext,R.layout.select_city_item,null);
            ((TextView)mView.findViewById(R.id.name)).setText(provinceTo.getCitysName());
            selectViewList.add(mView.findViewById(R.id.position_select));
            provinceLayout.addView(mView);
            mView.setOnClickListener(v -> Observable.from(selectViewList).subscribe(view -> {
                EventBus.getDefault().post(new Event<>("SelectCity",provinceTo.getCitysName()));
                ActivityManager.selectProvinceActivity.finish();
                finish();
                goToAnimation(2);

            }));
        }
    }


}
