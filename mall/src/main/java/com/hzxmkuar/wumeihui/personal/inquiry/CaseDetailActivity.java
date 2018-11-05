package com.hzxmkuar.wumeihui.personal.inquiry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.util.GlideRoundTransform;
import com.hzxmkuar.wumeihui.business.merchant.presenter.CaseDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCaseTo;

/**
 * Created by Administrator on 2018/8/31.
 */

public class CaseDetailActivity extends BaseActivity {
    @BindView(R.id.case_image)
    ImageView caseImage;
    @BindView(R.id.content)
    TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        ButterKnife.bind(this);
        setTitleName(Constant.CASE_DETAIL);
        CaseDetailPresenter presenter=new CaseDetailPresenter(this);

    }

    @Override
    public void loadDataSuccess(Object data) {
        MerchantCaseTo.ListsBean mode = (MerchantCaseTo.ListsBean) data;
        Glide.with(appContext).load(mode.getCase_img()).transform(new GlideRoundTransform(appContext, 10)).into(caseImage);
        content.setText(mode.getCase_desc());
    }
}
