
package com.hzxmkuar.wumeihui.business.myself.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.MyQuoteItemBinding;

import hzxmkuar.com.applibrary.domain.quote.MyQuoteTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class MyQuoteAdapter extends BaseAdapter<MyQuoteTo.ListsBean, MyQuoteItemBinding> {
    private String imagePath="";
    public MyQuoteAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<MyQuoteItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        MyQuoteItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_quote_item, parent, false);
        BindingHolder<MyQuoteItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<MyQuoteItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MyQuoteItemBinding binding = holder.getBinding();
        MyQuoteTo.ListsBean mode=mList.get(position);
        binding.inquirySn.setText(mode.getInquiry_sn());
        binding.statue.setText(mode.getStatus_arr().getStatus_txt());
        binding.serviceNum.setText("共"+ mode.getService_num() +"项服务");
        binding.countTime.start(mode.getValid_time()*1000);
        binding.quotePrice.setVisibility((TextUtils.isEmpty(mode.getOffer_amount())||"0".equals(mode.getOffer_amount()))?View.GONE:View.VISIBLE);
        binding.quotePrice.setText("￥"+mode.getOffer_amount());
        if (mode.getService_list()!=null) {
            imagePath="";
            Observable.from(mode.getService_list()).subscribe(serviceListBean -> imagePath=imagePath+serviceListBean.getService_img()+",");
            setImageLayout(imagePath.substring(0,imagePath.length()-1),binding.imageLayout);
        }
        binding.cancel.setVisibility(mode.getStatus_arr().getStatus()==1? View.VISIBLE:View.GONE);
        binding.confirm.setVisibility(mode.getStatus_arr().getStatus()==3?View.GONE:View.VISIBLE);
        binding.countTimeLayout.setVisibility((mode.getStatus_arr().getStatus()==1||mode.getStatus_arr().getStatus()==2)?View.VISIBLE:View.GONE);

       binding.cancel.setOnClickListener(view -> {
           if (listener!=null)
               listener.cancelClick(mode);
       });
    }

    public interface CancelClickListener{
        void cancelClick(MyQuoteTo.ListsBean mode);
    }
    public CancelClickListener listener;

    public void setCancelClickListener(CancelClickListener listener){
        this.listener=listener;
    }

}
