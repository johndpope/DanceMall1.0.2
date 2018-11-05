package com.hzxmkuar.wumeihui.personal.inquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.ContactPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.inquery.ContactPeopleTo;

/**
 * Created by Administrator on 2018/8/29.
 **/

public class EditContactActivity extends BaseActivity {

    @BindView(R.id.contact_name)
    EditText contactName;
    @BindView(R.id.contact_phone)
    EditText contactPhone;
    private ContactPresenter presenter;
    private ContactPeopleTo.ListsBean contactTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.bind(this);
        setTitleName(Constant.EDIT_CONTACT);
        presenter = new ContactPresenter(this);
        setView();
    }

    private void setView() {
        contactTo = (ContactPeopleTo.ListsBean) getIntent().getSerializableExtra("ContactPeopleTo");
        if (contactTo != null) {
            contactName.setText(contactTo.getConsignee());
            contactPhone.setText(contactTo.getTelephone());
        }
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(contactName.getText().toString())) {
            showMessage("请填写联系人姓名");
            return;
        }
        if (!checkPhone(contactPhone.getText().toString()))
            return;
        if (contactTo == null)
            presenter.addContact(contactName.getText().toString(), contactPhone.getText().toString());
        else
            presenter.editContact(contactName.getText().toString(), contactPhone.getText().toString(), contactTo.getId());
    }

    @Override
    public void loadDataSuccess(Object data) {

        Intent intent = new Intent();
        setResult(10, intent);
        finish();
        goToAnimation(2);

    }
}
