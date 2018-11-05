package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.ContactPeopleItemBinding;

import hzxmkuar.com.applibrary.domain.inquery.ContactPeopleTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class ContactPeopleAdapter extends BaseAdapter<ContactPeopleTo.ListsBean, ContactPeopleItemBinding> {
    public ContactPeopleAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<ContactPeopleItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        ContactPeopleItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_people_item, parent, false);

        BindingHolder<ContactPeopleItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<ContactPeopleItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        ContactPeopleTo.ListsBean mode = mList.get(position);
        ContactPeopleItemBinding binding = holder.getBinding();
        binding.name.setText(mode.getConsignee());
        binding.phone.setText(mode.getTelephone());
        binding.defaultIcon.setBackgroundResource("1".equals(mode.getIs_default())?R.drawable.service_select:R.drawable.position_un_select);
        binding.deleteLayout.setOnClickListener(view -> {
            if (listener!=null)
                listener.deleteContact(mode.getId());
        });
        binding.editLayout.setOnClickListener(view -> {
            if (listener!=null)
                listener.editContact(mode);
        });
        binding.setDefault.setOnClickListener(view -> {
            if (listener!=null)
                listener.defaultContact(mode.getId());
        });
    }

    public interface  ContactListener{
        void deleteContact(int id);
        void editContact(ContactPeopleTo.ListsBean id);
        void defaultContact(int id);
    }
    public  ContactListener listener;

    public void setContactListener(ContactListener listener){
        this.listener=listener;
    }
}
