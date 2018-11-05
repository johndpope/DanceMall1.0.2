package com.hzxmkuar.wumeihui.business.myself;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.business.myself.presenter.MoneyManagerPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/18.
 */

public class DepositActivity extends BaseActivity {

    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.card_no)
    EditText cardNo;
    private MoneyManagerPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        setTitleName("提现");
        presenter = new MoneyManagerPresenter(this);
        cardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim().replace(" ", "");
                String result = "";
                if (str.length() >= 4) {
                    cardNo.removeTextChangedListener(this);
                    for (int i = 0; i < str.length(); i++) {
                        result += str.charAt(i);
                        if ((i + 1) % 4 == 0) {
                            result += " ";
                        }
                    }
                    if (result.endsWith(" ")) {
                        result = result.substring(0, result.length() - 1);
                    }
                    cardNo.setText(result);
                    cardNo.addTextChangedListener(this);
                    cardNo.setSelection(cardNo.getText().toString().length());//焦点到输入框最后位置
                }



            }
        });
        money.setText(TextUtils.isEmpty(getIntent().getStringExtra("OutMoney"))?"":getIntent().getStringExtra("OutMoney"));
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
       if (TextUtils.isEmpty(money.getText().toString())){
           showMessage("请填写提现金额");
           return;
       }
        if (TextUtils.isEmpty(name.getText().toString())){
            showMessage("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(cardNo.getText().toString())){
            showMessage("请填写银行卡号");
            return;
        }
        presenter.putForward(cardNo.getText().toString(),name.getText().toString(),Float.valueOf(money.getText().toString()),getIntent().getBooleanExtra("IsOut",false)?2:1);
    }
}
