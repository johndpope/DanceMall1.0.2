package com.hzxmkuar.wumeihui.personal.inquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.inquiry.adapter.ContactPeopleAdapter;
import com.hzxmkuar.wumeihui.personal.inquiry.presenter.ContactPresenter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.inquery.ContactPeopleTo;

/**
 * Created by Administrator on 2018/8/29.
 */

public class ContactPeopleActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    private ContactPresenter presenter;
    private ContactPeopleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contant_people);
        ButterKnife.bind(this);
        setTitleName(Constant.CONTACT_PEOPLE);
        presenter = new ContactPresenter(this);
        presenter.getContactList();
        adapter = new ContactPeopleAdapter(this);
        setRecycleView(adapter, recycleView, presenter);
        recycleView.setLoadMoreEnabled(false);
        setAdapterListener();

    }

    private void setAdapterListener() {
        adapter.setContactListener(new ContactPeopleAdapter.ContactListener() {
            @Override
            public void deleteContact(int id) {
                AlertDialog.show(ContactPeopleActivity.this, "删除联系方式").setOnClickListener(view -> {
                    AlertDialog.dismiss();
                    presenter.deleteContact(id);
                });
            }

            @Override
            public void editContact(ContactPeopleTo.ListsBean id) {
                Intent intent = new Intent(appContext, EditContactActivity.class);
                intent.putExtra("ContactPeopleTo", id);
                startActivityForResult(intent, 10);
                goToAnimation(1);
            }

            @Override
            public void defaultContact(int id) {
                AlertDialog.show(ContactPeopleActivity.this, "设置为默认").setOnClickListener(view -> {
                    AlertDialog.dismiss();
                    presenter.setDefault(id);
                });
            }
        });
    }


    @OnClick(R.id.submit)
    public void onViewClicked() {
        Intent intent = new Intent(appContext, EditContactActivity.class);
        startActivityForResult(intent, 10);
        goToAnimation(1);
    }

    @Override
    public void loadDataSuccess(Object data) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 10) {
            presenter.getContactList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void recycleItemClick(View view, int position, Object data) {
        Intent intent = new Intent();
        intent.putExtra("ContactInfo", ((ContactPeopleTo.ListsBean) data));
        setResult(RESULT_OK, intent);
        finish();
        goToAnimation(2);
    }
}
