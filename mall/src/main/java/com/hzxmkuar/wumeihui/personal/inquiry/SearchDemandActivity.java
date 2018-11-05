package com.hzxmkuar.wumeihui.personal.inquiry;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.util.SpUtil;

import com.hzxmkuar.wumeihui.databinding.MerchantScreenLayoutBinding;
import com.ruffian.library.RTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;

/**
 * Created by Administrator on 2018/8/29.
 */

public class SearchDemandActivity extends BaseActivity {
    @BindView(R.id.history_layout)
    TagFlowLayout historyLayout;
    @BindView(R.id.search)
    EditText search;
    private List<String> historyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_demand);
        ButterKnife.bind(this);
        setView();
        ActivityManager.searchDemandActivity=this;
    }

    private void setView() {
        historyList = new ArrayList<>();
        if (!TextUtils.isEmpty(SpUtil.getString("SearchServiceListCache")))
            historyList = new Gson().fromJson(SpUtil.getString("SearchServiceListCache"), new TypeToken<List<String>>() {
            }.getType());
        historyLayout.setAdapter(new TagAdapter<String>(historyList) {
            @Override
            public View getView(FlowLayout parent, int position, String name) {
                View mView = View.inflate(appContext, R.layout.demand_search_history_item, null);
                ((TextView) mView.findViewById(R.id.search_name)).setText(name);
                return mView;
            }
        });
        historyLayout.setOnTagClickListener((view, position, parent) -> {
            Intent intent = new Intent(appContext, SearchDetailActivity.class);
            intent.putExtra("Search",historyList.get(position));
            startActivity(intent);
            goToAnimation(1);
            return false;
        });
    }

    @OnClick({R.id.back, R.id.search_layout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.search_layout:

                break;
            case R.id.submit:
                if (TextUtils.isEmpty(search.getText().toString())) {
                    showMessage("请输入搜索内容");
                    return;
                }
                if (!historyList.contains(search.getText().toString()))
                 historyList.add(search.getText().toString());
                SpUtil.put("SearchServiceListCache",JSON.toJSONString(historyList));
                Intent intent = new Intent(appContext, SearchDetailActivity.class);
                intent.putExtra("Search",search.getText().toString());
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setView();
    }


}
