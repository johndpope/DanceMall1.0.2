package com.hzxmkuar.wumeihui.personal.myself;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.myself.presenter.SuggestPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/5.
 */

public class SuggestActivity extends BaseActivity {
    @BindView(R.id.content)
    EditText content;
    private SuggestPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        ButterKnife.bind(this);
        setTitleName(Constant.SUGGEST);
        presenter = new SuggestPresenter(this);

    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(content.getText().toString())){
            showMessage("请填写建议内容");
            return;
        }
        presenter.suggest(content.getText().toString());
    }
}
