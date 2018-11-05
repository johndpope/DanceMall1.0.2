package com.hzxmkuar.wumeihui.personal.myself;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.myself.presenter.ModifyNickPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.user.NickNameTo;

/**
 * Created by Administrator on 2018/9/5.
 */

public class NickNameActivity extends BaseActivity {
    @BindView(R.id.nick_name)
    EditText nickName;
    private ModifyNickPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        ButterKnife.bind(this);
        setTitleName(Constant.NICK_NAME);
        presenter = new ModifyNickPresenter(this);
        nickName.setText(userInfoTo.getUsername());
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(nickName.getText().toString())) {
            showMessage("请填写昵称");
            return;
        }
        presenter.modifyNickName(nickName.getText().toString());

    }

    @Override
    public void loadDataSuccess(Object data) {
        Intent intent = new Intent(appContext, PersonSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         userInfoTo.setUsername(((NickNameTo)data).getUsername());
        userInfoHelp.saveUserInfo(userInfoTo);
        startActivity(intent);
        goToAnimation(1);

    }
}
