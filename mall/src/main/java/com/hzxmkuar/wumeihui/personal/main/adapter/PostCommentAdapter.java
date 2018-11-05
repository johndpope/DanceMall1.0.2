package com.hzxmkuar.wumeihui.personal.main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.PostCommentItemBinding;

import java.util.List;

import hzxmkuar.com.applibrary.domain.circle.PostDetailTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class PostCommentAdapter extends BaseAdapter<PostDetailTo.CommentsListBean, PostCommentItemBinding> {

    public PostCommentAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<PostCommentItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        PostCommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.post_comment_item, parent, false);

        BindingHolder<PostCommentItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<PostCommentItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        PostCommentItemBinding binding = holder.getBinding();
        PostDetailTo.CommentsListBean mode = mList.get(position);
        disPlayRoundImage(binding.headImage, mode.getUser_info().getHeadimgurl(),R.drawable.user_default_icon);
        binding.nickName.setText(mode.getUser_info().getUsername());
        binding.content.setText(mode.getContent() + "");
        binding.time.setText(mode.getDateline());
        setReplyLayout(mode.getList2(), binding.replyLayout);
       binding.content.setOnClickListener(view -> {
         if (listener!=null)
             listener.reply(mode);
       });

    }

    private void setReplyLayout(List<PostDetailTo.CommentsListBean.List2Bean> replyList, GridLayout replyLayout) {
       if (replyList==null||replyList.size()==0){
           replyLayout.setVisibility(View.GONE);
           return;
       }

       replyLayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < replyList.size(); i++) {
            View mView = View.inflate(mContext, R.layout.circle_reply_item, null);
            Spannable postContent=new SpannableString(replyList.get(i).getFrom_user().getUsername() + " 回复 " + replyList.get(i).getTo_user().getUsername()+" :  "+replyList.get(i).getContent());
            postContent.setSpan(new ForegroundColorSpan(Color.parseColor("#334455")), 0,replyList.get(i).getFrom_user().getUsername().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            postContent.setSpan(new ForegroundColorSpan(Color.parseColor("#334455")),replyList.get(i).getFrom_user().getUsername().length()+4,replyList.get(i).getFrom_user().getUsername().length()+4+replyList.get(i).getTo_user().getUsername().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((TextView) mView.findViewById(R.id.reply_content)).setText(postContent);
            replyLayout.addView(mView);

        }


    }

    public interface  ReplyListener{
        void reply(PostDetailTo.CommentsListBean commentTo);
    }

    public ReplyListener listener;

    public void setReplyListener(ReplyListener listener){
        this.listener=listener;
    }

}
