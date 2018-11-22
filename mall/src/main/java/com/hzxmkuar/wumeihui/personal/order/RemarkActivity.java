package com.hzxmkuar.wumeihui.personal.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.RemarkTo;

/**
 * Created by Administrator on 2018/9/29.
 */

public class RemarkActivity extends BaseActivity {
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.type_view1)
    View typeView1;
    @BindView(R.id.type_view2)
    View typeView2;
    @BindView(R.id.type_view3)
    View typeView3;
    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_no)
    EditText companyNo;
private int type;
    private RemarkTo remarkTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);
        ButterKnife.bind(this);
        setTitleName("备注");
        remarkTo = (RemarkTo) getIntent().getSerializableExtra("RemarkTo");

       setView();
    }

    private void setView() {
        if (remarkTo!=null){
            if (remarkTo.getInvoice()!=null){
                type=remarkTo.getInvoice().getType();
                typeView1.setBackgroundResource(remarkTo.getInvoice().getType()==1?R.drawable.position_select:R.drawable.position_un_select);
                typeView2.setBackgroundResource(remarkTo.getInvoice().getType()==2?R.drawable.position_select:R.drawable.position_un_select);
                typeView3.setBackgroundResource(remarkTo.getInvoice().getType()==3?R.drawable.position_select:R.drawable.position_un_select);
                companyName.setText(TextUtils.isEmpty(remarkTo.getInvoice().getInvoice_title())?"":remarkTo.getInvoice().getInvoice_title());
                companyNo.setText(TextUtils.isEmpty(remarkTo.getInvoice().getTaxpayer_no())?"":remarkTo.getInvoice().getTaxpayer_no());
            }
            remark.setText(TextUtils.isEmpty(remarkTo.getNote())?"":remarkTo.getNote());

            if (getIntent().getBooleanExtra("IsOrderEnter",false)){
                companyName.setCursorVisible(false);
                companyName.setFocusable(false);
                companyName.setFocusableInTouchMode(false);
                companyNo.setCursorVisible(false);
                companyNo.setFocusable(false);
                companyNo.setFocusableInTouchMode(false);
                remark.setCursorVisible(false);
                remark.setFocusable(false);
                remark.setFocusableInTouchMode(false);
            }
        }
    }

    @OnClick()
    public void onViewClicked() {

    }

    @OnClick({R.id.company, R.id.person, R.id.no_ticket,R.id.submit,R.id.type_view1,R.id.type_view2,R.id.type_view3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company:
            case R.id.type_view1:
                type=1;
                typeView1.setBackgroundResource(R.drawable.position_select);
                typeView2.setBackgroundResource(R.drawable.position_un_select);
                typeView3.setBackgroundResource(R.drawable.position_un_select);
                break;
            case R.id.person:
            case R.id.type_view2:
                type=2;
                typeView2.setBackgroundResource(R.drawable.position_select);
                typeView1.setBackgroundResource(R.drawable.position_un_select);
                typeView3.setBackgroundResource(R.drawable.position_un_select);
                break;
            case R.id.no_ticket:
            case R.id.type_view3:
                type=3;
                typeView3.setBackgroundResource(R.drawable.position_select);
                typeView2.setBackgroundResource(R.drawable.position_un_select);
                typeView1.setBackgroundResource(R.drawable.position_un_select);
                break;
            case R.id.submit:
                if (type==0){
                    showMessage("请选择发票类型");
                    return;
                }
                if (type!=3&&TextUtils.isEmpty(companyName.getText().toString())){
                    showMessage("请填写发票抬头");
                    return;
                }
                if (type!=3&&TextUtils.isEmpty(companyNo.getText().toString())){
                    showMessage("请填写纳税人识别号");
                    return;
                }

//                String remarkString = "{\"invoice\":{\"type\":" + type + ",\"invoice_title\":\""+ companyName.getText().toString()+ "\",\"taxpayer_no\":\""+companyNo.getText().toString()+ "\"},\"note\":\""+remark.getText().toString()+ "\"}";
               if (remarkTo==null)
                   remarkTo=new RemarkTo();
                RemarkTo.InvoiceTo invoice = remarkTo.getInvoice();
                if (invoice==null)
                    invoice=new RemarkTo.InvoiceTo();
                invoice.setTaxpayer_no(companyNo.getText().toString());
                invoice.setInvoice_title(companyName.getText().toString());
                invoice.setType(type);
                remarkTo.setInvoice(invoice);
                remarkTo.setNote(remark.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("RemarkTo", remarkTo);
                setResult(40, intent);
                finish();
                goToAnimation(2);
                break;
        }
    }
}
