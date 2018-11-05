package com.hzxmkuar.wumeihui.business.merchant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.order.presenter.EvaluatePresenter;
import com.ruffian.library.RTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.MerchantOrderDetailTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/4.
 */

public class EvaluateActivity extends BaseActivity {
    @BindView(R.id.image_layout)
    GridLayout imageLayout;
    @BindView(R.id.evaluate_image_layout)
    GridLayout evaluateImageLayout;
    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.service_number)
    TextView serviceNumber;
    @BindView(R.id.rating1)
    RatingBar rating1;
    @BindView(R.id.rating2)
    RatingBar rating2;
    @BindView(R.id.rating3)
    RatingBar rating3;
    @BindView(R.id.rating4)
    RatingBar rating4;
    @BindView(R.id.submit)
    RTextView submit;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tip)
    TextView tip;
    private EvaluatePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        setTitleName(Constant.EVALUATE);
        presenter = new EvaluatePresenter(this);
        setContent();


    }

    private void setContent() {
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tip.setText(content.length()+"/155");
                if (content.getText().toString().length() > 0) {
                    submit.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
                    submit.setEnabled(true);
                } else {
                    submit.setBackgroundColorNormal(Color.parseColor("#999999"));
                    submit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(content.getText().toString())){
            showMessage("请填写评价内容");
            return;
        }
        presenter.addEvaluate(content.getText().toString(),"{\"overall_evaluation\":"+rating1.getRating()+",\"service_quality\":"+rating2.getRating()+",\"service_attitude\":"+rating3.getRating()+",\"service_effect\":"+rating4.getRating()+"}",imagePaths);
    }

    @Override
    public void loadDataSuccess(Object data) {
        MerchantOrderDetailTo mode = (MerchantOrderDetailTo) data;
        List<String> imageList = new ArrayList<>();

            Observable.from(mode.getService_list()).subscribe(serviceListBean -> imageList.add(serviceListBean.getService_img()));
        serviceNumber.setText("共  " + mode.getService_num() + "  项服务");
        serviceName.setText(mode.getBusiness_info().getShop_name());
        setImageLayout(imageLayout,imageList,  120);
        setPostImageLayout(evaluateImageLayout, 120);

    }
}
