package com.hzxmkuar.wumeihui.business.quote.presenter;

import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.QuoteApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.quote.QuoteDetailTo;
import hzxmkuar.com.applibrary.domain.quote.QuoteParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/25.
 */

public class QuoteDetailPresenter extends BasePresenter {

    public QuoteDetailPresenter(BaseActivity activity){
        initContext(activity);
        getQuoteDetail();
    }

    private void getQuoteDetail() {
        IdParam param=new IdParam();
        param.setId(activity.getIntent().getIntExtra("QuoteId",0));
        param.setHash(getHashString(IdParam.class,param));
        showLoadingDialog();
        ApiClient.create(QuoteApi.class).quoteDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<QuoteDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<QuoteDetailTo> msg) {
                        if (msg.getCode()==0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void quote(String money,int haveTicket){
      QuoteParam param=new QuoteParam();
      param.setId(activity.getIntent().getIntExtra("QuoteId",0));
      param.setIs_invoice(haveTicket);
      param.setOffer_amount(money);
      param.setHash(getHashString(QuoteParam.class,param));
      showLoadingDialog();
      ApiClient.create(QuoteApi.class).quote(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
              new MyObserver<MessageTo>(this) {
                  @Override
                  public void onNext(MessageTo msg) {
                      if (msg.getCode()==0)
                   submitDataSuccess(msg.getData());
                  }
              }
      );
  }

    public void cancelQuote() {
        IdParam param = new IdParam();
        param.setId(activity.getIntent().getIntExtra("QuoteId",0));
        param.setHash(getHashString(IdParam.class, param));
        showLoadingDialog();
        ApiClient.create(QuoteApi.class).cancelQuote(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                             new Handler().postDelayed(() -> activity.finish(),2500);
                            showMessage("放弃报价成功");
                        }
                    }
                }
        );
    }
}
