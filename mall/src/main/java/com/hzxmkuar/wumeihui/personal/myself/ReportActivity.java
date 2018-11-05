package com.hzxmkuar.wumeihui.personal.myself;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.myself.presenter.ReportPresenter;
import com.ruffian.library.RTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/5.
 */

public class ReportActivity extends BaseActivity {
    @BindView(R.id.shop_name)
    EditText shopName;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.submit)
    RTextView submit;
    @BindView(R.id.content)
    EditText content;
    private ReportPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        setTitleName(Constant.REPORT_CENTER);
        presenter = new ReportPresenter(this);
        setView();
    }

    private void setView() {
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tip.setText(content.getText().toString().length() + "/100");
                submit.setBackgroundColorNormal(content.getText().toString().length()>0? Color.parseColor("#3FB9FF"):Color.parseColor("#d3d3d3"));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(shopName.getText().toString())) {
            showMessage("请输入店名");
            return;
        }
        if (TextUtils.isEmpty(content.getText().toString())) {
            showMessage("请输入举报内容");
            return;
        }
        presenter.report(content.getText().toString());

    }
}
