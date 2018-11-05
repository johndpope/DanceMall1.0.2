package com.hzxmkuar.wumeihui.business.quote;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.myself.BondActivity;
import com.hzxmkuar.wumeihui.business.myself.DepositActivity;
import com.hzxmkuar.wumeihui.business.quote.adapter.QuoteDetailAdapter;
import com.hzxmkuar.wumeihui.business.quote.presenter.QuoteDetailPresenter;
import com.hzxmkuar.wumeihui.databinding.QuoteHeadViewBinding;
import com.hzxmkuar.wumeihui.message.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.quote.QuoteDetailTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/4.
 **/

public class QuoteDetailActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.cancel_quote)
    TextView cancelQuote;
    private QuoteDetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quote_detail);
        ButterKnife.bind(this);
        setTitleName(Constant.QUOTE_DETAIL);
        presenter = new QuoteDetailPresenter(this);


    }

    @Override
    public void loadDataSuccess(Object data) {
        QuoteDetailTo detailTo = (QuoteDetailTo) data;
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        QuoteDetailAdapter adapter = new QuoteDetailAdapter(this);
        adapter.setList(detailTo.getService_list());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        headView = View.inflate(appContext, R.layout.quote_head_view, null);
        QuoteHeadViewBinding binding = DataBindingUtil.bind(headView);
        binding.sn.setText(detailTo.getInquiry_sn());
        binding.statue.setText(detailTo.getStatus_arr().getStatus_txt());
        binding.address.setText(detailTo.getService_address());
        binding.useTime.setText(detailTo.getUse_time());
        binding.contact.setText(detailTo.getContact_name() + "  " + detailTo.getContact_telphone());
        lRecyclerViewAdapter.addHeaderView(headView);
        recycleView.setAdapter(lRecyclerViewAdapter);
        if (detailTo.getStatus_arr().getStatus() != 1) {
         submit.setVisibility(View.GONE);
        }
        cancelQuote.setVisibility(detailTo.getStatus_arr().getStatus()==1?View.VISIBLE:View.GONE);
        binding.phone.setOnClickListener(view -> {
            if (!AppUtil.readSIMCard(appContext))
                return;
            AlertDialog.show(this, "确认拨打电话").setOnClickListener(view1 -> {
                AlertDialog.dismiss();
                getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                    @Override
                    public void accept(String permission) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + detailTo.getContact_telphone());
                        intent.setData(data);
                        startActivity(intent);
                    }

                    @Override
                    public void refuse(String permission) {

                    }
                });
            });
        });

        binding.message.setOnClickListener(view -> {
            Intent intent1 = new Intent(appContext, ChatActivity.class);
            intent1.putExtra("UserId", detailTo.getContact_telphone());
            intent1.putExtra("Name",detailTo.getContact_name());
            startActivity(intent1);
            goToAnimation(1);
        });
        binding.countTimeLayout.setVisibility((detailTo.getStatus_arr().getStatus()==1||detailTo.getStatus_arr().getStatus()==2)?View.VISIBLE:View.GONE);
        binding.countTime.start(detailTo.getValid_time());


    }

    private void quoteDialog() {

        NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(this);
        dialog.setContentView(R.layout.dialog_quote);

        dialog.findViewById(R.id.have_ticket).setOnClickListener(view -> {
            dialog.findViewById(R.id.have_ticket).setSelected(true);
            dialog.findViewById(R.id.no_ticket).setSelected(false);
            dialog.findViewById(R.id.have_ticket).setBackgroundResource(R.drawable.position_select);
            dialog.findViewById(R.id.no_ticket).setBackgroundResource(R.drawable.position_un_select);
        });
        dialog.findViewById(R.id.no_ticket).setOnClickListener(view -> {
            dialog.findViewById(R.id.have_ticket).setSelected(false);
            dialog.findViewById(R.id.no_ticket).setSelected(true);
            dialog.findViewById(R.id.no_ticket).setBackgroundResource(R.drawable.position_select);
            dialog.findViewById(R.id.have_ticket).setBackgroundResource(R.drawable.position_un_select);
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.confirm).setOnClickListener(view -> {
            if (TextUtils.isEmpty(((EditText) dialog.findViewById(R.id.money)).getText().toString())) {
                showMessage("请填写报价金额");
                return;
            }
            if (!dialog.findViewById(R.id.no_ticket).isSelected() && !dialog.findViewById(R.id.have_ticket).isSelected()) {
                showMessage("请选择是否支持开票");
                return;
            }
            dialog.dismiss();
            presenter.quote(((EditText) dialog.findViewById(R.id.money)).getText().toString(), dialog.findViewById(R.id.have_ticket).isSelected() ? 1 : 2);

        });
        dialog.show();
    }

    @OnClick({R.id.submit,R.id.cancel_quote})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.submit:
                if (SpUtil.getBoolean("HaveBond"))
                quoteDialog();
                else {
                    AlertDialog.show(this,"请先缴纳保证金再进行报价").setOnClickListener(view1 -> {
                        AlertDialog.dismiss();
                        Intent intent=new Intent(appContext, BondActivity.class);
                        startActivity(intent);
                        goToAnimation(1);
                    });
                }
                break;
            case R.id.cancel_quote:
                AlertDialog.show(this,"放弃报价").setOnClickListener(view2 -> {
                    AlertDialog.dismiss();
                    presenter.cancelQuote();
                });
                break;
        }

    }

    @Override
    protected void submitDataSuccess(Object data) {
        showMessage("报价成功");
        new Handler().postDelayed(this::finish, 2000);
    }
}
