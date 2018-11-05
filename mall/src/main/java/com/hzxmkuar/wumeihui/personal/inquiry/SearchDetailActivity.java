package com.hzxmkuar.wumeihui.personal.inquiry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.SearchDetailAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.SearchServicePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/29.
 **/

public class SearchDetailActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.search)
    EditText search;
    private SearchServicePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_demand_detail);
        ButterKnife.bind(this);
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
         ActivityManager.searchDetailActivity=this;
        presenter = new SearchServicePresenter(this);
        SearchDetailAdapter adapter = new SearchDetailAdapter(this);
        setRecycleView(adapter, recycleView, presenter);
        setSearch();
    }

    private void setSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               presenter.getSearchService(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick({R.id.back, R.id.search_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.search_layout:

                break;
        }
    }
}
